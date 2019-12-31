package com.example.digikala.RecyclersViews.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesData {
    private static final String PREF_NAME = "digikala";

    private static final String PREF_QUERY = "query";
    private static final String PREF_CHECK_LOGIN = "checklogin";
    private static final String PREF_EMAIL = "email";
    private static final String ID = "id";
    public static final String PREF_LAST_PRODUCT_ID = "lastProductId";
    private static final String CUSTOMER_NAME = "customername";
    private static final String CUSTOMER_ID = "customerId";

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
    public static String getCustomerEmail(Context context) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);

        if (prefs == null)
            return null;

        return prefs.getString(PREF_EMAIL, null);
    }

    public static void setCustomerEmail(Context context, String query) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_EMAIL, query);
        editor.apply();
    }
    public static String getCustomerName(Context context) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);

        if (prefs == null)
            return null;

        return prefs.getString(CUSTOMER_NAME, null);
    }

    public static void setCustomerName(Context context, String name) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CUSTOMER_NAME, name);
        editor.apply();
    }
    public static void setRadioGroupId(Context context, int id) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ID, id);
        editor.apply();
    }

    public static int getRadioGroupId(Context context) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);
        return prefs.getInt(ID, 0);
    }

    public static int getLastProductsId(Context context) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);

        return prefs.getInt(PREF_LAST_PRODUCT_ID, 0);
    }

    public static void setLastProductId(Context context, int productId) {
        getPproductsSharedPreferences(context)
                .edit()
                .putInt(PREF_LAST_PRODUCT_ID, productId)
                .apply();
    }
    public static int getCustomerId(Context context) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);

        return prefs.getInt(CUSTOMER_ID, 0);
    }

    public static void setCustomertId(Context context, int customerId) {
        getPproductsSharedPreferences(context)
                .edit()
                .putInt(CUSTOMER_ID, customerId)
                .apply();
    }
    public static boolean checkCustomerLogedIn(Context context) {

        SharedPreferences prefs = getPproductsSharedPreferences(context);
        return prefs.getBoolean(PREF_CHECK_LOGIN, false);
    }
    public static void setCustomerLogedIn(Context context, boolean check) {
        SharedPreferences prefs = getPproductsSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREF_CHECK_LOGIN, check);
        editor.apply();
    }

}
