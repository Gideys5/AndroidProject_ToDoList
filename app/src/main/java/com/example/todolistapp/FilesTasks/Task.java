package com.example.todolistapp.FilesTasks;


public class Task {
    private int id;
    private String title;
    private String deadline;

//    private String priority;

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
//    public String getPriority() {
//        return priority;
//    }
//
//    public void setPriority(String priority) {
//        this.priority = priority;
//    }
}
