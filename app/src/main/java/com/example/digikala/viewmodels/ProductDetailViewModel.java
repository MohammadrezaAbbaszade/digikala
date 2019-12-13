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

public class ProductDetailViewModel extends AndroidViewModel {

    private Repository mRepository;
    private MutableLiveData<WoocommerceBody> mProductById;
    private MutableLiveData<List<WoocommerceBody>> mRelatedProducts;
    private WooCommerce mWooCommerce;

    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
        mProductById = mRepository.getProductById();
        mRelatedProducts = mRepository.getRelatedProducts();
        mWooCommerce = new WooCommerce();
    }

    public void addToBag(int id) {
        mRepository.addBag(id);
    }

    public void loadSingleProduct(int id) {
        try {
            mWooCommerce.getProductById(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<WoocommerceBody> getProductById() {
        return mProductById;
    }

    public void loadRelatedProducts(List<Integer> integers) {
        try {
            mWooCommerce.getRelatedProducts(integers.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getRelatedProducts() {
        return mRelatedProducts;
    }
}
