package com.example.common;

import com.example.common.FooResponse;
import com.example.common.RemoteError;

interface IFooRemoteServiceResponseListener {
    void onResponse(in FooResponse response);
    void onError(in RemoteError error);
}