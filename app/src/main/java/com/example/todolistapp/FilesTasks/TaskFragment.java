package com.example.todolistapp.FilesTasks;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;

import java.util.Calendar;
import java.util.List;

public class TaskFragment extends Fragment {

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private TaskRepository taskRepository;
    private EditText taskInput, deadlineInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        taskInput = view.findViewById(R.id.task_title);
        deadlineInput = view.findViewById(R.id.task_deadline);
        Button addTaskButton = view.findViewById(R.id.add_task_button);
        taskRecyclerView = view.findViewById(R.id.task_recycler_view);

        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRepository = new TaskRepository(requireContext());

        loadTasks();

        deadlineInput.setOnClickListener(v -> openDatePicker());

        addTaskButton.setOnClickListener(v -> {
            String taskText = taskInput.getText().toString().trim();
            String deadlineText = deadlineInput.getText().toString().trim();

            if (!taskText.isEmpty() && !deadlineText.isEmpty()) {
                Task newTask = new Task(taskText, deadlineText);
                taskRepository.insertTask(newTask);

                loadTasks();

                taskInput.setText("");
                deadlineInput.setText("");
            } else {
                Toast.makeText(getContext(), "Inserisci un titolo e una scadenza", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadTasks() {
        List<Task> tasks = taskRepository.getAllTasks();

        taskAdapter = new TaskAdapter(tasks, position -> {
            Task taskToDelete = tasks.get(position);
            new AlertDialog.Builder(requireContext())
                    .setTitle("Conferma eliminazione")
                    .setMessage("Sei sicuro di voler eliminare questa task?")
                    .setPositiveButton("Elimina", (dialog, which) -> {

                        taskRepository.deleteTask(taskToDelete.getId());
                        tasks.remove(position);
                        taskAdapter.notifyItemRemoved(position);
                        Toast.makeText(getContext(), "Task eliminata con successo", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Annulla", null)
                    .show();
        });

        taskRecyclerView.setAdapter(taskAdapter);
    }
    private void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    deadlineInput.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }
}
