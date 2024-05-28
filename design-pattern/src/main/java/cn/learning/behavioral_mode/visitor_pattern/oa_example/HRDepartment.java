package cn.learning.behavioral_mode.visitor_pattern.oa_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class HRDepartment extends Department {
    @Override
    public void visit(FullTimeEmployee fullTimeEmployee) {
        int workTime = fullTimeEmployee.getWorkTime();
        System.out.println("正式员工" + fullTimeEmployee.getName() + "实际工作时间为：" + workTime + "小时。");
        if (workTime > 40) {
            System.out.println("正式员工" + fullTimeEmployee.getName() + "加班时间为：" + (workTime - 40) + "小时。");
        } else if (workTime < 40) {
            System.out.println("正式员工" + fullTimeEmployee.getName() + "请假时间为：" + (40 - workTime) + "小时。");
        }
    }

    @Override
    public void visit(PartTimeEmployee partTimeEmployee) {
        int workTime = partTimeEmployee.getWorkTime();
        System.out.println("临时员工" + partTimeEmployee.getName() + "实际工作时间为：" + workTime + "小时。");
    }
}
