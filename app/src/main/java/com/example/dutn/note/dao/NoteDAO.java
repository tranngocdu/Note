package com.example.dutn.note.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.dutn.note.dto.Note;
import com.example.dutn.note.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutn on 28/07/2015.
 */
public class NoteDAO {

    private ConnectDB connectDB;
    private SQLiteDatabase db;
    private NoteContentDAO noteContentDAO;
    private NoteTextDAO noteTextDAO;
    private NoteImageDAO noteImageDAO;
    private NoteVoiceDAO noteVoiceDAO;
    private NoteVideoClipDAO noteVideoClipDAO;
    private NoteReminderDAO noteReminderDAO;

    public NoteDAO(ConnectDB connectDB) {
        this.connectDB = connectDB;
        noteContentDAO = new NoteContentDAO(connectDB);
        noteTextDAO = new NoteTextDAO(connectDB);
        noteImageDAO = new NoteImageDAO(connectDB);
        noteVoiceDAO = new NoteVoiceDAO(connectDB);
        noteVideoClipDAO = new NoteVideoClipDAO(connectDB);
        noteReminderDAO = new NoteReminderDAO(connectDB);
    }

    public boolean insert(Note note) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "insert into " + StringDB.TB_NOTE + "("
                    + StringDB.ID + ","
                    + StringDB.TITLE + ","
                    + StringDB.CREATED_AT + ","
                    + StringDB.MODIFIED_AT
                    + ") values("
                    + note.getNoteId() + ",\'"
                    + note.getTitle() + "\',\'"
                    + note.getCreatedAt() + "\',\'"
                    + note.getModifiedAt()
                    + "\')";
            Log.e("TAG", "insert :" + sql);
            db.execSQL(sql);
            db.close();
            connectDB.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean update(int id, Note note) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "update " + StringDB.TB_NOTE + " set "
                    + StringDB.TITLE + "=\'"
                    + note.getTitle() + "\',"
                    + StringDB.MODIFIED_AT + "=\'"
                    + note.getModifiedAt() + "\'"
                    + " where " + StringDB.ID + "=" + id;
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
            String sql = "delete from " + StringDB.TB_NOTE + " where " + StringDB.ID + "=" + id;
            db.execSQL(sql);
            deleteRawData(id);
            noteContentDAO.delete(id);
            noteTextDAO.delete(id);
            noteImageDAO.delete(id);
            noteVoiceDAO.delete(id);
            noteVideoClipDAO.delete(id);
            noteReminderDAO.delete(id);
            db.close();
            connectDB.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public void deleteRawData(int id) {
        FileUtils.removeImage((ArrayList) noteImageDAO.getAll(id));
        FileUtils.removeVoice((ArrayList) noteVoiceDAO.getAll(id));
        FileUtils.removeVideoClip((ArrayList) noteVideoClipDAO.getAll(id));
    }

    public Note get(int id) {
        Note note = new Note();
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE + " where " + StringDB.ID + "=" + id;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        note.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
        note.setTitle(cursor.getString(cursor.getColumnIndex(StringDB.TITLE)));
        note.setCreatedAt(cursor.getString(cursor.getColumnIndex(StringDB.CREATED_AT)));
        note.setModifiedAt(cursor.getString(cursor.getColumnIndex(StringDB.MODIFIED_AT)));
        cursor.close();
        db.close();
        connectDB.close();
        return note;
    }

    public List<Note> getAll() {
        db = connectDB.getReadableDatabase();
        List<Note> list = new ArrayList<Note>();
        String sql = "select * from " + StringDB.TB_NOTE + " order by datetime(" + StringDB.MODIFIED_AT + ") desc";
        Note note;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(StringDB.TITLE)));
            note.setCreatedAt(cursor.getString(cursor.getColumnIndex(StringDB.CREATED_AT)));
            note.setModifiedAt(cursor.getString(cursor.getColumnIndex(StringDB.MODIFIED_AT)));
            list.add(note);
        }
        cursor.close();
        db.close();
        connectDB.close();
        return list;
    }

    public int getCountNote() {
        int tongso;
        db = connectDB.getReadableDatabase();
        String sql = "select max(id) as tongso from " + StringDB.TB_NOTE;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        tongso = cursor.getInt(cursor.getColumnIndex("tongso"));
        cursor.close();
        db.close();
        return tongso;
    }

}
