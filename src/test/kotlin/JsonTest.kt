import org.junit.Assert
import org.junit.Test
import org.tinhol.mapper.Main
import org.tinhol.mapper.api.Trivial
import org.tinhol.mapper.impl.ClassTargetFactory
import org.tinhol.mapper.impl.json.JsonReadCommand
import org.tinhol.mapper.impl.json.JsonReader

class JsonTest {
    @Test
    fun mapJson() {
        val main = Main(
            JsonReader(),
            listOf(
                Trivial("name"),
                Trivial("ipAddress"),
                Trivial("serialNumber")
            ), ClassTargetFactory(Device::class.java)
        )
        val readCommand = JsonReadCommand(
            inputStream = Device::class.java.getResourceAsStream("report.json"),
            jsonPath = "$.devices[*]"
        )
        val result = main.process(readCommand)
        Assert.assertEquals(3, result.size)
        Assert.assertEquals("HP Color LaserJet CP5520", result[0].getContent().name)
        println(result)
    }
}