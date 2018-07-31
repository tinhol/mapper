import api.Simple;
import impl.ClassTargetFactory;
import impl.MapTargetSourceFactory;
import impl.XlsxReadCommand;
import impl.XlsxReader;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import test.Device;

import java.io.File;
import java.util.Arrays;

public class Test {
    @org.junit.Test
    public void test() throws Docx4JException {
        SpreadsheetMLPackage spreadSheet = SpreadsheetMLPackage.load(new File("E:/personal/mapper/report.xlsx"));

        Main<XlsxReadCommand> main = new Main<>(new XlsxReader(new MapTargetSourceFactory()),
                Arrays.asList(
                        new Simple("A4", "name"),
                        new Simple("E4", "ipAddress"),
                        new Simple("F4", "serialNumber")
                ), new ClassTargetFactory(Device.class));

        XlsxReadCommand readCommand = new XlsxReadCommand(null, null, spreadSheet, 0, "A4:H4", true);
        System.out.println(main.process(readCommand));
    }
}
