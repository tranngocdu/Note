package com.example.dutn.note.dto;

import java.io.Serializable;

/**
 * Created by dutn on 05/08/2015.
 */
public class NoteContent extends Note implements Serializable {

    private int noteId;
    private String type;
    private int text_id;
    private int image_id;
    private int voice_id;
    private int videoclip_id;
    private int reminder_id;
    private int index;

    public NoteContent() {
    }

    public NoteContent(int noteId, String type, int text_id, int image_id, int voice_id, int videoclip_id, int reminder_id, int index) {
        this.noteId = noteId;
        this.type = type;
        this.text_id = text_id;
        this.image_id = image_id;
        this.voice_id = voice_id;
        this.videoclip_id = videoclip_id;
        this.reminder_id = reminder_id;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getText_id() {
        return text_id;
    }

    public void setText_id(int text_id) {
        this.text_id = text_id;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getVoice_id() {
        return voice_id;
    }

    public void setVoice_id(int voice_id) {
        this.voice_id = voice_id;
    }

    public int getVideoclip_id() {
        return videoclip_id;
    }

    public void setVideoclip_id(int videoclip_id) {
        this.videoclip_id = videoclip_id;
    }

    public int getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(int reminder_id) {
        this.reminder_id = reminder_id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "NoteContent{" +
                "noteId=" + noteId +
                ", type='" + type + '\'' +
                ", text_id=" + text_id +
                ", image_id=" + image_id +
                ", voice_id=" + voice_id +
                ", videoclip_id=" + videoclip_id +
                ", reminder_id=" + reminder_id +
                ", index=" + index +
                '}';
    }


}
