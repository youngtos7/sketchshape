package sample;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class SketchController {
    InteractionModel interactionModel;
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

    public void setInteractionModel(InteractionModel newModel) {
        interactionModel = newModel;
    }

    public void setModel(SketchModel newModel) {
        model = newModel;
    }

    public void handlePressed(MouseEvent event) {
        prevX = event.getX();
        prevY = event.getY();

        switch (currentState) {
            case READY:
                if(model.checkHit(prevX, prevY)){
                    Groupable selectedModel = model.whichItem(prevX, prevY);
                    interactionModel.clearSelection();
                    interactionModel.addSubtractSelection(selectedModel);
                    currentState = State.DRAGGING;
                } else {
                    System.out.println("I have started pressing");
                    interactionModel.startInk(event.getX(), event.getY());
                    currentState = State.INK_OR_UNSELECT;
                }
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
                interactionModel.continueInk(event.getX(), event.getY());
                currentState = State.INKING;
            case INKING:
                interactionModel.continueInk(event.getX(), event.getY());
                break;
            case DRAGGING:
                model.moveShapes(interactionModel.selectionSet, dX, dY);
                break;
            case HANDLING:
                break;
        }
//        view.draw();
    }

    public void handleReleased(MouseEvent event) {
        switch (currentState) {
            case INK_OR_UNSELECT:
                interactionModel.unSelect();
                currentState = State.READY;
                break;
            case INKING:
                interactionModel.finishInk();
                interactionModel.clearInk();
                switch (interactionModel.ink.bestMatch) {
                    case RECTANGLE: model.addRectangle(interactionModel.ink.left, interactionModel.ink.top, interactionModel.ink.right, interactionModel.ink.bottom); break;
                    case LINE: model.addLine(interactionModel.ink.x1, interactionModel.ink.y1, interactionModel.ink.x2, interactionModel.ink.y2); break;
                    case TRIANGLE: model.addTriangle(interactionModel.ink.x1, interactionModel.ink.y1, interactionModel.ink.x2, interactionModel.ink.y2); break;
                }
                System.out.println("Ticking....");

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
