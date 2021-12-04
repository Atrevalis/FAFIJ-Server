package fafij.server.requestbodies;

import java.awt.*;

public class DecodeQR {
    private Image qr;

    public DecodeQR(){}

    public Image getQr() {
        return qr;
    }

    public void setQr(Image qr) {
        this.qr = qr;
    }

    public DecodeQR(Image qr) {
        this.qr = qr;
    }
}
