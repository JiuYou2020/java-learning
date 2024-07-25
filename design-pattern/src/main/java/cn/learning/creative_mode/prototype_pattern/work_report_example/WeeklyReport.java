package cn.learning.creative_mode.prototype_pattern.work_report_example;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/24 下午4:47
 */
public class WeeklyReport implements Cloneable {
    private String name;
    private String content;
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public WeeklyReport clone() {
        Object obj;
        try {
            obj = super.clone();
            return (WeeklyReport) obj;
        } catch (CloneNotSupportedException e) {
            System.out.println("不支持复制！");
            return null;
        }
    }
}

class Client {
    public static void main(String[] args) {
        WeeklyReport weeklyReport = new WeeklyReport();
        weeklyReport.setName("张三");
        weeklyReport.setContent("这周工作很忙");
        weeklyReport.setDate("2024-04-24");
        WeeklyReport clone = weeklyReport.clone();
        System.out.println(clone.getDate());
        System.out.println(clone.getName());
        System.out.println(clone.getContent());
        System.out.println(clone.getContent() == weeklyReport.getContent());
    }
}