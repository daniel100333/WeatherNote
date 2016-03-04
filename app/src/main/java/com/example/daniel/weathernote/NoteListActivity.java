package com.example.daniel.weathernote;

import android.support.v4.app.Fragment;

/**
 * Created by Daniel on 3/4/2016.
 */
public class NoteListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new NoteListFragment();
    }
}
