package com.example.dutn.note.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.dutn.note.R;
import com.example.dutn.note.dao.ConnectDB;
import com.example.dutn.note.dao.NoteContentDAO;
import com.example.dutn.note.dao.NoteDAO;
import com.example.dutn.note.dao.NoteImageDAO;
import com.example.dutn.note.dao.NoteTextDAO;
import com.example.dutn.note.dao.NoteVideoClipDAO;
import com.example.dutn.note.dao.NoteVoiceDAO;
import com.example.dutn.note.dao.StringDB;
import com.example.dutn.note.dto.Note;
import com.example.dutn.note.dto.NoteContent;
import com.example.dutn.note.dto.NoteImage;
import com.example.dutn.note.dto.NoteText;
import com.example.dutn.note.dto.NoteVideoClip;
import com.example.dutn.note.dto.NoteVoice;
import com.example.dutn.note.listeners.MainListener;
import com.example.dutn.note.utils.FileUtils;
import com.example.dutn.note.utils.GestureListener;

import java.util.ArrayList;

/**
 * Created by dutn on 05/08/2015.
 */
public class ViewNoteFragment extends Fragment implements View.OnClickListener, GestureListener.Callback {

    public static final String TAG = ViewNoteFragment.class.getSimpleName();
    private static final int MARGIN = 10;
    private Bundle b;
    private ScrollView scrollView;
    private LinearLayout contentLayout;
    private TextView titleTextView;
    private ImageButton editButton;
    private int noteId;
    private ConnectDB connectDB;
    private Note note;
    private NoteContent noteContent;
    private NoteDAO noteDAO;
    private NoteContentDAO noteContentDAO;
    private NoteTextDAO noteTextDAO;
    private NoteImageDAO noteImageDAO;
    private NoteVoiceDAO noteVoiceDAO;
    private NoteVideoClipDAO noteVideoClipDAO;
    private ArrayList<NoteContent> noteContentArrayList;
    private ArrayList<String> stringArrayList;
    private ArrayList<Bitmap> bitmapArrayList;
    private LinearLayout.LayoutParams layoutParams;
    private LoadData loadData;
    private MainListener.Callback callback;
    private GestureDetector gestureDetector;
    private GestureListener gestureListener;


    public ViewNoteFragment() {
        gestureListener = new GestureListener();
        gestureListener.setCallback(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        connectDB = new ConnectDB(getActivity());
        noteDAO = new NoteDAO(connectDB);
        noteContentDAO = new NoteContentDAO(connectDB);
        noteTextDAO = new NoteTextDAO(connectDB);
        noteImageDAO = new NoteImageDAO(connectDB);
        noteVoiceDAO = new NoteVoiceDAO(connectDB);
        noteVideoClipDAO = new NoteVideoClipDAO(connectDB);
        stringArrayList = new ArrayList<String>();
        bitmapArrayList = new ArrayList<Bitmap>();
        gestureDetector = new GestureDetector(getActivity(), gestureListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_view_note, parent, false);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        contentLayout = (LinearLayout) view.findViewById(R.id.contentLayout);
        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        editButton = (ImageButton) view.findViewById(R.id.btn_edit);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, MARGIN, 0, MARGIN);
        b = getArguments();
        noteId = b.getInt(StringDB.NOTE_ID);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editButton.setOnClickListener(this);
        /*scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            int i;
            int k;

            @Override
            public void onScrollChanged() {
                int j = scrollView.getScrollY();
                try {
                    k = getResources().getDisplayMetrics().heightPixels;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (i > j || i < 0) {
                    editButton.setVisibility(View.VISIBLE);
                } else if (i < j || i < k || i == j) {
                    editButton.setVisibility(View.GONE);
                }
                Log.e(TAG, "onScrollChanged : " + i + "/" + j + "/" + k);
                i = j;
            }
        });*/
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        note = noteDAO.get(noteId);
        stringArrayList.add(note.getTitle());
        titleTextView.setText(note.getTitle());
        loadData = new LoadData();
        loadData.start();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                callback.goToEditNote(b, noteContentArrayList, stringArrayList, bitmapArrayList);
                break;
        }
    }

    public void setCallback(MainListener.Callback callback) {
        this.callback = callback;
    }

    @Override
    public void previous() {

    }

    @Override
    public void next() {

    }

    @Override
    public void up() {
        editButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void down() {
        editButton.setVisibility(View.GONE);
    }

    public class LoadData extends Thread {

        public LoadData() {
        }

        @Override
        public void run() {
            noteContentArrayList = (ArrayList) noteContentDAO.getAll(noteId);
            for (int i = 0; i < noteContentArrayList.size(); i++) {
                noteContent = noteContentArrayList.get(i);
                if (noteContent.getType().equals(StringDB.TYPE_TEXT)) {
                    final NoteText noteText = noteTextDAO.get(noteContent.getText_id());
                    if (!noteText.getContent().trim().equals("")) {
                        contentLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                TextView textView = new TextView(getActivity());
                                textView.setLayoutParams(layoutParams);
                                textView.setLinksClickable(true);
                                textView.setText(noteText.getContent());
                                contentLayout.addView(textView);
                                Linkify.addLinks(textView, Linkify.ALL);
                            }
                        });
                    }
                    stringArrayList.add(noteText.getContent());
                } else if (noteContent.getType().equals(StringDB.TYPE_IMAGE)) {
                    final NoteImage noteImage = noteImageDAO.get(noteContent.getImage_id());
                    contentLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bmp = new FileUtils().readBitmapFromSdcard(noteImage.getUrl());
                            ImageView imageView = new ImageView(getActivity());
                            imageView.setImageBitmap(bmp);
                            imageView.setAdjustViewBounds(true);
                            imageView.setLayoutParams(layoutParams);
                            contentLayout.addView(imageView);
                            bitmapArrayList.add(bmp);
                        }
                    });
                } else if (noteContent.getType().equals(StringDB.TYPE_VOICE)) {
                    NoteVoice noteVoice = noteVoiceDAO.get(noteContent.getVoice_id());

                } else if (noteContent.getType().equals(StringDB.TYPE_VIDEOCLIP)) {
                    NoteVideoClip noteVideoClip = noteVideoClipDAO.get(noteContent.getVideoclip_id());
                    contentLayout.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }
        }
    }
}
