package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class XTriangle extends XShape{

    public XTriangle(double left, double top, double right, double bottom){
        xs = new double[3];
        ys = new double[3];
        xs[0] = (left + right)/2;
        xs[1] = right;
        xs[2] = left;
        ys[0] = top;
        ys[1] = bottom;
        ys[2] = bottom;
    }

    public XTriangle(){
        xs = new double[3];
        ys = new double[3];
    }

    public void draw(GraphicsContext gc){
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
    }

    public boolean contains(double x, double y) {
        double total = area(xs[0], ys[0], xs[1], ys[1], xs[2], ys[2]);
        double a = area(x, y, xs[0], ys[0], xs[1], ys[1]);
        double b = area(x, y, xs[1], ys[1], xs[2], ys[2]);
        double c = area(x, y, xs[2], ys[2], xs[0], ys[0]);
        return (Math.abs(total - (a + b + c)) < 1);
    }
}
