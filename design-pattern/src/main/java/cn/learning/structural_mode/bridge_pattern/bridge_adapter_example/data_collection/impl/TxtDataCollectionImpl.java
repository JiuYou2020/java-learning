package cn.learning.structural_mode.bridge_pattern.bridge_adapter_example.data_collection.impl;

import cn.learning.structural_mode.bridge_pattern.bridge_adapter_example.data_collection.DataCollection;

/**
 * @author: jiuyou2020
 * @description: 文本文件读取方式
 */
public class TxtDataCollectionImpl implements DataCollection {
    @Override
    public void collectData() {
        System.out.println("文本文件读取方式");
    }
}
