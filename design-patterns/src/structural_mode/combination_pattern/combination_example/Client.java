package structural_mode.combination_pattern.combination_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Component leaf = new Leaf();
        Component composite = new Composite();
        composite.add(leaf);
        Component leaf1 = new Leaf();
        composite.add(leaf1);

        Component composite1 = new Composite();
        Component leaf2 = new Leaf();
        composite1.add(leaf2);
        composite.add(composite1);

        composite.operation();
        leaf1.remove(leaf1);
    }
}
