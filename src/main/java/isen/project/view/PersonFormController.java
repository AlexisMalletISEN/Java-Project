package isen.project.view;

import isen.project.App;
import isen.project.db.PersonDao;
import isen.project.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PersonFormController {
	
	private static Person person;
	
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
	
	public static void retrieveData(Person p) {
        person = p;
	}
	
	public void initialize() {
		if (person != null) {
			firstNameField.setText(person.getFirstname());
			lastNameField.setText(person.getLastname());
			nicknameField.setText(person.getNickname());
			phoneField.setText(person.getPhoneNumber());
			addressField.setText(person.getAddress());
			emailField.setText(person.getEmailAddress());
			birthdatePicker.setValue(person.getBirthDate());
		}
	}
	
	@FXML
	public void handleClearButton() {
		firstNameField.clear();
		lastNameField.clear();
		nicknameField.clear();
		phoneField.clear();
		addressField.clear();
		emailField.clear();
		birthdatePicker.setValue(null);
	}
	
	@FXML
	public void handleSaveButton() throws Exception {
		PersonDao personDao = new PersonDao();
		Person personToSave = new Person();
		personToSave.setFirstname(firstNameField.getText());
		personToSave.setLastname(lastNameField.getText());
		personToSave.setNickname(nicknameField.getText());
		personToSave.setPhoneNumber(phoneField.getText());
		personToSave.setAddress(addressField.getText());
		personToSave.setEmailAddress(emailField.getText());
		personToSave.setBirthDate(birthdatePicker.getValue());
		
		if (person != null) {
			Integer id = person.getIdperson();
			personDao.updatePerson(id, personToSave);
		}
		else {
			personDao.insertPerson(personToSave);
		}
		
		App.setRoot("PersonOverview");
	}
	
	@FXML
	public void handleCancelButton() throws Exception {
		App.setRoot("PersonOverview");
	}
}
