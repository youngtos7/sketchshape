package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Handle {
    double x, y;
    double r;

    public Handle(double newX, double newY) {
        x = newX;
        y = newY;
        r = 10;
    }

    public boolean contains(double px, double py) {
        return dist(x,y,px,py) <= r;
    }

    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUEVIOLET);
        gc.fillOval(x - r, y - r, r*2, r*2);
        //gc.fillOval(0 - r, 0 - r, r*2, r*2);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - r, y - r, r*2, r*2);
        //gc.strokeOval(0 - r, 0 - r, r*2, r*2);
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }
}
