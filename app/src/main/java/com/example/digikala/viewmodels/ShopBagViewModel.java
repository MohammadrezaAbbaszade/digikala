package com.example.digikala.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.WoocommerceBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import Woo.Repository.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopBagViewModel extends AndroidViewModel {

    private MutableLiveData<List<WoocommerceBody>> mRelatedProducts;
    private Repository mRepository;


    public ShopBagViewModel(@NonNull Application application) {
        super(application);
        mRepository=Repository.getInstance();
        mRelatedProducts=new MutableLiveData<>();
    }

public void loadRelatedProducts(List<String> ids)
{

    try {
       getRelatedProducts(ids.toString());
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public MutableLiveData<List<WoocommerceBody>> getRelatedProducts() {
        return mRelatedProducts;
    }
    public MutableLiveData<List<WoocommerceBody>> getRelatedProducts(String...id) throws IOException {
        Call<List<WoocommerceBody>> call = Repository.getInstance().getWoocommerceApi().getReleatedProducts(Repository.getInstance().getQueries(),id);
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if(response.isSuccessful())
                {
                    mRelatedProducts.setValue(response.body());

                }else
                {
                    mRelatedProducts=null;
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                mRelatedProducts=null;
            }
        });
        return mRelatedProducts;
    }
}
