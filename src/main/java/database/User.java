package database;

public class User {
    private String username, firstName, lastName, email, contact, bio;

    public User(String username, String firstName, String lastName, String email, String contact, String bio) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.bio = bio;
    }

    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getContact() { return contact; }
    public String getBio() { return bio; }
}

