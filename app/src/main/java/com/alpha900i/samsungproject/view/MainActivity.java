package com.alpha900i.samsungproject.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alpha900i.samsungproject.R;
import com.alpha900i.samsungproject.model.AppDatabase;
import com.alpha900i.samsungproject.service.LoggingService;

//Main Activity. Only master list in portrait mode, master-details in landscape
public class MainActivity extends AppCompatActivity {
    MenuItem startServiceMenuItem, stopServiceMenuItem;
    MasterListFragment masterFragment;
    DetailsFragment detailsFragment;
    View detailsPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setTitle(getString(R.string.log_title));

        detailsPlaceholder = findViewById(R.id.details_fragment);

        masterFragment = MasterListFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (detailsPlaceholder!=null) {
            detailsFragment = DetailsFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.master_fragment, masterFragment)
                    .replace(R.id.details_fragment, detailsFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.master_fragment, masterFragment)
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        startServiceMenuItem = menu.findItem(R.id.start_service_item);
        stopServiceMenuItem = menu.findItem(R.id.stop_service_item);
        setMenuItemsState(LoggingService.isServiceActive());
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
        } else if (item.getItemId() == R.id.clear_item) {
            clearDataWithConfirmation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startService(){
        Intent intent = new Intent(this, LoggingService.class);
        startService(intent);
        setMenuItemsState(true);
    }
    public void stopService(){
        Intent intent = new Intent(this, LoggingService.class);
        stopService(intent);
        setMenuItemsState(false);
    }
    private void setMenuItemsState(boolean isServiceActive) {
        startServiceMenuItem.setVisible(!isServiceActive);
        stopServiceMenuItem.setVisible(isServiceActive);
    }

    public void clearDataWithConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        DialogInterface.OnClickListener dialogListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                clearData();
            }
        };
        builder.setMessage(R.string.clear_data_confirmation_text)
                .setTitle(R.string.clear_data_confirmation_title)
                .setPositiveButton(R.string.clear_data_confirmation_yes, dialogListener)
                .setNegativeButton(R.string.clear_data_confirmation_no, dialogListener).show();
    }

    public void clearData() {
        AppDatabase.getInstance(getApplicationContext()).logDao().deleteAll();
    }

    public void openDetails(long id) {
        if (detailsPlaceholder!=null) {
            detailsFragment.setLogEntryId(id);
        } else {
            Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.LOG_ENTRY_ID_KEY, id);
            startActivity(intent);
        }
    }
}