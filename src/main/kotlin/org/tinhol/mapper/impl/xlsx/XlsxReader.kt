package org.tinhol.mapper.impl.xlsx

import org.docx4j.openpackaging.packages.SpreadsheetMLPackage
import org.docx4j.openpackaging.parts.SpreadsheetML.SharedStrings
import org.tinhol.mapper.api.Reader
import org.tinhol.mapper.api.Source
import org.tinhol.mapper.api.SourceFactory
import org.tinhol.mapper.impl.CellReference
import org.tinhol.mapper.impl.MapTargetSourceFactory
import org.tinhol.mapper.impl.Range
import org.xlsx4j.sml.Cell
import org.xlsx4j.sml.Row
import org.xlsx4j.sml.STCellType

class XlsxReader(val sourceFactory: SourceFactory<MutableMap<String, Any?>>) : Reader<XlsxReadCommand> {
    constructor() : this(MapTargetSourceFactory())

    override fun read(readCommand: XlsxReadCommand): List<Source> {
        val spreadsheetMLPackage = when {
            readCommand.spreadsheetMLPackage != null -> readCommand.spreadsheetMLPackage
            readCommand.inputStream != null -> SpreadsheetMLPackage.load(readCommand.inputStream)
            readCommand.file != null -> SpreadsheetMLPackage.load(readCommand.file)
            else -> throw RuntimeException("Xxlsx sources are empty")
        }

        val workbookPart = spreadsheetMLPackage.workbookPart
        val worksheet = workbookPart.getWorksheet(readCommand.sheet!!)
        val sheetId = worksheet.sourceRelationships.filter { rel -> rel.type.endsWith("worksheet") }.map { rel -> rel.id }.first()
        val sheetName = workbookPart.contents.sheets.sheet.filter { sheet -> sheet.id == sheetId }.map { sheet -> sheet.name }.first()

        val sheetData = worksheet.contents.sheetData
        val range = Range.fromRange(sheetName, readCommand.area)

        val height = range.lastRow - range.firstRow + 1

        return sheetData.row
                .filter { row ->
                    if (readCommand.repeat) {
                        row.r >= range.firstRow
                    } else {
                        row.r >= range.firstRow && row.r <= range.lastRow
                    }
                }
                .groupBy { row ->
                    if (readCommand.repeat) {
                        (row.r - range.firstRow) / height
                    } else {
                        1
                    }
                }.values
                .map { rows ->
                    rows
                            .flatMap { row -> row.c }
                            .fold(mutableMapOf<String, Any?>(), { acc, cell ->
                                val parent = cell.parent as Row
                                val position = ((parent.r - range.firstRow) % height).toInt()
                                val cellReference = CellReference(sheetName, cell.r)
                                acc.put(cellReference.move(range.firstRow + position, cellReference.column).toReference(),
                                        cellValue(cell, workbookPart.sharedStrings))
                                acc
                            })
                }
                .map { map -> sourceFactory.create(map) }
    }


    private fun cellValue(cell: Cell, sharedStrings: SharedStrings): String? {
        if (cell.v == null) return null
        if (cell.t == STCellType.S) {
            val ctRst = sharedStrings.contents.si.get(Integer.parseInt(cell.v))
            var value: String? = null

            if (ctRst.t != null) {
                value = ctRst.t.value
            } else {
                if (ctRst.r != null) {
                    val stringBuilder = StringBuilder()
                    for (ctrElt in ctRst.r) {
                        if (ctrElt.t != null) {
                            stringBuilder.append(ctrElt.t.value)
                        }
                    }
                    value = stringBuilder.toString()
                }
            }
            return value
        } else {
            return cell.v
        }
    }
}