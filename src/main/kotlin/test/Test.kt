package test

import impl.XlsxReadCommand
import api.Simple
import impl.MapTargetSourceFactory
import impl.XlsxReader
import Main
import impl.ClassTargetFactory

fun main(args: Array<String>) {
    val main = Main(XlsxReader(MapTargetSourceFactory()),
            listOf(
                    Simple("A4", "name"),
                    Simple("E4", "ipAddress"),
                    Simple("F4", "serialNumber")
            ), ClassTargetFactory(Printer::class.java))

    val readCommand = XlsxReadCommand(path = "E:/personal/mapper/report.xlsx", area = "A4:H4")
    println(main.process(readCommand))
}