package com.example.shakavya.simpleitodo;

/**
 * Created by shakavya on 8/25/15.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class ConfirmDeletionDialog {

    public static void showConfirmDeleteDialogForTask(Activity sourceActivity, Task task, DatabaseHelper databaseHelper){
        Dialog confirmCancelDialog;
        confirmCancelDialog = new AlertDialog.Builder(sourceActivity)
                .setIcon(android.R.drawable.ic_menu_help)
                .setTitle("Are you sure to want to delete?")
                .setPositiveButton("Yes",
                        new ConfirmDeleteDialogForTaskPositiveButtonListener(sourceActivity, task, databaseHelper))
                .setNegativeButton("No",
                        new ConfirmDeleteDialogNegativeButtonListener())
                .create();
        confirmCancelDialog.show();
    }

    private static class ConfirmDeleteDialogForTaskPositiveButtonListener implements OnClickListener{

        private Activity sourceActivity;
        private Task task;
        private DatabaseHelper databaseHelper;
        public ConfirmDeleteDialogForTaskPositiveButtonListener(Activity sourceActivity, Task task, DatabaseHelper databaseHelper){
            this.sourceActivity = sourceActivity;
            this.task = task;
            this.databaseHelper = databaseHelper;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            databaseHelper.deleteTask(task);
            sourceActivity.finish();
        }
    }

    private static class ConfirmDeleteDialogNegativeButtonListener implements OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }
}