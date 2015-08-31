package com.example.dutn.note.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dutn.note.R;
import com.example.dutn.note.adapters.NoteAdapter;
import com.example.dutn.note.dao.ConnectDB;
import com.example.dutn.note.dao.NoteDAO;
import com.example.dutn.note.dao.NoteImageDAO;
import com.example.dutn.note.dao.NoteVideoClipDAO;
import com.example.dutn.note.dto.Note;
import com.example.dutn.note.dto.NoteImage;
import com.example.dutn.note.dto.NoteVideoClip;
import com.example.dutn.note.listeners.MainListener;
import com.example.dutn.note.utils.BitmapUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dutn on 28/07/2015.
 */
public class MainFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = MainFragment.class.getSimpleName();
    private NoteDAO noteDAO;
    private ListView listView;
    private ImageButton btn_float;
    private NoteAdapter noteAdapter;
    private ArrayList<Note> arrayList;
    private MainListener.Callback callback;
    private TextView noticeTextView;
    private NoteImageDAO noteImageDAO;
    private NoteVideoClipDAO noteVideoClipDAO;
    private ConnectDB connectDB;
    private HashMap<Integer, Bitmap> hm;


    public MainFragment() {

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
        noteImageDAO = new NoteImageDAO(connectDB);
        noteVideoClipDAO = new NoteVideoClipDAO(connectDB);
        hm = new HashMap<Integer, Bitmap>();
        arrayList = (ArrayList) noteDAO.getAll();
        for (int i = 0; i < arrayList.size(); i++) {
            Note note = arrayList.get(i);
            NoteImage noteImage = noteImageDAO.getLastElement(note.getNoteId());
            if (noteImage != null) {
                hm.put(note.getNoteId(), BitmapUtils.readBitmapFromSdcard(noteImage.getUrlThumbnail()));
            } else {
                NoteVideoClip noteVideoClip = noteVideoClipDAO.getLastElement(note.getNoteId());
                if (noteVideoClip != null) {
                    File file = new File(noteVideoClip.getUrl());
                    hm.put(note.getNoteId(), ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.MINI_KIND));
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_main, parent, false);
        listView = (ListView) view.findViewById(R.id.listViewInMainFragment);
        btn_float = (ImageButton) view.findViewById(R.id.btn_float);
        noticeTextView = (TextView) view.findViewById(R.id.noticeTextView);
        btn_float.setOnClickListener(this);
        noteAdapter = new NoteAdapter(getActivity(), R.layout.list_item_note, arrayList, hm);
        listView.setAdapter(noteAdapter);
        listView.setOnItemClickListener(this);
        check();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(listView);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int i;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (i < firstVisibleItem) {
                    btn_float.setVisibility(View.GONE);
                } else if (i > firstVisibleItem) {
                    btn_float.setVisibility(View.VISIBLE);
                }
                i = firstVisibleItem;
            }
        });
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
            case R.id.btn_float:
                callback.createNewNote();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        callback.goToNoteDetail(arrayList.get(position));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_listview_mainfragment, menu);
        menu.setHeaderTitle("Select Action");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:

                break;
            case R.id.delete:
                noteDAO.delete(arrayList.get(adapterContextMenuInfo.position).getNoteId());
                arrayList.remove(adapterContextMenuInfo.position);
                noteAdapter.notifyDataSetChanged();
                check();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void setCallback(MainListener.Callback callback) {
        this.callback = callback;
    }

    public void check() {
        if (arrayList.size() != 0) {
            noticeTextView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.GONE);
            noticeTextView.setVisibility(View.VISIBLE);
        }
    }


}
