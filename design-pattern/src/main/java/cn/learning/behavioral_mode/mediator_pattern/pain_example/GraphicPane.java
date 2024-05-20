package cn.learning.behavioral_mode.mediator_pattern.pain_example;

class GraphicPane extends Pane {
    private String graphic;

    public GraphicPane(Mediator mediator) {
        super(mediator);
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
        mediator.paneChanged(this);
    }

    public String getGraphic() {
        return graphic;
    }

    @Override
    public void update() {
        System.out.println("GraphicPane updated: " + graphic);
    }
}
