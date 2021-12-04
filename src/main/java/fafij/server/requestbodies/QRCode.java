package fafij.server.requestbodies;

public class QRCode {
    private String value;

    public QRCode(){}

    public QRCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
