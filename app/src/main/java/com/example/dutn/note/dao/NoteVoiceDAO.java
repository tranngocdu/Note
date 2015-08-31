package com.example.dutn.note.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.dutn.note.dto.NoteImage;
import com.example.dutn.note.dto.NoteVoice;
import com.example.dutn.note.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutn on 29/07/2015.
 */
public class NoteVoiceDAO {

    private ConnectDB connectDB;
    private SQLiteDatabase db;

    public NoteVoiceDAO(ConnectDB connectDB) {
        this.connectDB = connectDB;
    }

    public boolean insert(NoteVoice noteVoice) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "insert into " + StringDB.TB_NOTE_VOICE + "("
                    + StringDB.ID + ","
                    + StringDB.FILE_NAME + ","
                    + StringDB.FILE_TYPE + ","
                    + StringDB.URL + ","
                    + StringDB.NOTE_ID
                    + ") values("
                    + noteVoice.getId() + ",\'"
                    + noteVoice.getFileName() + "\',\'"
                    + noteVoice.getFileType() + "\',\'"
                    + noteVoice.getUrl() + "\',\'"
                    + noteVoice.getNoteId() + "\')";
            db.execSQL(sql);
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean update(int id, NoteVoice noteVoice) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "update " + StringDB.TB_NOTE_VOICE + " set "
                    + StringDB.FILE_NAME + "=\'" + noteVoice.getFileName() + "\',"
                    + StringDB.FILE_TYPE + "=\'" + noteVoice.getFileType() + "\',"
                    + StringDB.URL + "=\'" + noteVoice.getUrl() + "\'"
                    + " where " + StringDB.NOTE_ID + "=" + id;
            db.execSQL(sql);
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean delete(int noteId) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "delete from " + StringDB.TB_NOTE_VOICE + " where " + StringDB.NOTE_ID + "=" + noteId;
            db.execSQL(sql);
            db.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public NoteVoice get(int id) {
        NoteVoice noteVoice = new NoteVoice();
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_VOICE + " where " + StringDB.NOTE_ID + "=" + id;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        noteVoice.setFileName(cursor.getString(cursor.getColumnIndex(StringDB.FILE_NAME)));
        noteVoice.setFileType(cursor.getString(cursor.getColumnIndex(StringDB.FILE_TYPE)));
        noteVoice.setUrl(cursor.getString(cursor.getColumnIndex(StringDB.URL)));
        noteVoice.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
        return noteVoice;
    }

    public List<NoteVoice> getAll() {
        List<NoteVoice> list = new ArrayList<NoteVoice>();
        NoteVoice noteVoice = new NoteVoice();
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_IMAGE;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            noteVoice.setFileName(cursor.getString(cursor.getColumnIndex(StringDB.FILE_NAME)));
            noteVoice.setFileType(cursor.getString(cursor.getColumnIndex(StringDB.FILE_TYPE)));
            noteVoice.setUrl(cursor.getString(cursor.getColumnIndex(StringDB.URL)));
            noteVoice.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            list.add(noteVoice);
        }
        return list;
    }

    public List<NoteVoice> getAll(int id) {
        List<NoteVoice> list = new ArrayList<NoteVoice>();
        NoteVoice noteVoice = new NoteVoice();
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_IMAGE + " where " + StringDB.NOTE_ID + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            noteVoice.setFileName(cursor.getString(cursor.getColumnIndex(StringDB.FILE_NAME)));
            noteVoice.setFileType(cursor.getString(cursor.getColumnIndex(StringDB.FILE_TYPE)));
            noteVoice.setUrl(cursor.getString(cursor.getColumnIndex(StringDB.URL)));
            noteVoice.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            list.add(noteVoice);
        }
        return list;
    }

    public int getCountNoteVoice() {
        int tongso;
        db = connectDB.getReadableDatabase();
        String sql = "select max(id) as tongso from " + StringDB.TB_NOTE_VOICE;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        tongso = cursor.getInt(cursor.getColumnIndex("tongso"));
        cursor.close();
        db.close();
        return tongso;
    }
}
