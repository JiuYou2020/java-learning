package behavioral_mode.interpreter_pattern.interpreter_example;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiuyou2020
 * @description: 环境类
 */
public class Context {
    private String expression;

    public Context(String expression) {
        this.expression = expression;
    }

    public AbstractExpression evaluate() {
        String[] tokens = expression.split(" ");
        AbstractExpression result = new NumberExpression(Integer.parseInt(tokens[0]));
        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            int number = Integer.parseInt(tokens[i + 1]);
            result = switch (operator) {
                case "+" -> new AddExpression(result, new NumberExpression(number));
                case "-" -> new SubtractExpression(result, new NumberExpression(number));
                default -> result;
            };
        }
        return result;
    }
}
