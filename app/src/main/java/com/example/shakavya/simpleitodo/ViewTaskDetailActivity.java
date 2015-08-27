package com.example.shakavya.simpleitodo;

/**
 * Created by shakavya on 8/25/15.
 */
import java.util.Calendar;
import java.util.Locale;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewTaskDetailActivity extends Activity {


    private Task task;

    public static final int EDIT_TASK_REQUEST_CODE = 1;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_detail);
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.open();
        Bundle taskDetailBundle = this.getIntent().getExtras();
        try{
            this.task = (Task) taskDetailBundle.getSerializable(Task.TASK_BUNDLE_KEY);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        if(this.task == null){
            this.finish();
        } else {
            this.putDataToView();
        }

    }
    private void putDataToView(){
        if(this.task == null){
            this.finish();
        } else {
            TextView taskTitleTextView = (TextView) findViewById(R.id.activity_view_task_detail_TextView_task_title_content);
            taskTitleTextView.setText(this.task.getTitle());
            TextView taskDueDateTextView = (TextView) findViewById(R.id.activity_view_task_detail_TextView_due_date_content);
            Calendar dueDate = this.task.getDueDate();
            String dueDateString = dueDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " "
                    + dueDate.get(Calendar.DATE) + " "
                    + dueDate.get(Calendar.YEAR);
            taskDueDateTextView.setText(dueDateString);
            TextView taskNoteTextView = (TextView) findViewById(R.id.activity_view_task_detail_TextView_note_content);
            taskNoteTextView.setText(this.task.getNote());
            TextView priorityTextView = (TextView) findViewById(R.id.activity_view_task_detail_TextView_prority_level_content);
            String priorityString;
            switch (this.task.getPriorityLevel()){
                case Task.HIGH_PRIORITY:
                    priorityString = this.getString(R.string.modify_task_priority_level_high);
                    break;
                case Task.MEDIUM_PRIORITY:
                    priorityString = this.getString(R.string.modify_task_priority_level_medium);
                    break;
                default:
                    priorityString = this.getString(R.string.modify_task_priority_level_low);
                    break;
            }
            priorityTextView.setText(priorityString);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_task_detail, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_TASK_REQUEST_CODE){
            this.task = (Task) data.getExtras().getSerializable(Task.TASK_BUNDLE_KEY);
            this.putDataToView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity_view_task_detail_Menu_actionbar_Item_edit:
                Navigator.editExistingTask(this, this.task);
                return true;
            case R.id.activity_view_task_detail_Menu_actionbar_Item_delete:
                ConfirmDeletionDialog.showConfirmDeleteDialogForTask(this, this.task, this.databaseHelper);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}