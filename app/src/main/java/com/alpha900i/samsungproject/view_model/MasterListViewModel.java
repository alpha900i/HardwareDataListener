package com.alpha900i.samsungproject.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alpha900i.samsungproject.model.AppDatabase;
import com.alpha900i.samsungproject.model.LogEntry;

import java.util.List;

//master fragment viewModel. gives access to live version of list logEntries
//livedata is needed, because list of logEntries is being filled by service
public class MasterListViewModel extends AndroidViewModel {
    private LiveData<List<LogEntry>> list;

    public MasterListViewModel(@NonNull Application application) {
        super(application);
        list = AppDatabase.getInstance(getApplication()).logDao().getAllLogsLive();
    }

    public LiveData<List<LogEntry>> getList() {
        return list;
    }
}
