package structural_mode.proxy_pattern.proxy_example;

/**
 * @author: jiuyou2020
 * @description: 真实角色
 */
public class RealSubject extends Subject{
    @Override
    public void request() {
        System.out.println("真实角色的请求");
    }
}
