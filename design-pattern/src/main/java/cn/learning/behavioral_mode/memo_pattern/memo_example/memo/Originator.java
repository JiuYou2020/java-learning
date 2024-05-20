package cn.learning.behavioral_mode.memo_pattern.memo_example.memo;

/**
 * @author: jiuyou2020
 * @description: 原发器
 */
public class Originator {
    private String state;

    public Originator() {
    }

    public Memento createMemento() {
        return new Memento(this);
    }

    public void restoreMemento(Memento memento) {
        this.state = memento.getState();
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
