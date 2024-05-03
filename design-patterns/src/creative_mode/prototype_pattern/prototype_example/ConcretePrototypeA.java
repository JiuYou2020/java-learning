package prototype_pattern.prototype_example;

/**
 * @author jiuyou2020
 * @description 原型类A
 * @date 2024/4/24 下午3:03
 */
public class ConcretePrototypeA extends Prototype {
    private String attr;//成员变量

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    @Override
    public Prototype clone() {
        ConcretePrototypeA concretePrototypeA = new ConcretePrototypeA();
        concretePrototypeA.setAttr(this.attr);
        return concretePrototypeA;
    }
}
