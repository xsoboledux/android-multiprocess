package com.example.multiprocessapp

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.troublesdk.SdkWithTrouble

class TroubleSdkService : Service() {
    private val binder = TroubleSdkBinder()

    inner class TroubleSdkBinder : Binder() {
        fun getService() = this@TroubleSdkService
    }

    override fun onBind(intent: Intent): IBinder = binder

    fun runOperation() {
        SdkWithTrouble.runOperation()
    }

    fun runOperationWithTrouble() {
        SdkWithTrouble.runOperationWithTrouble()
    }
}