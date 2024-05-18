package cn.learning.structural_mode.combination_pattern.practice_example;


import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiuyou2020
 * @description: 容器构件
 */
public class MiddlePanel extends Component {
    private List<Component> list = new ArrayList<>();

    @Override
    public void add(Component component) {
        list.add(component);
    }

    @Override
    public void remove(Component component) {
        list.remove(component);
    }

    @Override
    public Component getChild(int i) {
        return list.get(i);
    }

    @Override
    public void operation() {
        System.out.println("容器构件的操作--->");
        for (Component component : list) {
            component.operation();
        }
        System.out.println("<---容器构件的操作");
    }
}
