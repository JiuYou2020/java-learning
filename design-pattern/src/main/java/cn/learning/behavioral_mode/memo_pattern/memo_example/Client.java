package cn.learning.behavioral_mode.memo_pattern.memo_example;

import cn.learning.behavioral_mode.memo_pattern.memo_example.memo.Caretaker;
import cn.learning.behavioral_mode.memo_pattern.memo_example.memo.Originator;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        originator.setState("状态1");
        System.out.println("初始状态：" + originator.getState());

        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.createMemento());

        originator.setState("状态2");
        System.out.println("修改后的状态：" + originator.getState());

        originator.restoreMemento(caretaker.getMemento());
        System.out.println("恢复后的状态：" + originator.getState());
    }
}
