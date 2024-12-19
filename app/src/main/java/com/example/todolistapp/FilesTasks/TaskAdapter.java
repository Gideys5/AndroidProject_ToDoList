package com.example.todolistapp.FilesTasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> tasks;
    private final Context context;
    private final OnTaskDeleteListener onTaskDeleteListener;

    public interface OnTaskDeleteListener {
        void onTaskDelete(int position);
    }

    public TaskAdapter(List<Task> tasks, Context context, OnTaskDeleteListener onTaskDeleteListener) {
        this.tasks = tasks;
        this.context = context;
        this.onTaskDeleteListener = onTaskDeleteListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskTitle.setText(task.getTitle());
        holder.taskDeadline.setText(task.getDeadline());

        switch (task.getPriority()) {
            case "Alta":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                break;
            case "Media":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
                break;
            case "Bassa":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                break;
        }

        holder.deleteButton.setOnClickListener(v -> onTaskDeleteListener.onTaskDelete(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        TextView taskDeadline;
        Button deleteButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDeadline = itemView.findViewById(R.id.task_deadline);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
