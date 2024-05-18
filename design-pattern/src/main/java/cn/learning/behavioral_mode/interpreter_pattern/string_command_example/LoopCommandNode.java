package cn.learning.behavioral_mode.interpreter_pattern.string_command_example;

/**
 * loop ::= 'LOOP number' expression 'END'
 */
public class LoopCommandNode extends Node {
	private int number;
	private Node commandNode;
	
	public void interpret(Context context) {
		context.skipToken("LOOP");
		number = context.currentNumber();
		context.nextToken();
		commandNode = new ExpressionNode();
		commandNode.interpret(context);
	}
	
	public void execute() {
		for (int i=0;i<number;i++)
			commandNode.execute();
	}
}