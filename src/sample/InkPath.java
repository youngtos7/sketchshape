package sample;

import javafx.geometry.Point2D;
import java.util.ArrayList;

public class InkPath {
    String svgPathContents;
    ArrayList<Point2D> points;
    double length, bboxLength;
    double yOfLeftMost, leftMostYRatio;
    double minCorner;
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
        double distance;

        length = 0;
        left = points.get(0).getX();
        right = points.get(0).getX();
        top = points.get(0).getY();
        bottom = points.get(0).getY();
        //
        x1 = left;
        y1 = top;
        x2 =  points.get(points.size() - 1).getX();
        y2 =  points.get(points.size() - 1).getY();
        for (int i = 1; i < points.size(); i++) {
            distance = dist(points.get(i).getX(), points.get(i).getY(),
                    points.get(i - 1).getX(), points.get(i - 1).getY());
            length += distance;
            // bounds
            left = Math.min(left, points.get(i).getX());
            if (points.get(i).getX() == left) {
                yOfLeftMost = points.get(i).getX();
            }
            right = Math.max(right, points.get(i).getX());
            top = Math.min(top, points.get(i).getY());
            bottom = Math.max(bottom, points.get(i).getY());
        }
        bboxLength = (right - left) * 2 + (bottom - top) * 2;
        minCorner = minDistToCorner();
        System.out.println("Ink length: " + length);
        System.out.println("bbox length: " + bboxLength);
        System.out.println("Y value of leftmost: " + yOfLeftMost);
        leftMostYRatio = (yOfLeftMost - top) / (bottom - top);
        System.out.println("LeftMostYRatio: " + leftMostYRatio);
    }

    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
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

    private double minDistToCorner() {
        double minDist = Double.MAX_VALUE;
        double distanceTL, distanceTR, distanceBL, distanceBR;
        for (int i = 1; i < points.size(); i++) {
            distanceTL = dist(points.get(i).getX(), points.get(i).getY(), left, top);
            distanceTR = dist(points.get(i).getX(), points.get(i).getY(), right, top);
            distanceBL = dist(points.get(i).getX(), points.get(i).getY(), left, bottom);
            distanceBR = dist(points.get(i).getX(), points.get(i).getY(), right, bottom);
            minDist = Math.min(minDist, distanceTL);
            minDist = Math.min(minDist, distanceTR);
            minDist = Math.min(minDist, distanceBL);
            minDist = Math.min(minDist, distanceBR);
        }
        return minDist;
    }

}
