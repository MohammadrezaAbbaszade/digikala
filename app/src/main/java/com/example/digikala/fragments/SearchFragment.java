package com.example.digikala.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.activities.ListProductsActivity;
import com.example.digikala.network.WooCommerce;

import java.io.IOException;

import Woo.Repository.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private WooCommerce mWooCommerce = new WooCommerce();
    private SearchView searchView;
    private RecyclerView mRecyclerView;
    private ImageButton mArrowButton;
    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.fragment_search_search_view);
        mRecyclerView = view.findViewById(R.id.fragment_search_recycler);
        mArrowButton=view.findViewById(R.id.fragment_search_toolbar_arrow);
        mArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchAsynceTask searchAsynceTask = new SearchAsynceTask();
                searchAsynceTask.execute(query);
                SharedPreferencesData.setQuery(getActivity(),query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return view;
    }




    private class SearchAsynceTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... searchQuery) {

            try {
                Repository.getInstance().setSearchedProducts(mWooCommerce.searchInProducts(searchQuery[0]));

            } catch (IOException e) {
                e.printStackTrace();
                getActivity().finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Repository.getInstance().getSearchedProducts() != null) {
               Intent intent= ListProductsActivity.newIntent(getActivity(),4);
               startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "موردی یافت نشد", Toast.LENGTH_LONG).show();
            }
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
