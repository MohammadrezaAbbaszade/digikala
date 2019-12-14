package com.example.digikala.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.WoocommerceBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import Woo.Repository.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductsViewModel extends AndroidViewModel {
    private MutableLiveData<List<WoocommerceBody>> mNewestProducts;
    private MutableLiveData<List<WoocommerceBody>> mPopularProducts;
    private MutableLiveData<List<WoocommerceBody>> mRatedProducts;
    private MutableLiveData<List<WoocommerceBody>> mSortedProducts;
    private MutableLiveData<List<WoocommerceBody>> mSearchedProducts;
    private Repository mRepository;
    private Context mContext;


    public ListProductsViewModel(@NonNull Application application) {
        super(application);
        mContext = application;
        mRepository = Repository.getInstance();
        mNewestProducts = mRepository.getNewestProducts();
        mPopularProducts = mRepository.getPopularProducts();
        mRatedProducts = mRepository.getRatedProducts();
        mSortedProducts = mRepository.getSortedProducts();
        mSearchedProducts = mRepository.getSearchedProducts();

    }


    public MutableLiveData<List<WoocommerceBody>> getNewestProducts() {
        return mNewestProducts;
    }


    public MutableLiveData<List<WoocommerceBody>> getPopularProducts() {
        return mPopularProducts;
    }


    public MutableLiveData<List<WoocommerceBody>> getRatedProducts() {
        return mRatedProducts;
    }

    public void loadSortedProducts(Map<String, String> queries) {
        mSortedProducts = new MutableLiveData<>();
        try {
            getSortedBaseProducts(queries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getSortedProducts() {
        return mSortedProducts;
    }

    public void loadSearchedProducts() {
        mSearchedProducts = new MutableLiveData<>();
        try {
            searchInProducts(SharedPreferencesData.getQuery(mContext));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getSearchedProducts() {
        return mSearchedProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> getSortedBaseProducts(Map<String, String> queries) throws IOException {
        Call<List<WoocommerceBody>> call = Repository.getInstance().getWoocommerceApi().getSortedBaseProducts(queries);

        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if (response.isSuccessful()) {
                    response.body().remove(0);
                    mSortedProducts.setValue(response.body());

                } else {
                    mSortedProducts = null;
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                mSortedProducts = null;
            }
        });


        return mSortedProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> searchInProducts(String searchQuery) throws IOException {
        Call<List<WoocommerceBody>> call = Repository.getInstance().getWoocommerceApi().searchProducts(searchQuery, Repository.getInstance().getQueries());
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if (response.isSuccessful()) {
                    mSearchedProducts.setValue(response.body());
                } else {
                    mSearchedProducts = null;
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                mSearchedProducts = null;
            }
        });
        return mSearchedProducts;
    }
}
