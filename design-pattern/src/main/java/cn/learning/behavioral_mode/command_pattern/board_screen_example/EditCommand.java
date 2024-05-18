package cn.learning.behavioral_mode.command_pattern.board_screen_example;

// 具体命令类：编辑命令
class EditCommand extends Command {


    public EditCommand(BoardScreen boardScreen) {
        super(boardScreen);
    }

    @Override
    public void execute() {
        boardScreen.edit();
    }
}