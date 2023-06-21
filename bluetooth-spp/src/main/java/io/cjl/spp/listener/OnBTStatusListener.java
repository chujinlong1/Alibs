package io.cjl.spp.listener;

import io.cjl.spp.constant.ConnectStatus;

public interface OnBTStatusListener {
    void onBTStatusChange(ConnectStatus status);
}
