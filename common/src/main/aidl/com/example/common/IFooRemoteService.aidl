package com.example.common;

import com.example.common.FooRequest;
import com.example.common.IFooRemoteServiceResponseListener;

interface IFooRemoteService {
    oneway void doOperation(in FooRequest request,
            in IFooRemoteServiceResponseListener listener);

    oneway void doOperationWithTrouble(in FooRequest request,
                in IFooRemoteServiceResponseListener listener);
}