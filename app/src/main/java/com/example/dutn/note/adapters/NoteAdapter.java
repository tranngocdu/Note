package com.example.dutn.note.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dutn.note.R;
import com.example.dutn.note.dao.ConnectDB;
import com.example.dutn.note.dao.NoteImageDAO;
import com.example.dutn.note.dao.NoteTextDAO;
import com.example.dutn.note.dao.NoteVideoClipDAO;
import com.example.dutn.note.dto.Note;
import com.example.dutn.note.dto.NoteImage;
import com.example.dutn.note.dto.NoteText;
import com.example.dutn.note.dto.NoteVideoClip;
import com.example.dutn.note.utils.BitmapUtils;
import com.example.dutn.note.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dutn on 29/07/2015.
 */
public class NoteAdapter extends ArrayAdapter {

    private Activity context;
    private int layoutId;
    private ArrayList<Note> arrayList;
    private TextView title, content, thumbnailTextView;
    private ImageView thumbnailImageView;
    private LinearLayout contentLayout;
    private RelativeLayout thumbnailLayout;
    private ConnectDB connectDB;
    private NoteTextDAO noteTextDAO;
    private NoteVideoClipDAO noteVideoClipDAO;
    private NoteImageDAO noteImageDAO;
    private FileUtils fileUtils;
    private HashMap<Integer, Bitmap> hm;

    public NoteAdapter(Activity context, int layoutId, ArrayList<Note> arrayList, HashMap<Integer, Bitmap> hm) {
        super(context, layoutId, arrayList);
        this.context = context;
        this.layoutId = layoutId;
        this.arrayList = arrayList;
        this.hm = hm;
        connectDB = new ConnectDB(context);
        noteTextDAO = new NoteTextDAO(connectDB);
        noteImageDAO = new NoteImageDAO(connectDB);
        noteVideoClipDAO = new NoteVideoClipDAO(connectDB);
        fileUtils = new FileUtils();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(layoutId, null);
        contentLayout = (LinearLayout) view.findViewById(R.id.content);
        title = (TextView) view.findViewById(R.id.titleTextView);
        content = (TextView) view.findViewById(R.id.contentTextView);
        thumbnailLayout = (RelativeLayout) view.findViewById(R.id.thumbnail);
        thumbnailImageView = (ImageView) view.findViewById(R.id.thumbnailImage);
        thumbnailTextView = (TextView) view.findViewById(R.id.thumbnailText);
        final Note note = arrayList.get(position);
        final int noteId = note.getNoteId();
        title.setText(note.getTitle());
        content.setText(getContent(noteId));
        attachThumbnail(noteId);
        return view;
    }

    public void attachThumbnail(int noteId) {
        if (hm.containsKey(noteId)) {
            thumbnailImageView.setImageBitmap(hm.get(noteId));
        } else {
            thumbnailImageView.setVisibility(View.GONE);
            thumbnailTextView.setVisibility(View.GONE);
        }
    }

    public String getContent(int noteId) {
        StringBuilder builder = new StringBuilder();
        for (NoteText noteText : noteTextDAO.getAll(noteId)) {
            builder.append(noteText.getContent() + " ");
        }
        return builder.toString();
    }
}
