package com.example.dutn.note.utils;

import com.example.dutn.note.objects.CalendarObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dutn on 27/07/2015.
 */
public class CalendarUtils {

    private static final String TAG = CalendarUtils.class.getSimpleName();
    private static String dayOfWeek[] = {"Chủ nhật", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"};
    private static String monthOfYear[] = {"Tháng một", "Tháng hai", "Tháng ba", "Tháng tư", "Tháng năm", "Tháng sáu", "Tháng bảy", "Tháng tám", "Tháng chín", "Tháng mười", "Tháng mười một", "Tháng mười hai"};

    public static String getDateNow() {
        Calendar calendar = Calendar.getInstance();
        int thu = calendar.get(Calendar.DAY_OF_WEEK);
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        return dayOfWeek[thu - 1] + ", " + ngay + " " + monthOfYear[thang] + " " + nam;
    }

    public static int numDayOfMonth(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.MONTH);
    }

    public static int currentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static ArrayList setCalendarToArrayList(Calendar calendar) {
        Calendar clone = (Calendar) calendar.clone();
        clone.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        int totalDayOfClone = clone.getActualMaximum(Calendar.DAY_OF_MONTH);
        ArrayList arrayList = new ArrayList();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int totalDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        CalendarObject obj;
        int count;
        switch (day) {
            case 0:
                for (int i = 5; i > 0; i--) {
                    obj = new CalendarObject();
                    obj.setDay(totalDayOfClone - i);
                    obj.setShow(0);
                    arrayList.add(obj);
                }
                break;
            case 1:
                for (int i = 5; i >= 0; i--) {
                    obj = new CalendarObject();
                    obj.setDay(totalDayOfClone - i);
                    obj.setShow(0);
                    arrayList.add(obj);
                }
                break;
            default:
                count = 2;
                int cloneDay = day - 3;
                while (count < day) {
                    obj = new CalendarObject();
                    obj.setDay(totalDayOfClone - cloneDay);
                    obj.setShow(0);
                    arrayList.add(obj);
                    count++;
                    cloneDay--;
                }
                break;
        }
        for (int i = 1; i <= totalDayOfMonth; i++) {
            obj = new CalendarObject();
            obj.setDay(i);
            obj.setShow(1);
            obj.setReminder(0);
            obj.setMonth(calendar.get(Calendar.MONTH) + 1);
            obj.setYear(calendar.get(Calendar.YEAR));
            arrayList.add(obj);
        }
        count = 0;
        while (arrayList.size() < 42) {
            count++;
            obj = new CalendarObject();
            obj.setDay(count);
            obj.setShow(2);
            arrayList.add(obj);
        }
        return arrayList;
    }

    public static String getTimeNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date());
    }

    public static String addZero(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return i + "";
        }
    }

    public static String getDateTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }


}
