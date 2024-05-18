package cn.learning.behavioral_mode.interpreter_pattern.interpreter_example;

class AddExpression extends AbstractExpression {
    private AbstractExpression leftExpression;
    private AbstractExpression rightExpression;

    public AddExpression(AbstractExpression leftExpression, AbstractExpression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public int interpret() {
        return leftExpression.interpret() + rightExpression.interpret();
    }
}
