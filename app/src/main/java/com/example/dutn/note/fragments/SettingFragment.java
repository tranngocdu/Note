package com.example.dutn.note.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dutn.note.R;

/**
 * Created by dutn on 27/07/2015.
 */
public class SettingFragment extends Fragment {

    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {

        View view = inflater.inflate(R.layout.fragment_setting, parent, false);
        return view;

    }

}
