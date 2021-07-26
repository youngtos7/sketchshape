package sample;

import java.util.ArrayList;

public class SketchModel {
    ArrayList<Groupable> items;
    ArrayList<SketchModelListener> subscribers;

    public SketchModel() {
        items = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public void addRectangle(double left, double top, double right, double bottom) {
        items.add(new XRectangle(left, top, right, bottom));
        notifySubscribers();
    }

    public void addTriangle(double left, double top, double right, double bottom) {
        items.add(new XTriangle(left, top, right, bottom));
        notifySubscribers();
    }

    public void addLine(double x1, double y1, double x2, double y2) {
        items.add(new XLine(x1, y1, x2, y2));
        notifySubscribers();
    }

    public void addCircle(double left, double top, double right, double bottom) {
        double xr = (right - left) / 2;
        double yr = (bottom - top) / 2;
        double r = Math.min(xr, yr);
        items.add(new XCircle(left + (right - left) / 2, top + (bottom - top) / 2, r));
        notifySubscribers();
    }

    public void addSubscriber(SketchModelListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    public boolean checkHit(double x, double y) {
        return items.stream()
                .anyMatch(item -> item.contains(x, y));
    }

    public Groupable whichItem(double x, double y) {
        Groupable found = null;
        for (Groupable g : items) {
            if (g.contains(x, y)) {
                found = g;
            }
        }
        return found;
    }

    public void moveShapes(ArrayList<Groupable> selectionSet, double dX, double dY) {
        selectionSet.forEach(xs -> xs.move(dX, dY));
        notifySubscribers();
    }

    public Groupable createGroup(ArrayList<Groupable> selectionSet) {
        XShapeGroup xsg = new XShapeGroup();
        selectionSet.forEach(xs -> xsg.addItem(xs));
        selectionSet.forEach(xs -> items.remove(xs));
        items.add(xsg);
        return xsg;
    }

    public ArrayList<Groupable> ungroup(Groupable oldGroup) {
        ArrayList<Groupable> groupItems = new ArrayList<>();

        if (oldGroup.hasChildren()) {
            // add the children to the model and return the children
            oldGroup.getChildren().forEach(child -> {
                items.add(child);
                groupItems.add(child);
            });
            items.remove(oldGroup);
        }
        return groupItems;
    }

    public void addItems(ArrayList<Groupable> gs) {
        gs.forEach(g -> items.add(g));
        notifySubscribers();
    }

    public void deleteGroups(ArrayList<Groupable> gs) {
        gs.forEach(g -> deleteGroup(g));
    }

    public void deleteGroup(Groupable g) {
        if (!items.contains(g)) {
            System.out.println("Tried to delete groupable that was not in the model");
        } else {
            items.remove(g);
        }
        notifySubscribers();
    }
}
