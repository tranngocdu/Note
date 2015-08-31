package com.example.dutn.note.dto;

import java.io.Serializable;

/**
 * Created by dutn on 24/07/2015.
 */
public class NoteReminder extends Note implements Serializable {

    private int id;
    private String timeComplete;
    private String content;
    private int status;
    private int noteId;

    public NoteReminder() {
    }

    public NoteReminder(int id, String timeComplete, String content, int status, int noteId) {
        this.id = id;
        this.timeComplete = timeComplete;
        this.content = content;
        this.status = status;
        this.noteId = noteId;
    }

    public String getTimeComplete() {
        return timeComplete;
    }

    public void setTimeComplete(String timeComplete) {
        this.timeComplete = timeComplete;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NoteReminder{" +
                "id=" + id +
                ", timeComplete='" + timeComplete + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", noteId=" + noteId +
                '}';
    }
}
