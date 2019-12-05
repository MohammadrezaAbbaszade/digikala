package com.example.digikala.fragments;


import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.digikala.R;
import com.example.digikala.activities.CategoriesViewPagerActivity;
import com.example.digikala.model.categoriesmodel.CategoriesBody;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import Woo.Repository.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesViewPagerFragment extends Fragment {
    private static final String ID = "id";
    private int id;
    private RecyclerView mRecyclerView;
    private int position;

    public static CategoriesViewPagerFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ID, id);
        CategoriesViewPagerFragment fragment = new CategoriesViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CategoriesViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt(ID);
        position = Repository.getInstance().getPosition(id);
//        prepareNamesSubCategories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories_view_pager, container, false);
        mRecyclerView = view.findViewById(R.id.sub_categories_recycler);
        SubCategoriesAdaptor subCategoriesAdaptor = new SubCategoriesAdaptor(Repository.getInstance().getSubCategory(id));
        mRecyclerView.setAdapter(subCategoriesAdaptor);
        return view;
    }

    private class SubCategoriesHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public SubCategoriesHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.sub_categories_image_view);
            mTextView = itemView.findViewById(R.id.sub_categories_text_view);


        }

        void bind(CategoriesBody categoriesItem) {
            mTextView.setText(categoriesItem.getName());
        }

    }

    private class SubCategoriesAdaptor extends RecyclerView.Adapter<SubCategoriesHolder> {
        private List<CategoriesBody> mCategoriesItems;

        public SubCategoriesAdaptor(List<CategoriesBody> categoriesItems) {
            mCategoriesItems = categoriesItems;
        }

        @NonNull
        @Override
        public SubCategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.sub_categories_items, parent, false);
            SubCategoriesHolder subCategoriesHolder = new SubCategoriesHolder(view);
            return subCategoriesHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SubCategoriesHolder holder, int position) {
            holder.bind(mCategoriesItems.get(position));

        }

        @Override
        public int getItemCount() {
            return mCategoriesItems.size();
        }
    }

}
