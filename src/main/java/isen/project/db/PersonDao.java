package isen.project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import isen.project.model.Person;

/**
 * Data Access Object (DAO) class for interacting with the 'person' table in the database.
 * This class handles CRUD (Create, Read, Update, Delete) operations for the 'person' table.
 */
public class PersonDao {

    /**
     * Method to initialize the database by creating the 'person' table if it doesn't exist.
     * @throws Exception if an error occurs during database initialization.
     */
    public void initDb() throws Exception {
        Connection connection = DataSourceFactory.getDataSource().getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS person (idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,lastname VARCHAR(45) NOT NULL, firstname VARCHAR(45) NOT NULL, nickname VARCHAR(45) NOT NULL, phone_number VARCHAR(15) NULL, address VARCHAR(200) NULL, email_address VARCHAR(150) NULL, birth_date DATE NULL);");
        stmt.close();
        connection.close();
    }

    /**
     * Method to retrieve a list of all persons from the 'person' table.
     * @return A list of Person objects representing all persons in the database.
     */
    public List<Person> listPersons() {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet results = statement.executeQuery("SELECT * FROM person")) {
                    while (results.next()) {
                        persons.add(new Person(
                                results.getInt("idperson"),
                                results.getString("lastname"),
                                results.getString("firstname"),
                                results.getString("nickname"),
                                results.getString("phone_number"),
                                results.getString("address"),
                                results.getString("email_address"),
                                results.getDate("birth_date").toLocalDate()));
                    }
                }
            }
            return persons;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // return an empty list if there's an error
        }
    }

    /**
     * Method to insert a new person into the 'person' table.
     * @param person The Person object representing the person to be inserted.
     * @return The inserted Person object with its database-generated ID.
     * @throws Exception if an error occurs during the insertion process.
     */
    public Person insertPerson(Person person) throws Exception {
        try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
            String sqlQuery = "INSERT INTO person(lastname, firstname, nickname, phone_number, address, email_address, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, person.getLastname());
                statement.setString(2, person.getFirstname());
                statement.setString(3, person.getNickname());
                statement.setString(4, person.getPhoneNumber());
                statement.setString(5, person.getAddress());
                statement.setString(6, person.getEmailAddress());
                statement.setDate(7, java.sql.Date.valueOf(person.getBirthDate()));
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        person.setIdperson(generatedKeys.getInt(1));
                    }
                }
            }
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to update an existing person in the 'person' table.
     * @param id The ID of the person to be updated.
     * @param person The updated Person object with new data.
     * @throws Exception if an error occurs during the update process.
     */
    public void updatePerson(Integer id, Person person) throws Exception {
        try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
            String sqlQuery = "UPDATE person SET lastname = ?, firstname = ?, nickname = ?, phone_number = ?, address = ?, email_address = ?, birth_date = ? WHERE idperson = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setString(1, person.getLastname());
                statement.setString(2, person.getFirstname());
                statement.setString(3, person.getNickname());
                statement.setString(4, person.getPhoneNumber());
                statement.setString(5, person.getAddress());
                statement.setString(6, person.getEmailAddress());
                statement.setDate(7, java.sql.Date.valueOf(person.getBirthDate()));
                statement.setInt(8, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to delete a person from the 'person' table by their ID.
     * @param id, The ID of the person to be deleted.
     * @throws Exception if an error occurs during the deletion process.
     */
    public void deletePerson(Integer id) throws Exception {
        try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
            String sqlQuery = "DELETE FROM person WHERE idperson = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    /**
     * Method to search a person in the database by their lastname from a string input.
     * If the input is empty, it returns the full content of the database.
     * @param searchInput, The lastname provided in the text field to search the person
     * @throws Exception if an error occurs during the search process.
     */
	public List<Person> searchPerson(String searchInput) throws Exception {
		List<Person> persons = new ArrayList<>();
		if(searchInput.length() == 0) {
			return listPersons();
		}
		else {
			try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
				try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE lastname = ?")){
					statement.setString(1, searchInput);
					try(ResultSet results = statement.executeQuery()){
						while (results.next()) {
							persons.add(new Person(
									results.getInt("idperson"), 
									results.getString("lastname"),
									results.getString("firstname"), 
									results.getString("nickname"),
									results.getString("phone_number"), 
									results.getString("address"),
									results.getString("email_address"), 
									results.getDate("birth_date").toLocalDate()));
						}
					}
				}
				return persons;
			} catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<>(); // return an empty list if there's an error
			}
		}
	}
}
