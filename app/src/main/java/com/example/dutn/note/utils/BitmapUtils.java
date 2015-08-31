package com.example.dutn.note.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by trandu on 13/08/2015.
 */
public class BitmapUtils {

    private ContentResolver contentResolver;

    public BitmapUtils(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public static Bitmap getBitmapThumbnail(String path, int width, int height) {
        Bitmap bmp = null;
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            return null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = height > width ? originalSize / height : originalSize / width;
        bmp = BitmapFactory.decodeFile(path, opts);
        return bmp;
    }

    public static Bitmap getBitmapThumbnail(Bitmap bmp, int width, int height) {
        return Bitmap.createScaledBitmap(bmp, width, height, true);
    }

    public Bitmap rotateBitmap(Bitmap bmp, Uri uri) {
        if (getRotation(uri) != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(getRotation(uri));
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
        }
        return bmp;
    }

    public static Bitmap readBitmapFromSdcard(String path) {
        Bitmap bmp = null;
        bmp = BitmapFactory.decodeFile(path);
        return bmp;
    }

    public String getPath(Uri uri) {
        String pathColumn[] = {MediaStore.Images.Media.DATA};
        String path;
        Cursor cursor = contentResolver.query(uri, pathColumn, null, null, null);
        cursor.moveToNext();
        path = cursor.getString(cursor.getColumnIndex(pathColumn[0]));
        cursor.close();
        return path;
    }

    public int getRotation(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.ORIENTATION};
        Cursor cursor = contentResolver.query(uri, filePathColumn, null, null, null);
        cursor.moveToNext();
        int rotation = cursor.getInt(0);
        cursor.close();
        return rotation;
    }

}
