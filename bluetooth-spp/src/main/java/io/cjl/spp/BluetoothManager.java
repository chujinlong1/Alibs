package io.cjl.spp;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import io.cjl.spp.constant.ConnectStatus;
import io.cjl.spp.listener.OnBTStatusListener;
import io.cjl.spp.listener.OnSppListener;
import io.cjl.spp.utils.JLogKit;
import io.cjl.spp.utils.SharedPreManager;

public class BluetoothManager {

    private static BluetoothManager instance;
    public static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    public static final UUID CONNECT_UUID = UUID.fromString(SPP_UUID);
    private ConnectThread clientConnectThread = null;
    private boolean isRunning = false;
    private BluetoothSocket mSocket = null;
    private OnSppListener listener;
    private OnBTStatusListener btStatusListener;
    private ReadThread mReadThread;

    private BluetoothManager() {

    }

    public static BluetoothManager getInstance() {
        if (instance == null) {
            instance = new BluetoothManager();
        }
        return instance;
    }

    public void setOnSppListener(OnSppListener listener) {
        this.listener = listener;
    }

    public void setOnBTStatusListener(OnBTStatusListener listener) {
        btStatusListener = listener;
    }

    /**
     * 发起连接
     *
     * @param device 蓝牙设备
     */
    public void connectDevice(BluetoothDevice device) {
        stopConnectDevice();
        clientConnectThread = new ConnectThread(device);
        clientConnectThread.setPriority(Thread.MAX_PRIORITY);
        clientConnectThread.start();
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        if (btStatusListener != null) {
            btStatusListener.onBTStatusChange(ConnectStatus.CONNECT_STATE_DISCONNECTING);
        }
        JLogKit.getInstance().i("disconnect");
        isRunning = false;
        stopConnectDevice();
        if (mReadThread != null) {
            mReadThread.interrupt();
            mReadThread = null;
        }
    }

    /**
     * 取消连接
     */
    private void stopConnectDevice() {
        if (clientConnectThread != null) {
            clientConnectThread.interrupt();
            clientConnectThread = null;
        }
    }

    /**
     * 客户端连接线程
     *
     * @author foryou
     */
    private class ConnectThread extends Thread {
        private BluetoothDevice device;

        public ConnectThread(BluetoothDevice device) {
            this.device = device;
        }

        @SuppressLint("MissingPermission")
        public void run() {
            try {
                JLogKit.getInstance().i("connectThread.run name = " + device.getName());
                BluetoothSocket temp;
                // 创建一个Socket连接：只需要服务器在注册时的UUID号
                temp = device.createInsecureRfcommSocketToServiceRecord(CONNECT_UUID);
                // 连接
                mSocket = temp;
                mSocket.connect();
                JLogKit.getInstance().i("connectThread.run connect success!");
                isRunning = true;
                mReadThread = new ReadThread();
                mReadThread.start();
                SharedPreManager.getInstance().setLastAddress(device.getAddress());
                if (btStatusListener != null)
                    btStatusListener.onBTStatusChange(ConnectStatus.CONNECT_STATE_CONNECTED);
            } catch (Exception e) {
                JLogKit.getInstance().e(e.getMessage());
                SharedPreManager.getInstance().setLastAddress(null);
                if (mSocket != null) {
                    try {
                        mSocket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    mSocket = null;
                }
                if (btStatusListener != null)
                    btStatusListener.onBTStatusChange(ConnectStatus.CONNECT_STATE_CONNECT_FAIL);
                e.printStackTrace();
            }
        }
    }

    private class ReadThread extends Thread {
        @Override
        public void run() {
            InputStream inStream = null;
            while (isRunning) {
                int size;
                try {
                    byte[] buffer = new byte[1024];
                    Thread.sleep(10);
                    if (socketIsEmpty()) {
                        break;
                    }
                    inStream = mSocket.getInputStream();
                    int available = inStream.available();
                    if (available != 0) {
                        size = inStream.read(buffer);
                        if (size > 0 && listener != null) {
                            listener.onDataReceived(buffer, size);
                        }
                    }
                } catch (Exception e) {
                    closeInputStream(inStream);
                }
            }
            JLogKit.getInstance().e("exit read thread");
            closeSocket();
        }
    }

    public void write(byte[] buffer) {
        if (socketIsEmpty())
            return;
        try {
            mSocket.getOutputStream().write(buffer);
            mSocket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean socketIsEmpty() {
        //JLogKit.getInstance().e("socket Is Empty = " + (mSocket == null));
        return (mSocket == null);
    }

    /**
     * 关闭BluetoothSocket，一定要在读线程退出后才能操作
     */
    private void closeSocket() {
        if (socketIsEmpty()) {
            return;
        }
        try {
            InputStream is = mSocket.getInputStream();
            OutputStream os = mSocket.getOutputStream();
            is.close();
            os.close();
            JLogKit.getInstance().e("close socket");
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mSocket = null;
    }

    private void closeInputStream(InputStream is) {
        try {
            if (is != null)
                is.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
