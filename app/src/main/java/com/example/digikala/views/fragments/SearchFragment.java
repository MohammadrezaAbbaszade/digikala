package com.example.digikala.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.databinding.FragmentSearchBinding;
import com.example.digikala.viewmodels.SearchViewModel;
import com.example.digikala.views.activities.ListProductsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private FragmentSearchBinding mFragmentSearchBinding;
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
        mFragmentSearchBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
       mFragmentSearchBinding.fragmentSearchToolbarArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mFragmentSearchBinding.fragmentSearchSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>1){
                    mFragmentSearchBinding.fragmentSearchClearText.setVisibility(View.VISIBLE);

                }else {
                    mFragmentSearchBinding.fragmentSearchClearText.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mFragmentSearchBinding.fragmentSearchClearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentSearchBinding.fragmentSearchSearchEditText.setText("");
            }
        });
        mFragmentSearchBinding.fragmentSearchSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = ListProductsActivity.newIntent(getActivity(), 4);
                    startActivity(intent);
                    SharedPreferencesData.setQuery(getActivity(), textView.getText().toString());
                    return true;
                }
                return false;
            }
        });
        return mFragmentSearchBinding.getRoot();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
