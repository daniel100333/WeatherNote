package com.example.daniel.weathernote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by Daniel on 4/5/2016.
 */
public class EditNoteActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE_ID =
            "com.example.daniel.weathernote.note_id";


    protected Fragment createFragment(UUID noteId) {
        return EditNoteFragment.newInstance(noteId);
    }

    public static Intent newIntent(Context packageContext, UUID noteId) {
        Intent intent = new Intent(packageContext, EditNoteActivity.class);
        intent.putExtra(EXTRA_NOTE_ID, noteId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        UUID noteId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_NOTE_ID);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment == null) {
            fragment = createFragment(noteId);
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}
