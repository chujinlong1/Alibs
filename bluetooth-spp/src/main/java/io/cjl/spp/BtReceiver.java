package io.cjl.spp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

public class BtReceiver extends BroadcastReceiver {

    private OnBtReceiverListener listener;
    private List<BluetoothDevice> btList = new ArrayList<>();

    public void setOnBtReceiverListener(OnBtReceiverListener listener){
        this.listener = listener;
    }

    public IntentFilter getFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        return filter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener == null)
            return;
        final String action = intent.getAction();
        BluetoothDevice device;
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            for (BluetoothDevice bt : btList) {
                if (bt.getAddress().equals(device.getAddress())) {
                    return;
                }
            }
            listener.onFoundNewDevice(device);
            int rssi = intent.getExtras().getShort(
                    BluetoothDevice.EXTRA_RSSI);
            if (rssi != 0) {
                btList.add(device);
            }
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            listener.onSearchFinish(btList);
            btList.clear();
        } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
            device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            listener.onBondStateChange(device);
        } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
            listener.onSwitchStateChange(state);
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            device = intent
                    .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            listener.onSppDisconnected();
        }
    }

    public interface OnBtReceiverListener {
        void onFoundNewDevice(BluetoothDevice device);

        void onSearchFinish(List<BluetoothDevice> list);

        void onBondStateChange(BluetoothDevice device);

        void onSwitchStateChange(int state);

        void onSppDisconnected();
    }

}
