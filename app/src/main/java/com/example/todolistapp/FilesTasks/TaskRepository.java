package com.example.todolistapp.FilesTasks;

import android.content.Context;
import java.util.List;

public class TaskRepository {

    private final DBHelperTask dbHelperTask;

    public TaskRepository(Context context) {
        dbHelperTask = new DBHelperTask(context);
    }

    public void insertTask(Task task) {
        dbHelperTask.insertTask(task);
    }

    public List<Task> getAllTasks() {
        return dbHelperTask.getAllTasks();
    }

    public void deleteTask(int taskId) {
      dbHelperTask.deleteTask(taskId);
    }
}
