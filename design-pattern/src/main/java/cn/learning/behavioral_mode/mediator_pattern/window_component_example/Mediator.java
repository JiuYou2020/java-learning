package cn.learning.behavioral_mode.mediator_pattern.window_component_example;

/**
 * @author: jiuyou2020
 * @description: 抽象中介者类
 */
public abstract class Mediator {
    public abstract void componentChanged(Component c);
}
