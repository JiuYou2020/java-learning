package behavioral_mode.command_pattern.board_screen_example;

import java.util.ArrayList;
import java.util.List;

// 菜单类
class Menu {
    private List<MenuItem> menuItems = new ArrayList<>();

    // 添加菜单项
    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
}