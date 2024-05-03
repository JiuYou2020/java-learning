package structural_mode.bridge_pattern.bridge_adapter_example;

import structural_mode.bridge_pattern.bridge_adapter_example.data_collection.DataCollection;
import structural_mode.bridge_pattern.bridge_adapter_example.data_collection.impl.ExcelDataCollectionImpl;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        // 创建数据采集对象
        DataCollection dataCollection = new ExcelDataCollectionImpl(new ExcelApi());
        // 创建报表对象
        Report report = new ColumnChartReport();
        // 设置数据采集对象
        report.setDataCollection(dataCollection);
        // 展示报表
        report.show();
    }
}
