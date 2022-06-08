package com.alpha900i.samsungproject.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//main data structure - log entry about hardware data
//Room library Entity
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
    double angleX;
    double angleY;
    double angleZ;

    @Ignore
    public LogEntry() { }

    public LogEntry(long id, long timestamp, String note,
                    int batteryLevel, boolean isCharging, boolean acCharge, boolean usbCharge,
                    long totalRAM, long availRAM, long usedRAM,
                    double angleX, double angleY, double angleZ) {
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

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getNote() {
        return note;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public boolean isCharging() {
        return isCharging;
    }

    public boolean isAcCharge() {
        return acCharge;
    }

    public boolean isUsbCharge() {
        return usbCharge;
    }

    public long getTotalRAM() {
        return totalRAM;
    }

    public long getAvailRAM() {
        return availRAM;
    }

    public long getUsedRAM() {
        return usedRAM;
    }

    public double getAngleX() {
        return angleX;
    }

    public double getAngleY() {
        return angleY;
    }

    public double getAngleZ() {
        return angleZ;
    }

    public String getPrintableTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssz", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(new Date(getTimestamp()));
    }

    public String getShortDescription() {
        return String.format(Locale.US, "%s: battery is %d (charging is %b)",
                getNote(), getBatteryLevel(), isCharging());
    }
}
