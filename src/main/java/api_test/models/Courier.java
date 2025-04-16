package models;

public class Courier {

    private String id;
    private String login;
    private String firstName;

    public Courier(String id, String login, String firstName) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
