package com.example.digikala.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.network.RetrofitInstance;
import com.example.digikala.network.WoocommerceService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Woo.Repository.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static Woo.Repository.Repository.FLICKR_REST_PATH;

public class ProductDetailViewModel extends AndroidViewModel {

    private Repository mRepository;
    private MutableLiveData<WoocommerceBody> mProductById;
    private MutableLiveData<List<WoocommerceBody>> mRelatedProducts;


    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
        mProductById  =new MutableLiveData<>();
        mRelatedProducts = mRepository.getRelatedProducts();

    }

    public void addToBag(int id) {
        mRepository.addBag(id);
    }

    public void loadSingleProduct(int id) {
        try {
            getProductById(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<WoocommerceBody> getProductById(int id) throws IOException {
        final String CONSUMER_KEY = " ck_7c028a04c9faf616410b09e2ab90b1884c875d01";
        final String CONSUMER_SECRET = "cs_8c39f626780f01d135719f64214fd092b848f4aa";
        Map<String, String> mQueries = new HashMap<String, String>() {
            {
                put("consumer_key", CONSUMER_KEY);
                put("consumer_secret", CONSUMER_SECRET);

            }
        };
        WoocommerceService mWoocommerceApi = RetrofitInstance.getInstance(FLICKR_REST_PATH)
                .getRetrofit()
                .create(WoocommerceService.class);
        Call<WoocommerceBody> call = mWoocommerceApi.getProductById(String.valueOf(id),mQueries);
        call.enqueue(new Callback<WoocommerceBody>() {
            @Override
            public void onResponse(Call<WoocommerceBody> call, Response<WoocommerceBody> response) {
                if(response.isSuccessful())
                {
                    mProductById.setValue(response.body());
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

    public MutableLiveData<WoocommerceBody> getProductById() {
        return mProductById;
    }

    public void loadRelatedProducts(List<Integer> integers) {
        try {
            mRepository.getRelatedProducts(integers.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<WoocommerceBody>> getRelatedProducts() {
        return mRelatedProducts;
    }
}
