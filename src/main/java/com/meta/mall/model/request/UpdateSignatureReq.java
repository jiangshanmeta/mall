package com.meta.mall.model.request;

public class UpdateSignatureReq {
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "UpdateSignatureReq{" +
                "signature='" + signature + '\'' +
                '}';
    }
}
