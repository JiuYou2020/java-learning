package cn.learning.behavioral_mode.command_pattern.command_example;

/**
 * @author: jiuyou2020
 * @description: 具体命令A
 */
public class ConcreteCommandA extends Command {
    private ReceiverA receiver;

    public ConcreteCommandA() {
        receiver = new ReceiverA();
    }

    @Override
    public void execute() {
        receiver.action();
    }
}
