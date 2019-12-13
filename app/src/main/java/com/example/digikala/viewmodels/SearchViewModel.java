package com.example.digikala.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.network.WooCommerce;

import java.io.IOException;
import java.util.List;

import Woo.Repository.Repository;

public class SearchViewModel extends AndroidViewModel {
    private Repository mRepository;
    private WooCommerce mWooCommerce;
    private MutableLiveData<List<WoocommerceBody>> mSearchedProducts;


    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
        mSearchedProducts = mRepository.getSearchedProducts();
        mWooCommerce = new WooCommerce();

    }

    public void loadSearchedProducts(String queries) {
        try {
            mWooCommerce.searchInProducts(queries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getSearchedProducts() {
        return mSearchedProducts;
    }
}
