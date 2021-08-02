package sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class SketchView extends Pane
    {
        Canvas myCanvas;
        GraphicsContext gc;
        double canvasWidth, canvasHeight;
        SVGPath inkPath;
        SketchModel model;

        public SketchView() {
            canvasWidth = 1000;
            canvasHeight = 1000;
            setStyle("-fx-background-color: lightgrey");
            myCanvas = new Canvas(canvasWidth, canvasHeight);
            gc = myCanvas.getGraphicsContext2D();
            getChildren().add(myCanvas);
            inkPath = new SVGPath();
        }

        public void setModel(SketchModel newModel) {
            model = newModel;
        }

        public void setController(SketchController controller) {
            myCanvas.setOnMousePressed(controller::handlePressed);
            myCanvas.setOnMouseDragged(controller::handleDragged);
            myCanvas.setOnMouseReleased(controller::handleReleased);
        }
        public void draw() {
            gc.clearRect(0, 0, canvasWidth, canvasHeight);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2.0);
            gc.setStroke(Color.GREEN);
            gc.beginPath();
            gc.appendSVGPath(model.ink.svgPathContents);
            gc.stroke();
//            gc.restore();
//            System.out.println("Drawing View");
        }
        public void modelChanged() {
            draw();
        }
}
