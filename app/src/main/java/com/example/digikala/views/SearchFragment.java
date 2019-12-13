package com.example.digikala.views;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.network.WooCommerce;
import com.example.digikala.viewmodels.SearchViewModel;

import java.io.IOException;
import java.util.List;

import Woo.Repository.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private WooCommerce mWooCommerce = new WooCommerce();
    private EditText searchEditText;
    private ImageButton mArrowButton;
    private ImageView mClearEditText;
    private SearchViewModel mSearchViewModel;
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
        }else
        {
            mSearchViewModel= ViewModelProviders.of(this).get(SearchViewModel.class);
        }
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
        mSearchViewModel.getSearchedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if(woocommerceBodies!=null)
                {
                    Intent intent = ListProductsActivity.newIntent(getActivity(), 4);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(getActivity(), "موردی یافت نشد", Toast.LENGTH_LONG).show();
                }
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
                   mSearchViewModel.loadSearchedProducts(textView.toString());
                    SharedPreferencesData.setQuery(getActivity(), textView.getText().toString());
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
