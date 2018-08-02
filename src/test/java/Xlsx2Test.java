import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.junit.Assert;
import org.tinhol.mapper.Main;
import org.tinhol.mapper.api.Target;
import org.tinhol.mapper.impl.ClassTargetFactory;
import org.tinhol.mapper.impl.MapTargetSourceFactory;
import org.tinhol.mapper.impl.xlsx.XlsxReadCommand;
import org.tinhol.mapper.impl.xlsx.XlsxReader;

import java.util.Arrays;
import java.util.List;

import static org.tinhol.mapper.api.Mapping.simple;

public class Xlsx2Test {
    @org.junit.Test
    public void test() throws Docx4JException {
        SpreadsheetMLPackage spreadSheet = SpreadsheetMLPackage.load(getClass().getResourceAsStream("report.xlsx"));

        Main<XlsxReadCommand, Device> main = new Main<>(new XlsxReader(new MapTargetSourceFactory()),
                Arrays.asList(
                        simple("A4", "name"),
                        simple("E4", "ipAddress"),
                        simple("F4", "serialNumber")
                ), new ClassTargetFactory<>(Device.class));

        XlsxReadCommand readCommand = new XlsxReadCommand( null, null, spreadSheet, 0, "A4:H4", true);
        final List<Target<Device>> result = main.process(readCommand);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals("HP Color LaserJet CP5520", (result.get(0).getContent()).name);
        System.out.println((result));
    }
}
