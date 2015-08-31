package com.example.dutn.note.listeners;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.dutn.note.dto.Note;
import com.example.dutn.note.dto.NoteContent;
import com.example.dutn.note.objects.CalendarObject;

import java.util.ArrayList;

/**
 * Created by trandu on 19/08/2015.
 */
public class MainListener {
    public interface Callback {
        void createNewNote();

        void goToEditNote(Bundle bundle, ArrayList<NoteContent> noteContentArrayList, ArrayList<String> stringArrayList, ArrayList<Bitmap> bitmapArrayList);

        void goToNoteDetail(Note note);

        void goToMainFragment();

        void calendarItemClicked(CalendarObject calendarObject);
    }
}
