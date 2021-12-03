package fafij.server.requestbodies;

public class LoginJournal {
    private String login;
    private String journalName;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public LoginJournal(String login, String journalName) {
        this.login = login;
        this.journalName = journalName;
    }
}
