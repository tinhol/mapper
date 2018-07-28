package impl

import api.Reader
import api.Source
import api.SourceFactory
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage
import org.xlsx4j.sml.Row
import java.io.File
import CellReference

//todo support list of commands
class XlsxReader(val sourceFactory: SourceFactory<MutableMap<String, Any?>>) : Reader<XlsxReadCommand> {
    override fun read(readCommand: XlsxReadCommand): List<Source> {
        val spreadsheetMLPackage = SpreadsheetMLPackage.load(readCommand.file ?: File(readCommand.path))
        val workbookPart = spreadsheetMLPackage.workbookPart
        val worksheet = workbookPart.getWorksheet(readCommand.sheet!!)
        val sheetId = worksheet.sourceRelationships.filter { rel -> rel.type.endsWith("worksheet") }.map { rel -> rel.id }.first()
        val sheetName = workbookPart.contents.getSheets().getSheet().filter { sheet -> sheet.id == sheetId }.map { sheet -> sheet.name }.first()

        val sheetData = worksheet.contents.sheetData
        val range = Range.fromRange(sheetName, readCommand.area)

        val height = range.lastRow - range.firstRow + 1

        return sheetData.row
                .filter { row -> row.r >= range.firstRow }
                .groupBy { row -> (row.r - 1) / height }.values
                .map { rows ->
                    rows
                            .flatMap { row -> row.c }
                            .fold(mutableMapOf<String, Any?>(), { acc, cell ->
                                val parent = cell.parent as Row
                                val position = ((parent.r - 1) % height).toInt()
                                val cellReference = CellReference(sheetName, cell.r)
                                acc.put(cellReference.move(range.firstRow + position, cellReference.column).toReference(), cell.v)
                                acc
                            })
                }
                .map { map -> sourceFactory.create(map) }
    }


    fun readMap(path: String): Map<String, Any> {
        val file = File(path)
        return readMap(file)
    }

    fun readList(path: String): List<Map<String, Any>> {
        val file = File(path)
        return readList(file)
    }

    fun readMap(file: File): Map<String, Any> {
        return readList(file)
                .fold(mutableMapOf(), { acc, map ->
                    acc.putAll(map)
                    acc
                })
    }

    fun readList(file: File): List<Map<String, Any>> {
        val spreadsheetMLPackage = SpreadsheetMLPackage.load(file)
        val worksheet = spreadsheetMLPackage.workbookPart.getWorksheet(0)
        val sheetData = worksheet.contents.sheetData
        val row = sheetData.row
        return row.map({ r ->
            r.getC().fold(mutableMapOf<String, Any>(), { acc, cell ->
                acc.put(cell.r, cell.v)
                acc
            })
        })
    }
}