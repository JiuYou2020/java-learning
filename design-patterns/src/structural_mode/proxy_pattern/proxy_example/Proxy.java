package structural_mode.proxy_pattern.proxy_example;

/**
 * @author: jiuyou2020
 * @description: 代理类
 */
public class Proxy extends Subject {
    private RealSubject realSubject = new RealSubject();

    @Override
    public void request() {
        preRequest();
        realSubject.request();
        postRequest();
    }

    private void preRequest() {
        System.out.println("代理类的请求前处理");
    }

    private void postRequest() {
        System.out.println("代理类的请求后处理");
    }
}
