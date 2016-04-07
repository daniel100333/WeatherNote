package com.example.daniel.weathernote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Daniel on 3/4/2016.
 */
public class EditNoteFragment extends Fragment {

    public static final String EXTRA_NOTE = "com.example.daniel.weathernote.note";

    private static final String TAG = EditNoteFragment.class.toString();

    private static final String ARG_NOTE_ID = "note_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Note mNote;
    private EditText mTitleField;
    private EditText mDescriptionField;
    private Button mDateButton;
    private Button mTimeButton;
    private Spinner mTypeSpinner;


    public static EditNoteFragment newInstance(UUID noteId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE_ID, noteId);

        EditNoteFragment fragment = new EditNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID noteId = (UUID) getArguments().getSerializable(ARG_NOTE_ID);
        if (NoteLab.get(getActivity()).getNote(noteId) != null) {
            mNote = NoteLab.get(getActivity()).getNote(noteId);
        } else {
            mNote = new Note(noteId);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note, container, false);

        mTitleField = (EditText) v.findViewById(R.id.note_title);
        mTitleField.setText(mNote.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNote.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDescriptionField = (EditText) v.findViewById(R.id.note_description);
        mDescriptionField.setText(mNote.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNote.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.note_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mNote.getDate());
                dialog.setTargetFragment(EditNoteFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton = (Button) v.findViewById(R.id.note_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(mNote.getDate());
                dialog.setTargetFragment(EditNoteFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mTypeSpinner = (Spinner) v.findViewById(R.id.note_type);
        Log.d(TAG, Integer.toString(mNote.getType()));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.note_types, R.layout.spinner_item_note);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(new TypeSpinnerSelectedListener());
        mTypeSpinner.setSelection(mNote.getType());

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_note:
                Intent noteCreatedIntent = new Intent();
                noteCreatedIntent.putExtra(EXTRA_NOTE, mNote);
                getActivity().setResult(Activity.RESULT_OK, noteCreatedIntent);
                getActivity().finish();
                return true;
            case android.R.id.home:
                Intent noteCancelledIntent = new Intent();
                getActivity().setResult(Activity.RESULT_CANCELED, noteCancelledIntent);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mNote.setDate(date);
            updateDate();
            updateTime();
        }

        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mNote.setDate(date);
            updateDate();
            updateTime();
        }
    }

    private class TypeSpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int noteType = position;
            mNote.setType(noteType);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

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

    private void updateDate() {
        mDateButton.setText(formatMonth(mNote.getDate()));
    }

    private void updateTime() {
        mTimeButton.setText(formatTime(mNote.getDate()));
    }
}
