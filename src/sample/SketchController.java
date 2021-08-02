package sample;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class SketchController {
    SketchModel model;
    SketchView view;
    double prevX, prevY;

    private enum State {
        READY, INK_OR_UNSELECT, HANDLING, INKING, DRAGGING
    }

    private State currentState;

    public SketchController() {
        currentState = State.READY;
    }

    public void setModel(SketchModel newModel) {
        model = newModel;
    }

    public void setView(SketchView newView) {
        view = newView;
    }

    public void handlePressed(MouseEvent event) {
        prevX = event.getX();
        prevY = event.getY();


        switch (currentState) {
            case READY:
                System.out.println("I have started pressing");
                model.startInk(event.getX(), event.getY());
                break;
        }
//        view.draw();
    }

    public void handleDragged(MouseEvent event) {
        double dX = event.getX() - prevX;
        double dY = event.getY() - prevY;
        prevX = event.getX();
        prevY = event.getY();

        switch (currentState) {
            case INK_OR_UNSELECT:
                model.continueInk(event.getX(), event.getY());
                System.out.println("Continue..............");
                currentState = State.INKING;
            case INKING:
                model.continueInk(event.getX(), event.getY());
                break;
            case DRAGGING:
                model.moveShapes(dX, dY);
                break;
            case HANDLING:
                break;
        }
        view.draw();
    }

    public void handleReleased(MouseEvent event) {
        switch (currentState) {
            case INK_OR_UNSELECT:
                currentState = State.READY;
                break;
            case INKING:
                model.finishInk();
                model.clearInk();
                model.addLine(model.ink.x1, model.ink.y1, model.ink.x2, model.ink.y2);
                currentState = State.READY;
                break;
            case DRAGGING:
                currentState = State.READY;
                break;
            case HANDLING:
                //iModel.unselectHandle();
                currentState = State.READY;
        }
    }

    public void handleKeyReleased(KeyEvent event) {
    }
    public void handleKeyPressed(KeyEvent keyEvent) {
    }

}
