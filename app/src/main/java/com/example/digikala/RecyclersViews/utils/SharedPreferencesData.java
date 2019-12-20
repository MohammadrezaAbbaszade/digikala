package com.example.digikala.RecyclersViews.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesData {
    private static final String PREF_NAME = "digikala";

    private static final String PREF_QUERY = "query";
    private static final String ID = "id";

    private static SharedPreferences getPproductsSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getQuery(Context context) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);

        if (prefs == null)
            return null;

        return prefs.getString(PREF_QUERY, null);
    }

    public static void setQuery(Context context, String query) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_QUERY, query);
        editor.apply();
    }

   public static void setRadioGroupId(Context context,int id)
   {
       SharedPreferences prefs = getPproductsSharedPreferences(context);
       SharedPreferences.Editor editor = prefs.edit();
       editor.putInt(ID, id);
       editor.apply();
   }
    public static int getRadioGroupId(Context context) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);
        return prefs.getInt(ID, 0);
    }
}
