package com.ahnaf.taskmanager.adapter;


import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahnaf.taskmanager.R;
import com.ahnaf.taskmanager.model.BaseTask;
import com.google.android.material.color.MaterialColors;

import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final List<BaseTask> tasks;

    public TaskAdapter(List<BaseTask> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        BaseTask task = tasks.get(position);
        holder.title.setText(task.getTitle());
        holder.details.setText(task.getDescription());

        // Cancel any old timer (important for RecyclerView reuse)
        if (holder.countDownTimer != null) {
            holder.countDownTimer.cancel();
        }

        long millis = task.millisRemaining();

        if (millis > 0) {
            holder.countDownTimer = new CountDownTimer(millis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long minutes = millisUntilFinished / 1000 / 60;
                    long seconds = (millisUntilFinished / 1000) % 60;
                    holder.countdown.setText(
                            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
                }

                @Override
                public void onFinish() {
                    holder.countdown.setText("Time’s up!");
                }
            }.start();
        } else {
            holder.countdown.setText("Expired");
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, details, countdown;
        CountDownTimer countDownTimer;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            details = itemView.findViewById(R.id.taskDescription);
            countdown = itemView.findViewById(R.id.textCountdown);
        }
    }
}
