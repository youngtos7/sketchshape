package sample;

import java.util.ArrayList;

public class SketchModel {
    ArrayList<SketchModelListener> subscribers;
    ArrayList<Groupable> items;

    public SketchModel(){
        items = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public void addRectangle(double left, double top, double right, double bottom){
        items.add(new XRectangle(left, top, right, bottom));
        notifySubscribers();
    }

    public void addTriangle(double left, double top, double right, double bottom){
        items.add(new XTriangle(left, top, right, bottom));
    }

    public void addLine(double x1, double x2, double y1, double y2){
        items.add(new XLine(x1, x2, y1, y2));
    }

    public boolean checkHit(double x, double y){
        return items.stream()
                .anyMatch(item -> item.contains(x, y));
    }

    public Groupable whichItem(double x, double y){
        Groupable found = null;
        for(Groupable g: items){
            if(g.contains(x, y)){
                found = g;
            }
        }
        return found;
    }

    public void moveShapes(ArrayList<Groupable> selectionSet, double dX, double dY){
        selectionSet.forEach(xs -> xs.move(dX, dY));
        notifySubscribers();
    }

//    public Groupable createGroup(ArrayList<Groupable> selectionSet){
//        XShapeGroup
//    }

    public void addItems(ArrayList<Groupable> gs){
        gs.forEach(g -> items.add(g));
        notifySubscribers();
    }

    public void addSubscriber(SketchModelListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
