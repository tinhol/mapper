package impl

import CellReference
import Range
import api.Reader
import api.Source
import api.SourceFactory
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage
import org.docx4j.openpackaging.parts.SpreadsheetML.SharedStrings
import org.xlsx4j.sml.Cell
import org.xlsx4j.sml.Row
import org.xlsx4j.sml.STCellType
import java.io.File

class XlsxReader(val sourceFactory: SourceFactory<MutableMap<String, Any?>>) : Reader<XlsxReadCommand> {
    override fun read(readCommand: XlsxReadCommand): List<Source> {
        val spreadsheetMLPackage = readCommand.spreadsheetMLPackage ?: SpreadsheetMLPackage.load(readCommand.file
                ?: File(readCommand.path))
        val workbookPart = spreadsheetMLPackage.workbookPart
        val worksheet = workbookPart.getWorksheet(readCommand.sheet!!)
        val sheetId = worksheet.sourceRelationships.filter { rel -> rel.type.endsWith("worksheet") }.map { rel -> rel.id }.first()
        val sheetName = workbookPart.contents.getSheets().getSheet().filter { sheet -> sheet.id == sheetId }.map { sheet -> sheet.name }.first()

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


    fun cellValue(cell: Cell, sharedStrings: SharedStrings): String? {
        if (cell.v == null) return null
        if (cell.t == STCellType.S) {
            val ctRst = sharedStrings.contents.getSi().get(Integer.parseInt(cell.v))
            var value: String? = null

            if (ctRst.getT() != null) {
                value = ctRst.getT().getValue()
            } else {
                if (ctRst.getR() != null) {
                    val stringBuilder = StringBuilder()
                    for (ctrElt in ctRst.getR()) {
                        if (ctrElt.getT() != null) {
                            stringBuilder.append(ctrElt.getT().getValue())
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