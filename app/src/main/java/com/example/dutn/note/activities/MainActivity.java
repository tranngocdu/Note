package com.example.dutn.note.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dutn.note.R;
import com.example.dutn.note.adapters.MainMenuAdapter;
import com.example.dutn.note.dao.StringDB;
import com.example.dutn.note.dto.Note;
import com.example.dutn.note.dto.NoteContent;
import com.example.dutn.note.dto.NoteText;
import com.example.dutn.note.fragments.CalendarFragment;
import com.example.dutn.note.fragments.FileManagerFragment;
import com.example.dutn.note.fragments.MainFragment;
import com.example.dutn.note.fragments.NewNoteFragment;
import com.example.dutn.note.fragments.SettingFragment;
import com.example.dutn.note.fragments.ViewNoteFragment;
import com.example.dutn.note.fragments.ViewReminderFragment;
import com.example.dutn.note.listeners.MainListener;
import com.example.dutn.note.objects.CalendarObject;
import com.example.dutn.note.objects.FragmentObject;
import com.example.dutn.note.objects.MainMenuObject;
import com.example.dutn.note.utils.CalendarUtils;
import com.example.dutn.note.utils.FileUtils;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener,
        MainListener.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] listString = {
            StringDB.NEW_NOTE,
            StringDB.ALL_NOTE,
            StringDB.CALENDAR,
            StringDB.FILE_MANAGER,
            StringDB.SETTING
    };
    private int[] ic_black = {
            R.drawable.ic_add_newnote_black_24dp,
            R.drawable.ic_allnote_black_24dp,
            R.drawable.ic_calendar_black_24dp,
            R.drawable.ic_folder_black_24dp,
            R.drawable.ic_settings_black_24dp
    };
    private int[] ic_blue = {
            R.drawable.ic_add_newnote_blue_24dp,
            R.drawable.ic_allnote_blue_24dp,
            R.drawable.ic_calendar_blue_24dp,
            R.drawable.ic_folder_blue_24dp,
            R.drawable.ic_settings_blue_24dp
    };
    private MainMenuAdapter mainMenuAdapter;
    private ArrayList<MainMenuObject> arrayList;
    private RelativeLayout expandedMenu;
    private RelativeLayout action_bar;
    private Fragment fragment;
    private FragmentManager fragmentManager = this.getFragmentManager();
    private Stack<FragmentObject> stackFragment = new Stack<FragmentObject>();
    private FragmentObject fragmentObject = new FragmentObject(null, "null");
    private TextView action_bar_title;
    private ImageButton btn_menu, btn_search, btn_more;
    private String calendarDateNow;
    private MainFragment mainFragment;
    private CalendarFragment calendarFragment;
    private SettingFragment settingFragment;
    private ViewNoteFragment viewNoteFragment;
    private FileManagerFragment fileManagerFragment;


    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadData();
    }

    public void init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        expandedMenu = (RelativeLayout) findViewById(R.id.expanded_menu);
        action_bar = (RelativeLayout) findViewById(R.id.action_bar);
        listView = (ListView) findViewById(R.id.listView);
        action_bar_title = (TextView) findViewById(R.id.action_bar_title);
        btn_menu = (ImageButton) findViewById(R.id.btn_menu);
        btn_search = (ImageButton) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        btn_menu.setImageResource(R.drawable.ic_menu);
        btn_menu.setOnClickListener(this);
        btn_more = (ImageButton) findViewById(R.id.btn_more);
        btn_more.setOnClickListener(this);
        arrayList = refreshMainMenu();
        mainMenuAdapter = new MainMenuAdapter(this, R.layout.list_item_mainmenu, arrayList);
        listView.setAdapter(mainMenuAdapter);
        listView.setOnItemClickListener(this);
        initFragment();
        goToMainFragment();
    }

    public void initFragment() {
        mainFragment = new MainFragment();
        mainFragment.setCallback(this);
        calendarFragment = new CalendarFragment();
        calendarFragment.setCallback(this);
        settingFragment = new SettingFragment();
        viewNoteFragment = new ViewNoteFragment();
        viewNoteFragment.setCallback(this);
        fileManagerFragment = new FileManagerFragment();
    }

    public void loadData() {
        calendarDateNow = CalendarUtils.getDateNow();
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileUtils.createRootAppFolder();
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu:
                if (fragmentObject.getTitle().equalsIgnoreCase(StringDB.DETAIL_NOTE)) {
                    goToMainFragment();
                } else if (fragmentObject.getTitle().equalsIgnoreCase(StringDB.REMINDER)) {
                    viewCalendar();
                } else {
                    drawerLayout.openDrawer(expandedMenu);
                }
                break;
            case R.id.btn_search:

                break;
            case R.id.btn_more:
                showPopupMenu(v);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        switch (position) {
            case 0:
                createNewNote();
                break;
            case 1:
                goToMainFragment();
                break;
            case 2:
                viewCalendar();
                break;
            case 3:
                viewFileManager();
                break;
            case 4:
                viewSetting();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentObject.getTitle().equalsIgnoreCase(StringDB.ALL_NOTE)) {
            this.finish();
        } else if (fragmentObject.getTitle().equalsIgnoreCase(StringDB.REMINDER)) {
            viewCalendar();
        } else {
            goToMainFragment();
        }
    }

    @Override
    public void goToMainFragment() {
        fragment = mainFragment;
        fragmentObject = new FragmentObject(fragment, StringDB.ALL_NOTE);
        replaceFragment(fragmentObject);
        if (arrayList != null) {
            arrayList.clear();
            arrayList.addAll(refreshMainMenu());
            mainMenuAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void goToNoteDetail(Note note) {
        fragment = viewNoteFragment;
        Bundle bundle = new Bundle();
        bundle.putInt(StringDB.NOTE_ID, note.getNoteId());
        fragment.setArguments(bundle);
        fragmentObject = new FragmentObject(fragment, StringDB.DETAIL_NOTE);
        replaceFragment(fragmentObject);
    }

    @Override
    public void goToEditNote(Bundle bundle, ArrayList<NoteContent> noteContentArrayList, ArrayList<String> stringArrayList, ArrayList<Bitmap> bitmapArrayList) {
        if (fragmentObject.getTitle() == StringDB.NEW_NOTE) {
            drawerLayout.closeDrawer(expandedMenu);
        } else {
            stackFragment.add(fragmentObject);
            fragment = new NewNoteFragment();
            fragment.setArguments(bundle);
            ((NewNoteFragment) fragment).setCallback(this);
            ((NewNoteFragment) fragment).setState(StringDB.EDIT_NOTE_STATE);
            ((NewNoteFragment) fragment).setNoteContentArrayList(noteContentArrayList);
            ((NewNoteFragment) fragment).setStringArrayList(stringArrayList);
            ((NewNoteFragment) fragment).setBitmapArrayList(bitmapArrayList);
            fragmentObject = new FragmentObject(fragment, StringDB.NEW_NOTE);
            replaceFragment(fragmentObject);
            if (arrayList != null) {
                arrayList.clear();
                arrayList.addAll(refreshMainMenu());
                mainMenuAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void calendarItemClicked(CalendarObject calendarObject) {
        fragment = new ViewReminderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("day", calendarObject.getDay());
        bundle.putInt("month", calendarObject.getMonth());
        bundle.putInt("year", calendarObject.getYear());
        fragment.setArguments(bundle);
        Note note = new NoteText();
        fragmentObject = new FragmentObject(fragment, StringDB.REMINDER);
        replaceFragment(fragmentObject);
    }

    @Override
    public void createNewNote() {
        if (fragmentObject.getTitle() == StringDB.NEW_NOTE) {
            drawerLayout.closeDrawer(expandedMenu);
        } else {
            stackFragment.add(fragmentObject);
            fragment = new NewNoteFragment();
            ((NewNoteFragment) fragment).setCallback(this);
            fragmentObject = new FragmentObject(fragment, StringDB.NEW_NOTE);
            replaceFragment(fragmentObject);
            if (arrayList != null) {
                arrayList.clear();
                arrayList.addAll(refreshMainMenu());
                mainMenuAdapter.notifyDataSetChanged();
            }
        }
    }

    public void viewFileManager() {
        if (fragmentObject.getTitle() == StringDB.FILE_MANAGER) {
            drawerLayout.closeDrawer(expandedMenu);
        } else {
            stackFragment.add(fragmentObject);
            fragment = fileManagerFragment;
            fragmentObject = new FragmentObject(fragment, StringDB.FILE_MANAGER);
            replaceFragment(fragmentObject);
            if (arrayList != null) {
                arrayList.clear();
                arrayList.addAll(refreshMainMenu());
                mainMenuAdapter.notifyDataSetChanged();
            }
        }
    }

    public void viewCalendar() {
        if (fragmentObject.getTitle() == StringDB.CALENDAR) {
            drawerLayout.closeDrawer(expandedMenu);
        } else {
            stackFragment.add(fragmentObject);
            fragment = calendarFragment;
            fragmentObject = new FragmentObject(fragment, StringDB.CALENDAR);
            Bundle bundle = new Bundle();
            bundle.putString("dateNow", calendarDateNow);
            fragment.setArguments(bundle);
            replaceFragment(fragmentObject);
            if (arrayList != null) {
                arrayList.clear();
                arrayList.addAll(refreshMainMenu());
                mainMenuAdapter.notifyDataSetChanged();
            }
        }
    }

    public void viewSetting() {
        if (fragmentObject.getTitle() == StringDB.SETTING) {
            drawerLayout.closeDrawer(expandedMenu);
        } else {
            stackFragment.add(fragmentObject);
            fragment = settingFragment;
            fragmentObject = new FragmentObject(fragment, StringDB.SETTING);
            replaceFragment(fragmentObject);
            if (arrayList != null) {
                arrayList.clear();
                arrayList.addAll(refreshMainMenu());
                mainMenuAdapter.notifyDataSetChanged();
            }
        }
    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_main_fragment, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_sync:

                        break;
                    case R.id.popup_setting:
                        viewSetting();
                        break;
                    case R.id.popup_exit:
                        MainActivity.this.finish();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void replaceFragment(FragmentObject fragmentObject) {
        if (fragmentObject.getTitle().equalsIgnoreCase(StringDB.ALL_NOTE)) {
            btn_menu.setImageResource(R.drawable.ic_menu);
        } else {
            btn_menu.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        }
        if (!fragmentObject.getTitle().equalsIgnoreCase(StringDB.ALL_NOTE)) {
            btn_search.setVisibility(View.GONE);
            btn_more.setVisibility(View.GONE);
        } else {
            btn_search.setVisibility(View.VISIBLE);
            btn_more.setVisibility(View.VISIBLE);
        }
        if (fragmentObject.getTitle().equalsIgnoreCase(StringDB.NEW_NOTE)) {
            action_bar.setVisibility(View.GONE);
        } else {
            action_bar.setVisibility(View.VISIBLE);
        }
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragmentObject.getFragment()).commit();
        action_bar_title.setText(fragmentObject.getTitle());
        drawerLayout.closeDrawer(expandedMenu);
    }

    public ArrayList<MainMenuObject> refreshMainMenu() {
        ArrayList<MainMenuObject> arrayList = new ArrayList<MainMenuObject>();
        for (int i = 0; i < listString.length; i++) {
            if (fragmentObject.getTitle().equalsIgnoreCase(listString[i])) {
                arrayList.add(new MainMenuObject(ic_blue[i], listString[i], "#2dbe60"));
            } else {
                arrayList.add(new MainMenuObject(ic_black[i], listString[i], "#000000"));
            }
        }
        return arrayList;
    }
}
