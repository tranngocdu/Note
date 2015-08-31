package com.example.dutn.note.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.dutn.note.dto.NoteVideoClip;
import com.example.dutn.note.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutn on 29/07/2015.
 */
public class NoteVideoClipDAO {

    private ConnectDB connectDB;
    private SQLiteDatabase db;


    public NoteVideoClipDAO(ConnectDB connectDB) {
        this.connectDB = connectDB;
    }

    public boolean insert(NoteVideoClip noteVideoClip) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "insert into " + StringDB.TB_NOTE_VIDEO_CLIP + "("
                    + StringDB.ID + ","
                    + StringDB.FILE_NAME + ","
                    + StringDB.FILE_TYPE + ","
                    + StringDB.URL + ","
                    + StringDB.DURATION + ","
                    + StringDB.NOTE_ID + ","
                    + StringDB.THUMBNAIL
                    + ") values("
                    + noteVideoClip.getId() + ",\'"
                    + noteVideoClip.getFileName() + "\',\'"
                    + noteVideoClip.getFileType() + "\',\'"
                    + noteVideoClip.getUrl() + "\',\'"
                    + noteVideoClip.getDuration() + "\',\'"
                    + noteVideoClip.getNoteId() + "\',\'"
                    + noteVideoClip.getUrlThumbnail() + "\')";
            db.execSQL(sql);
            db.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean update(int id, NoteVideoClip noteVideoClip) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "update " + StringDB.TB_NOTE_VIDEO_CLIP + " set "
                    + StringDB.FILE_NAME + "=\'" + noteVideoClip.getFileName() + "\',"
                    + StringDB.FILE_TYPE + "=\'" + noteVideoClip.getFileType() + "\',"
                    + StringDB.URL + "=\'" + noteVideoClip.getUrl() + "\',"
                    + StringDB.DURATION + "=\'" + noteVideoClip.getDuration() + "\'"
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
            String sql = "delete from " + StringDB.TB_NOTE_VIDEO_CLIP + " where " + StringDB.NOTE_ID + "=" + noteId;
            db.execSQL(sql);
            db.close();
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public NoteVideoClip get(int id) {
        NoteVideoClip noteVideoClip = new NoteVideoClip();
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_VIDEO_CLIP + " where " + StringDB.ID + "=" + id;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        noteVideoClip.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
        noteVideoClip.setFileName(cursor.getString(cursor.getColumnIndex(StringDB.FILE_NAME)));
        noteVideoClip.setFileType(cursor.getString(cursor.getColumnIndex(StringDB.FILE_TYPE)));
        noteVideoClip.setUrl(cursor.getString(cursor.getColumnIndex(StringDB.URL)));
        noteVideoClip.setDuration(cursor.getInt(cursor.getColumnIndex(StringDB.DURATION)));
        noteVideoClip.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
        noteVideoClip.setUrlThumbnail(cursor.getString(cursor.getColumnIndex(StringDB.THUMBNAIL)));
        cursor.close();
        db.close();
        return noteVideoClip;
    }

    public NoteVideoClip getLastElement(int noteId) {
        NoteVideoClip noteVideoClip = null;
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_VIDEO_CLIP + " where " + StringDB.NOTE_ID + "=" + noteId;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            cursor.moveToLast();
            noteVideoClip = new NoteVideoClip();
            noteVideoClip.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
            noteVideoClip.setFileName(cursor.getString(cursor.getColumnIndex(StringDB.FILE_NAME)));
            noteVideoClip.setFileType(cursor.getString(cursor.getColumnIndex(StringDB.FILE_TYPE)));
            noteVideoClip.setUrl(cursor.getString(cursor.getColumnIndex(StringDB.URL)));
            noteVideoClip.setDuration(cursor.getInt(cursor.getColumnIndex(StringDB.DURATION)));
            noteVideoClip.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            noteVideoClip.setUrlThumbnail(cursor.getString(cursor.getColumnIndex(StringDB.THUMBNAIL)));
        }
        cursor.close();
        db.close();
        return noteVideoClip;
    }

    public List<NoteVideoClip> getAll(int id) {
        List<NoteVideoClip> list = new ArrayList<NoteVideoClip>();
        NoteVideoClip noteVideoClip = new NoteVideoClip();
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_VIDEO_CLIP + " where " + StringDB.NOTE_ID + "=" + id;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            noteVideoClip.setFileName(cursor.getString(cursor.getColumnIndex(StringDB.FILE_NAME)));
            noteVideoClip.setFileType(cursor.getString(cursor.getColumnIndex(StringDB.FILE_TYPE)));
            noteVideoClip.setUrl(cursor.getString(cursor.getColumnIndex(StringDB.URL)));
            noteVideoClip.setDuration(cursor.getInt(cursor.getColumnIndex(StringDB.DURATION)));
            noteVideoClip.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            list.add(noteVideoClip);
        }
        return list;
    }

    public int getCountNoteVideoClip() {
        int tongso;
        db = connectDB.getReadableDatabase();
        String sql = "select max(id) as tongso from " + StringDB.TB_NOTE_VIDEO_CLIP;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        tongso = cursor.getInt(cursor.getColumnIndex("tongso"));
        cursor.close();
        db.close();
        return tongso;
    }

}
