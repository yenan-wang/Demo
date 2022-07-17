package com.example.demo.nativelib;

public class EncryptLib {

    static {
        System.loadLibrary("encryptlib");
    }

    public native String getIv();

    public native String getKey();
}
