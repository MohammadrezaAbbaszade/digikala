package com.example.digikala.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.productsModels.WoocommerceBody;
import com.example.digikala.model.categoriesmodel.CategoriesBody;

import java.io.IOException;
import java.util.List;

import Woo.Repository.Repository;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<List<WoocommerceBody>> mNewestProducts;
    private MutableLiveData<List<WoocommerceBody>> mPopularProducts;
    private MutableLiveData<List<WoocommerceBody>> mRatedProducts;
    private MutableLiveData<List<WoocommerceBody>> mSpecialProducts;
    private List<CategoriesBody>  mFilteredCategoriesItems;
    private Repository mRepository;


    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
        mNewestProducts = mRepository.getNewestProducts();
        mPopularProducts = mRepository.getPopularProducts();
        mRatedProducts = mRepository.getRatedProducts();
        mSpecialProducts=mRepository.getSpecialProducts();

        Log.d("mainviewmodel", "mainViewModel");
    }

    public void loadNewestProducts(int page) {
        try {
            mRepository.productRecentPhotosSync(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CategoriesBody> getFilteredCategoriesItems() {
        return mRepository.getFilteredCategoriesItems();
    }

    public MutableLiveData<List<WoocommerceBody>> getNewestProducts() {
        return mNewestProducts;
    }
    public void loadPopularProducts(int page) {
        try {
            mRepository.productPopularitySync(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public MutableLiveData<List<WoocommerceBody>> getPopularProducts() {
        return mPopularProducts;
    }
    public void loadRatedProducts(int page) {
        try {
            mRepository.productRatedSync(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getRatedProducts() {
        return mRatedProducts;
    }
    public void loadSpecialProducts() {
        try {
            mRepository.getSpecialProductsAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getSpecialProducts() {
        return mSpecialProducts;
    }
}
