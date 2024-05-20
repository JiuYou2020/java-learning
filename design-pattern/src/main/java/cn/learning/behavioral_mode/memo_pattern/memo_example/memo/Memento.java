package cn.learning.behavioral_mode.memo_pattern.memo_example.memo;

/**
 * @author: jiuyou2020
 * @description: 备忘录
 */
class Memento {
    private String state;

    public Memento(Originator originator) {
        this.state = originator.getState();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
