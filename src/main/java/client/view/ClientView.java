package client.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientView {
    private FXMLLoader loader;
    private final Stage stage;
    public ClientView(Stage stage) {
        this.stage = stage;
    }
    public void runInterface() {
        Platform.runLater(() -> {
            try {
                System.out.println("Loading Client Interface");
                loader = new FXMLLoader(getClass().getResource("/fxml/client/landing_page.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setTitle("LEONARDO D' Cafe [CLIENT]");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
