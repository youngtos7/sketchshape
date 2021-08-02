package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class XLine extends XShape{
    double ratioA, ratioB, ratioC;
    double length;

    public XLine(double x1, double x2, double y1, double y2){
        xs = new double[2];
        ys = new double[2];
        xs[0] = x1;
        xs[1] = x2;
        ys[0] = y1;
        ys[1] = y2;
    }

    public void draw(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        gc.strokeLine(xs[0], ys[0], xs[1], ys[1]);
    }

    public void drawSelected(GraphicsContext gc){
        gc.setStroke(Color.RED);
        gc.strokeLine(xs[0], ys[0], xs[1], ys[1]);

    }

    private double distanceFromLine(double x, double y) {
        return ratioA * x + ratioB * y + ratioC;
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

}
