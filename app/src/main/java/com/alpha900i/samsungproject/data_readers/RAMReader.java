package com.alpha900i.samsungproject.data_readers;

import android.app.ActivityManager;
import android.content.Context;

//this class gets data about RAM
public class RAMReader {
    long totalRAM;
    long availableRAM;
    long usedRAM;

    public void formRAMData(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(memoryInfo);

        //we use KB as measure unit
        long divider = 1024;
        totalRAM = memoryInfo.totalMem / divider;
        availableRAM = memoryInfo.availMem / divider;
        usedRAM = totalRAM - availableRAM;
    }

    //////////////////////////////////////
    //getters
    //////////////////////////////////////
    public long getTotalRAM() {
        return totalRAM;
    }
    public long getAvailableRAM() {
        return availableRAM;
    }
    public long getUsedRAM() {
        return usedRAM;
    }
}
