package com.example.digikala.RecyclersViews.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesData {
    private static final String PREF_NAME = "digikala";

    private static final String PREF_QUERY = "query";
    private static final String RADIO_ID = "radioid";


    private static android.content.SharedPreferences getPhotoSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getQuery(Context context) {
        android.content.SharedPreferences prefs = getPhotoSharedPreferences(context);

        if (prefs == null)
            return null;

        return prefs.getString(PREF_QUERY, null);
    }

    public static void setQuery(Context context, String query) {
        android.content.SharedPreferences prefs = getPhotoSharedPreferences(context);

        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_QUERY, query);
        editor.apply();
    }
    public static void setRadioGroupId(Context context, int id) {
        android.content.SharedPreferences prefs = getPhotoSharedPreferences(context);

        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(RADIO_ID, id);
        editor.apply();
    }
    public static int getRadioGroupId(Context context) {
        android.content.SharedPreferences prefs = getPhotoSharedPreferences(context);

        if (prefs == null)
            return 0;

        return prefs.getInt(RADIO_ID, 0);
    }
}
