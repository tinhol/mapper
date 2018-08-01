package org.tinhol.mapper.impl.json

import org.tinhol.mapper.api.Reader
import org.tinhol.mapper.api.Source
import org.tinhol.mapper.api.SourceFactory
import com.jayway.jsonpath.JsonPath
import java.io.File

class JsonReader(val sourceFactory: SourceFactory<MutableMap<String, Any?>>) : Reader<JsonReadCommand> {
    override fun read(readCommand: JsonReadCommand): List<Source> {
        val listOfMaps = if (readCommand.json != null) {
            JsonPath.read<List<MutableMap<String, Any?>>>(readCommand.json, readCommand.jsonPath)
        } else {
            JsonPath.read<List<MutableMap<String, Any?>>>(readCommand.file
                    ?: File(readCommand.path), readCommand.jsonPath)
        }
        return listOfMaps.map { map ->
            sourceFactory.create(map)
        }
    }
}