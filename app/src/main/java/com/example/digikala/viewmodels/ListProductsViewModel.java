package com.example.digikala.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.ProductAttributeData;
import com.example.digikala.model.productsModels.WoocommerceBody;
import com.example.digikala.network.RetrofitInstance;
import com.example.digikala.network.WoocommerceService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Woo.Repository.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static Woo.Repository.Repository.FLICKR_REST_PATH;

public class ListProductsViewModel extends AndroidViewModel {
    public static final String CONSUMER_KEY = "ck_9fc06c2a7292f136b852aceda63740458feb20e1";
    public static final String CONSUMER_SECRET = "cs_5608c8ad5f3ce5b02ac5c629fcad909da759f63a";
    Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);

        }
    };
    WoocommerceService mWoocommerceApi = RetrofitInstance.getInstance(FLICKR_REST_PATH)
            .getRetrofit()
            .create(WoocommerceService.class);
    private MutableLiveData<List<WoocommerceBody>> mSubCategoriesProducts;
    private MutableLiveData<List<WoocommerceBody>> mNewestProducts;
    private MutableLiveData<List<WoocommerceBody>> mPopularProducts;
    private MutableLiveData<List<WoocommerceBody>> mRatedProducts;
    private MutableLiveData<List<WoocommerceBody>> mSortedProducts;
    private MutableLiveData<List<WoocommerceBody>> mFilteredAndSortedProducts;
    private MutableLiveData<List<WoocommerceBody>> mSearchedProducts;
    private Repository mRepository;
    private Context mContext;


    public ListProductsViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
        mRepository = Repository.getInstance();
        mNewestProducts = new MutableLiveData<>();
        mPopularProducts = new MutableLiveData<>();
        mRatedProducts = new MutableLiveData<>();
        mSortedProducts = mRepository.getSortedProducts();
        mSearchedProducts = mRepository.getSearchedProducts();


    }

    public void loadFilteredAndSortedProducts(ProductAttributeData event) {
        mFilteredAndSortedProducts = new MutableLiveData<>();
        try {
            getFilteredAndSortedProducts(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getFilteredAndSortedProducts() {
        return mFilteredAndSortedProducts;
    }

    public void loadNewestProducts(int page) {
        try {
            productRecentPhotosSync(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getNewestProducts() {
        return mNewestProducts;
    }

    public void loadPopularProducts(int page) {
        try {
            productPopularitySync(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getPopularProducts() {
        return mPopularProducts;
    }

    public void loadRatedProducts(int page) {
        try {
            productRatedSync(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void loadSearchedProducts(Map<String, String> queries, int page) {
        mSearchedProducts = new MutableLiveData<>();
        try {
            searchInProducts(queries, page);
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

    public MutableLiveData<List<WoocommerceBody>> searchInProducts(Map<String, String> queries, int page) throws IOException {
        Call<List<WoocommerceBody>> call = Repository.getInstance().getWoocommerceApi().searchProducts(queries, page);
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if (response.isSuccessful()) {
                    mSearchedProducts.setValue(response.body());
                    Log.e("n", response.message() + "successfull" + response.body().size());
                } else {
                    mSearchedProducts.setValue(new ArrayList<WoocommerceBody>());
                    Log.e("n", response.message() + "Notsuccessfull");
                }

            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                mSearchedProducts.setValue(new ArrayList<WoocommerceBody>());
                Log.e("n", t.getMessage() + "failed");
            }
        });
        return mSearchedProducts;
    }

    public void loadSubCategoriesProducts(String categoryId, int page) {
        mSubCategoriesProducts = new MutableLiveData<>();
        try {
            getSubCategoriesProducts(categoryId, page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getSubCategoriesProducts() {
        return mSubCategoriesProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> getSubCategoriesProducts(String categoryId, int page) throws IOException {
        mQueries.put("category", categoryId);
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries, page);

        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if (response.isSuccessful()) {
                    mSubCategoriesProducts.setValue(response.body());
                    Log.d("categoryreqAga", response.body().size() + "succed");
                } else {
                    Log.d("categoryreqAga", String.valueOf(response.body().size()));
                    mSubCategoriesProducts.setValue(new ArrayList<WoocommerceBody>());
                }
                Log.e("TAG4", "On response " + response.message() + "  " + response.code());
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                Log.e("categoryreqAga", "On fail " + t.getMessage());
                mSubCategoriesProducts.setValue(new ArrayList<WoocommerceBody>());
            }
        });

        return mSubCategoriesProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> productRecentPhotosSync(int page) throws IOException {
        mQueries.put("orderby", "date");
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries, page);
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if (response.isSuccessful()) {
                    mNewestProducts.setValue(response.body());
                } else {
                    mNewestProducts = null;
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {

                mNewestProducts = null;
            }
        });
        return mNewestProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> productPopularitySync(int page) throws IOException {
        mQueries.put("orderby", "popularity");
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries, page);
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if (response.isSuccessful()) {

                    mPopularProducts.setValue(response.body());
                } else {
                    mPopularProducts = null;
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                Log.d("fail", t.getMessage());
                mPopularProducts = null;
            }
        });
        return mPopularProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> productRatedSync(int page) throws IOException {
        mQueries.put("orderby", "rating");
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries, page);
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if (response.isSuccessful()) {

                    mRatedProducts.setValue(response.body());
                } else {
                    mRatedProducts = null;
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                Log.d("fail", t.getMessage());
                mRatedProducts = null;
            }
        });
        return mRatedProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> getFilteredAndSortedProducts(ProductAttributeData productAttributeData) throws IOException {
        if (productAttributeData.getOrderby() == null || productAttributeData.getOrderby().equals("")) {
            Log.e("eventaa", "firstIfLoad" + "");
            mQueries.put("order", "desc");
            mQueries.put("search", productAttributeData.getSearch());
            mQueries.put("attribute", productAttributeData.getAttribute());
            mQueries.put("attribute_term", productAttributeData.getAttributeTerm().toString());
        }else
        {
            Log.e("eventaa", "secondIfLoad" + "");
            mQueries.put("orderby",productAttributeData.getOrderby());
            Log.e("eventaaa", productAttributeData.getOrderby() + "");
            mQueries.put("order", productAttributeData.getOrder());
            Log.e("eventaaa", productAttributeData.getOrder() + "");
//            mQueries.put("search", productAttributeData.getSearch());
//            Log.e("eventaaa",  productAttributeData.getSearch() + "");
            if(productAttributeData.getAttribute()!=null&&productAttributeData.getAttributeTerm()!=null) {
                mQueries.put("attribute", productAttributeData.getAttribute());
                Log.e("eventaaa", productAttributeData.getAttribute() + "");
                mQueries.put("attribute_term", productAttributeData.getAttributeTerm().toString());
                Log.e("eventaaa", productAttributeData.getAttributeTerm().toString() + "");
            }
        }
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getsortedAndFilteredProductsList(mQueries,productAttributeData.getPage());
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if (response.isSuccessful()) {
                    Log.e("eventaa", response.body().size() + "");
                    mFilteredAndSortedProducts.setValue(response.body());
                } else {
                    Log.e("eventaa", response.message().toString() + "second");
                    mFilteredAndSortedProducts = null;
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                Log.d("eventaa", t.getMessage());
                mFilteredAndSortedProducts = null;
            }
        });
        return mFilteredAndSortedProducts;
    }
}
