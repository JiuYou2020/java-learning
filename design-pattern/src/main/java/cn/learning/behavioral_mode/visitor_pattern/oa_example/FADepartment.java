package cn.learning.behavioral_mode.visitor_pattern.oa_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class FADepartment extends Department {
    @Override
    public void visit(FullTimeEmployee fullTimeEmployee) {
        int workTime = fullTimeEmployee.getWorkTime();
        double weeklyWage = fullTimeEmployee.getWeeklyWage();
        if (workTime > 40) {
            weeklyWage = weeklyWage + (workTime - 40) * 100;
        } else if (workTime < 40) {
            weeklyWage = weeklyWage - (40 - workTime) * 80;
            if (weeklyWage < 0) {
                weeklyWage = 0;
            }
        }
        System.out.println("正式员工" + fullTimeEmployee.getName() + "实际工资为：" + weeklyWage);
    }

    @Override
    public void visit(PartTimeEmployee partTimeEmployee) {
        int workTime = partTimeEmployee.getWorkTime();
        double hourWage = partTimeEmployee.getHourWage();
        System.out.println("临时员工" + partTimeEmployee.getName() + "实际工资为：" + workTime * hourWage);
    }
}
