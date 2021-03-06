package org.tinhol.mapper.impl.xlsx

import org.tinhol.mapper.api.ReadCommand
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage
import java.io.File
import java.io.InputStream

class XlsxReadCommand(
        override val inputStream: InputStream? = null,
        override val file: File? = null,
        val spreadsheetMLPackage: SpreadsheetMLPackage? = null,
        val sheet: Int? = 0,
        val area: String,
        override val repeat: Boolean = true) : ReadCommand() {

    constructor(spreadsheetMLPackage: SpreadsheetMLPackage,
                sheet: Int? = 0,
                area: String,
                repeat: Boolean = true) : this(null, null, spreadsheetMLPackage, sheet, area, repeat)

    constructor(inputStream: InputStream,
                sheet: Int? = 0,
                area: String,
                repeat: Boolean = true) : this(inputStream, null, null, sheet, area, repeat)

    constructor(file: File,
                sheet: Int? = 0,
                area: String,
                repeat: Boolean = true) : this(null, file, null, sheet, area, repeat)
}