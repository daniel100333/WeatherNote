package com.example.daniel.weathernote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.daniel.weathernote.database.NoteDbSchema.NoteTable;

/**
 * Created by Daniel on 3/3/2016.
 */
public class NoteBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "noteBase.db";

    public NoteBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + NoteTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        NoteTable.Cols.UUID + ", " +
                        NoteTable.Cols.TITLE + ", " +
                        NoteTable.Cols.DESCRIPTION + ", " +
                        NoteTable.Cols.DATE + ", " +
                        NoteTable.Cols.TYPE +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
