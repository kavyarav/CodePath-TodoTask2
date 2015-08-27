package com.example.shakavya.simpleitodo;

/**
 * Created by shakavya on 8/25/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class ConfirmCancellationDialogHandler {

    public static void showConfirmCancelDialog(Activity sourceActivity){
        Dialog confirmCancelDialog;
        confirmCancelDialog = new AlertDialog.Builder(sourceActivity)
                .setIcon(android.R.drawable.ic_menu_help)
                .setTitle(R.string.modify_task_Dialog_confirmcancel_TextView)
                .setPositiveButton(R.string.modify_task_Dialog_confirmcancel_Button_positive,
                        new ConfirmCancellationDialogPositiveButtonListener(sourceActivity))
                .setNegativeButton(R.string.modify_task_Dialog_confirmcancel_Button_negative,
                        new ConfirmCancellationDialogNegativeButtonListener())
                .create();
        confirmCancelDialog.show();
    }
    
    private static class ConfirmCancellationDialogNegativeButtonListener implements OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }
    
    private static class ConfirmCancellationDialogPositiveButtonListener implements OnClickListener{

        private Activity sourceActivity;

        public ConfirmCancellationDialogPositiveButtonListener(Activity sourceActivity){
            this.sourceActivity = sourceActivity;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            sourceActivity.finish();
        }
    }


}