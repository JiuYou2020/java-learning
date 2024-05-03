package structural_mode.bridge_pattern.bridge_adapter_example;

/**
 * @author: jiuyou2020
 * @description: 数据表展示方式
 */
public class DataSheetReport extends Report{
    @Override
    public void show() {
        dataCollection.collectData();
        System.out.println("数据表展示方式");
    }
}
