package com.example.daniel.weathernote.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.daniel.weathernote.Note;
import com.example.daniel.weathernote.database.NoteDbSchema.NoteTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Daniel on 3/3/2016.
 */
public class NoteCursorWrapper extends CursorWrapper {
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote() {
        String uuidString = getString(getColumnIndex(NoteTable.Cols.UUID));
        String title = getString(getColumnIndex(NoteTable.Cols.TITLE));
        long date = getLong(getColumnIndex(NoteTable.Cols.DATE));
        String type = getString(getColumnIndex(NoteTable.Cols.TYPE));

        Note note = new Note(UUID.fromString(uuidString));
        note.setTitle(title);
        note.setDate(new Date(date));
        note.setType(type);

        return note;
    }
}
