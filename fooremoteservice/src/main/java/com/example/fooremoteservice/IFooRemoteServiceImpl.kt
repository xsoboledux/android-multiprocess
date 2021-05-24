package com.example.fooremoteservice

import android.os.RemoteException
import android.util.Log
import com.example.common.*

class IFooRemoteServiceImpl : IFooRemoteService.Stub() {
    private val TAG = IFooRemoteServiceImpl::class.java.simpleName

    override fun doOperation(request: FooRequest, listener: IFooRemoteServiceResponseListener) {
        Log.d(TAG, "remote service requested: $request")
        try {
            val response = FooResponse("success!")
            Thread.sleep(2000) // long operation
            listener.onResponse(response)
        } catch (e: RemoteException) {
            Log.e(TAG, e.localizedMessage, e)
            listener.onError(RemoteError(1000, e.localizedMessage))
        }
    }

    override fun doOperationWithTrouble(
        request: FooRequest,
        listener: IFooRemoteServiceResponseListener
    ) {
        Log.d(TAG, "remote service requested: $request")
        try {
            val response = FooResponse("success!")
            Thread.sleep(1000)
            throw OutOfMemoryError("out of memory")
            Thread.sleep(1000)
            listener.onResponse(response)
        } catch (e: Throwable) {
            Log.e(TAG, e.localizedMessage, e)
            listener.onError(RemoteError(1000, e.message))
        }
    }
}