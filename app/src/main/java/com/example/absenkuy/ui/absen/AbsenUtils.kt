package com.example.absenkuy.ui.absen

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File

object AbsenUtils {
    fun convertBitmapToFile(context: Context, bitmap: Bitmap): File {
        val file = File(context.cacheDir, "photo.jpg")
        file.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos)
        file.writeBytes(bos.toByteArray())
        return file
    }

    fun getBase64FromBitmap(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}