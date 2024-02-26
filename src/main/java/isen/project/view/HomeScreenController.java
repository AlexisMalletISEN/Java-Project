package isen.project.view;

import java.io.IOException;

import isen.project.App;
import javafx.fxml.FXML;

public class HomeScreenController {

	@FXML
	public void handleButton() throws IOException {	
        App.setRoot("PersonOverview");
    }
}
