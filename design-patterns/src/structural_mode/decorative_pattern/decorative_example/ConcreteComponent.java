package structural_mode.decorative_pattern.decorative_example;

/**
 * @author: jiuyou2020
 * @description: 具体构件
 */
public class ConcreteComponent extends Component{
    @Override
    public void operation() {
        System.out.println("具体构件的操作");
    }
}
