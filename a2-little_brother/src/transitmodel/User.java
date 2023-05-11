package transitmodel;

/**
 * An abstract class of User in the transit system.
 */
public abstract class User {
    private final String email;
    private String name;

    /**
     * Initialize the User with its name and email.
     *
     * @param name  name of the user
     * @param email email of the user
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Get the name of this User.
     *
     * @return name of this User.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Change the name of this User.
     *
     * @param name name to be changed to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the email of this User.
     *
     * @return email of this User.
     */
    public String getEmail() {
        return this.email;
    }
}
