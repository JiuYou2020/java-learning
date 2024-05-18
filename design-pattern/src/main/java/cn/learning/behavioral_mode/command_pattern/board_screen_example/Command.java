package cn.learning.behavioral_mode.command_pattern.board_screen_example;

// 抽象命令类
public abstract class Command {
    protected BoardScreen boardScreen;

    public Command(BoardScreen boardScreen) {
        this.boardScreen = boardScreen;
    }

    public abstract void execute();
}