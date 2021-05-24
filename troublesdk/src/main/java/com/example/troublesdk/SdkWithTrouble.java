package com.example.troublesdk;

import android.util.Log;

public class SdkWithTrouble {
    private static final String TAG = SdkWithTrouble.class.getSimpleName();

    public static void runOperation() {
        Log.d(TAG, "sdk operation");
    }

    public static void runOperationWithTrouble() {
        Log.d(TAG, "sdk operation with trouble");
        throw new OutOfMemoryError();
    }
}
