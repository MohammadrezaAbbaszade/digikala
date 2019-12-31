package com.example.digikala.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.digikala.R;
import com.example.digikala.views.fragments.CustomerInfoFragment;

public class CustomerInfoActivity extends AppCompatActivity {
    public static FragmentManager fm;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CustomerInfoActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.customer_info_container);
        if(fragment==null)
        {
            fm.beginTransaction().replace(R.id.customer_info_container, CustomerInfoFragment.newInstance())
                    .commit();
        }
    }
}
