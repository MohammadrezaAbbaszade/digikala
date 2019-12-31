package com.example.digikala.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.digikala.model.customermodels.CustomerBody;

import java.util.HashMap;
import java.util.Map;

import Woo.Repository.Repository;

public class CustomerInfoViewModel extends AndroidViewModel {
    public static final String CONSUMER_KEY = "ck_9fc06c2a7292f136b852aceda63740458feb20e1";
    public static final String CONSUMER_SECRET = "cs_5608c8ad5f3ce5b02ac5c629fcad909da759f63a";
    private Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);

        }
    };
    private Repository mRepository;
    public CustomerInfoViewModel(@NonNull Application application) {
        super(application);
        mRepository=Repository.getInstance();
    }
    public LiveData<CustomerBody> updateCustomer(CustomerBody customerBody){
        return mRepository.updateCustomer(mQueries,customerBody);
    }
}
