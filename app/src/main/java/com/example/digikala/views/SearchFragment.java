package com.example.digikala.views;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.network.WooCommerce;

import java.io.IOException;

import Woo.Repository.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private WooCommerce mWooCommerce = new WooCommerce();
    private EditText searchEditText;
    private ImageButton mArrowButton;
    private ImageView mClearEditText;
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
        if (!isNetworkConnected()) {
            getActivity().finish();
            Log.d("tag", "finished");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchEditText = view.findViewById(R.id.fragment_search_search_edit_text);
        mArrowButton = view.findViewById(R.id.fragment_search_toolbar_arrow);
        mClearEditText=view.findViewById(R.id.fragment_search_clear_text);
        mArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                SearchAsynceTask searchAsynceTask = new SearchAsynceTask();
//                searchAsynceTask.execute(query);
//                SharedPreferencesData.setQuery(getActivity(), query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>1){
                    mClearEditText.setVisibility(View.VISIBLE);

                }else {
                    mClearEditText.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mClearEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEditText.setText("");
            }
        });
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    SearchAsynceTask searchAsynceTask = new SearchAsynceTask();
                    searchAsynceTask.execute(textView.getText().toString());
                    SharedPreferencesData.setQuery(getActivity(), textView.getText().toString());
                    return true;
                }
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
                Intent intent = ListProductsActivity.newIntent(getActivity(), 4);
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
