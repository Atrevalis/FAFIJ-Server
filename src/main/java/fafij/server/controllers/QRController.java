package fafij.server.controllers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import fafij.server.requestbodies.DecodeQR;
import fafij.server.requestbodies.QRCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

@RestController
@RequestMapping("/private")
public class QRController {

    @PostMapping(path = "/QRcode", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody
    BufferedImage generateQRCodeImage(@RequestBody QRCode qrCode) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCode.getValue(), BarcodeFormat.QR_CODE, 250, 250);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @PostMapping(path = "/decodeQR")
    public @ResponseBody String readQR(@RequestBody DecodeQR decodeQR) throws IOException, NotFoundException
    {
        /*File convFile = new File(System.getProperty("java.io.tmpdir")+"/qr");
        qrCode.transferTo(convFile);*/
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource((BufferedImage) decodeQR.getQr())));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }
}
