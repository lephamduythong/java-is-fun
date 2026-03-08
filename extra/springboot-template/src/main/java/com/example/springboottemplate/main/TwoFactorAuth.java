package com.example.springboottemplate.main;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class TwoFactorAuth {

    public static void main(String[] args) throws Exception {
        // String issuer = "VIB";
        // String account = "thong.lepham@vib.com.vn";

        // // 1. Tạo Secret Key ngẫu nhiên
        // GoogleAuthenticator gAuth = new GoogleAuthenticator();
        // final GoogleAuthenticatorKey key = gAuth.createCredentials();
        // String secret = key.getKey(); 
        // System.out.println("Secret Key " + secret); // Copy this to frontend to generate QR code and verify OTP later

        // // 2. Tạo URI chuẩn otpauth
        // // Format: otpauth://totp/MyBankingApp:user@email?secret=...
        // String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(issuer, account, key);

        // System.out.println( "URI otpauth: " + otpAuthURL);

        // // 3. Chuyển URI thành chuỗi QR Code Base64
        // String qrBase64 = generateQRCodeBase64(otpAuthURL);
        // System.out.println("QR Base64 String (Dùng cho thẻ img): \n" + qrBase64);

        var secret = "Y36SEEQAQOGCLT5F5HZIDMQF5YWMHZXB";
        var code = 772897; // OTP code user nhập từ app Google Authenticator
        boolean isValid = verifyCode(secret, code);
        System.out.println("Is OTP valid? " + isValid);
    }

    public static String generateQRCodeBase64(String text) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        
        byte[] pngData = outputStream.toByteArray();
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(pngData);
    }

    public static boolean verifyCode(String secret, int code) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.authorize(secret, code); // True => login successful, False => invalid OTP
    }
}