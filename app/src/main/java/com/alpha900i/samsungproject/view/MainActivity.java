package com.alpha900i.samsungproject.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alpha900i.samsungproject.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    private boolean gotMagneticRead;
    private boolean gotAccelerometerRead;

    TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        mainText = findViewById(R.id.textView);
        formText();
    }

    private void formText() {
        String batteryText = formBatteryText() + formRAMText() + formPositionText();
        mainText.setText(batteryText);
    }

    private String formBatteryText() {
        Context context = getBaseContext();

        //current level?
        BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        //are we charging?
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        //how are we charging?
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        String result = String.format(Locale.US, "Battery level is %d\n" +
                        "Charging status is %b\n" +
                        "USB charing is %b\n" +
                        "AC charging is %b\n" ,
                batLevel, isCharging, usbCharge, acCharge);
        return result;
    }

    private String formRAMText() {
        Context context = getBaseContext();

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(memoryInfo);

        long divider = 1024*1024;
        long totalRAM = memoryInfo.totalMem / divider;
        long availRAM = memoryInfo.availMem / divider;  // remember to convert in GB,MB or KB.
        long usedRAM = totalRAM - availRAM;

        String result = String.format(Locale.US, "Available RAM is %d of %d total\n" +
                        "%d RAM is used\n" ,
                availRAM, totalRAM, usedRAM);
        return result;
    }


    protected void onResume() {
        super.onResume();

        // Get updates from the accelerometer and magnetometer at a constant rate.
        // To make batch operations more efficient and reduce power consumption,
        // provide support for delaying updates to the application.
        //
        // In this example, the sensor reporting delay is small enough such that
        // the application receives an update before the system checks the sensor
        // readings again.
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
    }

    private String formPositionText() {
        final float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // Express the updated rotation matrix as three orientation angles.
        final float[] orientationAngles = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        String result = String.format(Locale.US, "Rotation angles are\n" +
                        "%.2f x %.2f x %.2f\n" ,
                Math.toDegrees(orientationAngles[0]),
                Math.toDegrees(orientationAngles[1]),
                Math.toDegrees(orientationAngles[2]));
        return result;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }
        formText();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        ;
    }
}