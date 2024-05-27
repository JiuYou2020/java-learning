package cn.jiuyou2020.springlearning.aop.aspectj;

/**
 * 添加虚拟机参数
 * -javaagent:/home/jiuyou2020/.m2/repository/org/aspectj/aspectjweaver/1.9.7/aspectjweaver-1.9.7.jar
 */
// Main.java
public class Main {
    public static void main(String[] args) {
        DemoService service = new DemoService();
        service.performTask();
    }
}
