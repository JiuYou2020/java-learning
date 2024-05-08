package structural_mode.combination_pattern.practice_example;

/**
 * @author: jiuyou2020
 * @description: 叶子构件
 */
public class TextBox extends Component {
    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException("不支持add操作");
    }

    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException("不支持remove操作");
    }

    @Override
    public Component getChild(int i) {
        throw new UnsupportedOperationException("不支持getChild操作");
    }

    @Override
    public void operation() {
        System.out.println("叶子构件的操作");
    }
}
