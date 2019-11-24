package com.example.digikala.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.digikala.R;
import com.example.digikala.fragments.CategoriesViewPagerFragment;
import com.google.android.material.tabs.TabLayout;

public class CategoriesViewPagerActivity extends AppCompatActivity {
    private static final String STATE = "state";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageButton mImageButton;

    public static Intent newIntent(Context context, int state) {
        Intent intent = new Intent(context, CategoriesViewPagerActivity.class);
        intent.putExtra(STATE, state);
        return intent;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CategoriesViewPagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_view_pager);
        mViewPager = findViewById(R.id.categories_activity_view_pager);
        mTabLayout = findViewById(R.id.categories_activity_tab_layout);
        mImageButton = findViewById(R.id.categories_activity__arrow);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), 0) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return CategoriesViewPagerFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0)
                    return "تی شرت ها";
                else if (position == 1)
                    return "کالای دیجیتال";
                else if (position == 2)
                    return "کاور موبایل";
                else
                    return "هودی ها";

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
