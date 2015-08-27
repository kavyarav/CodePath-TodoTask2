package com.example.shakavya.simpleitodo;

/**
 * Created by shakavya on 8/25/15.
 */

import android.app.Activity;
import android.os.Bundle;

import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

    private ListView allTasksListView;
    private DatabaseHelper databaseHelper;
    private Cursor allTasksCursor;
    private SimpleCursorAdapter allTasksListViewAdapter;
    public static final int ADD_NEW_TASK_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allTasksListView = (ListView) findViewById(R.id.activity_view_all_tasks_Listview_all_tasks);
        allTasksListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                listAllTasksViewItemClickHandler(arg0, arg1, arg2);
            }
        });

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.open();
        initListAllTasksView();
    }

    public void initListAllTasksView(){
        if(this.databaseHelper != null){
            allTasksCursor = databaseHelper.getAllTasks();
            startManagingCursor(allTasksCursor);
            String[] from = new String[]{DatabaseHelper.TASK_TABLE_COLUMN_TITLE};
            int[] to = new int[]{R.id.activity_view_listview_all_tasks};
            allTasksListViewAdapter = new SimpleCursorAdapter(this,
                    R.layout.activity_view_all_tasks, allTasksCursor, from, to);
            this.allTasksListView.setAdapter(allTasksListViewAdapter);
        }
    }

    private void listAllTasksViewItemClickHandler(AdapterView<?> adapterView, View listView, int selectedItemId){

        Task selectedTask = new Task();
        allTasksCursor.moveToFirst();
        allTasksCursor.move(selectedItemId);
        selectedTask.setId(allTasksCursor.getString(allTasksCursor.getColumnIndex(DatabaseHelper.TASK_TABLE_COLUMN_ID)));
        selectedTask.setTitle(allTasksCursor.getString(allTasksCursor.getColumnIndex(DatabaseHelper.TASK_TABLE_COLUMN_TITLE)));
        selectedTask.getDueDate().setTimeInMillis(allTasksCursor.getLong(allTasksCursor.getColumnIndex(DatabaseHelper.TASK_TABLE_COLUMN_DUE_DATE)));
        selectedTask.setNote(allTasksCursor.getString(allTasksCursor.getColumnIndex(DatabaseHelper.TASK_TABLE_COLUMN_NOTE)));
        selectedTask.setPriorityLevel(allTasksCursor.getInt(allTasksCursor.getColumnIndex(DatabaseHelper.TASK_TABLE_COLUMN_PRIORITY)));
        Navigator.viewTaskDetails(this, selectedTask);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity_view_all_tasks_Menu_actionbar_Item_add_task:
                Navigator.addNewTask(this, this.databaseHelper);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}