package structural_mode.flyweight_pattern.editor_example;

// 动画类
class Animation implements Multimedia {
    private String name;

    public Animation(String name) {
        this.name = name;
    }

    @Override
    public void display(int x, int y) {
        System.out.println("Displaying animation '" + name + "' at position (" + x + ", " + y + ")");
    }
}