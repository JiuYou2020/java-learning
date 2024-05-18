package cn.learning.behavioral_mode.interpreter_pattern.interpreter_example;

class NumberExpression extends AbstractExpression {
    private int number;

    public NumberExpression(int number) {
        this.number = number;
    }

    @Override
    public int interpret() {
        return number;
    }
}
