package com.m_docket.network.rxandroid_callback;

public interface RxJava2ApiCallback<T> {
    void onSuccess(T t);

    void onFailed(Throwable throwable);
}