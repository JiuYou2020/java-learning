package cn.learning.behavioral_mode.command_pattern.command_example;

/**
 * @author: jiuyou2020
 * @description: 调用者
 */
public class Invoker {
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void call() {
        command.execute();
    }
}
