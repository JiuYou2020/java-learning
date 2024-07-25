package cn.southtang;

import lombok.Data;

@Data
public class Ext {
    private String name; // 只有一个name属性

    public void print() {
        System.out.println("Ext print()..." + name);
    }
}