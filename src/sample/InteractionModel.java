package sample;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    XShape selectedShape;
    ArrayList<Groupable> selectionSet;
    double mainViewWidth, mainViewHeight;
    ArrayList<SketchModelListener> subscribers;
    InkPath ink;
    XClipboard board;

    public InteractionModel() {
        subscribers = new ArrayList<>();
        selectedShape = null;
        selectionSet = new ArrayList<>();
        ink = new InkPath();
        board = new XClipboard();
    }

    public void addSubtractSelection(Groupable xs) {
        if (selectionSet.contains(xs)) {
            selectionSet.remove(xs);
        } else {
            selectionSet.add(xs);
        }
        notifySubscribers();
    }

    public void addSubtractSelection(List<Groupable> set) {
        set.forEach(g -> addSubtractSelection(g));
    }

    public void clearSelection() {
        selectionSet.clear();
        notifySubscribers();
    }

    public void setSelection(ArrayList<Groupable> set) {
        selectionSet = set;
        notifySubscribers();
    }

    public void setMainViewExtents(double w, double h) {
        mainViewWidth = w;
        mainViewHeight = h;
    }

    public void addSubscriber(SketchModelListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    public void unSelect() {
        selectedShape = null;
        selectionSet.clear();
        notifySubscribers();
    }

    public void startInk(double x, double y) {
        ink.start(x, y);
    }

    public void continueInk(double x, double y) {
        ink.addPoint(x, y);
        notifySubscribers();
    }

    public void finishInk() {
        ink.calculateLength();
        ink.calculateRatios();
    }

    public void clearInk() {
        ink.clear();
    }

    public boolean handleContains(double x, double y) {
        return selectionSet.stream()
                .anyMatch(s -> s.handleContains(x, y));
    }

    public void dragHandle(double dX, double dY) {
        selectionSet.forEach(xs -> xs.rotate(dX));
        notifySubscribers();
    }

    public void copyToClipboard() {
        board.copy(selectionSet);
        notifySubscribers();
    }

    public ArrayList<Groupable> getClipboard() {
        return board.get();
    }

    public String describeClipboard() {
        return board.describeClipboard();
    }
}
