package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public interface Groupable {

    boolean hasChildren();

    ArrayList<Groupable> getChildren();

    boolean contains(double x, double y);

    boolean handleContains(double x, double y);

    void draw(GraphicsContext gc);

    void drawSelected(GraphicsContext gc);

    void move(double dx, double dy);

    void rotate(double dT);

    void rotate(double dT, double otherCX, double otherCY);

    double getLeft();

    double getTop();

    double getRight();

    double getBottom();

    Groupable duplicate();
}
