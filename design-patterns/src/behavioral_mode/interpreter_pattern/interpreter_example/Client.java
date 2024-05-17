package behavioral_mode.interpreter_pattern.interpreter_example;

public class Client {
    public static void main(String[] args) {
        String expression = "3 + 5 - 2 + 8";
        Context context = new Context(expression);
        AbstractExpression exp = context.evaluate();
        int result = exp.interpret();
        System.out.println(expression + " = " + result);
    }
}
