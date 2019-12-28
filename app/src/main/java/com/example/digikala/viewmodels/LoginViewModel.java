package com.example.digikala.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.customermodels.CustomerBody;
import com.example.digikala.model.ordersModels.OrderBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Woo.Repository.Repository;


public class LoginViewModel extends AndroidViewModel {
    public static final String FLICKR_REST_PATH = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_9fc06c2a7292f136b852aceda63740458feb20e1";
    public static final String CONSUMER_SECRET = "cs_5608c8ad5f3ce5b02ac5c629fcad909da759f63a";
    private Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);

        }
    };
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
        return mRepository.getCustomer(mQueries,email);
    }
}
