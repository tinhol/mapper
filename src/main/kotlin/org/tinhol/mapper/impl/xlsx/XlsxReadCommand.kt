package org.tinhol.mapper.impl.xlsx

import org.tinhol.mapper.api.ReadCommand
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage
import java.io.File

class XlsxReadCommand (
        override val path: String? = null,
        val file: File? = null,
        val spreadsheetMLPackage: SpreadsheetMLPackage? = null,
        val sheet: Int? = 0,
        val area: String,
        override val repeat: Boolean = true) : ReadCommand() {
}