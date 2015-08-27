package com.example.shakavya.simpleitodo;

/**
 * Created by shakavya on 8/25/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class Navigator {

    public static void editExistingTask(Activity sourceActivity, Task existingTask){
        Intent editExistingTaskIntent = new Intent(sourceActivity, ModifyTaskActivity.class);
        Bundle editExistingTaskBundle = new Bundle();
        editExistingTaskBundle.putSerializable(Task.TASK_BUNDLE_KEY, existingTask);
        editExistingTaskIntent.putExtras(editExistingTaskBundle);
        sourceActivity.startActivityForResult(editExistingTaskIntent, ViewTaskDetailActivity.EDIT_TASK_REQUEST_CODE);
    }

    public static void addNewTask(Activity sourceActivity, DatabaseHelper databaseHelper) {
        Intent addNewTaskIntent = new Intent(sourceActivity, ModifyTaskActivity.class);
        sourceActivity.startActivityForResult(addNewTaskIntent, MainActivity.ADD_NEW_TASK_REQUEST_CODE);
    }

    public static void viewTaskDetails(Activity sourceActivity, Task task){
        Intent viewTaskDetailIntent = new Intent(sourceActivity, ViewTaskDetailActivity.class);
        Bundle viewTaskDetailBundle = new Bundle();
        viewTaskDetailBundle.putSerializable(Task.TASK_BUNDLE_KEY, task);
        viewTaskDetailIntent.putExtras(viewTaskDetailBundle);
        sourceActivity.startActivity(viewTaskDetailIntent);
    }

    public static void showAllTasks(Activity sourceActivity){
        Intent showAllTasksIntent = new Intent(sourceActivity, MainActivity.class);
        sourceActivity.startActivity(showAllTasksIntent);
    }

}
