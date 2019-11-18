package com.example.digikala.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.digikala.R;
import com.example.digikala.fragments.ListProductsFragment;

public class ListProductsActivity extends AppCompatActivity {

    private static final String STATE = "state";

    public static Intent newIntent(Context context, int state) {
        Intent intent = new Intent(context, ListProductsActivity.class);
        intent.putExtra(STATE,state);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);
        Log.d("tag","onCreateViewLA");
        int state=getIntent().getIntExtra(STATE,0);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.list_products_activity_container);
        if(fragment==null)
        {
            fm.beginTransaction().replace(R.id.list_products_activity_container, ListProductsFragment.newInstance(state))
                    .commit();
        }
    }
}
