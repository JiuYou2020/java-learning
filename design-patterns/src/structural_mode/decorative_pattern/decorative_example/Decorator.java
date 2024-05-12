package structural_mode.decorative_pattern.decorative_example;

/**
 * @author: jiuyou2020
 * @description: 抽象装饰类
 */
public abstract class Decorator extends Component{
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
    }
}
