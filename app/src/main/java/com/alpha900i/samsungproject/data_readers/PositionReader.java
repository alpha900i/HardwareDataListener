package com.alpha900i.samsungproject.data_readers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

//this class gets data about device position
//little trickier than others - some used data is red from special event from service
public class PositionReader {

    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];
    private final float[] orientationAngles = new float[3];

    public void processEvent(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }
    }
    public void formPositionData() {
        final float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // Express the updated rotation matrix as three orientation angles.
        final float[] orientationAngles = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
    }
    public double getXAngle() {
        return Math.toDegrees(orientationAngles[0]);
    }
    public double getYAngle() {
        return Math.toDegrees(orientationAngles[1]);
    }
    public double getZAngle() {
        return Math.toDegrees(orientationAngles[2]);
    }
}
