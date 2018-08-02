package org.tinhol.mapper.impl.json

import org.tinhol.mapper.api.ReadCommand
import java.io.File
import java.io.InputStream

class JsonReadCommand(
        override val inputStream: InputStream? = null,
        override val file: File? = null,
        val json: String? = null,
        val jsonPath: String? = null,
        override val repeat: Boolean = true
) : ReadCommand() {
}