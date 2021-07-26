package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public abstract class XShape implements Groupable {
    Handle rotateHandle;
    double cx, cy;
    double left, top, right, bottom;
    double[] dashPattern = {4,4};
    double[] xs, ys, hxs, hys;

    public abstract void draw(GraphicsContext gc);
    public abstract void drawSelected(GraphicsContext gc);
    protected void drawBoundsAndHandle(GraphicsContext gc) {
        gc.setStroke(Color.YELLOW);
        gc.setLineDashes(dashPattern);
        gc.strokeRect(left - 2, top - 2, right - left + 4, bottom - top + 4);
        gc.setLineDashes((double[]) null);
        rotateHandle.draw(gc);
    }

    public abstract boolean contains(double x, double y);
    protected double area(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }

    public void move(double dx, double dy) {
        for (int i = 0; i < xs.length; i++) {
            xs[i] += dx;
            ys[i] += dy;
        }
        moveBounds(dx,dy);
        recalculateCentre();
    }

    public boolean handleContains(double x, double y) {
        return rotateHandle.contains(x,y);
    }

    public void rotate(double dT) {
        // theta is in degrees, need radians
        double thetaRadians = dT * Math.PI / 180;
        for (int i = 0; i < xs.length; i++) {
            hxs[i] = xs[i] - cx;
            hys[i] = ys[i] - cy;
            xs[i] = rotateX(hxs[i], hys[i], thetaRadians) + cx;
            ys[i] = rotateY(hxs[i], hys[i], thetaRadians) + cy;
        }
        recalculateBounds();
    }

    public void rotate(double dT, double otherCX, double otherCY) {
        // theta is in degrees, need radians
        double thetaRadians = dT * Math.PI / 180;
        for (int i = 0; i < xs.length; i++) {
            hxs[i] = xs[i] - otherCX;
            hys[i] = ys[i] - otherCY;
            xs[i] = rotateX(hxs[i], hys[i], thetaRadians) + otherCX;
            ys[i] = rotateY(hxs[i], hys[i], thetaRadians) + otherCY;
        }
        recalculateBounds();
    }

    private double rotateX(double x, double y, double thetaR) {
        return Math.cos(thetaR) * x - Math.sin(thetaR) * y;
    }

    private double rotateY(double x, double y, double thetaR) {
        return Math.sin(thetaR) * x + Math.cos(thetaR) * y;
    }

    public boolean hasChildren() { return false; }

    public ArrayList<Groupable> getChildren() { return null;}

    protected void recalculateBounds() {
        left = Double.MAX_VALUE;
        right = 0;
        top = Double.MAX_VALUE;
        bottom = 0;
        for (int i = 0; i < xs.length; i++) {
            left = Math.min(left, xs[i]);
            right = Math.max(right, xs[i]);
            top = Math.min(top, ys[i]);
            bottom = Math.max(bottom, ys[i]);
        }
    }

    protected void recalculateCentre() {
        cx = (left + right) / 2;
        cy = (top + bottom) / 2;
        rotateHandle.x = cx;
        rotateHandle.y = cy;
    }

    protected void moveBounds(double dx, double dy) {
        left += dx;
        top += dy;
        right += dx;
        bottom += dy;
    }

    protected double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public double getLeft() {return left;}
    public double getTop() {return top;}
    public double getRight() {return right;}
    public double getBottom() {return bottom;}

    public abstract Groupable duplicate();
}
