package com.example.digikala.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.digikala.R;

public class CategoriesViewPagerActivity extends AppCompatActivity {
    private static final String STATE = "state";
    public static Intent newIntent(Context context, int state) {
        Intent intent = new Intent(context, CategoriesViewPagerActivity.class);
        intent.putExtra(STATE,state);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_view_pager);
    }
}
