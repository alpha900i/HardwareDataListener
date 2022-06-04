package com.alpha900i.samsungproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.alpha900i.samsungproject.R;
import com.alpha900i.samsungproject.service.LoggingService;

public class MainActivity extends AppCompatActivity {
    MenuItem startServiceMenuItem, stopServiceMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        startServiceMenuItem = menu.findItem(R.id.start_service_item);
        stopServiceMenuItem = menu.findItem(R.id.stop_service_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.start_service_item) {
            startService();
            return true;
        } else if (item.getItemId() == R.id.stop_service_item) {
            stopService();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startService(){
        Intent intent = new Intent(this, LoggingService.class);
        startService(intent);
        startServiceMenuItem.setVisible(false);
        stopServiceMenuItem.setVisible(true);
    }
    public void stopService(){
        Intent intent = new Intent(this, LoggingService.class);
        stopService(intent);
        startServiceMenuItem.setVisible(true);
        stopServiceMenuItem.setVisible(false);
    }
}