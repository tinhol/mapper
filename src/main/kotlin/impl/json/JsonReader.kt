package impl.json

import api.Reader
import api.Source
import api.SourceFactory
import com.jayway.jsonpath.JsonPath
import java.io.File

class JsonReader(val sourceFactory: SourceFactory<MutableMap<String, Any?>>) : Reader<JsonReadCommand> {
    override fun read(readCommand: JsonReadCommand): List<Source> {
        val file = readCommand.file ?: File(readCommand.path)
        val listOfMaps: List<MutableMap<String, Any?>> = JsonPath.read(file, readCommand.jsonPath)
        return listOfMaps.map { map -> sourceFactory.create(map) }
    }
}