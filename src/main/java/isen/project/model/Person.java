package isen.project.model;

import java.time.LocalDate;

/**
 * Represents a person entity with various attributes.
 */
public class Person {
    
    // Attributes of a person
    private int idperson; // Unique identifier for the person
    private String lastname; // Last name of the person
    private String firstname; // First name of the person
    private String nickname; // Nickname of the person
    private String phoneNumber; // Phone number of the person
    private String address; // Address of the person
    private String emailAddress; // Email address of the person
    private LocalDate birthDate; // Date of birth of the person
    
    /**
     * Default constructor for Person class.
     */
    public Person() {
    }
    
    /**
     * Parameterized constructor for Person class.
     * @param idperson The ID of the person.
     * @param lastname The last name of the person.
     * @param firstname The first name of the person.
     * @param nickname The nickname of the person.
     * @param phoneNumber The phone number of the person.
     * @param address The address of the person.
     * @param emailAddress The email address of the person.
     * @param birthDate The birth date of the person.
     */
    public Person(int idperson, String lastname, String firstname, String nickname, String phoneNumber, String address, String emailAddress, LocalDate birthDate) {
        // Set values for all attributes of the person
        this.setIdperson(idperson);
        this.setLastname(lastname);
        this.setFirstname(firstname);
        this.setNickname(nickname);
        this.setPhoneNumber(phoneNumber);
        this.setAddress(address);
        this.setEmailAddress(emailAddress);
        this.setBirthDate(birthDate);
    }

    // Getters and setters for person attributes

    /**
     * Get the ID of the person.
     * @return The ID of the person.
     */
    public int getIdperson() {
        return idperson;
    }

    /**
     * Set the ID of the person.
     * @param idperson The ID of the person.
     */
    public void setIdperson(int idperson) {
        this.idperson = idperson;
    }

    /**
     * Get the last name of the person.
     * @return The last name of the person.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Set the last name of the person.
     * @param lastname The last name of the person.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Get the first name of the person.
     * @return The first name of the person.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set the first name of the person.
     * @param firstname The first name of the person.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get the nickname of the person.
     * @return The nickname of the person.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Set the nickname of the person.
     * @param nickname The nickname of the person.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Get the phone number of the person.
     * @return The phone number of the person.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the phone number of the person.
     * @param phoneNumber The phone number of the person.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get the address of the person.
     * @return The address of the person.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address of the person.
     * @param address The address of the person.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the email address of the person.
     * @return The email address of the person.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Set the email address of the person.
     * @param emailAddress The email address of the person.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Get the birth date of the person.
     * @return The birth date of the person.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Set the birth date of the person.
     * @param birthDate The birth date of the person.
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}