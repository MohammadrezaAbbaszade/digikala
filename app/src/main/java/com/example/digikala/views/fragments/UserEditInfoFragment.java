package com.example.digikala.views.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.customermodels.Billing;
import com.example.digikala.model.customermodels.CustomerBody;
import com.example.digikala.viewmodels.CustomerInfoViewModel;
import com.example.digikala.views.activities.CustomerInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserEditInfoFragment extends Fragment {
    private ImageView mBackToolbarArrow;
    private RelativeLayout mSubmitInfoButton;
    private CustomerInfoViewModel mCustomerInfoViewModel;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPhoneNumber;

    public static UserEditInfoFragment newInstance() {

        Bundle args = new Bundle();

        UserEditInfoFragment fragment = new UserEditInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public UserEditInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerInfoViewModel = ViewModelProviders.of(this).get(CustomerInfoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_edit_info, container, false);
        init(view);
        mBackToolbarArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        mSubmitInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mFirstName.getText().toString().isEmpty()&&!mLastName.getText().toString().isEmpty()
                        &&!mPhoneNumber.getText().toString().isEmpty()) {
                    createNewCustomerObject();
                }else
                {
                    Toast.makeText(getActivity(),"لطفاتمامی فیلدهارا پر کنید",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void init(View view) {
        mBackToolbarArrow = view.findViewById(R.id.back_toolbar);
        mSubmitInfoButton = view.findViewById(R.id.updateCustomer);
        mFirstName = view.findViewById(R.id.first_name_edt);
        mLastName = view.findViewById(R.id.last_name_edt);
        mPhoneNumber = view.findViewById(R.id.phone_number_edt);
    }

    private void createNewCustomerObject() {
        CustomerBody customerBody = new CustomerBody();
        customerBody.setFirstName(mFirstName.getText().toString());
        customerBody.setLastName(mLastName.getText().toString());
        customerBody.setEmail(SharedPreferencesData.getCustomerEmail(getActivity()));
        customerBody.setId(SharedPreferencesData.getCustomerId(getActivity()));
        customerBody.setBilling(new Billing(mLastName.getText().toString(), mFirstName.getText().toString(),
                SharedPreferencesData.getCustomerEmail(getActivity()), mPhoneNumber.getText().toString()));
        mCustomerInfoViewModel.updateCustomer(customerBody).observe(this, new Observer<CustomerBody>() {
            @Override
            public void onChanged(CustomerBody customerBody) {
                if (customerBody !=null){
                    Toast.makeText(getActivity(), getActivity().getResources()
                                    .getString(R.string.update_successfully)
                            , Toast.LENGTH_LONG).show();
                    SharedPreferencesData.setCustomerEmail(getContext(), customerBody.getEmail());
                    SharedPreferencesData.setCustomertId(getContext(), customerBody.getId());
                    SharedPreferencesData.setCustomerName(getContext(), customerBody.getFirstName()+" "+customerBody.getLastName());
                    getActivity().getSupportFragmentManager().popBackStack();
                }else {
                    Toast.makeText(getActivity() , getActivity().getResources().getString(R.string.problem_occur) ,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
