package com.alpha900i.samsungproject.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "log")
public class LogEntry {
    //general info
    @PrimaryKey(autoGenerate = true)
    long id;
    long timestamp;
    String note;

    //battery block
    int batteryLevel;
    boolean isCharging;
    boolean acCharge;
    boolean usbCharge;

    //RAM block
    long totalRAM;
    long availRAM;
    long usedRAM;

    //orientation block
    float angleX;
    float angleY;
    float angleZ;

    public LogEntry() { }

    public LogEntry(long id, long timestamp, String note,
                    int batteryLevel, boolean isCharging, boolean acCharge, boolean usbCharge,
                    long totalRAM, long availRAM, long usedRAM,
                    float angleX, float angleY, float angleZ) {
        this.id = id;
        this.timestamp = timestamp;
        this.note = note;
        this.batteryLevel = batteryLevel;
        this.isCharging = isCharging;
        this.acCharge = acCharge;
        this.usbCharge = usbCharge;
        this.totalRAM = totalRAM;
        this.availRAM = availRAM;
        this.usedRAM = usedRAM;
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
    }
}
