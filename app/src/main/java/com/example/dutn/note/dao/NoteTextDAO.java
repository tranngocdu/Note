package com.example.dutn.note.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.dutn.note.dto.NoteText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trandu on 12/08/2015.
 */
public class NoteTextDAO {
    private ConnectDB connectDB;
    private SQLiteDatabase db;

    public NoteTextDAO(ConnectDB connectDB) {
        this.connectDB = connectDB;
    }

    public boolean insert(NoteText noteText) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "insert into " + StringDB.TB_NOTE_TEXT + "("
                    + StringDB.ID + ","
                    + StringDB.CONTENT + ","
                    + StringDB.NOTE_ID
                    + ") values("
                    + noteText.getId() + ",\'"
                    + noteText.getContent() + "\',\'"
                    + noteText.getNoteId() + "\')";
            Log.e("TAG", "insert :" + sql);
            db.execSQL(sql);
            db.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean update(int id, NoteText noteText) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "update " + StringDB.TB_NOTE_TEXT + " set " + StringDB.CONTENT + "=\'" + noteText.getContent() + "\'"
                    + " where " + StringDB.NOTE_ID + "=" + id;
            db.execSQL(sql);
            db.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean delete(int note_id) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "delete from " + StringDB.TB_NOTE_TEXT + " where " + StringDB.NOTE_ID + "=" + note_id;
            db.execSQL(sql);
            db.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public NoteText get(int id) {
        NoteText noteText = new NoteText();
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_TEXT + " where " + StringDB.ID + "=" + id;
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToNext();
            noteText.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
            noteText.setContent(cursor.getString(cursor.getColumnIndex(StringDB.CONTENT)));
            noteText.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cursor.close();
        db.close();
        return noteText;
    }

    public List<NoteText> getAll(int noteId) {
        List<NoteText> list = new ArrayList<NoteText>();
        NoteText noteText;
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_TEXT + " where " + StringDB.NOTE_ID + "=" + noteId;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            noteText = new NoteText();
            noteText.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
            noteText.setContent(cursor.getString(cursor.getColumnIndex(StringDB.CONTENT)));
            noteText.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            list.add(noteText);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int getCountNoteText() {
        int tongso;
        db = connectDB.getReadableDatabase();
        String sql = "select max(id) as tongso from " + StringDB.TB_NOTE_TEXT;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        tongso = cursor.getInt(cursor.getColumnIndex("tongso"));
        cursor.close();
        db.close();
        return tongso;
    }
}
