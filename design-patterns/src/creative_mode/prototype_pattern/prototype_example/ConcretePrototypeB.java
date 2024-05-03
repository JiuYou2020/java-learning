package prototype_pattern.prototype_example;

/**
 * @author jiuyou2020
 * @description 原型类B
 * @date 2024/4/24 下午3:04
 */
public class ConcretePrototypeB extends Prototype {
    private String attr;//成员变量

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    @Override
    public Prototype clone() {
        ConcretePrototypeB concretePrototypeB = new ConcretePrototypeB();
        concretePrototypeB.setAttr(this.attr);
        return concretePrototypeB;
    }
}
