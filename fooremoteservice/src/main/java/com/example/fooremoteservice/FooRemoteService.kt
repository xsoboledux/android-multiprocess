package com.example.fooremoteservice

import android.app.Service
import android.content.Intent
import android.os.*

class FooRemoteService : Service() {
    private val TAG = FooRemoteService::class.java.simpleName

    private var fooService: IFooRemoteServiceImpl? = null

    override fun onCreate() {
        super.onCreate()

        fooService = IFooRemoteServiceImpl()
    }

    override fun onBind(intent: Intent?): IBinder? = fooService

    override fun onDestroy() {
        super.onDestroy()

        fooService = null
    }
}