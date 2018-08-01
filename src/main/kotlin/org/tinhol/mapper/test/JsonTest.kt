package org.tinhol.mapper.test

import org.tinhol.mapper.Main
import org.tinhol.mapper.api.Simple
import org.tinhol.mapper.api.Trivial
import org.tinhol.mapper.impl.ClassTargetFactory
import org.tinhol.mapper.impl.MapTargetSourceFactory
import org.tinhol.mapper.impl.json.JsonReadCommand
import org.tinhol.mapper.impl.json.JsonReader
import java.io.File

fun main(args: Array<String>) {
    val main = Main(JsonReader(MapTargetSourceFactory()),
            listOf(
                    Trivial("name"),
                    Trivial("ipAddress"),
                    Trivial("serialNumber")
            ), ClassTargetFactory(Device::class.java))

    val readCommand = JsonReadCommand(file = File("E:/personal/mapper/report.json"), jsonPath = "$.devices[*]")
    println(main.process(readCommand))
}