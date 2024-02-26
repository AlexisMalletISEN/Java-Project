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

public class PersonDaoTestCase {
	
	private PersonDao personDao = new PersonDao();
	
	@Before
	public void initDb() throws Exception {
        Connection connection = DataSourceFactory.getDataSource().getConnection();
        Statement stmt = connection.createStatement();
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS person (idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,lastname VARCHAR(45) NOT NULL, firstname VARCHAR(45) NOT NULL, nickname VARCHAR(45) NOT NULL, phone_number VARCHAR(15) NULL, address VARCHAR(200) NULL, email_address VARCHAR(150) NULL, birth_date DATE NULL);");
		stmt.executeUpdate("DELETE FROM person");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) " 
				+ "VALUES (1,'Doe','John','Johnny','1234567890','1234 Elm Street','doe.john@test.com','1990-01-01 12:00:00.000')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) " 
				+ "VALUES (2,'Smith','Jane','Janie','0987654321','5678 Oak Street','smith.jane@test.com','1995-02-02 12:00:00.000')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) "
				+ "VALUES (3,'Brown','Jim','Jimmy','6781234560','9101 Pine Street','brown.jim@test.com','2000-03-03 12:00:00.000')");
		stmt.close();
		connection.close();        
	}
	
	@Test
	public void shouldListPersons() {
        // WHEN
        List<Person> persons = personDao.listPersons();
        // THEN
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
	
	@Test
	public void shouldDeletePerson() throws Exception {
		// GIVEN
		// WHEN
		// THEN
	}
	
	@Test
	public void shouldUpdatePerson() throws Exception {
		// GIVEN
		// WHEN
		// THEN
	}
}
