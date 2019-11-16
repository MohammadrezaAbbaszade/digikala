package com.example.digikala.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digikala.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoNetworkFragment extends Fragment {
    private  changeFragment mChangeFragment;
    public static NoNetworkFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NoNetworkFragment fragment = new NoNetworkFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public NoNetworkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNetworkConnected()) {
            mChangeFragment.changeFragment(true);
        }
        Log.d("tag","onResume");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof changeFragment) {
            mChangeFragment = (changeFragment) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mChangeFragment = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_network, container, false);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
