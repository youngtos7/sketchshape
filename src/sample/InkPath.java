package sample;

import javafx.geometry.Point2D;
import java.util.ArrayList;

public class InkPath {
    String svgPathContents;
    ArrayList<Point2D> points;
    double left, top, right, bottom;
    double x1, y1, x2, y2;

    enum ShapeType {
        NONE, RECTANGLE, CIRCLE, LINE, TRIANGLE
    }
    ShapeType bestMatch;

    public InkPath() {
        points = new ArrayList<>();
    }

    public void start(double x, double y) {
        points.clear();
        points.add(new Point2D(x, y));
        svgPathContents = "M" + x + "," + y;

    }

    public void addPoint(double x, double y) {
        points.add(new Point2D(x, y));
        svgPathContents += " L" + x + "," + y;
    }

    public void calculateLength() {
        left = points.get(0).getX();
        right = points.get(0).getX();
        top = points.get(0).getY();
        bottom = points.get(0).getY();
        //
        x1 = left;
        y1 = top;
        x2 =  points.get(points.size() - 1).getX();
        y2 =  points.get(points.size() - 1).getY();
    }

    public void calculateRatios() {
        bestMatch = ShapeType.NONE;
        double bestRatio = Double.MAX_VALUE;
        if (bestRatio > 0.15) {
            bestMatch = ShapeType.NONE;
        }
    }

    public void clear() {
        points.clear();
        svgPathContents = "";
    }


}
