package com.alpha900i.samsungproject.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.alpha900i.samsungproject.model.AppDatabase;
import com.alpha900i.samsungproject.model.LogEntry;

//details fragment viewModel. gives access to live version of logEntry being shown
//only reason to have viewModel and livedata here is "master-details" pattern
//livedata makes it easier to switch between logEntries
public class DetailsViewModel extends AndroidViewModel {
    MutableLiveData<Long> logEntryId;
    private LiveData<LogEntry> logEntry;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        logEntryId = new MutableLiveData<>();
        logEntry = Transformations.switchMap(logEntryId, id ->
                AppDatabase.getInstance(getApplication()).logDao().getLogByIdLive(id));
    }

    public void setLogEntryId(long logEntryId) {
        this.logEntryId.postValue(logEntryId);
    }
    public LiveData<LogEntry> getLogEntry() {
        return logEntry;
    }
}
