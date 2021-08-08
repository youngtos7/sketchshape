package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class SketchModel {
    ArrayList<Groupable> items;
    ArrayList<SketchModelListener> subscribers;

    public SketchModel() {
        items = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(SketchModelListener aSub) {
        subscribers.add(aSub);
    }

    public void addLine(double x1, double y1, double x2, double y2) {
        items.add(new XLine(x1, y1, x2, y2));
        notifySubscribers();
    }

    public void addRectangle(double left, double top, double right, double bottom) {
        items.add(new XRectangle(left, top, right, bottom));
        notifySubscribers();
    }

    public void moveShapes(ArrayList<Groupable> selectionSet, double dX, double dY) {
        selectionSet.forEach(xs -> xs.move(dX, dY));
        notifySubscribers();
    }

    public void addItems(ArrayList<Groupable> gs) {
        gs.forEach(g -> items.add(g));
        notifySubscribers();
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

}
