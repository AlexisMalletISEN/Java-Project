package isen.project.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import isen.project.model.Person;
import isen.project.db.DataSourceFactory;
import isen.project.db.PersonDao;

/**
 * Test case class for the PersonDao class.
 * This class contains test cases to ensure the functionality of the PersonDao class.
 */
public class PersonDaoTestCase {
    
    // Instance of the PersonDao class to be tested
    private PersonDao personDao = new PersonDao();
    
    /**
     * Method executed before each test case.
     * Initializes the database with sample data.
     * @throws Exception if an error occurs during database initialization.
     */
    @Before
    public void initDb() throws Exception {
        // Establish database connection and create necessary tables
        Connection connection = DataSourceFactory.getDataSource().getConnection();
        Statement stmt = connection.createStatement();
        // Create person table if not exists
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS person (idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,lastname VARCHAR(45) NOT NULL, firstname VARCHAR(45) NOT NULL, nickname VARCHAR(45) NOT NULL, phone_number VARCHAR(15) NULL, address VARCHAR(200) NULL, email_address VARCHAR(150) NULL, birth_date DATE NULL);");
        // Clear existing data in the person table
        stmt.executeUpdate("DELETE FROM person");
        // Insert sample data into the person table
        stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) " 
                + "VALUES (1,'Doe','John','Johnny','1234567890','1234 Elm Street','doe.john@test.com','1990-01-01 12:00:00.000')");
        stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) " 
                + "VALUES (2,'Smith','Jane','Janie','0987654321','5678 Oak Street','smith.jane@test.com','1995-02-02 12:00:00.000')");
        stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) "
                + "VALUES (3,'Brown','Jim','Jimmy','6781234560','9101 Pine Street','brown.jim@test.com','2000-03-03 12:00:00.000')");
        // Close resources
        stmt.close();
        connection.close();        
    }
    
    /**
     * Test case to ensure the listPersons() method functionality.
     * Verifies that the list of persons retrieved matches the expected data.
     */
    @Test
    public void shouldListPersons() {
        // WHEN
        List<Person> persons = personDao.listPersons();
        // THEN
        // Check that the number of retrieved persons matches the expected count
        assertThat(persons).hasSize(3);
        // Test the returned persons are the expected ones
        assertThat(persons.stream().allMatch(person ->
            person.getIdperson() == 1 && 
            person.getLastname().equals("Doe") && 
            person.getFirstname().equals("John") && 
            person.getNickname().equals("Johnny") && 
            person.getPhoneNumber().equals("1234567890") && 
            person.getAddress().equals("1234 Elm Street") && 
            person.getEmailAddress().equals("doe.john@test.com") &&
            person.getBirthDate().equals(LocalDate.of(1990, 1, 1)) 
            ||
            person.getIdperson() == 2 &&
            person.getLastname().equals("Smith") &&
            person.getFirstname().equals("Jane") &&
            person.getNickname().equals("Janie") &&
            person.getPhoneNumber().equals("0987654321") &&
            person.getAddress().equals("5678 Oak Street") &&
            person.getEmailAddress().equals("smith.jane@test.com") &&
            person.getBirthDate().equals(LocalDate.of(1995, 2, 2))
            ||
            person.getIdperson() == 3 &&
            person.getLastname().equals("Brown") &&
            person.getFirstname().equals("Jim") &&
            person.getNickname().equals("Jimmy") &&
            person.getPhoneNumber().equals("6781234560") &&
            person.getAddress().equals("9101 Pine Street") &&
            person.getEmailAddress().equals("brown.jim@test.com") &&
            person.getBirthDate().equals(LocalDate.of(2000, 3, 3))
            ));
    }
    
    /**
     * Test case to ensure the insertPerson() method functionality.
     * Verifies that a person is successfully inserted into the database.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void shouldInsertPerson() throws Exception {
        // GIVEN
        Person person = new Person(4, "Jackson", "Michael", "King of Pop", "1234567890", "1234 Elm Street", "kingofpop@test.com", LocalDate.of(1958, 8, 29));
        // WHEN
        Person insertedPerson = personDao.insertPerson(person);
        // THEN
        // Check that the person has been inserted in the database
        Connection connection = DataSourceFactory.getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM person WHERE idperson = " + insertedPerson.getIdperson());
        assertThat(results.next()).isTrue();
        assertThat(results.getInt("idperson")).isEqualTo(insertedPerson.getIdperson());
        assertThat(results.getString("lastname")).isEqualTo("Jackson");
        assertThat(results.getString("firstname")).isEqualTo("Michael");
        assertThat(results.getString("nickname")).isEqualTo("King of Pop");
        assertThat(results.getString("phone_number")).isEqualTo("1234567890");
        assertThat(results.getString("address")).isEqualTo("1234 Elm Street");
        assertThat(results.getString("email_address")).isEqualTo("kingofpop@test.com");
        assertThat(results.getDate("birth_date").toLocalDate()).isEqualTo(LocalDate.of(1958, 8, 29));
        assertThat(results.next()).isFalse();
        results.close();
        statement.close();
        connection.close();
    }
    
    /**
     * Test case to ensure the updatePerson() method functionality.
     * Verifies that a person is successfully updated in the database.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void shouldUpdatePerson() throws Exception {
        // GIVEN
        // Create a new person
        Person person = new Person(0, "Allen", "Tim", "Timmy", "1234567890", "1234 Elm Street", "allen.tim@test.com", LocalDate.of(1970, 1, 1));
        // Insert the person into the database
        Person insertedPerson = personDao.insertPerson(person);
        // Create another person with updated information
        Person updatedPerson = new Person(0, "Asley", "Rick", "GOD", "6669996669", "666 Elm Street", "asley.rick@test.com", LocalDate.of(1, 1, 1));
        // WHEN
        // Update the person in the database
        personDao.updatePerson(insertedPerson.getIdperson(), updatedPerson);
		// THEN
		// Check that the person has been updated in the database
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery("SELECT * FROM person WHERE idperson = " + insertedPerson.getIdperson());
		assertThat(results.next()).isTrue();
		assertThat(results.getInt("idperson")).isEqualTo(insertedPerson.getIdperson());
		assertThat(results.getString("lastname")).isEqualTo("Asley");
		assertThat(results.getString("firstname")).isEqualTo("Rick");
		assertThat(results.getString("nickname")).isEqualTo("GOD");
		assertThat(results.getString("phone_number")).isEqualTo("6669996669");
		assertThat(results.getString("address")).isEqualTo("666 Elm Street");
		assertThat(results.getString("email_address")).isEqualTo("asley.rick@test.com");
		assertThat(results.getDate("birth_date").toLocalDate()).isEqualTo(LocalDate.of(1, 1, 1));
		assertThat(results.next()).isFalse();
		results.close();
		statement.close();
		connection.close();		
	}
	
    /**
     * Test case to ensure the deletePerson() method functionality.
     * Verifies that a person is successfully deleted from the database.
     * @throws Exception if an error occurs during the test.
     */
	@Test
	public void shouldDeletePerson() throws Exception {
		// GIVEN
		// Creates a new person that is inserted in the database
		Person person = new Person(0, "Asley", "Rick", "GOD", "6669996669", "666 Elm Street", "asley.rick@test.com", LocalDate.of(1, 1, 1));
		Person insertedPerson = personDao.insertPerson(person);
		// WHEN
		// That new person is deleted from the database
		personDao.deletePerson(insertedPerson.getIdperson());
		// THEN
		// Check that the person has been deleted from the database
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery("SELECT * FROM person WHERE idperson = " + insertedPerson.getIdperson());
		assertThat(results.next()).isFalse();
		results.close();
		statement.close();
		connection.close();
	}
	
	/**
     * Test case to ensure the searchPerson() method functionality.
     * Verifies that the search function successfully works with the database.
     * @throws Exception if an error occurs during the test.
     */
	@Test
	public void shouldSearchPerson() throws Exception {
		// GIVEN
		// New person inserted in the db
		Person person = new Person(0, "Jobs", "Steve", "AppleMan", "6667865435", "45 California Road", "steve.jobs@test.com", LocalDate.of(1, 1, 1));
		Person insertedPerson = personDao.insertPerson(person);
		// WHEN
		// a search is conducted with the lastname of the new person
		List<Person> persons = personDao.searchPerson(insertedPerson.getLastname());
		// THEN
		// Check that the search function works and the person can be found
		assertThat(persons).hasSize(1);
		assertThat(persons.get(0).getIdperson()).isEqualTo(insertedPerson.getIdperson());
		assertThat(persons.get(0).getLastname()).isEqualTo("Jobs");
		assertThat(persons.get(0).getFirstname()).isEqualTo("Steve");
		assertThat(persons.get(0).getNickname()).isEqualTo("AppleMan");
		assertThat(persons.get(0).getPhoneNumber()).isEqualTo("6667865435");
		assertThat(persons.get(0).getAddress()).isEqualTo("45 California Road");
		assertThat(persons.get(0).getEmailAddress()).isEqualTo("steve.jobs@test.com");
		assertThat(persons.get(0).getBirthDate()).isEqualTo(LocalDate.of(1, 1, 1));
	}
}
