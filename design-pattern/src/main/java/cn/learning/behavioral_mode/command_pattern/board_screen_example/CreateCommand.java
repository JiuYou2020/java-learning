package cn.learning.behavioral_mode.command_pattern.board_screen_example;

// 具体命令类：新建命令
class CreateCommand extends Command {

    public CreateCommand(BoardScreen boardScreen) {
        super(boardScreen);
    }

    @Override
    public void execute() {
        boardScreen.create();
    }
}