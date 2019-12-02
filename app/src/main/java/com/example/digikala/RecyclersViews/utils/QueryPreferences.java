package com.example.digikala.RecyclersViews.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class QueryPreferences {
    private static final String PREF_NAME = "digikala";

    private static final String PREF_QUERY = "query";


    private static SharedPreferences getPhotoSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getQuery(Context context) {
        SharedPreferences prefs = getPhotoSharedPreferences(context);

        if (prefs == null)
            return null;

        return prefs.getString(PREF_QUERY, null);
    }

    public static void setQuery(Context context, String query) {
        SharedPreferences prefs = getPhotoSharedPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_QUERY, query);
        editor.apply();
    }
}
