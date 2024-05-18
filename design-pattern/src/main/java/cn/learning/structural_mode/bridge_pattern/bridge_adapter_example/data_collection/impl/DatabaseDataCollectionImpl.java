package cn.learning.structural_mode.bridge_pattern.bridge_adapter_example.data_collection.impl;

import cn.learning.structural_mode.bridge_pattern.bridge_adapter_example.data_collection.DataCollection;
import cn.learning.structural_mode.bridge_pattern.bridge_adapter_example.data_collection.DataCollection;

/**
 * @author: jiuyou2020
 * @description: 数据库读取方式
 */
public class DatabaseDataCollectionImpl implements DataCollection {
    @Override
    public void collectData() {
        System.out.println("数据库读取方式");
    }
}
