package com.alpha900i.samsungproject.data_readers;

import static android.content.Context.BATTERY_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

//this class gets data about battery
public class BatteryReader {
    int batteryLevel;
    boolean isCharging;
    boolean usbCharging;
    boolean acCharging;

    public void formBatteryData(Context context) {
        //level
        BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        batteryLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);


        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        //charging status
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        //charging type
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        usbCharging = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        acCharging = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
    }

    //////////////////////////////////////
    //getters
    //////////////////////////////////////
    public int getBatteryLevel() {
        return batteryLevel;
    }
    public boolean isCharging() {
        return isCharging;
    }
    public boolean isUsbCharging() {
        return usbCharging;
    }
    public boolean isAcCharging() {
        return acCharging;
    }
}
