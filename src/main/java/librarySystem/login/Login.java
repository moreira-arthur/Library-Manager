package librarySystem.login;

/**
 * Class to represent a login
 */
public class Login {
    private String username;
    private String password;

    /**
     * Constructor to create a login
     * @param new_username The username
     * @param new_password The password
     */
    public Login(String new_username, String new_password) {
        username = new_username;
        password = new_password;
    }

    /**
     * Method to get the username
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to get the password
     * @return The password
     */
    public String getPassword() {
        return password;
    }
}