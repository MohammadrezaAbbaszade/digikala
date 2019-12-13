package com.example.digikala.views;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.digikala.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoNetworkFragment extends Fragment {
    private  changeFragment mChangeFragment;
    private Button mTryNetworkButton;
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

//        if(isNetworkConnected())
//        {
//            Intent intent = SplashActivity.newIntent(getActivity());
//            startActivity(intent);
//            getActivity().finish();
//        }else
//        {
//            Toast.makeText(getContext(),"خطا در برقراری ارتباط",Toast.LENGTH_LONG).show();
//        }
        Log.d("tag","onResumeN");
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
        View view= inflater.inflate(R.layout.fragment_no_network, container, false);
        mTryNetworkButton=view.findViewById(R.id.no_network_button);


        mTryNetworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 if(isNetworkConnected())
                {
                    Intent intent = SplashActivity.newIntent(getActivity());
                    startActivity(intent);
                   getActivity().finish();
                }else
                {
                    Toast.makeText(getContext(),"خطا در برقراری ارتباط",Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
