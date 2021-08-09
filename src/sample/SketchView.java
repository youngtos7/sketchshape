package sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class SketchView extends Pane implements SketchModelListener
    {
        Canvas myCanvas;
        GraphicsContext gc;
        double canvasWidth, canvasHeight;
        SVGPath inkPath;
        SketchModel model;
        InteractionModel interactionModel;


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
        public void setInteractionModel(InteractionModel newInteractionModel) {
            interactionModel = newInteractionModel;
            interactionModel.setMainViewExtents(canvasWidth, canvasHeight);
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

            for (Groupable g : model.items) {
//                if (!interactionModel.selectionSet.contains(g)) {
                    g.draw(gc);
//                }
            }

            for (Groupable g : interactionModel.selectionSet) {
                g.drawSelected(gc);
            }

            gc.setStroke(Color.GREEN);
            gc.beginPath();
            gc.appendSVGPath(interactionModel.ink.svgPathContents);
            gc.stroke();
//            gc.restore();
//            System.out.println("Drawing View");
        }

        public void modelChanged() {
            draw();
        }
}
