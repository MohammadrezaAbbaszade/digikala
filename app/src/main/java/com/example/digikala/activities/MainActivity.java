package com.example.digikala.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import com.example.digikala.fragments.MainFragment;
import com.example.digikala.R;
import com.example.digikala.fragments.NoNetworkFragment;
import com.example.digikala.fragments.changeFragment;
import com.example.digikala.model.WoocommerceBody;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements changeFragment {
    private static final String STATE = "state";
    private static final String WOOCOMEERCE_BODY = "woocommercebody";
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ImageButton mImageButton;
    int state;
    List<WoocommerceBody> woocommerceBodies;
    FragmentManager fm = getSupportFragmentManager();

    public static Intent newIntent(Context context,int state) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(STATE,state);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        state=getIntent().getIntExtra(STATE,0);
        mDrawerLayout = findViewById(R.id.acvity_drawer);
        mNavigationView = findViewById(R.id.navigation);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mImageButton = findViewById(R.id.toolbar_burger);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(state==1) {
            fm.beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance())
                    .commit();
            mToolbar.setVisibility(View.VISIBLE);
            Log.d("tag","mainActivity"+"1");
        }else
        {
            fm.beginTransaction().replace(R.id.fragment_container, NoNetworkFragment.newInstance())
                    .commit();
            mToolbar.setVisibility(View.GONE);
            Log.d("tag","mainActivity"+"0");
        }

    }

    @Override
    public void changeFragment(boolean check) {
        if(check==false) {
            fm.beginTransaction().replace(R.id.fragment_container, NoNetworkFragment.newInstance())
                    .commit();
            mToolbar.setVisibility(View.GONE);
        }else {
            fm.beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance())
                    .commit();
            mToolbar.setVisibility(View.VISIBLE);
        }
    }
}
