package com.example.digikala.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import com.example.digikala.fragments.MainFragment;
import com.example.digikala.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ImageButton mImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.acvity_drawer);
        mNavigationView = findViewById(R.id.navigation);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mImageButton=findViewById(R.id.toolbar_burger);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
//                mDrawerLayout,
//                mToolbar, R.string.open,  R.string.close);
//
//        mDrawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                }
                else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fm.beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance())
                    .commit();
        }

    }
}
