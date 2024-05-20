package cn.learning.behavioral_mode.mediator_pattern.pain_example;

// 定义窗口类
class Window {
    private Mediator mediator;
    private TextPane textPane;
    private ListPane listPane;
    private GraphicPane graphicPane;

    public Window() {
        mediator = new ConcreteMediator();
        textPane = new TextPane(mediator);
        listPane = new ListPane(mediator);
        graphicPane = new GraphicPane(mediator);

        ((ConcreteMediator) mediator).setTextPane(textPane);
        ((ConcreteMediator) mediator).setListPane(listPane);
        ((ConcreteMediator) mediator).setGraphicPane(graphicPane);
    }

    public void simulateUserActions() {
        textPane.setText("Hello, World!");
        listPane.setItems(java.util.Arrays.asList("Item 1", "Item 2", "Item 3"));
        graphicPane.setGraphic("Graphic Image");
    }
}