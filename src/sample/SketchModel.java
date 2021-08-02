package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class SketchModel {
    ArrayList<XLine> items;
    XLine selectedShape;
    InkPath ink;
    GraphicsContext gc;


    public SketchModel() {
        items = new ArrayList<>();
        ink = new InkPath();
    }

    public void setGraphicsContext(GraphicsContext gcNew) {
        gc = gcNew;
    }

    public void addLine(double x1, double y1, double x2, double y2) {
        XLine xline = new XLine(x1, y1, x2, y2);
//        items.add(xline);
        xline.draw(gc);
    }

    public void startInk(double x, double y) {
        ink.start(x, y);
    }

    public void continueInk(double x, double y) {
        ink.addPoint(x, y);
    }

    public void moveShapes(double dX, double dY) {
        items.get(0).move(dX, dY);
    }

    public void finishInk() {
        ink.calculateLength();
        ink.calculateRatios();
    }
    public void clearInk() {
        ink.clear();
    }
}
