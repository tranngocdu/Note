package com.example.dutn.note.utils;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by dutn on 04/08/2015.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    public static final String TAG = GestureListener.class.getSimpleName();
    private static final int vt = 50;
    private Callback callback;


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float x1 = e1.getX();
        float x2 = e2.getX();
        float y1 = e1.getY();
        float y2 = e2.getY();
        if (x1 < x2 - vt) {
            callback.previous();
        } else if (x1 > x2 + vt) {
            callback.next();
        }
        if (y2 < y1 + vt) {
            callback.down();
        } else if (y2 > y1 + vt) {
            callback.up();
        }
        return false;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {

        void previous();

        void next();

        void up();

        void down();

    }

}
