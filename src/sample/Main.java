package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane root = new StackPane();
        SketchView view = new SketchView();
        root.getChildren().add(view);

        SketchModel model = new SketchModel();
        InteractionModel iModel = new InteractionModel();
        SketchController controller = new SketchController();

        model.addSubscriber(view);
        iModel.addSubscriber(view);
        view.setModel(model);
        view.setInteractionModel(iModel);
        view.setController(controller);
        controller.setInteractionModel(iModel);
        controller.setModel(model);

        primaryStage.setTitle("Shape Sketch");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.getScene().setOnKeyReleased(controller::handleKeyReleased);
        primaryStage.getScene().setOnKeyPressed(controller::handleKeyPressed);
        view.draw();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
