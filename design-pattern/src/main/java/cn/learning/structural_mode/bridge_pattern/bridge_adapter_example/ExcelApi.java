package cn.learning.structural_mode.bridge_pattern.bridge_adapter_example;

/**
 * @author: jiuyou2020
 * @description: 现有的Excel读取api
 */
public class ExcelApi {
    public void readExcel(String filePath){
        System.out.println("读取Excel"+filePath+"文件");
    }
}
