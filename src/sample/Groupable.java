package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public interface Groupable {
    boolean hasChildren();

    boolean contains(double x, double y);

    ArrayList<Groupable> getChildren();

    double getLeft();

    double getTop();

    double getRight();

    double getBottom();

    void move(double dx, double dy);

    void draw(GraphicsContext gc);

    void drawSelected(GraphicsContext gc);

}
