package com.example.dutn.note.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dutn.note.R;
import com.example.dutn.note.objects.MainMenuObject;

import java.util.ArrayList;

/**
 * Created by trandu on 07/08/2015.
 */
public class MainMenuAdapter extends ArrayAdapter {

    public static final String TAG = MainMenuAdapter.class.getSimpleName();

    private Activity context;
    private int layoutId;
    private ArrayList<MainMenuObject> arrayList;
    private MainMenuObject mainMenuObject;
    private ImageView imageView;
    private TextView textView;

    public MainMenuAdapter(Activity context, int layoutId, ArrayList<MainMenuObject> arrayList) {
        super(context, layoutId, arrayList);
        this.context = context;
        this.layoutId = layoutId;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        LayoutInflater inflater = context.getLayoutInflater();
        view = inflater.inflate(layoutId, null);
        mainMenuObject = arrayList.get(position);
        imageView = (ImageView) view.findViewById(R.id.mainmenu_icon);
        imageView.setImageResource(mainMenuObject.getIcon());
        textView = (TextView) view.findViewById(R.id.mainmenu_title);
        textView.setTextColor(Color.parseColor(mainMenuObject.getColor()));
        textView.setText(mainMenuObject.getTitle());
        return view;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }


}
