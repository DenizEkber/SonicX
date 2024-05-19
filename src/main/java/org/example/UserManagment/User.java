package org.example.UserManagment;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String salt;

    public User(int id, String firstName, String lastName, String email, String password) {
        this(id, firstName, lastName, email, password, null);
    }
    public User(int id, String firstName, String lastName, String email, String password, String salt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    // Getter ve Setter metotları
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String getSalt() {
        return salt;
    }
}
