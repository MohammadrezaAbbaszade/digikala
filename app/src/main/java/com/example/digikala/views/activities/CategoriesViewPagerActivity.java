package com.example.digikala.views.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.digikala.R;
import com.example.digikala.views.fragments.CategoriesViewPagerFragment;
import com.google.android.material.tabs.TabLayout;

import Woo.Repository.Repository;

public class CategoriesViewPagerActivity extends AppCompatActivity {
    private static final String ID = "id";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageButton mImageButton;
    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, CategoriesViewPagerActivity.class);
        intent.putExtra(ID, id);
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
        int position=Repository.getInstance().getPosition(getIntent().getIntExtra(ID,0));
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), 0) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return CategoriesViewPagerFragment.newInstance(Repository.getInstance().getFilteredCategoriesItems()
                .get(position).getId());
            }

            @Override
            public int getCount() {
                return Repository.getInstance().getFilteredCategoriesItems().size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                  return Repository.getInstance().getFilteredCategoriesItems().get(position).getName();
            }
        });
        mViewPager.setCurrentItem(position);
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
