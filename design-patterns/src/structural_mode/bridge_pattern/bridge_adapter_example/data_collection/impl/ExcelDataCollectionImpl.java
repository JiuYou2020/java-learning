package structural_mode.bridge_pattern.bridge_adapter_example.data_collection.impl;

import structural_mode.bridge_pattern.bridge_adapter_example.ExcelApi;

/**
 * @author: jiuyou2020
 * @description: Excel读取方式, 同时也是数据采集接口的适配器，适配已有的Excel读取api
 */
public class ExcelDataCollectionImpl implements structural_mode.bridge_pattern.bridge_adapter_example.data_collection.DataCollection {
    private ExcelApi excelApi;

    public ExcelDataCollectionImpl(ExcelApi excelApi) {
        this.excelApi = excelApi;
    }

    @Override
    public void collectData() {
        //假设文件路径已从配置文件中读取
        excelApi.readExcel("D:/test.xlsx");
        System.out.println("Excel文件读取方式");
    }
}
