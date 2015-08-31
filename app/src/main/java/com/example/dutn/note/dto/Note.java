package com.example.dutn.note.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dutn on 23/07/2015.
 */
public class Note implements Serializable {

    private int noteId;
    private String title;
    private String createdAt;
    private String modifiedAt;
    private String type;

    public Note() {
    }

    public Note(String title, String createdAt, String modifiedAt) {
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Note(int noteId, String title, String createdAt, String modifiedAt) {
        this.noteId = noteId;
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", title='" + title + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", modifiedAt='" + modifiedAt + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
