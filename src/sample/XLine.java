package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class XLine extends XShape {
    double ratioA, ratioB, ratioC;
    double length;

    public XLine(double nx1, double ny1, double nx2, double ny2) {
        xs = new double[2];
        ys = new double[2];
        xs[0] = nx1;
        xs[1] = nx2;
        ys[0] = ny1;
        ys[1] = ny2;
        hxs = new double[2];
        hys = new double[2];
        rotateHandle = new Handle(cx, cy);
        recalculateBounds();
        recalculateCentre();
    }

    public boolean contains(double x, double y) {
        length = dist(xs[0], ys[0], xs[1], ys[1]);
        ratioA = (ys[0] - ys[1]) / length;
        ratioB = (xs[1] - xs[0]) / length;
        ratioC = -1 * ((ys[0] - ys[1]) * xs[0] + (xs[1] - xs[0]) * ys[0]) / length;

        return Math.abs(distanceFromLine(x, y)) < 10
                && dist(x, y, xs[0], ys[0]) < length + 10 &&
                dist(x, y, xs[1], ys[1]) < length + 10;
    }

    private double distanceFromLine(double x, double y) {
        return ratioA * x + ratioB * y + ratioC;
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokeLine(xs[0], ys[0], xs[1], ys[1]);
    }

    public void drawSelected(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.strokeLine(xs[0], ys[0], xs[1], ys[1]);
        drawBoundsAndHandle(gc);
    }

    public Groupable duplicate() {
        return new XLine(xs[0],ys[0],xs[1],ys[1]);
    }
}
