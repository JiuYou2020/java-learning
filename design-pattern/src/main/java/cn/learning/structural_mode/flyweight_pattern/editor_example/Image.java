package cn.learning.structural_mode.flyweight_pattern.editor_example;

// 图片类
class Image implements Multimedia {
    private String name;

    public Image(String name) {
        this.name = name;
    }

    @Override
    public void display(int x, int y) {
        System.out.println("Displaying image '" + name + "' at position (" + x + ", " + y + ")");
    }
}