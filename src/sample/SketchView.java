package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class SketchView extends Pane implements SketchModelListener {
    Canvas myCanvas;
    GraphicsContext gc;
    double canvasWidth, canvasHeight;
    SketchModel model;
    InteractionModel iModel;
    SVGPath inkPath;

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
        // set up event handling
        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseDragged(controller::handleDragged);
        myCanvas.setOnMouseReleased(controller::handleReleased);
        //myCanvas.setOnMousePressed(e -> controller.handlePressed(e, e.getX() / canvasWidth, e.getY() / canvasHeight));
        //myCanvas.setOnMouseDragged(e -> controller.handleDrag(e, e.getX() / canvasWidth, e.getY() / canvasHeight));
        //myCanvas.setOnMouseReleased(e -> controller.handleRelease(e, e.getX() / canvasWidth, e.getY() / canvasHeight));

        //this.setOnKeyPressed(controller::handleKeyPressed);
        //this.setOnKeyReleased(controller::handleKeyReleased);
        //this.requestFocus();
    }

    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
        iModel.setMainViewExtents(canvasWidth, canvasHeight);
    }

    public void draw() {
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2.0);
        //gc.strokeLine(0,200,600,200);
        //gc.strokeLine(0,400,600,400);
        //gc.strokeLine(200,0,200,1000);
        //gc.strokeLine(400,0,400,1000);


        //model.items.forEach(s -> s.draw(gc,0,0));
        //if (iModel.selectionSet != null) {
        //    iModel.selectionSet.forEach(xs -> xs.drawSelected(gc,0,0));
        //}
        for (Groupable g : model.items) {
            if (!iModel.selectionSet.contains(g)) {
                g.draw(gc);
            }
        }
        for (Groupable g : iModel.selectionSet) {
                g.drawSelected(gc);
        }

        //gc.save();
        gc.setStroke(Color.GREEN);
        gc.beginPath();
        gc.appendSVGPath(iModel.ink.svgPathContents);
        gc.stroke();
        //gc.restore();
    }

    public void modelChanged() {
        draw();
    }
}
