package com.example.dutn.note.objects;

/**
 * Created by trandu on 07/08/2015.
 */
public class MainMenuObject {

    private int icon;
    private String title;
    private String color;

    public MainMenuObject() {
    }

    public MainMenuObject(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public MainMenuObject(int icon, String title, String color) {
        this.icon = icon;
        this.title = title;
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "MainMenuObject{" +
                "icon=" + icon +
                ", title='" + title + '\'' +
                '}';
    }
}
