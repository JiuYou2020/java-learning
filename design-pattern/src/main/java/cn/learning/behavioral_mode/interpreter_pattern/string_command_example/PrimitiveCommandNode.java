package cn.learning.behavioral_mode.interpreter_pattern.string_command_example;

/**
 * primitive ::= 'PRINT string' | 'SPACE' | 'BREAK'
 */
public class PrimitiveCommandNode extends Node {
    private String name;
    private String text;

    public void interpret(Context context) {
        name = context.currentToken();
        context.skipToken(name);
        if (!name.equals("PRINT") && !name.equals("BREAK") && !name.equals("SPACE")) {
            System.err.println("非法命令");
        }
        if (name.equals("PRINT")) {
            text = context.currentToken();
            context.nextToken();
        }
    }

    public void execute() {
        switch (name) {
            case "PRINT" -> System.out.print(text);
            case "SPACE" -> System.out.print(" ");
            case "BREAK" -> System.out.println();
        }
    }
}