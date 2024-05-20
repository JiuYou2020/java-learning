package cn.learning.behavioral_mode.memo_pattern.memo_example.memo;

/**
 * @author: jiuyou2020
 * @description: 负责人
 */
public class Caretaker {
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
