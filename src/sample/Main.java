package sample;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

//  implements EventHandler<ActionEvent>
public class Main extends Application{

    GraphicsContext gc;
    ArrayList<Point2D> points;
    String svgPathContents;
    double ratioA, ratioB, ratioC;
    double length;
    double prevX = 0, prevY = 0;
    double[] xs, ys, hxs, hys;
    private enum State {
        READY, INK_OR_UNSELECT, HANDLING, INKING, DRAGGING
    }
    State currentState = State.READY;

    public void start(double x, double y) {
        points.clear();
        points.add(new Point2D(x, y));
        svgPathContents = "M" + x + "," + y;
    }

    public void addPoint(double x, double y) {
        points.add(new Point2D(x, y));
        svgPathContents += " L" + x + "," + y;
//        System.out.println(svgPathContents);
    }

    public void clear() {
        points.clear();
        svgPathContents = "";
    }

    public void handlePressed(MouseEvent event) {

    }

    public void handleKeyReleased(MouseEvent event) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        points = new ArrayList<>();
        StackPane root = new StackPane();
        SketchView view = new SketchView();
        root.getChildren().add(view);

        SketchModel model = new SketchModel();
        InteractionModel interactionModel = new InteractionModel();
        SketchController controller = new SketchController();

        model.addSubscriber(view);
        interactionModel.addSubscriber(view);
        view.setInteractionModel(interactionModel);
        view.setModel(model);
        view.setController(controller);
        controller.setModel(model);
        controller.setInteractionModel(interactionModel);

//        Canvas myCanvas = new Canvas(1000, 1000);
//        gc = myCanvas.getGraphicsContext2D();
//        root.getChildren().add(myCanvas);

        primaryStage.setTitle("Shape Sketch");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        view.draw();

//        SVGPath inkPath = new SVGPath();
//        XLine xLine = new XLine(110, 220, 220, 450);
//
//        primaryStage.setTitle("Shape Sketch");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//
//        myCanvas.setOnMousePressed(event -> {
//            if(currentState == State.READY) {
//                xLine.xs[0] = event.getX();
//                xLine.ys[0] = event.getY();
//            }
//
//            if(currentState == State.DRAGGING){
//                boolean bCurrentContains = contains(xLine, event.getX(), event.getX());
//                System.out.println(prevX + " " + prevY + " --- Boolean = " + bCurrentContains);
//            }
//
//            prevX = event.getX();
//            prevY = event.getY();
//            start(event.getX(), event.getY());
//
//        });
//        myCanvas.setOnMouseDragged(event -> {
//
//            addPoint(event.getX(), event.getY());
//            draw();
//
//            if(currentState == State.DRAGGING){
//                double dX = event.getX() - prevX;
//                double dY = event.getY() - prevY;
//                prevX = event.getX();
//                prevY = event.getY();
//                System.out.println(dX + " --- " + dY);
//                xLine.move(dX, dY);
//                gc.clearRect(0, 0, 1000, 1000);
//                draw();
//                xLine.draw(gc);
//            }
//        });
//        myCanvas.setOnMouseReleased(event -> {
//
//
//            if(currentState == State.READY){
//                xLine.xs[1] = event.getX();
//                xLine.ys[1] = event.getY();
//                xLine.draw(gc);
//            }
//
//
//            draw();
//
//            currentState = State.DRAGGING;
//
//            System.out.println("Released");
//        });
//        primaryStage.getScene().setOnKeyReleased(event -> {
//            System.out.println(event.getText());
//            event
//        });
//        primaryStage.getScene().setOnKeyPressed(event -> {
//            System.out.println(event.getText());
//
//        });
//        draw();

    }

    public void draw() {
//        gc.clearRect(0, 0, 1000, 1000);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2.0);
        gc.setStroke(Color.GREEN);
        gc.beginPath();
//        System.out.println(svgPathContents);
        gc.appendSVGPath(svgPathContents);
        gc.stroke();
    }

    public boolean contains(XLine xLine, double x, double y){
        length = dist( xLine.xs[0], xLine.ys[0], xLine.xs[1], xLine.ys[1]);
        ratioA = (xLine.ys[0] - xLine.ys[1]) / length;
        ratioB = (xLine.xs[1] - xLine.xs[0]) / length;
        ratioC = -1 * ((xLine.ys[0] - xLine.ys[1]) * xLine.xs[0] + (xLine.xs[1] - xLine.xs[0]) * xLine.ys[0]) / length;

        return Math.abs(distanceFromLine(x, y)) < 10
                && dist(x, y, xLine.xs[0], xLine.ys[0]) < length + 10 &&
                dist(x, y, xLine.xs[1], xLine.ys[1]) < length + 10;
    }

    public double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public double distanceFromLine(double x, double y) {
        return ratioA * x + ratioB * y + ratioC;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
