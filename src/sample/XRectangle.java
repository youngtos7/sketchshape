package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class XRectangle extends XShape{


    public XRectangle(double left, double top, double right, double bottom){
        xs = new double[4];
        ys = new double[4];
        xs[0] = xs[3] = left;
        xs[1] = xs[2] = right;
        ys[0] = ys[1] = top;
        ys[2] = ys[3] = bottom;
    }

    private XRectangle(){
        xs = new double[4];
        ys = new double[4];
    }

    public void drawSelected(GraphicsContext gc) {
        gc.setFill(Color.PALEGREEN);
        gc.setStroke(Color.RED);
        gc.fillPolygon(xs, ys, 4);
        gc.strokePolygon(xs, ys, 4);
    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.CHARTREUSE);
        gc.setStroke(Color.BLACK);
        gc.fillPolygon(xs, ys, 4);
        gc.strokePolygon(xs, ys, 4);
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



}
