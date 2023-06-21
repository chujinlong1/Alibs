package io.cjl.spp.constant;

public enum ConnectStatus {

    CONNECT_STATE_BT_NOT_OPEN(-1),  //蓝牙未打开
    CONNECT_STATE_IDLE(0),          //未连接
    CONNECT_STATE_SEARCHING(1),     //正在搜索...
    CONNECT_STATE_SEARCHED(2),     //搜索完成
    CONNECT_STATE_CONNECTING(3),    //正在连接...
    CONNECT_STATE_CONNECTED(4),     //已连接
    CONNECT_STATE_CONNECT_FAIL(5),  //连接失败
    CONNECT_STATE_DISCONNECTING(6); //正在断开...

    private int state;

    ConnectStatus(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

}
