package com.aprendiendo.themovie.util

import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity

abstract class CheckPermissions(private val activity: AppCompatActivity, private val permissions: List<String>,
                       private val requestCode : Int) {
    val perms: ArrayList<String> = ArrayList()
    fun onVerifying() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                    perms.add(permission)
            }
            if (perms.size != 0)
                activity.requestPermissions(perms.toTypedArray(), requestCode)
            else onPermissionsGranted()
        }
    }

    abstract fun onPermissionsGranted()

}