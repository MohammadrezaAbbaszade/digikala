package com.example.digikala.views.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.digikala.R;
import com.example.digikala.views.fragments.FilterFragment;

public class FilterActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, FilterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.sort_activity_container);
        if (fragment == null) {
                    fm.beginTransaction().replace(R.id.sort_activity_container, FilterFragment.newInstance())
                            .commit();
        }

    }
}
