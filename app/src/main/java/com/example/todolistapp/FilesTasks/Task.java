package com.example.todolistapp.FilesTasks;

import android.renderscript.RenderScript;

public class Task {
    private int id;
    private String title;
    private String deadline;

    private String priority;

    public Task(String title, String deadline) {
        this.title = title;
        this.deadline = deadline;
    }

    public Task(int id, String title, String deadline) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public String getPriorita() {
        return priority;
    }

    public void setPriorita(String priority) {
        this.priority = priority;
    }
}
/*

<Spinner
    android:id="@+id/spinnerPriorita"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:entries="@array/priorita_array" />


    <resources>
    <string-array name="priorita_array">
        <item>Alta</item>
        <item>Media</item>
        <item>Bassa</item>
    </string-array>
</resources>
 */
