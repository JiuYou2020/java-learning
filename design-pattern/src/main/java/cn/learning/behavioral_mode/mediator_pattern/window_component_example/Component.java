package cn.learning.behavioral_mode.mediator_pattern.window_component_example;

/**
 * @author: jiuyou2020
 * @description: 抽象组件类
 */
public abstract class Component {
    private Mediator mediator;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    //转发调用
    public void changed() {
        mediator.componentChanged(this);
    }

    public abstract void update();

}
