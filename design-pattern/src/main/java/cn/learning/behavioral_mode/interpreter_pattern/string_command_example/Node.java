package cn.learning.behavioral_mode.interpreter_pattern.string_command_example;

public abstract class Node {
	public abstract void interpret(Context text);
	public abstract void execute();
}
