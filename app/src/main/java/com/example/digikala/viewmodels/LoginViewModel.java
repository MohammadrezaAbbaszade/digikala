package com.example.digikala.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.customermodels.CustomerBody;
import com.example.digikala.model.ordersModels.OrderBody;

import java.io.IOException;
import java.util.List;

import Woo.Repository.Repository;


public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<CustomerBody> mCustomerBody;
    private Repository mRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
    }
    public MutableLiveData<CustomerBody> registerCustomer(CustomerBody customerBody) {
        return mRepository.registerCustomer(customerBody);
    }
    public MutableLiveData<List<CustomerBody>> getCustomer(String email) {
        return mRepository.getCustomer(email);
    }
}
