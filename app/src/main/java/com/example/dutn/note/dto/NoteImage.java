package com.example.dutn.note.dto;

import java.io.Serializable;

/**
 * Created by dutn on 24/07/2015.
 */
public class NoteImage extends Note implements Serializable {

    private int id;
    private String fileName;
    private String url;
    private String fileType;
    private int noteId;
    private String urlThumbnail;

    public NoteImage() {
    }

    public NoteImage(int id, String fileName, String url, String fileType, int noteId, String urlThumbnail) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
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
        return "NoteImage{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", fileType='" + fileType + '\'' +
                ", noteId=" + noteId +
                ", urlThumbnail='" + urlThumbnail + '\'' +
                '}';
    }
}
