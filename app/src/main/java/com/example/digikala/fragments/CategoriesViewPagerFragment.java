package com.example.digikala.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digikala.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesViewPagerFragment extends Fragment {
    private RecyclerView mRecyclerView;

    public static CategoriesViewPagerFragment newInstance() {

        Bundle args = new Bundle();

        CategoriesViewPagerFragment fragment = new CategoriesViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CategoriesViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories_view_pager, container, false);





        return view;
    }

}
