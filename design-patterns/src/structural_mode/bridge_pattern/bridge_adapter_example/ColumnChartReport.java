package structural_mode.bridge_pattern.bridge_adapter_example;

/**
 * @author: jiuyou2020
 * @description: 柱形图展示方式
 */
public class ColumnChartReport extends Report {
    @Override
    public void show() {
        dataCollection.collectData();
        System.out.println("柱形图展示方式");
    }
}
