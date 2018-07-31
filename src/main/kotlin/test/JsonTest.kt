package test

import Main
import api.Simple
import impl.ClassTargetFactory
import impl.MapTargetSourceFactory
import impl.json.JsonReadCommand
import impl.json.JsonReader
import java.io.File

fun main(args: Array<String>) {
    val main = Main(JsonReader(MapTargetSourceFactory()),
            listOf(
                    Simple("name", "name"),
                    Simple("ipAddress", "ipAddress"),
                    Simple("serialNumber", "serialNumber")
            ), ClassTargetFactory(Device::class.java))

    val readCommand = JsonReadCommand(file = File("E:/personal/mapper/report.json"), jsonPath = "$.devices[*]")
    println(main.process(readCommand))
}