package fafij.server.requestbodies;

public class AddUser {
    private String journalName;
    private String login;
    private String role;

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AddUser(String journalName, String login, String role) {
        this.journalName = journalName;
        this.login = login;
        this.role = role;
    }
}
