package cn.learning.behavioral_mode.mediator_pattern.pain_example;

class GraphicPane extends Pane {
    private String graphic;

    public GraphicPane(Mediator mediator) {
        super(mediator);
    }

    public String getGraphic() {
        return graphic;
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
        mediator.paneChanged(this);
    }

    @Override
    public void update() {
        System.out.println("GraphicPane updated: " + graphic);
    }
}
