package com.example.dutn.note.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dutn.note.R;
import com.example.dutn.note.adapters.CalendarAdapter;
import com.example.dutn.note.listeners.MainListener;
import com.example.dutn.note.utils.GestureListener;
import com.example.dutn.note.objects.CalendarObject;
import com.example.dutn.note.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by dutn on 24/07/2015.
 */

public class CalendarFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, GestureListener.Callback, View.OnTouchListener {

    private String dayOfWeek[] = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
    private TextView nowTextView, monthyearTextView;
    private GridView gridView, calendarHeader;
    private ArrayList<CalendarObject> arrayList, arrayList2;
    private CalendarAdapter calendarCustomItemArrayAdapter;
    private ArrayAdapter adapter;
    private ImageButton arrowLeft, arrowRight;
    private Calendar calendar;
    private int month, year;
    private GestureDetector gestureDetector;
    private GestureListener gestureListener;
    private Animation animation;
    private MainListener.Callback callback;

    public CalendarFragment() {
        calendar = new GregorianCalendar();
        gestureListener = new GestureListener();
        gestureListener.setCallback(this);
        arrayList = new ArrayList(Arrays.asList(dayOfWeek));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        gestureDetector = new GestureDetector(getActivity(), gestureListener);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        arrayList2 = CalendarUtils.setCalendarToArrayList(calendar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
        bundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_calendar, parent, false);
        nowTextView = (TextView) view.findViewById(R.id.nowTextView);
        nowTextView.setText(bundle.getString("dateNow"));
        monthyearTextView = (TextView) view.findViewById(R.id.monthyearTextView);
        calendarHeader = (GridView) view.findViewById(R.id.calendarHeaderGridView);
        gridView = (GridView) view.findViewById(R.id.calendarGridView);
        arrowLeft = (ImageButton) view.findViewById(R.id.arrow_left);
        arrowRight = (ImageButton) view.findViewById(R.id.arrow_right);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        calendarHeader.setAdapter(adapter);
        monthyearTextView.setText((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
        calendarCustomItemArrayAdapter = new CalendarAdapter(getActivity(), R.layout.list_item_calendar, arrayList2);
        gridView.setAdapter(calendarCustomItemArrayAdapter);
        gridView.setOnItemClickListener(this);
        arrowLeft.setOnClickListener(this);
        arrowRight.setOnClickListener(this);
        gridView.setLongClickable(true);
        gridView.setOnTouchListener(this);
        view.setLongClickable(true);
        view.setOnTouchListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_left:
                previous();
                break;
            case R.id.arrow_right:
                next();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        final CalendarObject obj = arrayList2.get(position);
        if (obj.getShow() == 2) {
            next();
        } else if (obj.getShow() == 0) {
            previous();
        } else {
            callback.calendarItemClicked(obj);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return false;
    }

    @Override
    public void next() {
        month++;
        if (month > 11) {
            year++;
            month = 0;
        }
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        monthyearTextView.setText((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
        arrayList2.clear();
        arrayList2.addAll(CalendarUtils.setCalendarToArrayList(calendar));
        calendarCustomItemArrayAdapter.notifyDataSetChanged();
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce_right);
        gridView.startAnimation(animation);
    }

    @Override
    public void previous() {
        month--;
        if (month < 0) {
            year--;
            month = 11;
        }
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        monthyearTextView.setText((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
        arrayList2.clear();
        arrayList2.addAll(CalendarUtils.setCalendarToArrayList(calendar));
        calendarCustomItemArrayAdapter.notifyDataSetChanged();
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce_left);
        gridView.startAnimation(animation);
    }

    @Override
    public void up() {
    }

    @Override
    public void down() {
    }

    public void setCallback(MainListener.Callback callback) {
        this.callback = callback;
    }

}
