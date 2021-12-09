package fafij.server.requestbodies;

public class UpdateNote {
    private Long id;
    private String date;
    private Long sum;
    private String category;
    private String comment;
    private String login;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UpdateNote(Long id, String date, Long sum, String category, String comment, String login) {
        this.id = id;
        this.date = date;
        this.sum = sum;
        this.category = category;
        this.comment = comment;
        this.login = login;
    }

    public UpdateNote(){}
}


