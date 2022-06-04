package com.alpha900i.samsungproject.service;

import static java.lang.Thread.sleep;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.alpha900i.samsungproject.model.AppDatabase;
import com.alpha900i.samsungproject.model.LogEntry;

import java.util.Calendar;
import java.util.Locale;

public class LoggingService extends Service {
    private final String TAG = "LoggingService";
    MyTask task;

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        task = new MyTask();
        startWork();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        task.stop();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    void startWork(){
        Thread thread = new Thread(task,"MainThread");
        thread.start();
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

                LogEntry logEntry = new LogEntry(
                        0, calendar.getTimeInMillis(),
                        String.format(Locale.US, "Note %d", counter),
                        0, false, false, false,
                        0, 0, 0,
                        0.0f, 0.0f, 0.0f
                );
                AppDatabase.getInstance(getBaseContext()).logDao().insertLog(logEntry);
                int logCount = AppDatabase.getInstance(getBaseContext()).logDao().getLogCount();

                Log.d(TAG, String.format(Locale.US, "Counted to %d; %d records", counter, logCount));
                counter++;
                try{
                    sleep(5000);
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
