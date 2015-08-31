package com.example.dutn.note.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.dutn.note.R;
import com.example.dutn.note.dao.ConnectDB;
import com.example.dutn.note.dao.NoteContentDAO;
import com.example.dutn.note.dao.NoteDAO;
import com.example.dutn.note.dao.NoteImageDAO;
import com.example.dutn.note.dao.NoteTextDAO;
import com.example.dutn.note.dao.NoteVideoClipDAO;
import com.example.dutn.note.dao.NoteVoiceDAO;
import com.example.dutn.note.dao.StringDB;
import com.example.dutn.note.dto.Note;
import com.example.dutn.note.dto.NoteContent;
import com.example.dutn.note.dto.NoteImage;
import com.example.dutn.note.dto.NoteText;
import com.example.dutn.note.dto.NoteVideoClip;
import com.example.dutn.note.dto.NoteVoice;
import com.example.dutn.note.listeners.MainListener;
import com.example.dutn.note.utils.BitmapUtils;
import com.example.dutn.note.utils.CalendarUtils;
import com.example.dutn.note.utils.FileUtils;

import java.util.ArrayList;

/**
 * Created by dutn on 29/07/2015.
 */
public class NewNoteFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = NewNoteFragment.class.getSimpleName();
    private EditText titleEditText, contentEditText;
    private Button saveButton;
    private ImageButton btn_attach_file;
    private PopupMenu popupMenuAttachFile;
    private LinearLayout contentLayout;
    private ConnectDB connectDB;
    private Note note;
    private NoteContent noteContent;
    private NoteText noteText;
    private NoteImage noteImage;
    private NoteVoice noteVoice;
    private NoteVideoClip noteVideoClip;
    private NoteDAO noteDAO;
    private NoteContentDAO noteContentDAO;
    private NoteTextDAO noteTextDAO;
    private NoteImageDAO noteImageDAO;
    private NoteVoiceDAO noteVoiceDAO;
    private NoteVideoClipDAO noteVideoClipDAO;
    private MainListener.Callback callback;
    public static final int SELECT_PHOTO = 101;
    public static final int CAPTURE_VIDEO = 102;
    public static final int RECORD_VOICE = 103;
    private FileUtils fileUtils;
    private BitmapUtils bitmapUtils;
    private Bitmap bmp = null;
    private DisplayMetrics displayMetrics;
    private ArrayList<Note> noteArrayList;
    private ArrayList<EditText> editTextArrayList;
    private String imageFileName;
    private String videoFileName;
    private String videoFileUrl;
    private View clickedView;
    private LinearLayout.LayoutParams layoutParams;
    private int state;
    private ArrayList<NoteContent> noteContentArrayList;
    private ArrayList<String> stringArrayList;
    private ArrayList<Bitmap> bitmapArrayList;
    private Bundle b;
    private int noteId;


    public NewNoteFragment() {
        noteArrayList = new ArrayList<Note>();
        editTextArrayList = new ArrayList<EditText>();
        bitmapArrayList = new ArrayList<Bitmap>();
        note = new Note();
        noteContent = new NoteContent();
        noteText = new NoteText();
        noteImage = new NoteImage();
        noteVoice = new NoteVoice();
        noteVideoClip = new NoteVideoClip();
        state = StringDB.NEW_NOTE_STATE;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        connectDB = new ConnectDB(getActivity());
        noteDAO = new NoteDAO(connectDB);
        noteContentDAO = new NoteContentDAO(connectDB);
        noteTextDAO = new NoteTextDAO(connectDB);
        noteImageDAO = new NoteImageDAO(connectDB);
        noteVoiceDAO = new NoteVoiceDAO(connectDB);
        noteVideoClipDAO = new NoteVideoClipDAO(connectDB);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_new_note, parent, false);
        contentLayout = (LinearLayout) view.findViewById(R.id.contentLayout);
        titleEditText = (EditText) view.findViewById(R.id.titleEditText);
        contentEditText = (EditText) view.findViewById(R.id.contentEditText);
        saveButton = (Button) view.findViewById(R.id.saveButton);
        btn_attach_file = (ImageButton) view.findViewById(R.id.btn_attach_file);
        popupMenuAttachFile = new PopupMenu(getActivity(), btn_attach_file);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 3, 0, 3);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        contentLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        saveButton.setOnClickListener(this);
        btn_attach_file.setOnClickListener(this);
        popupMenuAttachFile.getMenuInflater().inflate(R.menu.popup_menu_add_item, popupMenuAttachFile.getMenu());
        popupMenuAttachFile.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_chuphinh:
                        break;
                    case R.id.popup_hinhtufile:
                        pickImage();
                        break;
                    case R.id.popup_quayvideo:
                        captureVideoClip();
                        break;
                    case R.id.popup_ghiam:
                        break;
                }
                return true;
            }
        });
        if (state == StringDB.NEW_NOTE_STATE) {
            contentEditText.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(contentEditText, InputMethodManager.SHOW_IMPLICIT);
            editTextArrayList.add(contentEditText);
            noteText = new NoteText();
            noteText.setType(StringDB.TYPE_TEXT);
            noteArrayList.add(noteText);
        } else if (state == StringDB.EDIT_NOTE_STATE) {
            contentLayout.removeAllViews();
            titleEditText.setText(stringArrayList.get(0));
        }
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        fileUtils = new FileUtils();
        bitmapUtils = new BitmapUtils(getActivity().getContentResolver());
        displayMetrics = getResources().getDisplayMetrics();
        if (state == StringDB.EDIT_NOTE_STATE) {
            int countText = 1;
            int countBitmap = 0;
            for (int i = 0; i < noteContentArrayList.size(); i++) {
                NoteContent noteContent = noteContentArrayList.get(i);
                if (noteContent.getType().equals(StringDB.TYPE_TEXT)) {
                    EditText editText = new EditText(getActivity());
                    editText.setLayoutParams(layoutParams);
                    editText.setBackground(null);
                    if (!stringArrayList.get(countText).equals("")) {
                        editText.setText(stringArrayList.get(countText));
                    }
                    countText++;
                    contentLayout.addView(editText);
                    editTextArrayList.add(editText);
                    noteText = new NoteText();
                    noteText.setType(StringDB.TYPE_TEXT);
                    noteArrayList.add(noteText);
                } else if (noteContent.getType().equals(StringDB.TYPE_IMAGE)) {
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);
                    registerForContextMenu(imageView);
                    imageView.setImageBitmap(bitmapArrayList.get(countBitmap));
                    imageFileName = System.currentTimeMillis() + ".png";
                    countBitmap++;
                    contentLayout.addView(imageView);
                    noteImage = new NoteImage();
                    noteImage.setFileType("png");
                    noteImage.setFileName(imageFileName);
                    noteImage.setUrl(FileUtils.sdcard + FileUtils.folder[1] + "/" + imageFileName);
                    noteImage.setType(StringDB.TYPE_IMAGE);
                    noteArrayList.add(noteImage);
                } else if (noteContent.getType().equals(StringDB.TYPE_VIDEOCLIP)) {

                } else if (noteContent.getType().equals(StringDB.TYPE_VOICE)) {

                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                saveData();
                callback.goToMainFragment();
                break;
            case R.id.btn_attach_file:
                popupMenuAttachFile.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri;
        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            bmp = bitmapUtils.getBitmapThumbnail(bitmapUtils.getPath(uri), displayMetrics.widthPixels, displayMetrics.heightPixels);
            bmp = bitmapUtils.rotateBitmap(bmp, uri);
            attachImage(bmp);
            bitmapArrayList.add(bmp);
        } else if (requestCode == CAPTURE_VIDEO && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            attachVideoClip(videoFileUrl);
        } else if (requestCode == RECORD_VOICE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_new_note_fragment, menu);
        this.clickedView = v;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                int indexChild = contentLayout.indexOfChild(clickedView);
                contentLayout.removeViewAt(indexChild + 1);
                contentLayout.removeViewAt(indexChild);
                noteArrayList.remove(indexChild + 1);
                noteArrayList.remove(indexChild);
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void setCallback(MainListener.Callback callback) {
        this.callback = callback;
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    public void captureVideoClip() {
        videoFileName = "Video_" + CalendarUtils.getDateTime("yyyy-MM-dd_HH-mm-ss") + ".mp4";
        videoFileUrl = FileUtils.sdcard + FileUtils.folder[3] + "/" + videoFileName;
        Uri fileUri = fileUtils.createUriVideo(videoFileUrl);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, CAPTURE_VIDEO);
    }

    public void recordVoice() {
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, RECORD_VOICE);
    }

    public void attachText() {
        EditText editText = new EditText(getActivity());
        editText.setLayoutParams(layoutParams);
        editText.setBackground(null);
        contentLayout.addView(editText);
        editTextArrayList.add(editText);
        noteText = new NoteText();
        noteText.setType(StringDB.TYPE_TEXT);
        noteArrayList.add(noteText);
    }

    public void attachImage(Bitmap bmp) {

        // attach to UI
        ImageView imageView = new ImageView(getActivity());
        imageView.setLayoutParams(layoutParams);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(bmp);
        registerForContextMenu(imageView);
        contentLayout.addView(imageView);

        // attach to DB
        noteImage = new NoteImage();
        noteImage.setFileType("png");
        noteImage.setFileName(imageFileName);
        noteImage.setUrl(FileUtils.sdcard + FileUtils.folder[1] + "/" + imageFileName);
        noteImage.setType(StringDB.TYPE_IMAGE);
        noteArrayList.add(noteImage);
        // attach EditText to UI
        attachText();
    }

    public void attachVideoClip(String path) {
        // attach to UI
        VideoView videoView = new VideoView(getActivity());
        videoView.setVideoPath(path);
        videoView.setLayoutParams(layoutParams);
        contentLayout.addView(videoView);
        videoView.start();

        //attach to DB
        NoteVideoClip noteVideoClip = new NoteVideoClip();
        noteVideoClip.setFileName(videoFileName);
        noteVideoClip.setUrl(videoFileUrl);
        noteVideoClip.setType(StringDB.TYPE_VIDEOCLIP);
        noteVideoClip.setFileType("MP4");
        noteArrayList.add(noteVideoClip);

        //attach EditText
        attachText();
    }

    public void attachVoice() {

    }

    public void saveData() {
        Toast.makeText(getActivity(), "Đang lưu ghi chú...", Toast.LENGTH_SHORT).show();
        Note note;
        NoteContent noteContent;
        NoteText noteText;
        NoteImage noteImage;
        NoteVoice noteVoice;
        NoteVideoClip noteVideoClip;
        if (state == StringDB.NEW_NOTE_STATE) {
            noteId = noteDAO.getCountNote() + 1;
        } else if (state == StringDB.EDIT_NOTE_STATE) {
            b = this.getArguments();
            noteId = b.getInt(StringDB.NOTE_ID);
            noteDAO.deleteRawData(noteId);
            noteContentDAO.delete(noteId);
            noteTextDAO.delete(noteId);
            noteImageDAO.delete(noteId);
            noteVoiceDAO.delete(noteId);
            noteVideoClipDAO.delete(noteId);
        }
        int countText = 0;
        int countBitmap = 0;
        int i;
        for (i = 0; i < noteArrayList.size(); i++) {
            note = noteArrayList.get(i);
            noteContent = new NoteContent();
            String type = note.getType();
            if (type.equals(StringDB.TYPE_TEXT)) {
                noteContent.setType(StringDB.TYPE_TEXT);
                noteContent.setText_id(noteTextDAO.getCountNoteText() + 1);
                noteText = (NoteText) note;
                if (countText < editTextArrayList.size()) {
                    noteText.setContent(editTextArrayList.get(countText).getText().toString());
                    countText++;
                }
                noteText.setId(noteTextDAO.getCountNoteText() + 1);
                noteText.setNoteId(noteId);
                noteTextDAO.insert(noteText);
            } else if (type.equals(StringDB.TYPE_IMAGE)) {
                noteContent.setType(StringDB.TYPE_IMAGE);
                noteContent.setImage_id(noteImageDAO.getCountImage() + 1);
                imageFileName = System.currentTimeMillis() + ".png";
                noteImage = (NoteImage) note;
                noteImage.setId(noteImageDAO.getCountImage() + 1);
                noteImage.setNoteId(noteId);
                noteImage.setFileName(imageFileName);
                noteImage.setUrl(FileUtils.sdcard + FileUtils.folder[1] + "/" + imageFileName);
                noteImage.setUrlThumbnail(FileUtils.sdcard + FileUtils.folder[4] + "/" + imageFileName);
                noteImageDAO.insert(noteImage);
                final int countBitmap2 = countBitmap;
                final Bitmap bmp = bitmapArrayList.get(countBitmap2);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        fileUtils.setImageFileName(imageFileName);
                        fileUtils.writeBitmapToSdcard(2, BitmapUtils.getBitmapThumbnail(bmp, 150, 150));
                        fileUtils.writeBitmapToSdcard(1, bmp);
                    }
                }).start();
                countBitmap++;
            } else if (type.equals(StringDB.TYPE_VOICE)) {
                noteContent.setType(StringDB.TYPE_VOICE);
                noteContent.setVoice_id(noteVoiceDAO.getCountNoteVoice() + 1);
                noteVoice = (NoteVoice) note;
                noteVoice.setId(noteVoiceDAO.getCountNoteVoice() + 1);
                noteVoice.setNoteId(noteId);
                noteVoiceDAO.insert(noteVoice);
            } else if (type.equals(StringDB.TYPE_VIDEOCLIP)) {
                noteContent.setType(StringDB.TYPE_VIDEOCLIP);
                noteContent.setVideoclip_id(noteVideoClipDAO.getCountNoteVideoClip() + 1);
                noteVideoClip = (NoteVideoClip) note;
                noteVideoClip.setId(noteVideoClipDAO.getCountNoteVideoClip() + 1);
                noteVideoClip.setNoteId(noteId);
                noteVideoClipDAO.insert(noteVideoClip);
            }
            noteContent.setIndex(i + 1);
            noteContent.setNoteId(noteId);
            noteContentDAO.insert(noteContent);
        }
        note = new Note();
        note.setTitle(titleEditText.getText().toString());
        note.setCreatedAt(CalendarUtils.getDateTime("yyyy-MM-dd HH:mm:ss"));
        note.setModifiedAt(CalendarUtils.getDateTime("yyyy-MM-dd HH:mm:ss"));
        note.setId(noteId);
        if (state == StringDB.NEW_NOTE_STATE) {
            noteDAO.insert(note);
        } else if (state == StringDB.EDIT_NOTE_STATE) {
            noteDAO.update(noteId, note);
        }
        Toast.makeText(getActivity(), "Đã lưu", Toast.LENGTH_SHORT).show();
    }

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public void setBitmapArrayList(ArrayList<Bitmap> bitmapArrayList) {
        this.bitmapArrayList = bitmapArrayList;
    }

    public void setNoteContentArrayList(ArrayList<NoteContent> noteContentArrayList) {
        this.noteContentArrayList = noteContentArrayList;
    }

    public void setState(int state) {
        this.state = state;
    }

}
