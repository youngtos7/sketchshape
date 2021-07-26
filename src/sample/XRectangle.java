package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class XRectangle extends XShape {

    public XRectangle(double nleft, double ntop, double nright, double nbottom) {
        xs = new double[4];
        ys = new double[4];
        xs[0] = xs[3] = nleft;
        xs[1] = xs[2] = nright;
        ys[0] = ys[1] = ntop;
        ys[2] = ys[3] = nbottom;
        hxs = new double[4];
        hys = new double[4];
        rotateHandle = new Handle(cx, cy);
        recalculateBounds();
        recalculateCentre();
    }

    private XRectangle() {
        xs = new double[4];
        ys = new double[4];
        hxs = new double[4];
        hys = new double[4];
        rotateHandle = new Handle(cx, cy);
    }

    public boolean contains(double x, double y) {
        // total area
        double total = area(xs[0], ys[0], xs[1], ys[1], xs[2], ys[2]) +
                area(xs[0], ys[0], xs[3], ys[3], xs[2], ys[2]);
        double a = area(x, y, xs[0], ys[0], xs[1], ys[1]);
        double b = area(x, y, xs[1], ys[1], xs[2], ys[2]);
        double c = area(x, y, xs[2], ys[2], xs[3], ys[3]);
        double d = area(x, y, xs[3], ys[3], xs[0], ys[0]);
        return (Math.abs(total - (a + b + c + d)) < 1);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.CHARTREUSE);
        gc.setStroke(Color.BLACK);
        gc.fillPolygon(xs, ys, 4);
        gc.strokePolygon(xs, ys, 4);
    }

    public void drawSelected(GraphicsContext gc) {
        gc.setFill(Color.PALEGREEN);
        gc.setStroke(Color.RED);
        gc.fillPolygon(xs, ys, 4);
        gc.strokePolygon(xs, ys, 4);
        drawBoundsAndHandle(gc);
    }

    public Groupable duplicate() {
        XRectangle dupe = new XRectangle();
        dupe.xs[0] = xs[0];
        dupe.xs[1] = xs[1];
        dupe.xs[2] = xs[2];
        dupe.xs[3] = xs[3];
        dupe.ys[0] = ys[0];
        dupe.ys[1] = ys[1];
        dupe.ys[2] = ys[2];
        dupe.ys[3] = ys[3];
        dupe.recalculateBounds();
        dupe.recalculateCentre();
        return dupe;
    }
}
