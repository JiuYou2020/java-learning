package cn.learning.behavioral_mode.visitor_pattern.oa_example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiuyou2020
 * @description:
 */
public class EmployeeList {
    private final List<Employee> elements;

    public EmployeeList() {
        elements = new ArrayList<>();
    }

    public void accept(Department visitor) {
        for (Employee element : elements) {
            element.accept(visitor);
        }
    }

    public void addElement(Employee element) {
        elements.add(element);
    }

    public void removeElement(Employee element) {
        elements.remove(element);
    }
}
