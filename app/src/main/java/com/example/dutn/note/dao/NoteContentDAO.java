package com.example.dutn.note.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.dutn.note.dto.NoteContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutn on 05/08/2015.
 */
public class NoteContentDAO {

    private ConnectDB connectDB;
    private SQLiteDatabase db;

    public NoteContentDAO(ConnectDB connectDB) {
        this.connectDB = connectDB;
    }

    public boolean insert(NoteContent noteContent) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "insert into " + StringDB.TB_NOTE_CONTENT + "("
                    + StringDB.NOTE_ID + ","
                    + StringDB.TYPE + ","
                    + StringDB.NOTE_TEXT_ID + ","
                    + StringDB.NOTE_IMAGE_ID + ","
                    + StringDB.NOTE_VOICE_ID + ","
                    + StringDB.NOTE_VIDEOCLIP_ID + ","
                    + StringDB.NOTE_REMINDER_ID + ","
                    + StringDB.STT
                    + ") values(\'"
                    + noteContent.getNoteId() + "\',\'"
                    + noteContent.getType() + "\',\'"
                    + noteContent.getText_id() + "\',\'"
                    + noteContent.getImage_id() + "\',\'"
                    + noteContent.getVoice_id() + "\',\'"
                    + noteContent.getVideoclip_id() + "\',\'"
                    + noteContent.getVoice_id() + "\',\'"
                    + noteContent.getIndex() + "\')";
            Log.e("TAG", "insert :" + sql);
            db.execSQL(sql);
            db.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean update(int id, NoteContent noteContent) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "update " + StringDB.TB_NOTE_CONTENT + " set "
                    + StringDB.STT + "=\'" + noteContent.getIndex() + "\',"
                    + " where " + StringDB.NOTE_ID + "=" + id;
            db.execSQL(sql);
            db.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean delete(int noteId) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "delete from " + StringDB.TB_NOTE_CONTENT + " where " + StringDB.NOTE_ID + "=" + noteId;
            db.execSQL(sql);
            db.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean delete(int id, int stt) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "delete from " + StringDB.TB_NOTE_CONTENT + " where " + StringDB.NOTE_ID + "=" + id + " and " + StringDB.STT + " = " + stt;
            db.execSQL(sql);
            db.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public List<NoteContent> getAll(int id) {
        List<NoteContent> list = new ArrayList<NoteContent>();
        NoteContent noteContent;
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_CONTENT + " where " + StringDB.NOTE_ID + "=" + id + " order by " + StringDB.STT + " asc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            noteContent = new NoteContent();
            noteContent.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            noteContent.setType(cursor.getString(cursor.getColumnIndex(StringDB.TYPE)));
            noteContent.setText_id(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_TEXT_ID)));
            noteContent.setImage_id(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_IMAGE_ID)));
            noteContent.setVoice_id(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_VOICE_ID)));
            noteContent.setVideoclip_id(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_VIDEOCLIP_ID)));
            noteContent.setIndex(cursor.getInt(cursor.getColumnIndex(StringDB.STT)));
            list.add(noteContent);
        }
        cursor.close();
        db.close();
        return list;
    }

}
