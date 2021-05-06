package com.example.multiprocessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.troublesdk.SdkWithTrouble

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val operationButton = findViewById<Button>(R.id.operation_button)
        val operationWithTroubleButton = findViewById<Button>(R.id.operation_with_trouble_button)

        operationButton.setOnClickListener { operation() }
        operationWithTroubleButton.setOnClickListener { operationWithTrouble() }
    }

    private fun operation() {
        SdkWithTrouble.runOperation()
        Log.d(TAG, "operation")
        Toast.makeText(this, "operation complete", LENGTH_SHORT).show()
    }

    private fun operationWithTrouble() {
        SdkWithTrouble.runOperationWithTrouble()
        Log.d(TAG, "operation with trouble")
        Toast.makeText(this, "operation with trouble complete", LENGTH_SHORT).show()
    }
}