package com.example.shakavya.simpleitodo;

/**
 * Created by shakavya on 8/25/15.
 */
        import java.util.Calendar;

        import android.os.Bundle;
        import android.app.Activity;
        import android.content.Intent;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.Spinner;

public class ModifyTaskActivity extends Activity {

    private Task task = null;
    private int currentJob;
    private final int CURRENT_JOB_EDIT = 1;
    private final int CURRENT_JOB_ADD = 2;
    private DatabaseHelper databaseHelper;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent;
        Bundle resultBundle;

        switch (item.getItemId()){

            case R.id.activity_modify_task_Menu_actionbar_Item_cancel:
            case android.R.id.home:
                resultIntent = new Intent();
                resultBundle = new Bundle();
                resultBundle.putSerializable(Task.TASK_BUNDLE_KEY, this.task);
                resultIntent.putExtras(resultBundle);
                setResult(ViewTaskDetailActivity.EDIT_TASK_REQUEST_CODE, resultIntent);

                ConfirmCancellationDialogHandler.showConfirmCancelDialog(this);
                return true;

            case R.id.activity_modify_task_Menu_actionbar_Item_save:

                if(this.currentJob == CURRENT_JOB_ADD){
                    addNewTask();
                } else {
                    editExistingTask();
                    resultIntent = new Intent();
                    resultBundle = new Bundle();
                    resultBundle.putSerializable(Task.TASK_BUNDLE_KEY, this.task);
                    resultIntent.putExtras(resultBundle);
                    setResult(ViewTaskDetailActivity.EDIT_TASK_REQUEST_CODE, resultIntent);

                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        Bundle resultBundle = new Bundle();
        resultBundle.putSerializable(Task.TASK_BUNDLE_KEY, this.task);
        resultIntent.putExtras(resultBundle);
        setResult(ViewTaskDetailActivity.EDIT_TASK_REQUEST_CODE, resultIntent);
        ConfirmCancellationDialogHandler.showConfirmCancelDialog(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.open();
        Bundle modifyTaskBundle = this.getIntent().getExtras();
        try {
            this.task = (Task) modifyTaskBundle.getSerializable(Task.TASK_BUNDLE_KEY);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        if (task != null){
            this.currentJob = this.CURRENT_JOB_EDIT;
            putDataToForm();
        } else {
            this.task = new Task();
            this.currentJob = this.CURRENT_JOB_ADD;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.modify_task, menu);
        return true;
    }

    private void updateTask(){

        String taskTitle = ((EditText)findViewById(R.id.activity_modify_task_Edittext_task_title)).getText().toString();
        this.task.setTitle(taskTitle);
        DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.activity_modify_task_Datepicker_due_date);
        this.task.getDueDate().set(Calendar.DATE, taskDueDatePicker.getDayOfMonth());
        this.task.getDueDate().set(Calendar.MONTH, taskDueDatePicker.getMonth());
        this.task.getDueDate().set(Calendar.YEAR, taskDueDatePicker.getYear());
        String taskNote = ((EditText)findViewById(R.id.activity_modify_task_EditText_note)).getText().toString();
        this.task.setNote(taskNote);
        int priorityLevel = ((Spinner)findViewById(R.id.activity_modify_task_Spinner_priority_level)).getSelectedItemPosition();
        this.task.setPriorityLevel(priorityLevel);

    }

    private void addNewTask(){
        updateTask();
        String taskId = databaseHelper.getNewTaskId();
        this.task.setId(taskId);
        this.databaseHelper.insertTask(this.task);
    }

    private void editExistingTask(){
        updateTask();
        databaseHelper.editExistingTask(this.task);
    }

    private void putDataToForm(){
        if (this.currentJob == this.CURRENT_JOB_EDIT){

            EditText taskTitleEditText = (EditText) findViewById(R.id.activity_modify_task_Edittext_task_title);
            taskTitleEditText.setText(this.task.getTitle());
            DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.activity_modify_task_Datepicker_due_date);
            taskDueDatePicker.updateDate(this.task.getDueDate().get(Calendar.YEAR),
                    this.task.getDueDate().get(Calendar.MONTH),
                    this.task.getDueDate().get(Calendar.DATE));
            EditText taskNoteEditText = (EditText) findViewById(R.id.activity_modify_task_EditText_note);
            taskNoteEditText.setText(this.task.getNote());
            Spinner taskPriorityLevelSpinner = (Spinner) findViewById(R.id.activity_modify_task_Spinner_priority_level);
            taskPriorityLevelSpinner.setSelection(this.task.getPriorityLevel());

        }
    }

}