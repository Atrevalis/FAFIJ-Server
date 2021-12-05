package fafij.server.requestbodies;

public class Login {
    private String login;

    public Login(){}

    public Login(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
