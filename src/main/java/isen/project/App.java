package isen.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main application class that extends JavaFX's Application class.
 */
public class App extends Application {

    // Scene to hold the UI elements
    private static Scene scene;

    /**
     * The entry point of the JavaFX application.
     * @param stage The primary stage for this application.
     * @throws Exception if an error occurs during the application startup.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Load the HomeScreen.fxml file into the scene with specified dimensions
        scene = new Scene(loadFXML("HomeScreen"), 900, 600);
        // Set the scene to the primary stage
        stage.setScene(scene);
        // Show the primary stage
        stage.show();
    }

    /**
     * Method to set the root of the scene to the FXML specified.
     * @param fxml The FXML file to be loaded as the root of the scene.
     * @throws IOException if an error occurs during the loading of the FXML file.
     */
    public static void setRoot(String fxml) throws IOException {
        // Set the root of the scene to the loaded FXML file
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Method to load an FXML file and return its root node.
     * @param fxml The FXML file to be loaded.
     * @return The root node of the loaded FXML file.
     * @throws IOException if an error occurs during the loading of the FXML file.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        // Create an FXMLLoader object with the specified FXML file path
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/isen/project/view/" + fxml + ".fxml"));
        // Load the FXML file and return its root node
        return fxmlLoader.load();
    }

    /**
     * The main method, which launches the JavaFX application.
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }

}