package com.example.shakavya.simpleitodo;

/**
 * Created by shakavya on 8/25/15.
 */

import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper {



    private DatabaseConnect databaseConnect;
    private SQLiteDatabase sqLiteDatabase;

    private final Context context;

    public static final String DATABASE_NAME = "simpleTodo.db";

    public static final int DATABASE_VERSION = 2;


    public static final String TASK_TABLE_NAME = "_task";
    public static final String TASK_TABLE_COLUMN_ID = "_id";
    public static final String TASK_TABLE_COLUMN_TITLE = "_title";
    public static final String TASK_TABLE_COLUMN_DUE_DATE = "_due_date";
    public static final String TASK_TABLE_COLUMN_NOTE = "_note";
    public static final String TASK_TABLE_COLUMN_PRIORITY = "_priority";
    public static final String TASK_TABLE_CREATE
            = "create table " + TASK_TABLE_NAME
            + " ( "
            + TASK_TABLE_COLUMN_ID + " text primary key, "
            + TASK_TABLE_COLUMN_TITLE + " text not null, "
            + TASK_TABLE_COLUMN_DUE_DATE + " integer not null, "
            + TASK_TABLE_COLUMN_NOTE + " text,"
            + TASK_TABLE_COLUMN_PRIORITY + " integer not null"
            + " ); ";

    private static class DatabaseConnect extends SQLiteOpenHelper{

        public DatabaseConnect(Context context, String name, CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(com.example.shakavya.simpleitodo.DatabaseHelper.TASK_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table if exists " + com.example.shakavya.simpleitodo.DatabaseHelper.TASK_TABLE_NAME);
            onCreate(db);
        }

    }

    public DatabaseHelper(Context context){
        this.context = context;
    }

    public com.example.shakavya.simpleitodo.DatabaseHelper open(){
        databaseConnect = new DatabaseConnect(context, this.DATABASE_NAME, null, this.DATABASE_VERSION);
        sqLiteDatabase = databaseConnect.getWritableDatabase();
        return this;
    }

    public String getNewTaskId(){
        String uuid = null;
        Cursor cursor = null;
        do {
            uuid = UUID.randomUUID().toString();
            cursor = getTaskById(uuid);
        } while (cursor.getCount() > 0);

        return uuid;
    }
    public void editExistingTask(Task task){
        ContentValues updateValues;

        updateValues = new ContentValues();
        updateValues.put(TASK_TABLE_COLUMN_TITLE, task.getTitle());
        updateValues.put(TASK_TABLE_COLUMN_NOTE, task.getNote());
        updateValues.put(TASK_TABLE_COLUMN_DUE_DATE, task.getDueDate().getTimeInMillis());
        updateValues.put(TASK_TABLE_COLUMN_PRIORITY, task.getPriorityLevel());
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.update(TASK_TABLE_NAME, updateValues, TASK_TABLE_COLUMN_ID + " = '" + task.getId() + "'", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public void insertTask(Task task){
        ContentValues initialValues = new ContentValues();
        initialValues.put(TASK_TABLE_COLUMN_ID, task.getId());
        initialValues.put(TASK_TABLE_COLUMN_TITLE, task.getTitle());
        initialValues.put(TASK_TABLE_COLUMN_DUE_DATE, task.getDueDate().getTimeInMillis());
        initialValues.put(TASK_TABLE_COLUMN_NOTE, task.getNote());
        initialValues.put(TASK_TABLE_COLUMN_PRIORITY, task.getPriorityLevel());
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.insert(TASK_TABLE_NAME, null, initialValues);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public Cursor getAllTasks(){
        return sqLiteDatabase.query(TASK_TABLE_NAME,
                new String[] {TASK_TABLE_COLUMN_ID, TASK_TABLE_COLUMN_TITLE, TASK_TABLE_COLUMN_DUE_DATE, TASK_TABLE_COLUMN_NOTE, TASK_TABLE_COLUMN_PRIORITY},
                null, null, null, null, null);
    }

    public Cursor getTaskById(String taskId){
        return sqLiteDatabase.query(TASK_TABLE_NAME,
                new String[] {TASK_TABLE_COLUMN_ID, TASK_TABLE_COLUMN_TITLE, TASK_TABLE_COLUMN_DUE_DATE, TASK_TABLE_COLUMN_NOTE, TASK_TABLE_COLUMN_PRIORITY},
                TASK_TABLE_COLUMN_ID + " = '" + taskId + "'", null, null, null, null);
    }

    public void deleteTask(Task task){
        deleteTask(task.getId());
    }

    public void deleteTask(String taskId){
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.delete(TASK_TABLE_NAME, TASK_TABLE_COLUMN_ID + " = '" + taskId + "'", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }


}