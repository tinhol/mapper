package org.tinhol.mapper.api

import java.io.File
import java.io.InputStream

open class ReadCommand(
        open val inputStream: InputStream? = null,
        open val file: File? = null,
        open val repeat: Boolean = true) {
}