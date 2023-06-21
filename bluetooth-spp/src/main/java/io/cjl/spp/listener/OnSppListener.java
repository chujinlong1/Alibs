package io.cjl.spp.listener;

public interface OnSppListener {
    void onDataReceived(byte[] buffer, int size);
}
