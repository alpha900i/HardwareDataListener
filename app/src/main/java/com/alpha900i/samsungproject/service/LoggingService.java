package com.alpha900i.samsungproject.service;

import static java.lang.Thread.sleep;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import com.alpha900i.samsungproject.R;
import com.alpha900i.samsungproject.data_readers.BatteryReader;
import com.alpha900i.samsungproject.data_readers.PositionReader;
import com.alpha900i.samsungproject.data_readers.RAMReader;
import com.alpha900i.samsungproject.model.AppDatabase;
import com.alpha900i.samsungproject.model.LogEntry;

import java.util.Calendar;
import java.util.Locale;

public class LoggingService extends Service implements SensorEventListener {
    private final String TAG = "LoggingService";
    MyTask task;

    private final long DELAY_MSEC = 5000;

    private SensorManager sensorManager;
    private BatteryReader batteryReader;
    private PositionReader positionReader;
    private RAMReader ramReader;

    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        batteryReader = new BatteryReader();
        ramReader = new RAMReader();
        positionReader = new PositionReader();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        task = new MyTask();
        registerSensorListeners();
        startWork();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterSensorListeners();
        if (task!=null) {
            task.stop();
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    void startWork(){
        Thread thread = new Thread(task,"MainThread");
        thread.start();
    }

    ////////////////////////////////////////////
    //sensor support block
    ////////////////////////////////////////////
    private void registerSensorListeners() {
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
    private void unregisterSensorListeners() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER
                || event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            positionReader.processEvent(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private class MyTask implements Runnable{
        private boolean serviceIsActive;

        MyTask() { }

        @Override
        public void run() {
            serviceIsActive = true;
            int counter = 0;
            while(serviceIsActive){
                Calendar calendar = Calendar.getInstance();

                batteryReader.formBatteryData(getBaseContext());
                ramReader.formRAMData(getBaseContext());
                positionReader.formPositionData();

                LogEntry logEntry = new LogEntry(
                        0, calendar.getTimeInMillis(),
                        String.format(Locale.US, getString(R.string.note_placeholder), counter),

                        batteryReader.getBatteryLevel(),
                        batteryReader.isCharging(),
                        batteryReader.isAcCharging(),
                        batteryReader.isUsbCharging(),

                        ramReader.getTotalRAM(),
                        ramReader.getAvailableRAM(),
                        ramReader.getUsedRAM(),

                        positionReader.getXAngle(),
                        positionReader.getYAngle(),
                        positionReader.getZAngle()
                );
                AppDatabase.getInstance(getBaseContext()).logDao().insertLog(logEntry);
                int logCount = AppDatabase.getInstance(getBaseContext()).logDao().getLogCount();

                Log.d(TAG, String.format(Locale.US, getString(R.string.debug_log_count_record), counter, logCount));
                counter++;
                try{
                    sleep(DELAY_MSEC);
                } catch (InterruptedException e){
                    e.printStackTrace();
                    break;
                }
            }
        }
        void stop(){
            serviceIsActive = false;
        }
    }
}
