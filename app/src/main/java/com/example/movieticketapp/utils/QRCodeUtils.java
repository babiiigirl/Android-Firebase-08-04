package com.example.movieticketapp.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeUtils {

    public static Bitmap generateQRCode(String content, int width, int height) throws WriterException {
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
        BarcodeEncoder encoder = new BarcodeEncoder();
        return encoder.createBitmap(bitMatrix);
    }
}
