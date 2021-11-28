package fafij.server.requestbodies;

public class JournalName {
    private String journalName;

    public JournalName(){}

    public JournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }
}
