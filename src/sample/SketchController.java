package sample;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class SketchController {
    InteractionModel iModel;
    SketchModel model;
    double prevX, prevY;

    private enum State {
        READY, INK_OR_UNSELECT, HANDLING, INKING, DRAGGING
    }

    private State currentState;

    public SketchController() {
        currentState = State.READY;
    }

    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
    }

    public void setModel(SketchModel newModel) {
        model = newModel;
    }

    public void handlePressed(MouseEvent event) {
        prevX = event.getX();
        prevY = event.getY();
        switch (currentState) {
            case READY:
                if (iModel.selectionSet.size() != 0) {
                    if (iModel.handleContains(event.getX(), event.getY())) {
                        //iModel.selectHandle(event.getX(), event.getY());
                        currentState = State.HANDLING;
                        break;
                    }
                }
                if (model.checkHit(event.getX(), event.getY())) {
                    Groupable g = model.whichItem(event.getX(), event.getY());
                    System.out.println("Clicked on: g.toString()" + "  selection set size is " + iModel.selectionSet.size());
                    // context: Shift key pressed?
                    if (event.isShiftDown()) {
                        iModel.addSubtractSelection(g);
                    } else {
                        // if object is not already selected, make this the only selection
                        if (!iModel.selectionSet.contains(g)) {
                            System.out.println("clearing selection and setting to new clicked object");

                            iModel.clearSelection();
                            iModel.addSubtractSelection(g);
                        }
                    }
                    currentState = State.DRAGGING;
                } else {
                    iModel.unSelect();
                    iModel.startInk(event.getX(), event.getY());
                    currentState = State.INK_OR_UNSELECT;
                }
                break;
        }
        //iModel.fakeNotify();
        System.out.println("Pressed -> " + currentState.toString());
    }

    public void handleDragged(MouseEvent event) {
        double dX = event.getX() - prevX;
        double dY = event.getY() - prevY;
        prevX = event.getX();
        prevY = event.getY();
        switch (currentState) {
            case INK_OR_UNSELECT:
                iModel.continueInk(event.getX(), event.getY());
                currentState = State.INKING;
            case INKING:
                iModel.continueInk(event.getX(), event.getY());
                break;
            case DRAGGING:
                model.moveShapes(iModel.selectionSet, dX, dY);
                break;
            case HANDLING:
                iModel.dragHandle(dX, dY);
                break;
        }
        //System.out.println("Dragged -> " + currentState.toString());
    }

    public void handleReleased(MouseEvent event) {
        switch (currentState) {
            case INK_OR_UNSELECT:
                iModel.unSelect();
                currentState = State.READY;
                break;
            case INKING:
                iModel.finishInk();
                System.out.println("Best match: " + iModel.ink.bestMatch.toString());
                iModel.clearInk();
                switch (iModel.ink.bestMatch) {
                    case RECTANGLE : model.addRectangle(iModel.ink.left, iModel.ink.top, iModel.ink.right, iModel.ink.bottom);
                    case CIRCLE : model.addCircle(iModel.ink.left, iModel.ink.top, iModel.ink.right, iModel.ink.bottom);
                    case LINE : model.addLine(iModel.ink.x1, iModel.ink.y1, iModel.ink.x2, iModel.ink.y2);
                    case TRIANGLE : model.addTriangle(iModel.ink.left, iModel.ink.top, iModel.ink.right, iModel.ink.bottom);
                }
                currentState = State.READY;
                break;
            case DRAGGING:
                currentState = State.READY;
                break;
            case HANDLING:
                //iModel.unselectHandle();
                currentState = State.READY;
        }
        //System.out.println("Released -> " + currentState.toString());
    }

    public void handleKeyReleased(KeyEvent event) {
        //System.out.println(event.getCode());
        switch (event.getCode()) {
            case G:
                System.out.println("G");
                if (iModel.selectionSet.size() > 0) {
                    Groupable newGroup = model.createGroup(iModel.selectionSet);
                    System.out.println("after create group");
                    iModel.clearSelection();
                    System.out.println("after clear selection");
                    iModel.addSubtractSelection(newGroup);
                    System.out.println("after add/subtract with new group; done handleKeyReleased");
                }
                break;
            case U:
                System.out.println("U");
                if (iModel.selectionSet.size() == 1) {
                    ArrayList<Groupable> items = model.ungroup(iModel.selectionSet.get(0));
                    iModel.clearSelection();
                    iModel.addSubtractSelection(items);
                }
                break;
            case DELETE:
                break;
        }
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case C:
                if (keyEvent.isControlDown()) {
                    System.out.println("Copy");
                    iModel.copyToClipboard();
                }
                break;
            case V:
                if (keyEvent.isControlDown()) {
                    System.out.println("Paste");
                    ArrayList<Groupable> pasteSet = iModel.getClipboard();
                    model.addItems(pasteSet);
                    iModel.setSelection(pasteSet);
                }
                break;
            case X:
                if (keyEvent.isControlDown()) {
                    System.out.println("Cut");
                    iModel.copyToClipboard();
                    model.deleteGroups(iModel.selectionSet);
                    iModel.clearSelection();
                }
                break;

        }
    }
}
