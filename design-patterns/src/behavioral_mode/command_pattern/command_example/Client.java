package behavioral_mode.command_pattern.command_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Command commandA = new ConcreteCommandA();
        Invoker invokerA = new Invoker(commandA);
        invokerA.call();

        Command commandB = new ConcreteCommandB();
        Invoker invokerB = new Invoker(commandB);
        invokerB.call();
    }
}
