package cn.learning.behavioral_mode.mediator_pattern.pain_example;

// 定义窗格接口或抽象类
abstract class Pane {
    protected Mediator mediator;

    public Pane(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void update();
}