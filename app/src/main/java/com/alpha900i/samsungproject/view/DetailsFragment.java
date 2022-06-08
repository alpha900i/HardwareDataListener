package com.alpha900i.samsungproject.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alpha900i.samsungproject.R;
import com.alpha900i.samsungproject.view_model.DetailsViewModel;

import java.util.Locale;

//details fragment. Shows extended info on log entry
public class DetailsFragment extends Fragment {
    long logEntryId = 0;

    ConstraintLayout mainLayout;
    TextView noteText, timestampText;
    TextView batteryLevelText, batteryChargingText, batteryUSBChargingText, batteryACChargingText;
    TextView totalRAMText, availableRAMText, usedRAMText;
    TextView positionTitle, xAngleTitle, yAngleTitle, zAngleTitle;
    TextView xAngleText, yAngleText, zAngleText;

    DetailsViewModel viewModel;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        setHasOptionsMenu(true);

        findViews(view);

        mainLayout.setVisibility(View.GONE);

        setupViewModel();

        return view;
    }

    private void findViews(View parent) {
        mainLayout = parent.findViewById(R.id.main_layout);

        noteText = parent.findViewById(R.id.note_text);
        timestampText = parent.findViewById(R.id.timestamp_text);

        batteryLevelText = parent.findViewById(R.id.battery_level_text);
        batteryChargingText = parent.findViewById(R.id.battery_charging_text);
        batteryUSBChargingText = parent.findViewById(R.id.battery_usb_text);
        batteryACChargingText = parent.findViewById(R.id.battery_ac_text);

        totalRAMText = parent.findViewById(R.id.total_ram_text);
        availableRAMText = parent.findViewById(R.id.available_ram_text);
        usedRAMText = parent.findViewById(R.id.used_ram_text);

        positionTitle = parent.findViewById(R.id.position_title);
        xAngleTitle = parent.findViewById(R.id.x_angle_title);
        yAngleTitle = parent.findViewById(R.id.y_angle_title);
        zAngleTitle = parent.findViewById(R.id.z_angle_title);

        xAngleText = parent.findViewById(R.id.x_angle_text);
        yAngleText = parent.findViewById(R.id.y_angle_text);
        zAngleText = parent.findViewById(R.id.z_angle_text);
    }
    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        viewModel.setLogEntryId(logEntryId);
        viewModel.getLogEntry().observe(getViewLifecycleOwner(), logEntry -> {
            if (logEntry == null) {
                return;
            }
            mainLayout.setVisibility(View.VISIBLE);
            noteText.setText(logEntry.getNote());
            timestampText.setText(logEntry.getPrintableTimestamp());

            batteryLevelText.setText(getString(R.string.battery_level_placeholder, logEntry.getBatteryLevel()));
            batteryChargingText.setText(getBooleanText(logEntry.isCharging()));
            batteryUSBChargingText.setText(getBooleanText(logEntry.isCharging()));
            batteryACChargingText.setText(getBooleanText(logEntry.isCharging()));

            totalRAMText.setText(getString(R.string.memory_placeholder, logEntry.getTotalRAM()));
            availableRAMText.setText(getString(R.string.memory_placeholder, logEntry.getAvailRAM()));
            usedRAMText.setText(getString(R.string.memory_placeholder, logEntry.getUsedRAM()));

            xAngleText.setText(String.format(Locale.US, "%f", logEntry.getAngleX()));
            yAngleText.setText(String.format(Locale.US, "%f", logEntry.getAngleY()));
            zAngleText.setText(String.format(Locale.US, "%f", logEntry.getAngleZ()));

            int positionDataVisibility = View.VISIBLE;
            if (!logEntry.isPositionDataGood()) {
                positionDataVisibility = View.GONE;
            }

            positionTitle.setVisibility(positionDataVisibility);
            xAngleTitle.setVisibility(positionDataVisibility);
            yAngleTitle.setVisibility(positionDataVisibility);
            zAngleTitle.setVisibility(positionDataVisibility);
            xAngleText.setVisibility(positionDataVisibility);
            yAngleText.setVisibility(positionDataVisibility);
            zAngleText.setVisibility(positionDataVisibility);
        });
    }
    public void setLogEntryId(long logEntryId) {
        this.logEntryId = logEntryId;
        if (viewModel!=null) {
            viewModel.setLogEntryId(logEntryId);
        }
    }

    private String getBooleanText(boolean value) {
        return value ? getString(R.string.field_value_yes) : getString(R.string.field_value_no);
    }
}
