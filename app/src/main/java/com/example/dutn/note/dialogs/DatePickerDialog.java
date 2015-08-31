package com.example.dutn.note.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.dutn.note.R;
import com.example.dutn.note.utils.CalendarUtils;

/**
 * Created by trandu on 12/08/2015.
 */
public class DatePickerDialog extends Dialog implements View.OnClickListener {

    private DatePicker datePicker;
    private Button btn_cancel;
    private Button btn_ok;
    private Callback callback;

    public DatePickerDialog(Context context) {
        super(context);
    }

    public DatePickerDialog(Context context, int theme) {
        super(context, theme);
    }

    public DatePickerDialog(Context context, boolean cancelable, OnCancelListener listener) {
        super(context, cancelable, listener);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_datepicker);
        this.setTitle("Thêm nhắc nhở");
        init();
    }

    public void init() {
        datePicker = (DatePicker) findViewById(R.id.datePicker);
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
                callback.setDateForTextView(CalendarUtils.addZero(datePicker.getYear()) + "-" +
                        CalendarUtils.addZero(datePicker.getMonth() + 1) + "-" +
                        CalendarUtils.addZero(datePicker.getDayOfMonth()));
                this.dismiss();
                break;
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void setDateForTextView(String date);
    }

}
