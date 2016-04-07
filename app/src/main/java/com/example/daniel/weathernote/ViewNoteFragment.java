package com.example.daniel.weathernote;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Daniel on 4/1/2016.
 */
public class ViewNoteFragment extends Fragment {

    private static final String TAG = ViewNoteFragment.class.toString();

    private static final int EDIT_NOTE = 0;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_note, container, false);

        mTitleTextView = (TextView) v.findViewById(R.id.note_title);
        mTitleTextView.setText(mNote.getTitle());

        mDescriptionTextView = (TextView) v.findViewById(R.id.note_description);
        mDescriptionTextView.setText(mNote.getDescription());

        mDateButton = (Button) v.findViewById(R.id.note_date);
        mDateButton.setText(formatMonth(mNote.getDate()));
        mDateButton.setClickable(false);

        mTimeButton = (Button) v.findViewById(R.id.note_time);
        mTimeButton.setText(formatTime(mNote.getDate()));
        mTimeButton.setClickable(false);

        mTypeSpinner = (Spinner) v.findViewById(R.id.note_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.note_types, R.layout.spinner_item_note);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setSelection(mNote.getType());
        mTypeSpinner.setEnabled(false);

        mEditNoteFloatingActionButton = (FloatingActionButton) v.findViewById(R.id.edit_note_fab);
        mEditNoteFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditNoteActivity.newIntent(getActivity(), mNote.getId());
                startActivityForResult(intent, EDIT_NOTE);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == EDIT_NOTE) {
            mNote = (Note) data.getSerializableExtra(EditNoteFragment.EXTRA_NOTE);
            NoteLab.get(getActivity()).updateNote(mNote);

            mTitleTextView.setText(mNote.getTitle());
            mDescriptionTextView.setText(mNote.getDescription());
            mDateButton.setText(formatMonth(mNote.getDate()));
            mTimeButton.setText(formatTime(mNote.getDate()));
            mTypeSpinner.setSelection(mNote.getType());
        }
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

    private String formatMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    private String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
