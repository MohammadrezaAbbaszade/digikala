package com.example.digikala.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.digikala.R;

public class SearchActivity extends AppCompatActivity {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.search_activity_container);
        if(fragment==null)
        {
            fm.beginTransaction().replace(R.id.search_activity_container, SearchFragment.newInstance())
                    .commit();
        }
    }
}
