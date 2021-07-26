package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class XCircle extends XShape {
    double r;

    public XCircle(double nx, double ny, double nr) {
        cx = nx;
        cy = ny;
        r = nr;
        xs = new double[5];
        ys = new double[5];
        xs[0] = cx;
        xs[1] = cx + r;
        xs[2] = cx;
        xs[3] = cx - r;
        xs[4] = cx;
        ys[0] = cy - r;
        ys[1] = cy;
        ys[2] = cy + r;
        ys[3] = cy;
        ys[4] = cy;
        hxs = new double[5];
        hys = new double[5];
        rotateHandle = new Handle(cx, cy);
        recalculateBounds();
        recalculateCentre();
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.CHARTREUSE);
        gc.setStroke(Color.BLACK);
        gc.fillOval(xs[4] - r, ys[4] - r, r*2, r*2);
        gc.strokeOval(xs[4] - r, ys[4] - r, r*2, r*2);
        gc.strokeLine(xs[0],ys[0],xs[2],ys[2]);
        gc.strokeLine(xs[3],ys[3],xs[1],ys[1]);
    }

    public void drawSelected(GraphicsContext gc) {
        gc.setFill(Color.PALEGREEN);
        gc.setStroke(Color.RED);
        gc.fillOval(xs[4] - r, ys[4] - r, r*2, r*2);
        gc.strokeOval(xs[4] - r, ys[4] - r, r*2, r*2);
        gc.strokeLine(xs[0],ys[0],xs[2],ys[2]);
        gc.strokeLine(xs[3],ys[3],xs[1],ys[1]);
        drawBoundsAndHandle(gc);
    }

    protected void recalculateBounds() {
        left = xs[4] - r;
        right = xs[4] + r;
        top = ys[4] - r;
        bottom = ys[4] + r;
    }

    public boolean contains(double x, double y) {
        return dist(x,y,cx,cy) <= r;
    }

    public Groupable duplicate() {
        return new XCircle(cx,cy,r);
    }
}
