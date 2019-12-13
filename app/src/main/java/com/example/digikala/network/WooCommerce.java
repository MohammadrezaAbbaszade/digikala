package com.example.digikala.network;

import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.model.categoriesmodel.CategoriesBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Woo.Repository.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WooCommerce {
    public static final String TAG = "FlickrFetcher";
    public static final String FLICKR_REST_PATH = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_7c028a04c9faf616410b09e2ab90b1884c875d01";
    public static final String CONSUMER_SECRET = "cs_8c39f626780f01d135719f64214fd092b848f4aa";
    private Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);

        }
    };

    public Map<String, String> getQueries() {
        return mQueries;
    }

    private WoocommerceService mWoocommerceApi = RetrofitInstance.getInstance(FLICKR_REST_PATH)
            .getRetrofit()
            .create(WoocommerceService.class);

    public WoocommerceService getWoocommerceApi() {
        return mWoocommerceApi;
    }

    public MutableLiveData<List<WoocommerceBody>> productRecentPhotosSync() throws IOException {
        mQueries.put("orderby", "date");
         Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries);
         call.enqueue(new Callback<List<WoocommerceBody>>() {
             @Override
             public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                 if(response.isSuccessful())
                 {
                     Repository.getInstance().getNewestProducts().setValue(response.body());
                 }else
                 {
                     Repository.getInstance().setNewestProducts(null);
                 }
             }

             @Override
             public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                 Repository.getInstance().setNewestProducts(null);
             }
         });
        return Repository.getInstance().getNewestProducts();
    }

    public MutableLiveData<List<WoocommerceBody>> productPopularitySync() throws IOException {
        mQueries.put("orderby", "popularity");
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries);
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if(response.isSuccessful())
                {
                    Repository.getInstance().getPopularProducts().setValue(response.body());
                }else
                {
                    Repository.getInstance().setPopularProducts(null);
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                Repository.getInstance().setPopularProducts(null);
            }
        });
        return Repository.getInstance().getPopularProducts();
    }

    public MutableLiveData<List<WoocommerceBody>> productRatedSync() throws IOException {
        mQueries.put("orderby", "rating");
//        mQueries.put("page", "2");
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries);
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if(response.isSuccessful())
                {
                    Repository.getInstance().getRatedProducts().setValue(response.body());
                }else
                {
                    Repository.getInstance().setRatedProducts(null);
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                Repository.getInstance().setRatedProducts(null);
            }
        });
        return  Repository.getInstance().getRatedProducts();
    }
    public MutableLiveData<List<WoocommerceBody>> searchInProducts(String searchQuery) throws IOException {
        Call<List<WoocommerceBody>> call = mWoocommerceApi.searchProducts(searchQuery,mQueries);
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if(response.isSuccessful())
                {
                    Repository.getInstance().getSearchedProducts().setValue(response.body());
                }else
                {
                    Repository.getInstance().setSearchedProducts(null);
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                Repository.getInstance().setSearchedProducts(null);
            }
        });
        return  Repository.getInstance().getSearchedProducts();
    }

    public List<CategoriesBody> productCategoriesSync() throws IOException {

//        mQueries.put("page", "2");
        Call<List<CategoriesBody>> call = mWoocommerceApi.getCategories();
        return call.execute().body();
    }
    public MutableLiveData<WoocommerceBody> getProductById(int id) throws IOException {
        Call<WoocommerceBody> call = mWoocommerceApi.getProductById(String.valueOf(id),mQueries);
        call.enqueue(new Callback<WoocommerceBody>() {
            @Override
            public void onResponse(Call<WoocommerceBody> call, Response<WoocommerceBody> response) {
                if(response.isSuccessful())
                {
                    Repository.getInstance().getProductById().setValue(response.body());
                }else
                {
                   Repository.getInstance().setProductById(null);
                }
            }

            @Override
            public void onFailure(Call<WoocommerceBody> call, Throwable t) {
                Repository.getInstance().setProductById(null);
            }
        });
        return Repository.getInstance().getProductById();
    }
    public MutableLiveData<List<WoocommerceBody>> getRelatedProducts(String...id) throws IOException {
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getReleatedProducts(mQueries,id);
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if(response.isSuccessful())
                {
                    Repository.getInstance().getRelatedProducts().setValue(response.body());

                }else
                {
                    Repository.getInstance().setRelatedProducts(null);
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                Repository.getInstance().setRelatedProducts(null);
            }
        });
        return Repository.getInstance().getRelatedProducts();
    }
}
