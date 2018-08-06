import org.docx4j.openpackaging.packages.SpreadsheetMLPackage
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.tinhol.mapper.Main
import org.tinhol.mapper.api.Simple
import org.tinhol.mapper.api.Target
import org.tinhol.mapper.api.Trivial
import org.tinhol.mapper.impl.ClassTargetFactory
import org.tinhol.mapper.impl.MapTargetSourceFactory
import org.tinhol.mapper.impl.json.JsonReadCommand
import org.tinhol.mapper.impl.json.JsonReader
import org.tinhol.mapper.impl.xlsx.XlsxReadCommand
import org.tinhol.mapper.impl.xlsx.XlsxReader

class XlsxTest {
    private lateinit var spreadsheetMLPackage: SpreadsheetMLPackage

    @Before
    fun setUp() {
        spreadsheetMLPackage = SpreadsheetMLPackage.load(Device::class.java.getResourceAsStream("report.xlsx"))
    }

    @Test
    fun mapXlsxHorizontal() {
        val main = Main(XlsxReader(),
                listOf(
                        Simple("A4", "name"),
                        Simple("E4", "ipAddress"),
                        Simple("F4", "serialNumber")
                ), ClassTargetFactory<Device>(Device::class.java))

        val readCommand = XlsxReadCommand(area = "A4:H4", spreadsheetMLPackage = spreadsheetMLPackage)
        val result = main.process(readCommand)
        Assert.assertEquals(3, result.size)
        Assert.assertEquals("HP Color LaserJet CP5520", result[0].getContent().name)
        println(result)
    }

    @Test
    fun mapXlsxVerticalSingle() {
        val main = Main(XlsxReader(),
                listOf(
                        Simple("B2", "name"),
                        Simple("B4", "ipAddress"),
                        Simple("B3", "serialNumber")
                ), ClassTargetFactory<Device>(Device::class.java))

        val readCommand = XlsxReadCommand(area = "A2:B4", repeat = false, sheet = 1, spreadsheetMLPackage = spreadsheetMLPackage)
        val result = main.process(readCommand)
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("HP Color LaserJet CP5520", result[0].getContent().name)
        println(result)
    }

    @Test
    fun mapXlsxVerticalMultiple() {
        val main = Main(XlsxReader(),
                listOf(
                        Simple("B2", "name"),
                        Simple("B4", "ipAddress"),
                        Simple("B3", "serialNumber")
                ), ClassTargetFactory<Device>(Device::class.java))

        val readCommand = XlsxReadCommand(area = "A2:B5", repeat = true, sheet = 2, spreadsheetMLPackage = spreadsheetMLPackage)
        val result = main.process(readCommand)
        Assert.assertEquals(3, result.size)
        Assert.assertEquals("HP Color LaserJet CP5520", result[0].getContent().name)
        println(result)
    }
}