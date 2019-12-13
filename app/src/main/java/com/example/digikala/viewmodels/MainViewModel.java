package com.example.digikala.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.network.WooCommerce;

import java.io.IOException;
import java.util.List;

import Woo.Repository.Repository;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<List<WoocommerceBody>> mNewestProducts;
    private MutableLiveData<List<WoocommerceBody>> mPopularProducts;
    private MutableLiveData<List<WoocommerceBody>> mRatedProducts;
    private Repository mRepository;
    private WooCommerce mWooCommerce;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
        mNewestProducts = mRepository.getNewestProducts();
        mPopularProducts = mRepository.getPopularProducts();
        mRatedProducts = mRepository.getRatedProducts();
        mWooCommerce = new WooCommerce();
        Log.d("mainviewmodel", "mainViewModel");
    }

    public void loadNewestProducts() {
        try {
            mWooCommerce.productRecentPhotosSync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getNewestProducts() {
        return mNewestProducts;
    }
    public void loadPopularProducts() {
        try {
            mWooCommerce.productPopularitySync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public MutableLiveData<List<WoocommerceBody>> getPopularProducts() {
        return mPopularProducts;
    }
    public void loadRatedProducts() {
        try {
            mWooCommerce.productRatedSync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public MutableLiveData<List<WoocommerceBody>> getRatedProducts() {
        return mRatedProducts;
    }
}
