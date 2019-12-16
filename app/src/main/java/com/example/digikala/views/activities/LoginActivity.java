package com.example.digikala.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.digikala.R;
import com.example.digikala.views.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.login_activity_container);
        if(fragment==null)
        {
            fm.beginTransaction().replace(R.id.login_activity_container, LoginFragment.newInstance())
                    .commit();
        }

    }
}
