package com.example.dutn.note.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutn.note.R;
import com.example.dutn.note.dao.ConnectDB;
import com.example.dutn.note.dao.NoteReminderDAO;
import com.example.dutn.note.dto.NoteReminder;

/**
 * Created by trandu on 09/08/2015.
 */
public class AddReminderDialog extends Dialog implements View.OnClickListener, TimePickerDialog.Callback, DatePickerDialog.Callback {

    private DisplayMetrics displayMetrics;
    private TextView dateTextView, timeTextView;
    private Button btn_cancel, btn_ok;
    private EditText edt_content;
    private NoteReminderDAO noteReminderDAO;
    private NoteReminder noteReminder;
    public Callback callback;


    public AddReminderDialog(Context context) {
        super(context);
    }

    public AddReminderDialog(Context context, int theme) {
        super(context, theme);
    }

    protected AddReminderDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_reminder);
        setTitle("Tạo nhắc nhở");
        init();
    }

    public void init() {
        displayMetrics = getContext().getResources().getDisplayMetrics();
        this.getWindow().setLayout(displayMetrics.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        btn_cancel = (Button) findViewById(R.id.dialog_add_reminder_cancel);
        btn_ok = (Button) findViewById(R.id.dialog_add_reminder_ok);
        edt_content = (EditText) findViewById(R.id.dialog_add_reminder_content);
        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        timeTextView.setOnClickListener(this);
        dateTextView.setOnClickListener(this);
        callback.setDateForTextView(dateTextView);
        callback.setTimeForTextView(timeTextView);
        noteReminderDAO = new NoteReminderDAO(new ConnectDB(getContext()));
        noteReminder = new NoteReminder();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_add_reminder_cancel:
                this.cancel();
                break;
            case R.id.dialog_add_reminder_ok:
                noteReminder.setTimeComplete(dateTextView.getText() + " " + timeTextView.getText());
                noteReminder.setContent(edt_content.getText().toString());
                noteReminder.setStatus(1);
                callback.addReminder(noteReminderDAO, noteReminder);
                this.dismiss();
                Toast.makeText(getContext(), "Tạo nhắc nhở thành công!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dateTextView:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), 0);
                datePickerDialog.setCallback(this);
                datePickerDialog.show();
                break;
            case R.id.timeTextView:
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), 0);
                timePickerDialog.setCallback(this);
                timePickerDialog.show();
                break;
        }
    }

    @Override
    public void setTimeForTextView(String time) {
        timeTextView.setText(time);
    }

    @Override
    public void setDateForTextView(String date) {
        dateTextView.setText(date);
    }

    public interface Callback {
        void setDateForTextView(TextView tv);

        void setTimeForTextView(TextView tv);

        void addReminder(NoteReminderDAO noteReminderDAO, NoteReminder noteReminder);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

}
