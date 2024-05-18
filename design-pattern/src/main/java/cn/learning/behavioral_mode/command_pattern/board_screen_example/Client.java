package cn.learning.behavioral_mode.command_pattern.board_screen_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        // 创建接收者对象
        BoardScreen boardScreen = new BoardScreen();
        // 创建命令对象
        Command openCommand = new OpenCommand(boardScreen);
        Command createCommand = new CreateCommand(boardScreen);
        Command editCommand = new EditCommand(boardScreen);
        // 创建请求者对象
        MenuItem openMenuItem = new MenuItem("打开", openCommand);
        MenuItem createMenuItem = new MenuItem("新建", createCommand);
        MenuItem editMenuItem = new MenuItem("编辑", editCommand);
        // 请求者发起请求
        openMenuItem.click();
        createMenuItem.click();
        editMenuItem.click();
    }
}
