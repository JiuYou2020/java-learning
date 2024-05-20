package cn.learning.behavioral_mode.mediator_pattern.pain_example;

// 实现具体的中介者类
class ConcreteMediator implements Mediator {
    private TextPane textPane;
    private ListPane listPane;
    private GraphicPane graphicPane;

    public void setTextPane(TextPane textPane) {
        this.textPane = textPane;
    }

    public void setListPane(ListPane listPane) {
        this.listPane = listPane;
    }

    public void setGraphicPane(GraphicPane graphicPane) {
        this.graphicPane = graphicPane;
    }

    @Override
    public void paneChanged(Pane pane) {
        if (pane instanceof TextPane) {
            System.out.println("TextPane changed, updating other panes...");
            listPane.update();
            graphicPane.update();
        } else if (pane instanceof ListPane) {
            System.out.println("ListPane changed, updating other panes...");
            textPane.update();
            graphicPane.update();
        } else if (pane instanceof GraphicPane) {
            System.out.println("GraphicPane changed, updating other panes...");
            textPane.update();
            listPane.update();
        }
    }
}