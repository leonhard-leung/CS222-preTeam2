package util;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class LoadingScreenUtility {
    public static void showLoadingIndicator(Pane parentPane) {
        ProgressIndicator loadingIndicator = new ProgressIndicator();

        StackPane stackPane = new StackPane(loadingIndicator);
        stackPane.setStyle("-fx-background-color: transparent");

        stackPane.setMinSize(35, 35);
        stackPane.setMaxSize(35, 35);

        StackPane.setAlignment(loadingIndicator, Pos.CENTER);

        parentPane.getChildren().add(stackPane);
    }

    public static void hideLoadingIndicator(Pane parentPane) {
        parentPane.getChildren().removeIf(node -> node instanceof StackPane);
    }
} // end of LoadingScreenUtility
