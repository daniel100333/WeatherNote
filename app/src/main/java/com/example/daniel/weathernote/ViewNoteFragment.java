package com.example.daniel.weathernote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Daniel on 4/1/2016.
 */
public class ViewNoteFragment extends Fragment {

    private static final String TAG = ViewNoteFragment.class.toString();

    private static final String ARG_NOTE_ID = "note_id";

    private Note mNote;
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private Button mDateButton;
    private Button mTimeButton;
    private Spinner mTypeSpinner;
    private FloatingActionButton mEditNoteFloatingActionButton;

    public static ViewNoteFragment newInstance(UUID noteId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE_ID, noteId);

        ViewNoteFragment fragment = new ViewNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID noteId = (UUID) getArguments().getSerializable(ARG_NOTE_ID);
        mNote = NoteLab.get(getActivity()).getNote(noteId);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_note, container, false);

        mTitleTextView = (TextView) v.findViewById(R.id.note_title);
        mTitleTextView.setText(mNote.getTitle());

        mDescriptionTextView = (TextView) v.findViewById(R.id.note_description);
        mDescriptionTextView.setText(mNote.getDescription());

        mDateButton = (Button) v.findViewById(R.id.note_date);
        mDateButton.setEnabled(false);

        mTimeButton = (Button) v.findViewById(R.id.note_time);
        mTimeButton.setEnabled(false);

        mTypeSpinner = (Spinner) v.findViewById(R.id.note_type);
        mTypeSpinner.setEnabled(false);

        mEditNoteFloatingActionButton = (FloatingActionButton) v.findViewById(R.id.edit_note_fab);
        mEditNoteFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                NoteFragment editnote = NoteFragment.newInstance(mNote.getId());

            }
        });

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_view_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_note:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
