package structural_mode.decorative_pattern.decorative_example;

/**
 * @author: jiuyou2020
 * @description: 具体装饰类
 */
public class ConcreteDecorator extends Decorator {

    public ConcreteDecorator(Component component) {
        super(component);
    }

    public void operation() {
        super.operation();
        addedFunction();
    }

    private void addedFunction() {
        System.out.println("为具体构件角色增加额外的功能addedFunction()");
    }
}
