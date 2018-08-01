package org.tinhol.mapper.test

import org.tinhol.mapper.impl.xlsx.XlsxReadCommand
import org.tinhol.mapper.api.Simple
import org.tinhol.mapper.impl.MapTargetSourceFactory
import org.tinhol.mapper.impl.xlsx.XlsxReader
import org.tinhol.mapper.Main
import org.tinhol.mapper.impl.ClassTargetFactory
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage
import java.io.File

fun main(args: Array<String>) {
    val spreadSheet = SpreadsheetMLPackage.load(File("E:/personal/mapper/report.xlsx"))

    var main = Main(XlsxReader(MapTargetSourceFactory()),
            listOf(
                    Simple("A4", "name"),
                    Simple("E4", "ipAddress"),
                    Simple("F4", "serialNumber")
            ), ClassTargetFactory(Device::class.java))

    var readCommand = XlsxReadCommand(path = "E:/personal/mapper/report.xlsx", area = "A4:H4", spreadsheetMLPackage = spreadSheet)
    println(main.process(readCommand))

    main = Main(XlsxReader(MapTargetSourceFactory()),
            listOf(
                    Simple("B2", "name"),
                    Simple("B4", "ipAddress"),
                    Simple("B3", "serialNumber")
            ), ClassTargetFactory(Device::class.java))

    readCommand = XlsxReadCommand(path = "E:/personal/mapper/report.xlsx", area = "A2:B4", repeat = false, sheet = 1, spreadsheetMLPackage = spreadSheet)
    println(main.process(readCommand))

    main = Main(XlsxReader(MapTargetSourceFactory()),
            listOf(
                    Simple("B2", "name"),
                    Simple("B4", "ipAddress"),
                    Simple("B3", "serialNumber")
            ), ClassTargetFactory(Device::class.java))

    readCommand = XlsxReadCommand(path = "E:/personal/mapper/report.xlsx", area = "A2:B5", repeat = true, sheet = 2, spreadsheetMLPackage = spreadSheet)
    println(main.process(readCommand))
}