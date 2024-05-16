package behavioral_mode.command_pattern.board_screen_example;

// 具体命令类：打开命令
class OpenCommand extends Command {


    public OpenCommand(BoardScreen boardScreen) {
        super(boardScreen);
    }

    @Override
    public void execute() {
        boardScreen.open();
    }
}