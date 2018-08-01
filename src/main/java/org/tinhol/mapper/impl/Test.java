package org.tinhol.mapper.impl;

import org.tinhol.mapper.api.Simple;
import org.tinhol.mapper.impl.ClassTargetFactory;
import org.tinhol.mapper.Main;
import org.tinhol.mapper.impl.MapTargetSourceFactory;
import org.tinhol.mapper.impl.xlsx.XlsxReadCommand;
import org.tinhol.mapper.impl.xlsx.XlsxReader;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.tinhol.mapper.test.Device;

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
