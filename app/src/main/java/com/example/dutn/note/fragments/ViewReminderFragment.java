package com.example.dutn.note.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dutn.note.R;
import com.example.dutn.note.adapters.ViewReminderAdapter;
import com.example.dutn.note.dao.ConnectDB;
import com.example.dutn.note.dao.NoteReminderDAO;
import com.example.dutn.note.dialogs.AddReminderDialog;
import com.example.dutn.note.dto.NoteReminder;
import com.example.dutn.note.utils.CalendarUtils;

import java.util.ArrayList;

/**
 * Created by trandu on 10/08/2015.
 */

public class ViewReminderFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, AddReminderDialog.Callback, ViewReminderAdapter.Callback {

    private Bundle bundle;
    private TextView dateTextView, noticeTextView;
    private ListView listView;
    private ArrayList<NoteReminder> arrayList;
    private ViewReminderAdapter adapter;
    private ImageButton btn_add_reminder;
    private AddReminderDialog addReminderDialog;
    private NoteReminderDAO noteReminderDAO;
    private String dateClicked;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_view_reminder, parent, false);
        listView = (ListView) view.findViewById(R.id.view_reminder_listview);
        btn_add_reminder = (ImageButton) view.findViewById(R.id.btn_float);
        dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        noticeTextView = (TextView) view.findViewById(R.id.noticeTextView);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        bundle = getArguments();
        dateClicked = CalendarUtils.addZero(bundle.getInt("year")) +
                "-" + CalendarUtils.addZero(bundle.getInt("month")) +
                "-" + CalendarUtils.addZero(bundle.getInt("day"));
        noteReminderDAO = new NoteReminderDAO(new ConnectDB(getActivity()));
        dateTextView.setText(dateClicked);
        arrayList = (ArrayList) noteReminderDAO.getAll(dateClicked);
        adapter = new ViewReminderAdapter(getActivity(), R.layout.list_item_reminder, arrayList);
        adapter.setCallback(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
        btn_add_reminder.setOnClickListener(this);
        check();
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_float:
                showDialogAddReminder();
                break;
        }
    }

    public void check() {
        if (arrayList.size() != 0) {
            noticeTextView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.GONE);
            noticeTextView.setVisibility(View.VISIBLE);
        }
    }

    public void showDialogAddReminder() {
        addReminderDialog = new AddReminderDialog(getActivity());
        addReminderDialog.setCallback(this);
        addReminderDialog.show();
    }

    @Override
    public void setDateForTextView(TextView tv) {
        tv.setText(dateClicked);
    }

    @Override
    public void setTimeForTextView(TextView tv) {
        tv.setText(CalendarUtils.getTimeNow().trim());
    }

    @Override
    public void addReminder(NoteReminderDAO noteReminderDAO, NoteReminder noteReminder) {
        noteReminderDAO.insert(noteReminder);
        refreshListView();
    }

    @Override
    public void deleteReminder(NoteReminderDAO noteReminderDAO, int id) {
        noteReminderDAO.delete(id);
        refreshListView();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_new_note_fragment, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                noteReminderDAO.delete(arrayList.get(adapterContextMenuInfo.position).getId());
                arrayList.remove(adapterContextMenuInfo.position);
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void refreshListView() {
        arrayList.clear();
        arrayList.addAll(noteReminderDAO.getAll(dateClicked));
        adapter.notifyDataSetChanged();
        check();
    }


}
