package com.techsphere.asociaplan.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream

public suspend fun generate_qr_code (datosString: String) : String {

    val writer = QRCodeWriter()
    try {
        val bitMatrix: BitMatrix = writer.encode(datosString, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        val filePath = Environment.getExternalStorageDirectory().absolutePath + "/Documents/qr_image.png"
        val file = File(filePath)
        val fileOutputStream = FileOutputStream(file)
        bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()

        return filePath
//        return bmp
    } catch (e: WriterException) {
        e.printStackTrace()
    }
    return ""
}