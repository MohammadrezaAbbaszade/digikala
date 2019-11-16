package com.example.digikala.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digikala.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoNetworkFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_network, container, false);
    }

}
