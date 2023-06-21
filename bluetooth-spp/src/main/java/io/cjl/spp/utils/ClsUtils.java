package io.cjl.spp.utils;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClsUtils {

    public static Object invoke(Object className, String funcName,
                                Object... param) {
        try {
            if (param == null) {
                Method method = className.getClass().getMethod(funcName);
                return method.invoke(className);
            } else {
                Class<?>[] paramClass = new Class[param.length];
                for (int i = 0; i < param.length; i++) {
                    paramClass[i] = param[i].getClass();
                }
                Method method = className.getClass().getMethod(funcName,
                        paramClass);
                return method.invoke(className, param);
            }

        } catch (NoSuchMethodException e) {
            JLogKit.getInstance().e("Method " + funcName + " Not Found");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            JLogKit.getInstance().e("Method " + funcName + " Invoke Fail");
            return null;
        }
    }

    /**
     * 与设备配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    static public void createBond(Class<?> btClass, BluetoothDevice btDevice)
            throws Exception {
        Method createBondMethod = btClass.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        Log.e("createBond", "" + returnValue);
    }

    /**
     * 与设备解除配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    static public void removeBond(Class<?> btClass, BluetoothDevice btDevice)
            throws Exception {
        Method removeBondMethod = btClass.getMethod("removeBond");
        removeBondMethod.invoke(btDevice);
    }

    static public boolean setPin(Class<?> btClass, BluetoothDevice btDevice, String str) {
        try {
            Method removeBondMethod = btClass.getDeclaredMethod("setPin",
                    new Class[]{byte[].class});
            Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice,
                    new Object[]{str.getBytes()});
            Log.e("setPin", "setPin result = " + returnValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    // 取消用户输入
    static public boolean cancelPairingUserInput(Class<?> btClass,
                                                 BluetoothDevice device) throws Exception {
        Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
        // cancelBondProcess()
        return (Boolean) createBondMethod.invoke(device);
    }

    // 取消配对
    static public boolean cancelBondProcess(Class<?> btClass,
                                            BluetoothDevice device)

            throws Exception {
        Method createBondMethod = btClass.getMethod("cancelBondProcess");
        return (Boolean) createBondMethod.invoke(device);
    }

    static public void printAllInform(Class<?> clsShow) {
        try {
            // 取得所有方法
            Method[] hideMethod = clsShow.getMethods();
            int i = 0;
            for (; i < hideMethod.length; i++) {
                Log.e("method name", hideMethod[i].getName() + ";and the i is:"
                        + i);
            }
            // 取得所有常量
            Field[] allFields = clsShow.getFields();
            for (i = 0; i < allFields.length; i++) {
                Log.e("Field name", allFields[i].getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
