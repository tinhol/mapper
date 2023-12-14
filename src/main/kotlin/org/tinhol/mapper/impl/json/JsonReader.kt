package org.tinhol.mapper.impl.json

import com.jayway.jsonpath.JsonPath
import org.tinhol.mapper.api.Reader
import org.tinhol.mapper.api.Source
import org.tinhol.mapper.api.SourceFactory
import org.tinhol.mapper.impl.MapTargetSourceFactory

class JsonReader(val sourceFactory: SourceFactory<MutableMap<String, Any?>>) : Reader<JsonReadCommand> {
    constructor() : this(MapTargetSourceFactory()) {

    }

    override fun read(readCommand: JsonReadCommand): List<Source> {
        val listOfMaps = when {
            readCommand.json != null -> JsonPath.read(readCommand.json, readCommand.jsonPath)
            readCommand.inputStream != null -> JsonPath.read(readCommand.inputStream, readCommand.jsonPath)
            readCommand.file != null -> JsonPath.read<List<MutableMap<String, Any?>>>(
                readCommand.file,
                readCommand.jsonPath
            )

            else -> throw RuntimeException("Json sources are empty")
        }

        return listOfMaps.map { map ->
            sourceFactory.create(map)
        }
    }
}