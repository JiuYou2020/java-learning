package prototype_pattern.prototype_example;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/24 下午4:44
 */
public class Client {
    public static void main(String[] args) {
        Prototype prototypeA = new ConcretePrototypeA();
        prototypeA.clone();
        Prototype prototypeB = new ConcretePrototypeB();
        Prototype clone = prototypeB.clone();
        System.out.println(clone);
        System.out.println(prototypeB);
        System.out.println(clone==prototypeB);
    }
}
