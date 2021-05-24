package com.example.multiprocessapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.common.*
import com.example.fooremoteservice.FooRemoteService
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private var fooService: IFooRemoteService? = null
    private var isBound = false

    private lateinit var operationButton: Button
    private lateinit var operationWithTroubleButton: Button
    private lateinit var mainLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout = findViewById(R.id.main_layout)
        operationButton = findViewById<Button>(R.id.operation_button)
        operationWithTroubleButton = findViewById<Button>(R.id.operation_with_trouble_button)

        operationButton.setOnClickListener { operation() }
        operationWithTroubleButton.setOnClickListener { operationWithTrouble() }
    }

    private fun operationWithTrouble() {
        if(isBound) {
            try {
                Log.d(TAG, "operation: requested remote service")
                fooService?.doOperationWithTrouble(
                    FooRequest(1),
                    object : IFooRemoteServiceResponseListener.Stub() {
                        override fun onResponse(response: FooResponse?) {
                            Log.d(TAG, "response: ${response?.result}")
                        }

                        override fun onError(error: RemoteError?) {
                            Log.e(TAG, error?.message ?: "error!")
                        }
                    })
            } catch (e: RemoteException) {
                Log.e(TAG, e.message, e)
            }
        } else {
            serviceIsUnbound()
        }
    }

    private fun operation() {
        if(isBound) {
            try {
                Log.d(TAG, "operation: requested remote service")
                fooService?.doOperation(
                    FooRequest(1),
                    object : IFooRemoteServiceResponseListener.Stub() {
                        override fun onResponse(response: FooResponse?) {
                            Log.d(TAG, "response: ${response?.result}")
                        }

                        override fun onError(error: RemoteError?) {
                            Log.e(TAG, error?.message ?: "error!")
                        }
                    })
            } catch (e: RemoteException) {
                Log.e(TAG, e.message, e)
            }
        } else {
            serviceIsUnbound()
        }
    }

    override fun onStart() {
        super.onStart()
        bindFooRemoteService()
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }

    private fun serviceIsUnbound() {
        Log.d(TAG, "service is unbound")
        Snackbar.make(mainLayout, "Trouble service has problem", Snackbar.LENGTH_LONG)
            .setAction("Try Reconnect") {
                bindFooRemoteService()
            }
            .show()
    }

    private fun bindFooRemoteService() {
        Intent(this, FooRemoteService::class.java)
            .apply {
                action = IFooRemoteService::class.java.name
            }.also {
               bindService(it, connection, Context.BIND_AUTO_CREATE)
            }
    }


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "onServiceConnected: ${service.javaClass.name}")
            fooService = IFooRemoteService.Stub.asInterface(service)
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "onServiceDisconnected: $name")
            fooService = null
            serviceIsUnbound()
            isBound = false
        }
    }
}