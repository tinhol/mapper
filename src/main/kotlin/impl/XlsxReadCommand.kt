package impl

import api.ReadCommand
import java.io.File

class XlsxReadCommand (
        override val path: String? = null,
        val file: File? = null,
        val sheet: Int? = 0,
        val area: String,
        override val repeat: Boolean = true) : ReadCommand() {
}