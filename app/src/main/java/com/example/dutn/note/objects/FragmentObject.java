package com.example.dutn.note.objects;

import android.app.Fragment;

/**
 * Created by dutn on 06/08/2015.
 */
public class FragmentObject {

    private Fragment fragment;
    private String title;

    public FragmentObject() {
    }

    public FragmentObject(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FragmentObject{" +
                "fragment=" + fragment +
                ", title='" + title + '\'' +
                '}';
    }
}
