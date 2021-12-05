package fafij.server.requestbodies;

public class DeleteNote {
    private long idNote;
    private String login;
    private String journalName;

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public long getIdNote() {
        return idNote;
    }

    public void setIdNote(long idNote) {
        this.idNote = idNote;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public DeleteNote(long idNote, String login, String journalName) {
        this.idNote = idNote;
        this.login = login;
        this.journalName = journalName;
    }
}
