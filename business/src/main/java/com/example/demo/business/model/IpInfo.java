package com.example.demo.business.model;

public class IpInfo {
    private int code;
    private IpData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public IpData getData() {
        return data;
    }

    public void setData(IpData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IpInfo{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
