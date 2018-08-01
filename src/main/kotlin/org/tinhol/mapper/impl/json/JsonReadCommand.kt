package org.tinhol.mapper.impl.json

import org.tinhol.mapper.api.ReadCommand
import java.io.File

class JsonReadCommand(
        override val path: String? = null,
        val file: File? = null,
        val json: String? = null,
        val jsonPath: String? = null,
        override val repeat: Boolean = true
) : ReadCommand() {
}