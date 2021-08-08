package sample;

import java.util.ArrayList;

public class InteractionModel {
    XShape selectedShape;
    ArrayList selectionSet;
    double mainViewWidth, mainViewHeight;
    InkPath ink;
    ArrayList<SketchModelListener> subscribers;

    public InteractionModel(){
        subscribers = new ArrayList<>();
        selectedShape = null;
        selectionSet = new ArrayList<>();
        ink = new InkPath();
        selectedShape = null;
    }

    public void setSelection(ArrayList set){
        selectionSet = set;
        notifySubscribers();
    }

    public void setMainViewExtents(double w, double h) {
        mainViewWidth = w;
        mainViewHeight = h;
    }

    public void clearSelection(){
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

    private void notifySubscribers(){
        subscribers.forEach(sub -> sub.modelChanged());
    }

    public void addSubscriber(SketchModelListener aSub) {
        subscribers.add(aSub);
    }

    public void unSelect(){
        selectedShape = null;
        notifySubscribers();
    }
}
