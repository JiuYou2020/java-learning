package cn.learning.structural_mode.flyweight_pattern.editor_example;

public class Client {
    public static void main(String[] args) {
        DocumentEditor editor = new DocumentEditor();
        // 插入多媒体到文档中
        editor.insertMultimedia(1, "image", "image1.jpg");
        editor.insertMultimedia(2, "animation", "animation1.gif");
        editor.insertMultimedia(3, "image", "image1.jpg"); // 重复插入相同的图片
        editor.insertMultimedia(4, "video", "video1.mp4");

        // 显示文档内容
        editor.displayDocument();
    }
}