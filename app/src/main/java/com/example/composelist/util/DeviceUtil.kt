package com.example.composelist.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.example.composelist.BuildConfig
import com.example.composelist.network.HttpConstants.KEY_DEVICE_ID

/**
 * Created by JeeteshSurana.
 */
@SuppressLint("HardwareIds")
class DeviceUtil(context: Context, private val preferenceProvider: PreferenceProvider) {
    private val appContext = context.applicationContext
    private var mDeviceID = ""

    fun getDeviceId(): String {
        if (mDeviceID.isEmpty()) {
            val deviceID = preferenceProvider.getString(KEY_DEVICE_ID)
            if (!deviceID.isNullOrEmpty()) {
                mDeviceID = deviceID
                return mDeviceID
            } else {
                mDeviceID = Settings.Secure.getString(
                    appContext.applicationContext.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
                preferenceProvider.putString(KEY_DEVICE_ID, mDeviceID)
            }
        }
        return mDeviceID
    }

    fun getDeviceName(): String {
        return "android/${BuildConfig.VERSION_NAME}"
    }

    fun getUserAgent(): String? = System.getProperty("http.agent")

}
