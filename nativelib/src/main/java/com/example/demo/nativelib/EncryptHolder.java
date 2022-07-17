package com.example.demo.nativelib;

public class EncryptHolder {

    private volatile static EncryptHolder mEncryptHolder;
    private EncryptLib mEncryptLib = null;

    private EncryptHolder() {
        mEncryptLib = new EncryptLib();
    }

    public static EncryptHolder sInstance () {
        if (mEncryptHolder == null) {
            synchronized (EncryptHolder.class) {
                if (mEncryptHolder == null) {
                    mEncryptHolder = new EncryptHolder();
                }
            }
        }
        return mEncryptHolder;
    }

    public String getIvContent() {
        return mEncryptLib.getIv();
    }

    public String getKey() {
        return mEncryptLib.getKey();
    }
}
