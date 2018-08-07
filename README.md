# mapper

Kotlin-based simple library for mapping data from different sources (xlsx/xls/json/xml/etc) to pojos/entities

## Example 

Load XLSX file and map it to the Device pojo.

```java
SpreadsheetMLPackage spreadSheet = SpreadsheetMLPackage.load(getClass().getResourceAsStream("report.xlsx"));

Main<XlsxReadCommand, Device> main = mapping(new XlsxReader(),
                asList(
                        simple("A4", "name"),
                        simple("E4", "ipAddress"),
                        simple("F4", "serialNumber")
                ), new ClassTargetFactory<>(Device.class));

XlsxReadCommand readCommand = new XlsxReadCommand(spreadSheet, 0, "A4:H4", true);
final List<Device> result = main.process(readCommand).stream().map(Target::getContent).collect(Collectors.toList());
        
```
