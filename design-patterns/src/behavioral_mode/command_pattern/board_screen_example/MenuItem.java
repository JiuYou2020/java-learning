package behavioral_mode.command_pattern.board_screen_example;

// 菜单项类
class MenuItem {
    private String name;
    private Command command;

    public MenuItem(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    // 点击菜单项
    public void click() {
        command.execute();
    }
}