package isen.project.view;

import isen.project.App;
import isen.project.db.PersonDao;
import isen.project.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Controller class for the PersonForm.fxml file.
 * This class handles actions and events related to the Person form.
 */
public class PersonFormController {
    
    // Static variable to hold the person data
    private static Person person;
    
    // FXML fields for various input elements in the form
    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField nicknameField;
    @FXML
    TextField phoneField;
    @FXML
    TextField addressField;
    @FXML
    TextField emailField;
    @FXML
    DatePicker birthdatePicker;
    
    /**
     * Method to retrieve data for a person.
     * @param p The Person object containing the data to be displayed in the form.
     */
    public static void retrieveData(Person p) {
        person = p;
    }
    
    /**
     * Method to initialize the form with data if available.
     */
    public void initialize() {
        if (person != null) {
            // Populate form fields with person data
            firstNameField.setText(person.getFirstname());
            lastNameField.setText(person.getLastname());
            nicknameField.setText(person.getNickname());
            phoneField.setText(person.getPhoneNumber());
            addressField.setText(person.getAddress());
            emailField.setText(person.getEmailAddress());
            birthdatePicker.setValue(person.getBirthDate());
        }
    }
    
    /**
     * Event handler for the Clear button.
     * Clears all input fields in the form.
     */
    @FXML
    public void handleClearButton() {
        // Clear all input fields
        firstNameField.clear();
        lastNameField.clear();
        nicknameField.clear();
        phoneField.clear();
        addressField.clear();
        emailField.clear();
        birthdatePicker.setValue(null);
    }
    
    /**
     * Event handler for the Save button.
     * Saves or updates the person data based on user input.
     * @throws Exception if an error occurs during the save/update process.
     */
    @FXML
    public void handleSaveButton() throws Exception {
        // Create a new instance of PersonDao
        PersonDao personDao = new PersonDao();
        // Create a new Person object to store form data
        Person personToSave = new Person();
        // Set form data to the Person object
        personToSave.setFirstname(firstNameField.getText());
        personToSave.setLastname(lastNameField.getText());
        personToSave.setNickname(nicknameField.getText());
        personToSave.setPhoneNumber(phoneField.getText());
        personToSave.setAddress(addressField.getText());
        personToSave.setEmailAddress(emailField.getText());
        personToSave.setBirthDate(birthdatePicker.getValue());
        
        // If person data is available, update the existing person
        if (person != null) {
            Integer id = person.getIdperson();
            personDao.updatePerson(id, personToSave);
        }
        // Otherwise, insert a new person
        else {
            personDao.insertPerson(personToSave);
        }
        
        // Navigate back to the PersonOverview screen
        App.setRoot("PersonOverview");
    }
    
    /**
     * Event handler for the Cancel button.
     * Cancels the current action and navigates back to the PersonOverview screen.
     * @throws Exception if an error occurs during the navigation process.
     */
    @FXML
    public void handleCancelButton() throws Exception {
        // Navigate back to the PersonOverview screen
        App.setRoot("PersonOverview");
    }
}