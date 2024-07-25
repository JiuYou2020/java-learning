package cn.southtang.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * 虚拟机参数
 * --add-opens java.base/java.lang=ALL-UNNAMED
 */
public class CGlibProxyDemo {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ProductService.class);
        enhancer.setCallback(new ProductServiceMethodInterceptor());

        ProductService proxyInstance = (ProductService) enhancer.create();
        proxyInstance.addProduct();
    }
}
