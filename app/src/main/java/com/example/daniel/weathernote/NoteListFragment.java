package com.example.daniel.weathernote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel on 3/3/2016.
 */
public class NoteListFragment extends Fragment {

    private static final String TAG = NoteListFragment.class.getName();

    private RecyclerView mNoteRecyclerView;
    private FloatingActionButton mAddNoteFloatingActionButton;
    private NoteAdapter mAdapter;
    private List<Note> mNotes = new ArrayList<>();
    private FetchNotesTask mFetchNotes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFetchNotes = new FetchNotesTask();
        mFetchNotes.execute(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        mNoteRecyclerView = (RecyclerView) view
                .findViewById(R.id.note_recycler_view);
        mNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNoteRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mAddNoteFloatingActionButton = (FloatingActionButton) view
                .findViewById(R.id.add_note_fab);
        mAddNoteFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note();
                NoteLab.get(getActivity()).addNote(note);
                Intent intent = NotePagerActivity
                        .newIntent(getActivity(), note.getId());
                startActivity(intent);
            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchNotes.cancel(true);
    }

    private void updateUI() {
        if (mAdapter == null && isAdded()) {
            mAdapter = new NoteAdapter(mNotes);
            mNoteRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNotes(mNotes);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class NoteHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;

        //colormap in here
        //getActivity().getResources

        private Note mNote;

        public NoteHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_note_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_note_date_text_view);
        }

        public void bindNote(Note note) {
            mNote = note;
            mTitleTextView.setText(mNote.getTitle());
            //int valuefrommap
            //mTitleTextView.setTextColor(getContext().getResources().getColor());
            mDateTextView.setText(formatDateAndTime(mNote.getDate()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = NotePagerActivity.newIntent(getActivity(), mNote.getId());
            startActivity(intent);
        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {
        private List<Note> mNoteListItems;

        public NoteAdapter(List<Note> notes) {
            mNoteListItems = notes;
        }

        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_note, parent, false);
            return new NoteHolder(view);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {
            Note note = mNoteListItems.get(position);
            holder.bindNote(note);
        }

        @Override
        public int getItemCount() {
            return mNoteListItems.size();
        }

        public void setNotes(List<Note> notes) {
            mNoteListItems = notes;
        }
    }

    private class FetchNotesTask extends AsyncTask<Context,Void,List<Note>> {


        @Override
        protected List<Note> doInBackground(Context... params) {
            Log.i(TAG, "Fetching notes");
            return new NoteFetcher().fetchNotes(params[0]);
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            mNotes = notes;
            updateUI();
        }
    }

    private String formatDateAndTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy | hh:mm a");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}
