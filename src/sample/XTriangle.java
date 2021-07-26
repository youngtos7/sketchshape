package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class XTriangle extends XShape {

    public XTriangle(double nleft, double ntop, double nright, double nbottom) {
        xs = new double[3];
        ys = new double[3];
        xs[0] = (nleft + nright) / 2;
        xs[1] = nright;
        xs[2] = nleft;
        ys[0] = ntop;
        ys[1] = nbottom;
        ys[2] = nbottom;
        hxs = new double[3];
        hys = new double[3];
        rotateHandle = new Handle(cx, cy);
        recalculateBounds();
        recalculateCentre();
    }

    private XTriangle() {
        xs = new double[3];
        ys = new double[3];
        hxs = new double[3];
        hys = new double[3];
        rotateHandle = new Handle(cx, cy);
    }

    public boolean contains(double x, double y) {
        double total = area(xs[0], ys[0], xs[1], ys[1], xs[2], ys[2]);
        double a = area(x, y, xs[0], ys[0], xs[1], ys[1]);
        double b = area(x, y, xs[1], ys[1], xs[2], ys[2]);
        double c = area(x, y, xs[2], ys[2], xs[0], ys[0]);
        return (Math.abs(total - (a + b + c)) < 1);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.CHARTREUSE);
        gc.setStroke(Color.BLACK);
        gc.fillPolygon(xs, ys, 3);
        gc.strokePolygon(xs, ys, 3);
    }

    public void drawSelected(GraphicsContext gc) {
        gc.setFill(Color.PALEGREEN);
        gc.setStroke(Color.RED);
        gc.fillPolygon(xs, ys, 3);
        gc.strokePolygon(xs, ys, 3);
        drawBoundsAndHandle(gc);
    }

    public Groupable duplicate() {
        XTriangle dupe = new XTriangle();
        dupe.xs[0] = xs[0];
        dupe.xs[1] = xs[1];
        dupe.xs[2] = xs[2];
        dupe.ys[0] = ys[0];
        dupe.ys[1] = ys[1];
        dupe.ys[2] = ys[2];
        dupe.recalculateBounds();
        dupe.recalculateCentre();
        return dupe;
    }
}
