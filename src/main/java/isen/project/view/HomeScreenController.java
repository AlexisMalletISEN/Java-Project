package isen.project.view;

import java.io.IOException;

import isen.project.App;
import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * Controller class for the HomeScreen.fxml file.
 * This class handles actions and events related to the Home screen.
 */
public class HomeScreenController {

    /**
     * Event handler for the button to access the database.
     * This method is called when the main button on the Home screen is clicked.
     * It navigates to the PersonOverview screen.
     * @throws IOException if an error occurs during the navigation process.
     */
    @FXML
    public void handleButton() throws IOException {   
        // Set the root of the scene to the PersonOverview.fxml file
        App.setRoot("PersonOverview");
    }
	
    /**
     * Event handler for the second button click action.
     * This method is called when the "Quit" button in the Home screen is clicked.
     * It closes the program.
     * @throws IOException if an error occurs during the exit process.
     */
	@FXML
	public void exitButton() throws IOException {
		Platform.exit();
	}
}
