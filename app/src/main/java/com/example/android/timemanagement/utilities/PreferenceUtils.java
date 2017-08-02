package com.example.android.timemanagement.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by icyfillup on 8/1/2017.
 */

public class PreferenceUtils
{
    public static final String IS_FIRST = "isFirst";
    public static final String MONDAY_TARGET_TIME = "mondayTargerTime";
    public static final String TUESDAY_TARGET_TIME = "tuesdayTargerTime";
    public static final String WEDNESDAY_TARGET_TIME = "wednesdayTargerTime";
    public static final String THURSDAY_TARGET_TIME = "thursdayTargerTime";
    public static final String FRIDAY_TARGET_TIME = "fridayTargerTime";
    public static final String SATURDAY_TARGET_TIME = "saturdayTargerTime";
    public static final String SUNDAY_TARGET_TIME = "sundayTargerTime";
    public static final String DEFAULT_TARGET_TIME = "0:00";

    public static void initSharePreference(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFirst = prefs.getBoolean(IS_FIRST, true);
        if(isFirst)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(IS_FIRST, false);
            editor.putString(MONDAY_TARGET_TIME, DEFAULT_TARGET_TIME);
            editor.putString(TUESDAY_TARGET_TIME, DEFAULT_TARGET_TIME);
            editor.putString(WEDNESDAY_TARGET_TIME, DEFAULT_TARGET_TIME);
            editor.putString(THURSDAY_TARGET_TIME, DEFAULT_TARGET_TIME);
            editor.putString(FRIDAY_TARGET_TIME, DEFAULT_TARGET_TIME);
            editor.putString(SATURDAY_TARGET_TIME, DEFAULT_TARGET_TIME);
            editor.putString(SUNDAY_TARGET_TIME, DEFAULT_TARGET_TIME);

            editor.commit();
        }
    }

    public static Boolean updateDayTargetTime(Context context, String day, String targetTime)
    {
        Boolean result = false;
         if(day.equals(MONDAY_TARGET_TIME) || day.equals(TUESDAY_TARGET_TIME) ||
                 day.equals(WEDNESDAY_TARGET_TIME) || day.equals(THURSDAY_TARGET_TIME) ||
                 day.equals(FRIDAY_TARGET_TIME) ||day.equals(SATURDAY_TARGET_TIME) ||
                 day.equals(SUNDAY_TARGET_TIME))
         {
             SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
             SharedPreferences.Editor editor = prefs.edit();

             editor.putString(day, targetTime);

             editor.commit();
             result = true;
         }
         else
         {
             result = false;
         }

         return result;
    }

    public static String getDayTargetTime(Context context, String day)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String targetTime = DEFAULT_TARGET_TIME;

        if(day.equals(MONDAY_TARGET_TIME) || day.equals(TUESDAY_TARGET_TIME) ||
                day.equals(WEDNESDAY_TARGET_TIME) || day.equals(THURSDAY_TARGET_TIME) ||
                day.equals(FRIDAY_TARGET_TIME) ||day.equals(SATURDAY_TARGET_TIME) ||
                day.equals(SUNDAY_TARGET_TIME))
        {
            targetTime = prefs.getString(day, DEFAULT_TARGET_TIME);
        }
        else
        {
            targetTime = null;
        }

        return targetTime;
    }
}
