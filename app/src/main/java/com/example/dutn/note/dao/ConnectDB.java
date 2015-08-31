package com.example.dutn.note.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dutn on 24/07/2015.
 */
public class ConnectDB extends SQLiteOpenHelper {

    private static final String TAG = "ConnectDB";
    private Context context;
    private static final String DB_NAME = "NoteDB";
    private static final int DB_VERSION = 1;

    private String[] tableArray = {
            StringDB.TB_NOTE,
            StringDB.TB_NOTE_CONTENT,
            StringDB.TB_NOTE_TEXT,
            StringDB.TB_NOTE_IMAGE,
            StringDB.TB_NOTE_VOICE,
            StringDB.TB_NOTE_VIDEO_CLIP,
            StringDB.TB_NOTE_REMINDER
    };

    private String create_note_sql = "create table " + StringDB.TB_NOTE + "("
            + StringDB.ID + " Integer , "
            + StringDB.TITLE + " text,"
            + StringDB.CREATED_AT + " datetime,"
            + StringDB.MODIFIED_AT + " datetime,"
            + StringDB.SYNC_STT + " Integer)";
    private String create_notecontent_sql = "create table " + StringDB.TB_NOTE_CONTENT + "("
            + StringDB.NOTE_ID + " Integer,"
            + StringDB.TYPE + " text,"
            + StringDB.NOTE_TEXT_ID + " Integer,"
            + StringDB.NOTE_IMAGE_ID + " Integer,"
            + StringDB.NOTE_VOICE_ID + " Integer,"
            + StringDB.NOTE_VIDEOCLIP_ID + " Integer,"
            + StringDB.NOTE_REMINDER_ID + " Integer,"
            + StringDB.STT + " Integer,"
            + StringDB.SYNC_STT + " Integer)";
    private String create_notetext_sql = "create table " + StringDB.TB_NOTE_TEXT + "("
            + StringDB.ID + " Integer , " +
            StringDB.CONTENT + " text,"
            + StringDB.NOTE_ID + " Integer,"
            + StringDB.SYNC_STT + " Integer)";
    private String create_noteimage_sql = "create table " + StringDB.TB_NOTE_IMAGE + "("
            + StringDB.ID + " Integer , " +
            StringDB.FILE_NAME + " text,"
            + StringDB.FILE_TYPE + " text,"
            + StringDB.URL + " text,"
            + StringDB.NOTE_ID + " Integer,"
            + StringDB.THUMBNAIL + " text,"
            + StringDB.SYNC_STT + " Integer)";
    private String create_notevoice_sql = "create table " + StringDB.TB_NOTE_VOICE + "("
            + StringDB.ID + " Integer , " +
            StringDB.FILE_NAME + " text,"
            + StringDB.FILE_TYPE + " text,"
            + StringDB.URL + " text,"
            + StringDB.NOTE_ID + " Integer,"
            + StringDB.SYNC_STT + " Integer)";
    private String create_notevideoclip_sql = "create table " + StringDB.TB_NOTE_VIDEO_CLIP + "("
            + StringDB.ID + " Integer , " +
            StringDB.FILE_NAME + " text,"
            + StringDB.FILE_TYPE + " text,"
            + StringDB.URL + " text,"
            + StringDB.DURATION + " Integer,"
            + StringDB.NOTE_ID + " Integer,"
            + StringDB.THUMBNAIL + " text,"
            + StringDB.SYNC_STT + " Integer)";
    private String create_notereminder_sql = "create table " + StringDB.TB_NOTE_REMINDER + "("
            + StringDB.ID + " Integer primary key autoincrement," +
            StringDB.TIME_COMPLETE + " datetime,"
            + StringDB.CONTENT + " text, "
            + StringDB.STATUS + " Integer,"
            + StringDB.NOTE_ID + " Integer,"
            + StringDB.SYNC_STT + " Integer)";

    private String[] sql = {
            create_note_sql,
            create_notecontent_sql,
            create_notetext_sql,
            create_noteimage_sql,
            create_notevoice_sql,
            create_notevideoclip_sql,
            create_notereminder_sql
    };

    public ConnectDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < sql.length; i++) {
            db.execSQL(sql[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = 0; i < tableArray.length; i++) {
            db.execSQL("drop table if exists " + tableArray[i]);
        }
        onCreate(db);
    }

}
