package com.aprendiendo.themovie.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

fun isConnected(context: Context): Boolean {
    val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetworkInfo
    return network != null && network.isConnectedOrConnecting
}

fun getLanguage(): String =
        Locale.getDefault().displayLanguage

fun getBitmapFromURL(src: String): Bitmap? {
    try {
        val url = URL(src)
        val connection = url.openConnection() as HttpURLConnection
        connection.setDoInput(true)
        connection.connect()
        val input = connection.getInputStream()
        return BitmapFactory.decodeStream(input)
    } catch (e: IOException) {
        throw e
    }

}

private fun saveToInternalStorage(name : String, bitmapImage: Bitmap, context: Context): String {
    val cw = ContextWrapper(context.applicationContext)
    // path to /data/data/yourapp/app_data/imageDir
    val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
    // Create imageDir
    Log.d("PATH", directory.path)
    val mypath = File(directory, name+".jpg")

    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(mypath)
        // Use the compress method on the BitMap object to write image to the OutputStream
        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            fos!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    return directory.absolutePath
}

const val ERROR401 = ""
const val ERROR404 = ""
const val ERROR_PARSE =""
const val QUERY = "query"
const val ID ="id"