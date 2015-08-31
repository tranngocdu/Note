package com.example.dutn.note.dto;

import java.io.Serializable;

/**
 * Created by trandu on 12/08/2015.
 */
public class NoteText extends Note implements Serializable {

    private int id;
    private String content;
    private int noteId;

    public NoteText() {
    }

    public NoteText(int id, String content, int noteId) {
        this.id = id;
        this.content = content;
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

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    @Override
    public String toString() {
        return "NoteText{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", noteId=" + noteId +
                '}';
    }
}
