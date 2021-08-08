package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class XShape implements Groupable{
    double[] xs, ys, hxs, hys;;
    double left, top, right, bottom;


    public void move(double dx, double dy){
        for(int i = 0; i < xs.length; i++){
            xs[i] += dx;
            ys[i] += dy;
        }
    }
    public abstract void draw(GraphicsContext gc);
    public abstract void drawSelected(GraphicsContext gc);
    public abstract boolean contains(double x, double y);
    protected double area(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }

    protected double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public boolean hasChildren() { return false; }
    public ArrayList<Groupable> getChildren() { return null;}

    public double getLeft() {return left;}
    public double getTop() {return top;}
    public double getRight() {return right;}
    public double getBottom() {return bottom;}
}

