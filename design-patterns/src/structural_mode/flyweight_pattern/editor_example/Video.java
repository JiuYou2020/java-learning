package structural_mode.flyweight_pattern.editor_example;

// 视频类
class Video implements Multimedia {
    private String name;

    public Video(String name) {
        this.name = name;
    }

    @Override
    public void display(int x, int y) {
        System.out.println("Displaying video '" + name + "' at position (" + x + ", " + y + ")");
    }
}
