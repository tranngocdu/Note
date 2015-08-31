package com.example.dutn.note.dto;

import com.example.dutn.note.dao.StringDB;

import java.io.Serializable;

/**
 * Created by dutn on 24/07/2015.
 */
public class NoteVideoClip extends Note implements Serializable {

    private int id;
    private String fileName;
    private String url;
    private int duration;
    private String fileType;
    private int noteId;
    private String urlThumbnail;

    public NoteVideoClip() {
    }

    public NoteVideoClip(int id, String fileName, String url, int duration, String fileType, int noteId, String urlThumbnail) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.duration = duration;
        this.fileType = fileType;
        this.noteId = noteId;
        this.urlThumbnail = urlThumbnail;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    @Override
    public String toString() {
        return "NoteVideoClip{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", duration=" + duration +
                ", fileType='" + fileType + '\'' +
                ", noteId=" + noteId +
                '}';
    }
}
