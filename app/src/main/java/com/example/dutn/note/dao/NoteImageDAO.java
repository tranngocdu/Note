package com.example.dutn.note.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.dutn.note.dto.Note;
import com.example.dutn.note.dto.NoteImage;
import com.example.dutn.note.utils.FileUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutn on 29/07/2015.
 */
public class NoteImageDAO {

    private static final String TAG = NoteImageDAO.class.getSimpleName();

    private ConnectDB connectDB;
    private SQLiteDatabase db;

    public NoteImageDAO(ConnectDB connectDB) {
        this.connectDB = connectDB;
    }

    public boolean insert(NoteImage noteImage) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "insert into " + StringDB.TB_NOTE_IMAGE + "("
                    + StringDB.ID + ","
                    + StringDB.FILE_NAME + ","
                    + StringDB.FILE_TYPE + ","
                    + StringDB.URL + ","
                    + StringDB.NOTE_ID + ","
                    + StringDB.THUMBNAIL
                    + ") values("
                    + noteImage.getId() + ",\'"
                    + noteImage.getFileName() + "\',\'"
                    + noteImage.getFileType() + "\',\'"
                    + noteImage.getUrl() + "\',\'"
                    + noteImage.getNoteId() + "\',\'"
                    + noteImage.getUrlThumbnail() + "\')";
            Log.e(TAG, "insert :" + sql);
            db.execSQL(sql);
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public boolean update(int id, NoteImage noteImage) {
        try {
            db = connectDB.getWritableDatabase();
            String sql = "update " + StringDB.TB_NOTE_IMAGE + " set "
                    + StringDB.FILE_NAME + "=\'" + noteImage.getFileName() + "\',"
                    + StringDB.FILE_TYPE + "=\'" + noteImage.getFileType() + "\',"
                    + StringDB.URL + "=\'" + noteImage.getUrl() + "\'"
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
            String sql = "delete from " + StringDB.TB_NOTE_IMAGE + " where " + StringDB.NOTE_ID + "=" + noteId;
            db.execSQL(sql);
            return true;
        } catch (SQLiteException sqlException) {
            return false;
        }
    }

    public NoteImage get(int id) {
        NoteImage noteImage = new NoteImage();
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_IMAGE + " where " + StringDB.ID + "=" + id;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        noteImage.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
        noteImage.setFileName(cursor.getString(cursor.getColumnIndex(StringDB.FILE_NAME)));
        noteImage.setFileType(cursor.getString(cursor.getColumnIndex(StringDB.FILE_TYPE)));
        noteImage.setUrl(cursor.getString(cursor.getColumnIndex(StringDB.URL)));
        noteImage.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
        noteImage.setUrlThumbnail(cursor.getString(cursor.getColumnIndex(StringDB.THUMBNAIL)));
        cursor.close();
        db.close();
        return noteImage;
    }

    public NoteImage getLastElement(int noteId) {
        NoteImage noteImage = null;
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_IMAGE + " where " + StringDB.NOTE_ID + "=" + noteId;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            cursor.moveToLast();
            noteImage = new NoteImage();
            noteImage.setId(cursor.getInt(cursor.getColumnIndex(StringDB.ID)));
            noteImage.setFileName(cursor.getString(cursor.getColumnIndex(StringDB.FILE_NAME)));
            noteImage.setFileType(cursor.getString(cursor.getColumnIndex(StringDB.FILE_TYPE)));
            noteImage.setUrl(cursor.getString(cursor.getColumnIndex(StringDB.URL)));
            noteImage.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            noteImage.setUrlThumbnail(cursor.getString(cursor.getColumnIndex(StringDB.THUMBNAIL)));
        }
        cursor.close();
        db.close();
        return noteImage;
    }

    public List<NoteImage> getAll() {
        List<NoteImage> list = new ArrayList<NoteImage>();
        NoteImage noteImage = new NoteImage();
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_IMAGE;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            noteImage.setFileName(cursor.getString(cursor.getColumnIndex(StringDB.FILE_NAME)));
            noteImage.setFileType(cursor.getString(cursor.getColumnIndex(StringDB.FILE_TYPE)));
            noteImage.setUrl(cursor.getString(cursor.getColumnIndex(StringDB.URL)));
            noteImage.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            noteImage.setUrlThumbnail(cursor.getString(cursor.getColumnIndex(StringDB.THUMBNAIL)));
            list.add(noteImage);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<NoteImage> getAll(int id) {
        List<NoteImage> list = new ArrayList<NoteImage>();
        NoteImage noteImage;
        db = connectDB.getReadableDatabase();
        String sql = "select * from " + StringDB.TB_NOTE_IMAGE + " where " + StringDB.NOTE_ID + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            noteImage = new NoteImage();
            noteImage.setFileName(cursor.getString(cursor.getColumnIndex(StringDB.FILE_NAME)));
            noteImage.setFileType(cursor.getString(cursor.getColumnIndex(StringDB.FILE_TYPE)));
            noteImage.setUrl(cursor.getString(cursor.getColumnIndex(StringDB.URL)));
            noteImage.setNoteId(cursor.getInt(cursor.getColumnIndex(StringDB.NOTE_ID)));
            noteImage.setUrlThumbnail(cursor.getString(cursor.getColumnIndex(StringDB.THUMBNAIL)));
            list.add(noteImage);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int getCountImage() {
        int tongso;
        db = connectDB.getReadableDatabase();
        String sql = "select max(id) as tongso from " + StringDB.TB_NOTE_IMAGE;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        tongso = cursor.getInt(cursor.getColumnIndex("tongso"));
        cursor.close();
        db.close();
        return tongso;
    }

}
