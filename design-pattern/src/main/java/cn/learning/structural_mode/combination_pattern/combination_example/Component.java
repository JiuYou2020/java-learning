package cn.learning.structural_mode.combination_pattern.combination_example;

/**
 * @author: jiuyou2020
 * @description: 抽象构件，既可以是叶子构件，也可以是容器构件
 */
public abstract class Component {
    public abstract void add(Component component);

    public abstract void remove(Component component);

    public abstract Component getChild(int i);

    public abstract void operation();
}
