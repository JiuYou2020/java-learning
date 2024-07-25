package cn.learning.behavioral_mode.interpreter_pattern.string_command_example;

/**
 * command ::= loop | primitive
 */
public class CommandNode extends Node {
    private Node node;

    public void interpret(Context context) {
        if (context.currentToken().equals("LOOP")) {
            node = new LoopCommandNode();
            node.interpret(context);
        } else {
            node = new PrimitiveCommandNode();
            node.interpret(context);
        }
    }

    public void execute() {
        node.execute();
    }
}