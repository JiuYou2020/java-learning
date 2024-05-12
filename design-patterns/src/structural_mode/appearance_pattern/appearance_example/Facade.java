package structural_mode.appearance_pattern.appearance_example;

/**
 * @author: jiuyou2020
 * @description: 外观类，负责与子系统和客户端进行交互
 */
public class Facade {
    private SubSystemA subSystemA = new SubSystemA();
    private SubSystemB subSystemB = new SubSystemB();
    private SubSystemC subSystemC = new SubSystemC();

    public void methodA() {
        subSystemA.methodA();
        subSystemB.methodB();
    }

    public void methodB() {
        subSystemB.methodB();
        subSystemC.methodC();
    }
}
