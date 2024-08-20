package com.gmail.kfasih.notespad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    public static DB instance;
    public static final String DB_NAME = "NotesDB";
    public static final int DB_VERSION = 2;

    private DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public static DB getInstance(Context context) {
        if (instance == null) {
            instance = new DB(context);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(NotesModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            sqLiteDatabase.execSQL(NotesModel.DROP_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    // CRUD Operations for NOTES Table
    public boolean insertNotes(NotesModel notesModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesModel.COL_Title, notesModel.getTitle());
        values.put(NotesModel.COL_DESCRIPTION, notesModel.getDescription());
        values.put(NotesModel.COL_DATE, notesModel.getDate());
        values.put(NotesModel.COL_NUM_OF_CHARACTER, notesModel.getNumOfCharacter());
        if (notesModel.getImagePath() != null){
            values.put(NotesModel.COL_IMAGE_PATH, notesModel.getImagePath());
        }
        if (notesModel.getBackgroundResourceId() != 0){
            values.put(NotesModel.COL_BACKGROUND_RESOURCE_ID, notesModel.getBackgroundResourceId());
        }
        long rowId;
        try{
           rowId = db.insert(NotesModel.TABLE_NAME, null, values);
        }catch (Exception exception){
            return false;
        }
        return  rowId!=-1;
    }

    public boolean updateNotes(NotesModel notesModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesModel.COL_Title, notesModel.getTitle());
        values.put(NotesModel.COL_DESCRIPTION, notesModel.getDescription());
        values.put(NotesModel.COL_DATE, notesModel.getDate());
        values.put(NotesModel.COL_NUM_OF_CHARACTER, notesModel.getNumOfCharacter());
        if (notesModel.getImagePath() != null){
            values.put(NotesModel.COL_IMAGE_PATH, notesModel.getImagePath());
        }
        if (notesModel.getBackgroundResourceId() != 0){
            values.put(NotesModel.COL_BACKGROUND_RESOURCE_ID, notesModel.getBackgroundResourceId());
        }
        long rowId;
        try{
            rowId = db.update(NotesModel.TABLE_NAME,values,NotesModel.COL_ID+"=?",new String[]{String.valueOf(notesModel.getId())});
        }catch (Exception exception){
            return false;
        }
        return  rowId != -1;
    }

    public boolean deleteNotes(NotesModel notesModel){
        SQLiteDatabase db = getWritableDatabase();
        long rowId;

        try{
            rowId = db.delete(NotesModel.TABLE_NAME,NotesModel.COL_ID+"=?",new String[]{String.valueOf(notesModel.getId())});
        }catch (Exception exception) {
            return false;
        }
        return rowId != -1;
    }


    public List<NotesModel> getAllNotes(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(NotesModel.SELECT_ALL, null);
        List<NotesModel> notesList = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            do {
                NotesModel notesModel = new NotesModel();
                int index = cursor.getColumnIndex(NotesModel.COL_ID);
                notesModel.setId(cursor.getInt(index));
                index = cursor.getColumnIndex(NotesModel.COL_Title);
                notesModel.setTitle(cursor.getString(index));
                index = cursor.getColumnIndex(NotesModel.COL_DESCRIPTION);
                notesModel.setDescription(cursor.getString(index));
                index = cursor.getColumnIndex(NotesModel.COL_DATE);
                notesModel.setDate(cursor.getString(index));
                index = cursor.getColumnIndex(NotesModel.COL_IMAGE_PATH);
                notesModel.setImagePath(cursor.getString(index));
                index = cursor.getColumnIndex(NotesModel.COL_BACKGROUND_RESOURCE_ID);
                notesModel.setBackgroundResourceId(cursor.getInt(index));
                notesList.add(notesModel);
            }while (cursor.moveToNext());
        }
        return notesList;
    }
}
