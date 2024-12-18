package com.example.todolistapp.FilesTasks;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolistapp.R;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private OnTaskDeleteListener onTaskClickListener;

    public interface OnTaskDeleteListener {
        void onTaskDelete(int position);
    }

    public TaskAdapter(List<Task> tasks, OnTaskDeleteListener onTaskClickListener) {
        this.tasks = tasks;
        this.onTaskClickListener = onTaskClickListener;
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
        holder.taskDeadLine.setText(task.getDeadline());

        holder.deleteButton.setOnClickListener(v -> onTaskClickListener.onTaskDelete(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        TextView taskDeadLine;
        Button deleteButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDeadLine = itemView.findViewById(R.id.task_deadline);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
