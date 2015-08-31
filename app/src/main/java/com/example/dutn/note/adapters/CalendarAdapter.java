package com.example.dutn.note.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dutn.note.R;
import com.example.dutn.note.dao.ConnectDB;
import com.example.dutn.note.dao.NoteReminderDAO;
import com.example.dutn.note.dto.NoteReminder;
import com.example.dutn.note.objects.CalendarObject;
import com.example.dutn.note.utils.CalendarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dutn on 27/07/2015.
 */
public class CalendarAdapter extends ArrayAdapter {

    private static final String TAG = CalendarAdapter.class.getSimpleName();
    private Activity context;
    private int layoutId;
    private ArrayList<CalendarObject> arrayList;
    private NoteReminderDAO noteReminderDAO;
    private NoteReminder noteReminder;

    public CalendarAdapter(Activity context, int layoutId, ArrayList<CalendarObject> arrayList) {
        super(context, layoutId, arrayList);
        this.context = context;
        this.layoutId = layoutId;
        this.arrayList = arrayList;
        noteReminderDAO = new NoteReminderDAO(new ConnectDB(context));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        TextView tv = (TextView) convertView.findViewById(R.id.numCalendar);
        CalendarObject obj = arrayList.get(position);
        if (obj.getShow() == 1) {
            if (obj.getDay() == CalendarUtils.currentDay()) {
                tv.setTextColor(Color.WHITE);
                tv.setTypeface(null, Typeface.BOLD);
                tv.setBackgroundResource(R.drawable.circle_gray);
            } else {
                tv.setTextColor(Color.BLACK);
            }
        } else {
            tv.setTextColor(Color.GRAY);
        }
        if (obj.getShow() == 1) {
            noteReminder = noteReminderDAO.get(obj.getYear() + "-" + CalendarUtils.addZero(obj.getMonth()) + "-" + CalendarUtils.addZero(obj.getDay()));
            if (noteReminder != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date d = sdf.parse(noteReminder.getTimeComplete());
                    int day = d.getDate();
                    int month = d.getMonth() + 1;
                    int year = d.getYear() + 1900;
                    if (obj.getDay() == day && obj.getMonth() == month && obj.getYear() == year) {
                        tv.setTextColor(Color.WHITE);
                        tv.setTypeface(null, Typeface.BOLD);
                        tv.setBackgroundResource(R.drawable.circle_green_float);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        tv.setText(obj.getDay() + "");
        return convertView;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

}
