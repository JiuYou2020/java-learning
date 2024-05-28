package cn.learning.behavioral_mode.visitor_pattern.oa_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        EmployeeList employeeList = new EmployeeList();
        Employee fte1, fte2, fte3, pte1, pte2;
        fte1 = new FullTimeEmployee("张无忌", 3200.00, 45);
        fte2 = new FullTimeEmployee("杨过", 2000.00, 40);
        fte3 = new FullTimeEmployee("段誉", 2400.00, 38);
        pte1 = new PartTimeEmployee("洪七公", 80.00, 20);
        pte2 = new PartTimeEmployee("郭靖", 60.00, 18);

        employeeList.addElement(fte1);
        employeeList.addElement(fte2);
        employeeList.addElement(fte3);
        employeeList.addElement(pte1);
        employeeList.addElement(pte2);

        Department fad = new FADepartment();
        Department hr = new HRDepartment();

        employeeList.accept(fad);
        System.out.println("=====================================");
        employeeList.accept(hr);
    }
}
