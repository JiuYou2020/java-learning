package cn.learning.behavioral_mode.visitor_pattern.visitor_example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiuyou2020
 * @description:
 */
public class ObjectStructure {
    private final List<Element> elements;

    public ObjectStructure() {
        elements = new ArrayList<>();
    }

    public void accept(Visitor visitor) {
        for (Element element : elements) {
            element.accept(visitor);
        }
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    public void removeElement(Element element) {
        elements.remove(element);
    }
}
