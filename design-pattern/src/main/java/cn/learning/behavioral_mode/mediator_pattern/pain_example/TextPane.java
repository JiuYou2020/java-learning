package cn.learning.behavioral_mode.mediator_pattern.pain_example;

// 具体窗格类
class TextPane extends Pane {
    private String text;

    public TextPane(Mediator mediator) {
        super(mediator);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        mediator.paneChanged(this);
    }

    @Override
    public void update() {
        System.out.println("TextPane updated: " + text);
    }
}