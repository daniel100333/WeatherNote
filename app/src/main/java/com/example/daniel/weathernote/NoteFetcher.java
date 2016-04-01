package com.example.daniel.weathernote;

import android.content.Context;
import android.util.Log;

import com.example.daniel.weathernote.Note;
import com.example.daniel.weathernote.NoteLab;

import java.util.List;

/**
 * Created by Daniel on 3/30/2016.
 */
public class NoteFetcher {
    private static final String TAG = "NoteFetcher";

    public List<Note> fetchNotes(Context context) {
        NoteLab noteLab = NoteLab.get(context);
        List<Note> notes = noteLab.getNotes();

        return notes;
    }
}
