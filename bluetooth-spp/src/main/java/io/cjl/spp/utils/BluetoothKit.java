package io.cjl.spp.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import io.cjl.spp.BtReceiver;


public class BluetoothKit {

    private static BluetoothKit instance;
    private Context context;
    private BluetoothAdapter btAdapter = null;
    private BtReceiver receiver;

    private BluetoothKit() {

    }

    public static BluetoothKit getInstance() {
        if (instance == null) {
            instance = new BluetoothKit();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        btAdapter = initBluetoothAdapter();
    }

    @SuppressLint("NewApi")
    private BluetoothAdapter initBluetoothAdapter() {
        final int sdk = Build.VERSION.SDK_INT;
        PackageManager manager = context.getPackageManager();
        if ((sdk >= 18) && (manager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))) {
            final BluetoothManager bluetoothManager = (BluetoothManager) context
                    .getSystemService(Context.BLUETOOTH_SERVICE);
            return bluetoothManager.getAdapter();
        } else {
            return BluetoothAdapter.getDefaultAdapter();
        }
    }

    public void registerBtReceiver() {
        if (!isEnabled()) {
            enable();
        }
        receiver = new BtReceiver();
        context.registerReceiver(receiver, receiver.getFilter());
    }

    public void unregisterBtReceiver() {
        context.unregisterReceiver(receiver);
    }

    public void setOnBtReceiverListener(BtReceiver.OnBtReceiverListener listener) {
        if (receiver != null) {
            receiver.setOnBtReceiverListener(listener);
        }
    }

    @SuppressLint("MissingPermission")
    public boolean isEnabled() {
        return btAdapter == null ? false : btAdapter.isEnabled();
    }

    @SuppressLint("MissingPermission")
    public boolean enable() {
        return btAdapter == null ? false : btAdapter.enable();
    }

    @SuppressLint("MissingPermission")
    public List<BluetoothDevice> getBondedDevices() {
        if (btAdapter == null)
            return null;
        List<BluetoothDevice> devices = new ArrayList<>();
        for (BluetoothDevice device : btAdapter.getBondedDevices()) {
            devices.add(device);
        }
        return devices;
    }

    @SuppressLint("MissingPermission")
    public boolean isDiscovering() {
        return btAdapter == null ? false : btAdapter.isDiscovering();
    }

    public BluetoothDevice getRemoteDevice(String address) {
        return btAdapter == null ? null : btAdapter.getRemoteDevice(address);
    }

    /**
     * 开始搜索
     */
    @SuppressLint("MissingPermission")
    public void startDiscovery() {
        if (btAdapter != null && !btAdapter.isDiscovering()) {
            JLogKit.getInstance().i("startDiscovery");
            btAdapter.startDiscovery();
        }
    }

    /**
     * 取消搜索
     */
    @SuppressLint("MissingPermission")
    public void cancelDiscovery() {
        if (isDiscovering()) {
            JLogKit.getInstance().i("cancelDiscovery");
            btAdapter.cancelDiscovery();
        }
    }

    /**
     * 配对设备
     *
     * @param device 配对蓝牙设备
     */
    public void pairDevice(BluetoothDevice device) {
        try {
            ClsUtils.createBond(device.getClass(), device);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return btAdapter;
    }

}
