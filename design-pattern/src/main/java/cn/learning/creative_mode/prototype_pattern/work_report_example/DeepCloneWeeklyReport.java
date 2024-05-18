package cn.learning.creative_mode.prototype_pattern.work_report_example;

import java.io.*;

/**
 * @author jiuyou2020
 * @description 深克隆原型模式
 * @date 2024/4/24 下午4:52
 */
public class DeepCloneWeeklyReport implements Serializable {
    private String name;
    private String content;
    private String date;
    private Attachment attachment;

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

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

    public DeepCloneWeeklyReport deepClone() {
        DeepCloneWeeklyReport deepCloneWeeklyReport = new DeepCloneWeeklyReport();
        deepCloneWeeklyReport.setName(this.name);
        deepCloneWeeklyReport.setContent(this.content);
        deepCloneWeeklyReport.setDate(this.date);
        Attachment attachment = new Attachment();
        attachment.setName(this.attachment.getName());
        deepCloneWeeklyReport.setAttachment(attachment);
        return deepCloneWeeklyReport;
    }

    public DeepCloneWeeklyReport deepClone2() throws IOException, ClassNotFoundException {
        //将对象写入流中
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(this);

        //将对象从流中取出
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (DeepCloneWeeklyReport) objectInputStream.readObject();
    }
}

class Client2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DeepCloneWeeklyReport deepCloneWeeklyReport = new DeepCloneWeeklyReport();
        deepCloneWeeklyReport.setName("张三");
        deepCloneWeeklyReport.setContent("这周工作很忙");
        deepCloneWeeklyReport.setDate("2024-04-24");
        Attachment attachment = new Attachment();
        attachment.setName("附件");
        deepCloneWeeklyReport.setAttachment(attachment);

        DeepCloneWeeklyReport clone = deepCloneWeeklyReport.deepClone();
        System.out.println(clone.getAttachment() == deepCloneWeeklyReport.getAttachment());
        clone.getAttachment().download();

        DeepCloneWeeklyReport clone2 = deepCloneWeeklyReport.deepClone2();
        System.out.println(clone2.getAttachment() == deepCloneWeeklyReport.getAttachment());
        clone2.getAttachment().download();
    }
}

class Attachment implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void download() {
        System.out.println("下载附件，文件名为" + name);
    }
}
