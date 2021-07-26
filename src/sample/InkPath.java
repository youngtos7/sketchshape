package sample;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class InkPath {
    String svgPathContents;
    ArrayList<Point2D> points;
    double length, bboxLength;
    double left, top, right, bottom;
    double x1, y1, x2, y2;
    double yOfLeftMost, leftMostYRatio;
    double minCorner;

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
        x1 = left;
        y1 = top;
        x2 = points.get(points.size() - 1).getX();
        y2 = points.get(points.size() - 1).getY();
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

    public void clear() {
        points.clear();
        svgPathContents = "";
    }

    public void calculateRatios() {
        bestMatch = ShapeType.NONE;
        double bestRatio = Double.MAX_VALUE;
        // rectangle
        double rectRatio = Math.abs(1.0 - length / bboxLength);
        //System.out.println("Rectangle: " + rectRatio);
        if (rectRatio < bestRatio) {
            bestRatio = rectRatio;
            bestMatch = ShapeType.RECTANGLE;
        }
        // ellipse
        double a = (right - left) / 2;
        double b = (bottom - top) / 2;
        double ellipseCirc = 2 * Math.PI * Math.sqrt((a * a + b * b) / 2);
        double ellipseRatio = Math.abs(1.0 - length / ellipseCirc);
        System.out.println("Ellipse: " + ellipseRatio);
        if (ellipseRatio < bestRatio) {
            bestRatio = ellipseRatio;
            bestMatch = ShapeType.CIRCLE;
        }

        // circle
        double xr = (right - left) / 2;
        double yr = (bottom - top) / 2;
        double r = Math.min(xr, yr);
        double circ = 2 * Math.PI * r;
        double circleRatio = Math.abs(1.0 - length / circ);
        System.out.println("Circle: " + circleRatio + "  Raw: " + (length/circ));
        if (circleRatio < bestRatio && minCorner > 10) {
            bestRatio = circleRatio;
            bestMatch = ShapeType.CIRCLE;
        }
        // line
        double lineDist = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double lineRatio = Math.abs(1.0 - length / lineDist);
        //System.out.println("Line: " + lineRatio);
        if (lineRatio < bestRatio) {
            bestRatio = lineRatio;
            bestMatch = ShapeType.LINE;
        }
        // triangle
        double centreX = (right+left)/2;
        double triangleDist = (right-left) + dist(left,bottom,centreX,top) + dist(right,bottom,centreX,top);
        double triangleRatio = Math.abs(1.0 - length / triangleDist);
        System.out.println("Triangle: " + triangleRatio + "  Raw: " + (length/triangleDist));
        System.out.println("MinDist to corner: " + minCorner);
        if (triangleRatio < bestRatio && minCorner < 10) {
            bestRatio = triangleRatio;
            bestMatch = ShapeType.TRIANGLE;
        }

        // check whether best is good enough
        if (bestRatio > 0.15) {
            bestMatch = ShapeType.NONE;
        }
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
