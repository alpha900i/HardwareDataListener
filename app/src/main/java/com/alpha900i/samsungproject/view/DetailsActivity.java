package com.alpha900i.samsungproject.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alpha900i.samsungproject.R;

public class DetailsActivity extends AppCompatActivity {
    public static final String LOG_ENTRY_ID_KEY = "log_entry_id";
    DetailsFragment infoFragment;

    long logEntryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment);

        Intent incomingIntent = getIntent();
        if(incomingIntent!=null) {
            logEntryId = incomingIntent.getLongExtra(LOG_ENTRY_ID_KEY, 0);
        }

        infoFragment = DetailsFragment.newInstance();
        //yes, we could use newInstance. I am just planning for Master-Details as well
        infoFragment.setLogEntryId(logEntryId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.place_for_main_fragment, infoFragment)
                .commit();
    }
}