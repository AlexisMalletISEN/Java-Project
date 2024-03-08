package isen.project.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import isen.project.App;
import isen.project.db.PersonDao;
import isen.project.model.Person;
import isen.project.util.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller class for the PersonOverview.fxml file.
 * This class handles actions and events related to the Person overview screen.
 */
public class PersonOverviewController {

    // FXML fields for various UI components
	@FXML
	TextField searchField;
	@FXML
	TableView<Person> personTable;
	@FXML
	TableColumn<Person, Integer> idColumn;
	@FXML
	TableColumn<Person, String> lastNameColumn;
	@FXML
	TableColumn<Person, String> firstNameColumn;
	@FXML
	TableColumn<Person, String> nicknameColumn;
	@FXML
	TableColumn<Person, String> phoneColumn;
	@FXML
	TableColumn<Person, String> addressColumn;
	@FXML
	TableColumn<Person, String> emailColumn;
	@FXML
	TableColumn<Person, LocalDate> birthdateColumn;
	
	// Selected person from the table
    private Person selectedPerson;
    
    // Data access object for person operations
    private PersonDao personDao = new PersonDao();
    
    // List to store retrieved persons from the database
    private List<Person> persons = personDao.listPersons();
    
    /**
     * Method to refresh the displayed list of persons.
     */
    private void refreshList() {
        personTable.refresh();
        personTable.getSelectionModel().clearSelection();
    }
    
    /**
     * Method to populate the table with persons data.
     */
    private void populateList() {
        personTable.getItems().clear();
        personTable.getItems().addAll(persons);
        refreshList();
    }
    
    /**
     * Method called upon initialization of the controller.
     * Sets up table columns and populates the table with data.
     */
    public void initialize() {
        // Set cell value factories for each column
        idColumn.setCellValueFactory(new PersonValueFactoryId());
        lastNameColumn.setCellValueFactory(new PersonValueFactoryLastname());
        firstNameColumn.setCellValueFactory(new PersonValueFactoryFirstname());
        nicknameColumn.setCellValueFactory(new PersonValueFactoryNickname());
        phoneColumn.setCellValueFactory(new PersonValueFactoryPhone());
        addressColumn.setCellValueFactory(new PersonValueFactoryAddress());
        emailColumn.setCellValueFactory(new PersonValueFactoryEmail());
        birthdateColumn.setCellValueFactory(new PersonValueFactoryBirthDate());
        
        // Populate the table with data
        populateList();
        
        // Add listener for selection change in the table
        personTable.getSelectionModel().selectedItemProperty().addListener(new PersonChangeListener() {
            @Override
            public void handleNewValue(Person newValue) {
                selectedPerson = newValue;
            }
        });
    }
	
    /**
     * Event handler for searching a person.
     * Searches for a person in the database using a lastname in the text field 
     * next to the button, and updates the table list with the result if any is found.
     * If no lastname is entered, it shows the full list. 
     * @throws Exception if an error occurs during search process.
     */
	@FXML
	public void searchPerson() throws Exception {
		String nameToSearch = searchField.getText();
		persons = personDao.searchPerson(nameToSearch);
		populateList();
	}
	
	/**
     * Event handler for refreshing the table.
     * Refreshes the content showed of the database on the screen.
     * Can be useful when a search as been done and the user quickly wants to see the
     * full table.
     */
	@FXML
	public void refreshButton() {
		persons = personDao.listPersons();
		populateList();
	}
	
	/**
     * Event handler for deleting a person.
     * Deletes the selected person from the database and updates the table.
     * @throws Exception if an error occurs during deletion process.
     */
    @FXML
    public void handleDeletePerson() throws Exception {
        personDao.deletePerson(selectedPerson.getIdperson());
        persons = personDao.listPersons();
        populateList();
    }
    
    /**
     * Event handler for updating a person.
     * Opens the PersonForm.fxml file for editing the selected person.
     * @throws IOException if an error occurs during navigation.
     */
    @FXML
    public void handleUpdatePerson() throws IOException {
        PersonFormController.retrieveData(selectedPerson);
        App.setRoot("PersonForm");
    }
    
    /**
     * Event handler for adding a new person.
     * Opens the PersonForm.fxml file for adding a new person.
     * @throws IOException if an error occurs during navigation.
     */
    @FXML
    public void handleAddPerson() throws IOException {
        PersonFormController.retrieveData(null);
        App.setRoot("PersonForm");
    }
	
    /**
     * Event handler for exiting the program.
     * Quits the program (like the "Quit" button on the Home Screen).
     * @throws IOException if an error occurs during exit.
     */
	@FXML
	public void exitButton() throws IOException {
		Platform.exit();
	}
}
