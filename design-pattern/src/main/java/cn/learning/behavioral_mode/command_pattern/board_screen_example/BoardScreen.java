package cn.learning.behavioral_mode.command_pattern.board_screen_example;

// 公告板系统界面类
class BoardScreen {
    public void open() {
        System.out.println("打开公告板系统界面");
    }

    public void create() {
        System.out.println("新建公告");
    }

    public void edit() {
        System.out.println("编辑公告");
    }
}