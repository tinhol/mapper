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

    constructor(inputStream: InputStream,
                jsonPath: String? = null,
                repeat: Boolean = true) : this(inputStream, null, null, jsonPath, repeat)

    constructor(file: File,
                jsonPath: String? = null,
                repeat: Boolean = true) : this(null, file, null, jsonPath, repeat)

    constructor(json: String,
                jsonPath: String? = null,
                repeat: Boolean = true) : this(null, null, json, jsonPath, repeat)
}