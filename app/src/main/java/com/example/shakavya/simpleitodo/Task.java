package com.example.shakavya.simpleitodo;

/**
 * Created by shakavya on 8/25/15.
 */
import java.io.Serializable;
import java.util.Calendar;

public class Task implements Serializable {

    private String id;
    private String title;
    private Calendar dueDate;
    public static final int HIGH_PRIORITY = 0;
    public static final int MEDIUM_PRIORITY = 1;
    public static final int LOW_PRIORITY = 2;


    private String note;
    private int priorityLevel;

    public static final String TASK_BUNDLE_KEY = "task_bundle_key";

    public Task(){
        this.id = "";
        this.title = "";
        this.dueDate = Calendar.getInstance();
        this.note = "";
        this.priorityLevel = HIGH_PRIORITY;
    }

    public Task(String id, String title, Calendar dueDate, String note,
                int priorityLevel) {
        super();
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.note = note;
        this.priorityLevel = priorityLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

}
