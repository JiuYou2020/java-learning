package cn.jiuyou2020.springlearning.context.custom_registered_spring_beans;

import lombok.Data;

@Data
public class Ext {
    private String name; // 只有一个name属性

    public void print() {
        System.out.println("Ext print()..." + name);
    }
}