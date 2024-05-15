package behavioral_mode.chain_of_responsibility_pattern.chain_of_responsibility_example;

/**
 * @author: jiuyou2020
 * @description: 具体处理者
 */
public class ConcreteHandlerA extends Handler {
    @Override
    public void handleRequest(int request) {
        if (request >= 0 && request < 10) {
            System.out.println("ConcreteHandlerA处理请求");
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
