package com.example.digikala.views.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.views.activities.CustomerInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerInfoFragment extends Fragment {
    private ImageView mBackArrowToolbar;
    private LinearLayout mEditCustomerInfo;
    private LinearLayout mCustomerOrdersInfo;
    private LinearLayout mCustomerAdress;
    private LinearLayout mLogOut;

    public static CustomerInfoFragment newInstance() {

        Bundle args = new Bundle();

        CustomerInfoFragment fragment = new CustomerInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CustomerInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_info, container, false);
        init(view);
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesData.setCustomerLogedIn(getActivity(), false);
                getActivity().finish();
            }
        });
        mBackArrowToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mEditCustomerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.customer_info_container, UserEditInfoFragment.newInstance())
                        .addToBackStack(null).commit();
            }
        });
        return view;
    }

    private void init(View view) {
        mBackArrowToolbar = view.findViewById(R.id.back_toolbar);
        mEditCustomerInfo = view.findViewById(R.id.edit_customer_info);
        mCustomerOrdersInfo = view.findViewById(R.id.customer_orders);
        mCustomerAdress = view.findViewById(R.id.customer_user_address);
        mLogOut = view.findViewById(R.id.customer_log_out);
    }

}
