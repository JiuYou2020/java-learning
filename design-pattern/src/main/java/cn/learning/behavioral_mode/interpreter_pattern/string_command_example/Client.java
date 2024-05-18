package cn.learning.behavioral_mode.interpreter_pattern.string_command_example;

public class Client {
	public static void main(String[] args){
		String text = "LOOP 2 PRINT YANGGUO SPACE SPACE PRINT XIAOLONGNV BREAK END PRINT GUOJING SPACE SPACE PRINT HUANGRONG";
		Context context = new Context(text);
			
		Node node = new ExpressionNode();
		node.interpret(context);
		node.execute();
	}
}
