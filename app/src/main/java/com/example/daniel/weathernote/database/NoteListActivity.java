package com.example.daniel.weathernote.database;

import android.support.v4.app.Fragment;

import com.example.daniel.weathernote.NoteListFragment;
import com.example.daniel.weathernote.SingleFragmentActivity;

/**
 * Created by Daniel on 3/3/2016.
 */
public class NoteListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new NoteListFragment();
    }
}
