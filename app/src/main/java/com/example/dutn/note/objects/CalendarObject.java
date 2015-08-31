package com.example.dutn.note.objects;

/**
 * Created by dutn on 27/07/2015.
 */
public class CalendarObject {

    private int day;
    private int show;
    private int reminder;
    private int month;
    private int year;

    public CalendarObject() {
    }

    public CalendarObject(int day, int show) {
        this.day = day;
        this.show = show;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "CalendarObject{" +
                "day=" + day +
                ", show=" + show +
                ", reminder=" + reminder +
                ", month=" + month +
                ", year=" + year +
                '}';
    }

}
