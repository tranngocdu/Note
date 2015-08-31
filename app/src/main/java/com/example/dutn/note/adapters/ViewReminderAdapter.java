package com.example.dutn.note.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dutn.note.R;
import com.example.dutn.note.dao.ConnectDB;
import com.example.dutn.note.dao.NoteReminderDAO;
import com.example.dutn.note.dto.NoteReminder;

import java.util.ArrayList;

/**
 * Created by trandu on 11/08/2015.
 */
public class ViewReminderAdapter extends ArrayAdapter {

    private String TAG = ViewReminderAdapter.class.getSimpleName();
    private Activity context;
    private int layoutId, reminderId;
    private ArrayList<NoteReminder> arrayList;
    private TextView time;
    private TextView content;
    private ImageButton imageButton;
    private NoteReminder noteReminder;
    private NoteReminderDAO noteReminderDAO;
    private Callback callback;

    public ViewReminderAdapter(Activity context, int layoutId, ArrayList<NoteReminder> arrayList) {
        super(context, layoutId, arrayList);
        this.context = context;
        this.layoutId = layoutId;
        this.arrayList = arrayList;
        noteReminderDAO = new NoteReminderDAO(new ConnectDB(this.context));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(layoutId, null);
        time = (TextView) view.findViewById(R.id.timeReminderTextView);
        content = (TextView) view.findViewById(R.id.contentReminderTextView);
        imageButton = (ImageButton) view.findViewById(R.id.deleteReminderButton);
        noteReminder = arrayList.get(position);
        reminderId = noteReminder.getId();
        Log.e(TAG, "getView : " + position + "/" + noteReminder.getId()+ "/" + noteReminder.getTimeComplete() + "/" + noteReminder.getContent());
        try {
            time.setText(noteReminder.getTimeComplete().substring(noteReminder.getTimeComplete().lastIndexOf(" "), noteReminder.getTimeComplete().length()));
        } catch (Exception ex) {
        }
        content.setTextColor(Color.parseColor("#000000"));
        content.setText(noteReminder.getContent());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.deleteReminder(noteReminderDAO, reminderId);
                //Log.e(TAG, "onClick : " + position + "/" + reminderId);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public interface Callback {
        void deleteReminder(NoteReminderDAO noteReminderDAO, int id);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

}
