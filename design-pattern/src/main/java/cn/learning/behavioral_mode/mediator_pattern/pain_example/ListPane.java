package cn.learning.behavioral_mode.mediator_pattern.pain_example;

class ListPane extends Pane {
    private java.util.List<String> items = new java.util.ArrayList<>();

    public ListPane(Mediator mediator) {
        super(mediator);
    }

    public java.util.List<String> getItems() {
        return items;
    }

    public void setItems(java.util.List<String> items) {
        this.items = items;
        mediator.paneChanged(this);
    }

    @Override
    public void update() {
        System.out.println("ListPane updated: " + items);
    }
}