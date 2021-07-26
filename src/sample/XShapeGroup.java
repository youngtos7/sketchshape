package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class XShapeGroup extends XShape implements Groupable {
    ArrayList<Groupable> items;

    public XShapeGroup() {
        items = new ArrayList<>();
        rotateHandle = new Handle(0, 0);
    }

    public boolean hasChildren() {
        return true;
    }

    public boolean contains(double x, double y) {
        return items.stream()
                .anyMatch(g -> g.contains(x, y));
    }

    public void rotate(double dT) {
        items.forEach(item -> item.rotate(dT, cx, cy));
        recalculateBounds();
    }

    public void rotate(double dT, double otherCX, double otherCY) {
        items.forEach(item -> item.rotate(dT, otherCX, otherCY));
        recalculateBounds();
    }

    protected void recalculateBounds() {
        left = Float.MAX_VALUE;
        top = Float.MAX_VALUE;
        right = 0;
        bottom = 0;

        for (Groupable g : items) {
            // update bounding box
            left = Math.min(left, g.getLeft());
            top = Math.min(top, g.getTop());
            right = Math.max(right, g.getRight());
            bottom = Math.max(bottom, g.getBottom());
        }
    }

    public void move(double dx, double dy) {
        items.forEach(g -> g.move(dx, dy));
        recalculateBounds();
        recalculateCentre();
    }

    public void draw(GraphicsContext gc) {
        items.forEach(g -> g.draw(gc));
    }

    public void drawSelected(GraphicsContext gc) {
        items.forEach(g -> g.draw(gc));
        drawBoundsAndHandle(gc);
    }

    public ArrayList<Groupable> getChildren() {
        return items;
    }

    public void addItem(Groupable xs) {
        items.add(xs);
        recalculateBounds();
        recalculateCentre();
    }

    public Groupable duplicate() {
        XShapeGroup dupe = new XShapeGroup();
        items.forEach(item -> dupe.addItem(item.duplicate()));
        dupe.recalculateBounds();
        dupe.recalculateCentre();
        return dupe;
    }
}
