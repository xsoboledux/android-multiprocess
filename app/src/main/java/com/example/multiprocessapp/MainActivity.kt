package com.example.multiprocessapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.troublesdk.SdkWithTrouble

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private lateinit var troubleSdkService: TroubleSdkService
    private var isBound = false

    private lateinit var operationButton: Button
    private lateinit var operationWithTroubleButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operationButton = findViewById<Button>(R.id.operation_button)
        operationWithTroubleButton = findViewById<Button>(R.id.operation_with_trouble_button)

        operationButton.setOnClickListener { operation() }
        operationWithTroubleButton.setOnClickListener { operationWithTrouble() }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, TroubleSdkService::class.java).also { intent ->
//            startService(intent)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }

    private fun operation() {
        if (isBound) {
            troubleSdkService.runOperation()
            Log.d(TAG, "operation")
            Toast.makeText(this, "operation complete", LENGTH_SHORT).show()
        } else {
            serviceIsUnbound()
        }
    }

    private fun serviceIsUnbound() {
        Log.d(TAG, "service is unbound")
        Toast.makeText(this, "service is unbound", LENGTH_SHORT).show()
    }

    private fun operationWithTrouble() {
        if (isBound) {
            troubleSdkService.runOperationWithTrouble()
            Log.d(TAG, "operation with trouble")
            Toast.makeText(this, "operation with trouble complete", LENGTH_SHORT).show()
        } else {
            serviceIsUnbound()
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as TroubleSdkService.TroubleSdkBinder
            troubleSdkService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }
}