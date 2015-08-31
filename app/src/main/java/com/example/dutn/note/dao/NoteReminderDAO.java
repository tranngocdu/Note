package com.example.dutn.note.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.dutn.note.dto.NoteReminder;
import com.example.dutn.note.dto.NoteVoice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutn on 29/07/2015.
 */
public class NoteReminderDAO {

    private ConnectDB connectDB;
    private SQLiteDatabase db;

    public NoteReminderDAO(ConnectDB connectDB) {
        this.connectDB = connectDB;
    }

    public boolean insert(NoteReminder noteReminder) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "insert into " + StringDB.TB_NOTE_REMINDER + "("
                    + StringDB.TIME_COMPLETE + ","
                    + StringDB.CONTENT + ","
                    + StringDB.STATUS + ","
                    + StringDB.NOTE_ID
                    + ") values(\'"
                    + noteReminder.getTimeComplete() + "\',\'"
                    + noteReminder.getContent() + "\',\'"
                    + noteReminder.getStatus() + "\',\'"
                    + noteReminder.getNoteId() + "\')";
            db.execSQL(sql);
            db.close();
            connectDB.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean update(int id, NoteReminder noteReminder) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "update " + StringDB.TB_NOTE_REMINDER + " set "
                    + StringDB.TIME_COMPLETE + "=\'" + noteReminder.getTimeComplete() + "\',"
                    + StringDB.STATUS + "=\'" + noteReminder.getStatus() + "\'"
                    + " where " + StringDB.NOTE_ID + "=" + id;
            db.execSQL(sql);
            db.close();
            connectDB.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "delete from " + StringDB.TB_NOTE_REMINDER + " where " + StringDB.ID + "=" + id;
            db.execSQL(sql);
            db.close();
            connectDB.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean deleteByNoteId(int id) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "delete from " + StringDB.TB_NOTE_REMINDER + " where " + StringDB.NOTE_ID + "=" + id;
            db.execSQL(sql);
            db.close();
            connectDB.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public NoteReminder get(int id) {
        NoteReminder noteReminder = new NoteReminder();
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_REMINDER + " where " + StringDB.NOTE_ID + "=" + id;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        noteReminder.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
        noteReminder.setContent(cursor.getString(cursor.getColumnIndex(StringDB.CONTENT)));
        noteReminder.setTimeComplete(cursor.getString(cursor.getColumnIndex(StringDB.TIME_COMPLETE)));
        noteReminder.setStatus(cursor.getInt(cursor.getColumnIndex(StringDB.STATUS)));
        noteReminder.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
        cursor.close();
        db.close();
        connectDB.close();
        return noteReminder;
    }

    public NoteReminder get(String date) {
        NoteReminder noteReminder = null;
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_REMINDER + " where strftime('%Y-%m-%d'," + StringDB.TIME_COMPLETE + ") = \'" + date + "\' order by strftime('%H:%M'," + StringDB.TIME_COMPLETE + ") asc";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            cursor.moveToFirst();
            noteReminder = new NoteReminder();
            noteReminder.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
            noteReminder.setContent(cursor.getString(cursor.getColumnIndex(StringDB.CONTENT)));
            noteReminder.setTimeComplete(cursor.getString(cursor.getColumnIndex(StringDB.TIME_COMPLETE)));
            noteReminder.setStatus(cursor.getInt(cursor.getColumnIndex(StringDB.STATUS)));
            noteReminder.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            cursor.close();
            db.close();
            connectDB.close();
        }
        return noteReminder;
    }

    public List<NoteReminder> getAll() {
        List<NoteReminder> list = new ArrayList<NoteReminder>();
        NoteReminder noteReminder;
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_REMINDER;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            noteReminder = new NoteReminder();
            noteReminder.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
            noteReminder.setContent(cursor.getString(cursor.getColumnIndex(StringDB.CONTENT)));
            noteReminder.setTimeComplete(cursor.getString(cursor.getColumnIndex(StringDB.TIME_COMPLETE)));
            noteReminder.setStatus(cursor.getInt(cursor.getColumnIndex(StringDB.STATUS)));
            noteReminder.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            list.add(noteReminder);
        }
        cursor.close();
        db.close();
        connectDB.close();
        return list;
    }

    public List<NoteReminder> getAll(int id) {
        List<NoteReminder> list = new ArrayList<NoteReminder>();
        NoteReminder noteReminder;
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_REMINDER + " where " + StringDB.NOTE_ID + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            noteReminder = new NoteReminder();
            noteReminder.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
            noteReminder.setContent(cursor.getString(cursor.getColumnIndex(StringDB.CONTENT)));
            noteReminder.setTimeComplete(cursor.getString(cursor.getColumnIndex(StringDB.TIME_COMPLETE)));
            noteReminder.setStatus(cursor.getInt(cursor.getColumnIndex(StringDB.STATUS)));
            noteReminder.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            list.add(noteReminder);
        }
        cursor.close();
        db.close();
        connectDB.close();
        return list;
    }

    public List<NoteReminder> getAll(String date) {
        List<NoteReminder> list = new ArrayList<NoteReminder>();
        NoteReminder noteReminder;
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_REMINDER + " where strftime('%Y-%m-%d'," + StringDB.TIME_COMPLETE + ") = \'" + date + "\' order by strftime('%H:%M'," + StringDB.TIME_COMPLETE + ") asc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            noteReminder = new NoteReminder();
            noteReminder.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
            noteReminder.setTimeComplete(cursor.getString(cursor.getColumnIndex(StringDB.TIME_COMPLETE)));
            noteReminder.setContent(cursor.getString(cursor.getColumnIndex(StringDB.CONTENT)));
            noteReminder.setStatus(cursor.getInt(cursor.getColumnIndex(StringDB.STATUS)));
            noteReminder.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            list.add(noteReminder);
        }
        cursor.close();
        db.close();
        connectDB.close();
        return list;
    }

    public int getCountNoteReminder() {
        int tongso;
        db = connectDB.getReadableDatabase();
        String sql = "select max(id) as tongso from " + StringDB.TB_NOTE_REMINDER;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        tongso = cursor.getInt(cursor.getColumnIndex("tongso"));
        cursor.close();
        db.close();
        return tongso;
    }
}
