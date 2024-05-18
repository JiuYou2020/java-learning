package cn.learning.behavioral_mode.command_pattern.command_example;

/**
 * @author: jiuyou2020
 * @description: 具体命令B
 */
public class ConcreteCommandB extends Command {
    private ReceiverB receiverB;

    public ConcreteCommandB() {
        receiverB = new ReceiverB();
    }

    @Override
    public void execute() {
        receiverB.action();
    }
}
