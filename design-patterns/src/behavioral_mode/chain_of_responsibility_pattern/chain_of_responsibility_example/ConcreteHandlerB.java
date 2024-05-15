package behavioral_mode.chain_of_responsibility_pattern.chain_of_responsibility_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class ConcreteHandlerB extends Handler {

    @Override
    public void handleRequest(int request) {
        if (request >= 10 && request < 20) {
            System.out.println("ConcreteHandlerB处理请求");
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
