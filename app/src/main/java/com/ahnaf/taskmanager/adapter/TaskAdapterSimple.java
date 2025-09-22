package com.ahnaf.taskmanager.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahnaf.taskmanager.model.BaseTask;

import java.util.List;

public class TaskAdapterSimple extends RecyclerView.Adapter<TaskAdapterSimple.TaskViewSHolder> {
    private final List<BaseTask> tasks;

    public TaskAdapterSimple(List<BaseTask> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewSHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class TaskViewSHolder extends RecyclerView.ViewHolder {
        public TaskViewSHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
