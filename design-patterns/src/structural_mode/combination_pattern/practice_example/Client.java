package structural_mode.combination_pattern.practice_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Component leaf = new TextBox();
        Component composite = new Window();
        composite.add(leaf);
        Component leaf1 = new TextBox();
        composite.add(leaf1);

        Component composite1 = new Window();
        Component leaf2 = new TextBox();
        composite1.add(leaf2);
        composite.add(composite1);

        composite.operation();
        leaf1.remove(leaf1);
    }
}
