package com.example.dutn.note.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.dutn.note.R;
import com.example.dutn.note.utils.CalendarUtils;

/**
 * Created by trandu on 12/08/2015.
 */
public class TimePickerDialog extends Dialog implements View.OnClickListener {

    private TimePicker timePicker;
    private Button btn_cancel;
    private Button btn_ok;
    private Callback callback;

    public TimePickerDialog(Activity context) {
        super(context);
    }

    public TimePickerDialog(Context context, int theme) {
        super(context, theme);
    }

    public TimePickerDialog(Context context, boolean cancelable, OnCancelListener listener) {
        super(context, cancelable, listener);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_timepicker);
        this.setTitle("Thêm nhắc nhở");
        init();
    }

    public void init() {
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                this.dismiss();
                break;
            case R.id.btn_ok:
                callback.setTimeForTextView(CalendarUtils.addZero(timePicker.getCurrentHour()) + ":" + CalendarUtils.addZero(timePicker.getCurrentMinute()));
                this.dismiss();
                break;
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void setTimeForTextView(String time);
    }

}
