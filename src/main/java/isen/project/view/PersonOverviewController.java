package isen.project.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import isen.project.App;
import isen.project.db.PersonDao;
import isen.project.model.Person;
import isen.project.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {

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
	
	private Person selectedPerson;
	
	private PersonDao personDao = new PersonDao();
	
	private List<Person> persons = personDao.listPersons();
	
	private void refreshList() {
		personTable.refresh();
		personTable.getSelectionModel().clearSelection();
	}
	
	private void populateList() {
		personTable.getItems().clear();
		personTable.getItems().addAll(persons);
		refreshList();
	}
	
	public void initialize() {
		
		idColumn.setCellValueFactory(new PersonValueFactoryId());
		lastNameColumn.setCellValueFactory(new PersonValueFactoryLastname());
		firstNameColumn.setCellValueFactory(new PersonValueFactoryFirstname());
		nicknameColumn.setCellValueFactory(new PersonValueFactoryNickname());
		phoneColumn.setCellValueFactory(new PersonValueFactoryPhone());
		addressColumn.setCellValueFactory(new PersonValueFactoryAddress());
		emailColumn.setCellValueFactory(new PersonValueFactoryEmail());
		birthdateColumn.setCellValueFactory(new PersonValueFactoryBirthDate());
		
		populateList();
		
		personTable.getSelectionModel().selectedItemProperty().addListener(new PersonChangeListener() {

			@Override
			public void handleNewValue(Person newValue) {
				selectedPerson = newValue;
			}
		});
	}
	
	@FXML
	public void handleDeletePerson() throws Exception {
		personDao.deletePerson(selectedPerson.getIdperson());
		persons = personDao.listPersons();
		populateList();
	}
	
	@FXML
	public void handleUpdatePerson() throws IOException {
		PersonFormController.retrieveData(selectedPerson);
		App.setRoot("PersonForm");
	}
	
	@FXML
	public void handleAddPerson() throws IOException {
		PersonFormController.retrieveData(null);
		App.setRoot("PersonForm");
	}
}
