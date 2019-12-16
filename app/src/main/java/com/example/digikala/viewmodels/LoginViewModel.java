package com.example.digikala.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.ordersModels.OrderBody;

import java.io.IOException;

import Woo.Repository.Repository;


public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<OrderBody> mOrderBodyies;
    private Repository mRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
        mOrderBodyies = mRepository.getOrderBody();
        mOrderBodyies=mRepository.getOrderBody();
    }

    public void setOrder(OrderBody order) {
        try {
            mRepository.getOrderResult(order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<OrderBody> getOrderBodyies() {
        return mOrderBodyies;
    }
}
