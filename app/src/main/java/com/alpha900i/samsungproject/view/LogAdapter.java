package com.alpha900i.samsungproject.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha900i.samsungproject.R;
import com.alpha900i.samsungproject.model.LogEntry;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    private List<LogEntry> list;
    private Context context;

    LogAdapter(Context context) {
        this.context = context;
    }

    public void initialize(List<LogEntry> feeds) {
        this.list = feeds;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list==null) {
            return;
        }
        LogEntry log = list.get(position);
        holder.id = log.getId();
        holder.title.setText(log.getPrintableTimestamp());
        holder.shortDescription.setText(log.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView title;
        final TextView shortDescription;

        long id;

        ViewHolder(View view) {
            super(view);
            mView = view;
            title = view.findViewById(R.id.timestamp_text);
            shortDescription = view.findViewById(R.id.short_description_text);
        }
    }
}

