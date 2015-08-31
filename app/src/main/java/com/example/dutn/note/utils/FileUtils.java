package com.example.dutn.note.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.dutn.note.dto.NoteImage;
import com.example.dutn.note.dto.NoteVideoClip;
import com.example.dutn.note.dto.NoteVoice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by trandu on 13/08/2015.
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();
    public static String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String ROOT_FOLDER = "/HNote";
    public static final String IMAGE_FOLDER = "/images";
    public static final String VOICE_FOLDER = "/voices";
    public static final String VIDEO_FOLDER = "/videoclips";
    public static final String THUMBNAIL_FOLDER = "/thumbnails";
    public static String[] folder = {
            ROOT_FOLDER,
            ROOT_FOLDER + IMAGE_FOLDER,
            ROOT_FOLDER + VOICE_FOLDER,
            ROOT_FOLDER + VIDEO_FOLDER,
            ROOT_FOLDER + IMAGE_FOLDER + THUMBNAIL_FOLDER,
            ROOT_FOLDER + VIDEO_FOLDER + THUMBNAIL_FOLDER
    };
    private String imageFileName;

    public FileUtils() {

    }

    public static void createRootAppFolder() {
        File file;
        for (int i = 0; i < folder.length; i++) {
            file = new File(sdcard + folder[i]);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    public void writeBitmapToSdcard(int index, Bitmap bmp) {
        File file;
        if (index == 1) {
            file = new File(sdcard + ROOT_FOLDER + IMAGE_FOLDER + "/" + imageFileName);
        } else {
            file = new File(sdcard + ROOT_FOLDER + IMAGE_FOLDER + THUMBNAIL_FOLDER + "/" + imageFileName);
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(baos.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Uri createUriVideo(String path) {
        Uri uri;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        uri = Uri.fromFile(file);
        return uri;
    }

    public Bitmap readBitmapFromSdcard(String path) {
        Bitmap bmp = null;
        bmp = BitmapFactory.decodeFile(path);
        return bmp;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public static void removeImage(ArrayList<NoteImage> noteImageArrayList) {
        for (int i = 0; i < noteImageArrayList.size(); i++) {
            try {
                NoteImage noteImage = noteImageArrayList.get(i);
                File file = new File(noteImage.getUrl());
                if (file.exists()) {
                    file.delete();
                }
                file = new File(noteImage.getUrlThumbnail());
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception ex) {
                Log.e(TAG, "removeImage : " + ex.toString());
            }
        }

    }

    public static void removeVideoClip(ArrayList<NoteVideoClip> noteVideoClipArrayList) {
        for (int i = 0; i < noteVideoClipArrayList.size(); i++) {
            File file = new File(noteVideoClipArrayList.get(i).getUrl());
            file.delete();
        }
    }

    public static void removeVoice(ArrayList<NoteVoice> noteVoiceArrayList) {
        for (int i = 0; i < noteVoiceArrayList.size(); i++) {
            File file = new File(noteVoiceArrayList.get(i).getUrl());
            file.delete();
        }
    }

}
