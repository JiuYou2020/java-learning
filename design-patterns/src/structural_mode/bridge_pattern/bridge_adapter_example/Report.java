package structural_mode.bridge_pattern.bridge_adapter_example;

import structural_mode.bridge_pattern.bridge_adapter_example.data_collection.DataCollection;

/**
 * @author: jiuyou2020
 * @description: 报表基类
 */
public abstract class Report {
    /**
     * 数据采集接口
     */
    protected DataCollection dataCollection;

    public void setDataCollection(DataCollection dataCollection) {
        this.dataCollection = dataCollection;
    }

    /**
     * 展示报表
     */
    public abstract void show();

}
