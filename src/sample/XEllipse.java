package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class XEllipse {
    double left, top, right, bottom;
    Handle selectedHandle;
    double theta;

    public XEllipse(double nleft, double ntop, double nright, double nbottom) {
        left = nleft;
        top = ntop;
        right = nright;
        bottom = nbottom;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.CHARTREUSE);
        gc.fillOval(left, top, right - left, bottom - top);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(left, top, right - left, bottom - top);
    }

    public void drawSelected(GraphicsContext gc) {
        gc.setFill(Color.PALEGREEN);
        gc.fillOval(left, top, right - left, bottom - top);
        gc.setStroke(Color.RED);
        gc.strokeOval(left, top, right - left, bottom - top);
    }

    public boolean contains(double x, double y) {
        double cx = left + (right - left) / 2; // centre x of ellipse
        double cy = top + (bottom - top) / 2; // centre y of ellipse
        double horiz = (right - left) / 2; // horizontal diameter
        double vert = (bottom - top) / 2; // vertical diameter
        double a, b;
        if (horiz > vert) {
            // horiz is major
            a = horiz;
            b = vert;
        } else {
            b = horiz;
            a = vert;
        }

        int result = ((int) Math.pow((x - cx), 2) / (int) Math.pow(a, 2))
                + ((int) Math.pow((y - cy), 2) / (int) Math.pow(b, 2));
        System.out.println("Contains: " + result);
        return result == 0;
    }

    public void move(double dx, double dy) {
        left += dx;
        top += dy;
        right += dx;
        bottom += dy;
    }

    public boolean handleContains(double x, double y) {
    return false;
    }

    public void unselectHandle() {
        selectedHandle = null;
    }

    public void selectHandle(double x, double y) {
    }

    public void dragHandle(double dx, double dy) {
    }
}
