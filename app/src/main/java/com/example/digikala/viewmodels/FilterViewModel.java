package com.example.digikala.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.ProductAttributeData;
import com.example.digikala.model.attributesmodels.AttributeBody;
import com.example.digikala.model.attributetermsmodels.AttributeTermsBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Woo.Repository.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterViewModel extends AndroidViewModel {
    public static final String FLICKR_REST_PATH = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_9fc06c2a7292f136b852aceda63740458feb20e1";
    public static final String CONSUMER_SECRET = "cs_5608c8ad5f3ce5b02ac5c629fcad909da759f63a";
    private Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);

        }
    };
    private Repository mRepository;
    private MutableLiveData<List<AttributeBody>> mAttributes;
    private MutableLiveData<List<AttributeTermsBody>> mColorsAttributeTerms;
    private MutableLiveData<List<AttributeTermsBody>> mSizessAttributeTerms;

    public FilterViewModel(@NonNull Application application) {
        super(application);
        mAttributes = new MutableLiveData<>();
        mColorsAttributeTerms = new MutableLiveData<>();
        mSizessAttributeTerms = new MutableLiveData<>();
        mRepository = Repository.getInstance();
    }

    public void loadAttributes() {
        try {
            getAttributesAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<AttributeBody>> getAttributes() {
        return mAttributes;
    }

    public void loadColorsAttributeTerms(int id) {
        try {
            getColorAttributeTermsAsync(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSizessAttributeTerms(int id) {
        try {
            getSizesAttributeTermsAsync(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<AttributeTermsBody>> getColorsAttributeTerms() {
        return mColorsAttributeTerms;
    }

    public MutableLiveData<List<AttributeTermsBody>> getSizessAttributeTerms() {
        return mSizessAttributeTerms;
    }

    public MutableLiveData<List<AttributeBody>> getAttributesAsync() throws IOException {
        Call<List<AttributeBody>> call = mRepository.getWoocommerceApi().getProductAttributes(mRepository.getQueries());
        call.enqueue(new Callback<List<AttributeBody>>() {
            @Override
            public void onResponse(Call<List<AttributeBody>> call, Response<List<AttributeBody>> response) {
                if (response.isSuccessful()) {
                    mAttributes.setValue(response.body());
                    Log.d("attribute", response.message() + "Suc");
                } else {
                    mAttributes.setValue(new ArrayList<AttributeBody>());
                }
            }

            @Override
            public void onFailure(Call<List<AttributeBody>> call, Throwable t) {
                Log.d("attribute", t.getMessage() + "notS");
                mAttributes.setValue(new ArrayList<AttributeBody>());
            }
        });
        return mAttributes;
    }

    public MutableLiveData<List<AttributeTermsBody>> getColorAttributeTermsAsync(int id) throws IOException {
        Call<List<AttributeTermsBody>> call = mRepository.getWoocommerceApi().getAttributesTerms(id, mQueries);
        call.enqueue(new Callback<List<AttributeTermsBody>>() {
            @Override
            public void onResponse(Call<List<AttributeTermsBody>> call, Response<List<AttributeTermsBody>> response) {
                if (response.isSuccessful()) {
                    mColorsAttributeTerms.postValue(response.body());
                    Log.d("attribute", response.message());
                } else {
                    mColorsAttributeTerms.setValue(new ArrayList<AttributeTermsBody>());
                    Log.d("attribute", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<AttributeTermsBody>> call, Throwable t) {
                Log.d("attribute", t.getMessage().toString());
                mColorsAttributeTerms.setValue(new ArrayList<AttributeTermsBody>());
            }
        });
        return mColorsAttributeTerms;
    }

    public MutableLiveData<List<AttributeTermsBody>> getSizesAttributeTermsAsync(int id) throws IOException {
        Call<List<AttributeTermsBody>> call = mRepository.getWoocommerceApi().getAttributesTerms(id, mQueries);
        call.enqueue(new Callback<List<AttributeTermsBody>>() {
            @Override
            public void onResponse(Call<List<AttributeTermsBody>> call, Response<List<AttributeTermsBody>> response) {
                if (response.isSuccessful()) {
                    mSizessAttributeTerms.postValue(response.body());
                    Log.d("attribute", response.message());
                } else {
                    mSizessAttributeTerms.setValue(new ArrayList<AttributeTermsBody>());
                    Log.d("attribute", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<AttributeTermsBody>> call, Throwable t) {
                Log.d("attribute", t.getMessage().toString());
                mSizessAttributeTerms.setValue(new ArrayList<AttributeTermsBody>());
            }
        });
        return mSizessAttributeTerms;
    }
}
