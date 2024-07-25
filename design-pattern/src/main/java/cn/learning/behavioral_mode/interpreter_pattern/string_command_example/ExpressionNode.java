package cn.learning.behavioral_mode.interpreter_pattern.string_command_example;

import java.util.*;

/**
 * expression ::= command*
 */
public class ExpressionNode extends Node {
    private List<Node> list = new ArrayList<Node>();

    public void interpret(Context context) {
        while (true) {
            if (context.currentToken() == null) {
                break;
            } else if (context.currentToken().equals("END")) {
                context.skipToken("END");
                break;
            } else {
                Node commandNode = new CommandNode();
                commandNode.interpret(context);
                list.add(commandNode);
            }
        }
    }

    public void execute() {
        for (Node node : list) {
            node.execute();
        }
    }
}