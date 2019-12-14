package Woo.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.DaoSession;
import com.example.digikala.model.ShoppingBag;
import com.example.digikala.model.ShoppingBagDao;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.model.categoriesmodel.CategoriesBody;
import com.example.digikala.network.RetrofitInstance;
import com.example.digikala.network.WoocommerceService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository repository;
    public static final String TAG = "FlickrFetcher";
    public static final String FLICKR_REST_PATH = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = " ck_7c028a04c9faf616410b09e2ab90b1884c875d01";
    public static final String CONSUMER_SECRET = "cs_8c39f626780f01d135719f64214fd092b848f4aa";
    private Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);

        }
    };

    private MutableLiveData<List<WoocommerceBody>> mNewestProducts;
    private MutableLiveData<List<WoocommerceBody>> mPopularProducts;
    private MutableLiveData<List<WoocommerceBody>> mRatedProducts;
    private MutableLiveData<List<WoocommerceBody>> mSearchedProducts;
    private MutableLiveData<List<WoocommerceBody>> mSortedProducts;
    private MutableLiveData<List<WoocommerceBody>> mAllProducts;
    private MutableLiveData<List<WoocommerceBody>> mRelatedProducts;
    private MutableLiveData<WoocommerceBody> mProductById;
    private List<CategoriesBody> mCategoriesItems;
    private List<CategoriesBody> mFilteredCategoriesItems;
    private List<CategoriesBody> mSubCategoriesItems;
    private List<ShoppingBag> mShoppingBags=new ArrayList<>();
    private DaoSession daoSession;
    private ShoppingBagDao mShoppingBagDao;

    private Repository() {
        mNewestProducts=new MutableLiveData<>();
        mPopularProducts=new MutableLiveData<>();
        mRatedProducts=new MutableLiveData<>();
        mSearchedProducts=new MutableLiveData<>();
        mAllProducts=new MutableLiveData<>();
        mRelatedProducts=new MutableLiveData<>();
        mProductById=new MutableLiveData<>();
        mSortedProducts=new MutableLiveData<>();
        daoSession = DBApplication.getInstance().getDaoSession();
        mShoppingBagDao = daoSession.getShoppingBagDao();
    }


    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    public MutableLiveData<List<WoocommerceBody>> getSortedProducts() {
        return mSortedProducts;
    }

    public void setSortedProducts(MutableLiveData<List<WoocommerceBody>> sortedProducts) {
        mSortedProducts = sortedProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> getNewestProducts() {
        return mNewestProducts;
    }

    public void setNewestProducts(MutableLiveData<List<WoocommerceBody>> newestProducts) {
        mNewestProducts = newestProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> getPopularProducts() {
        return mPopularProducts;
    }

    public void setPopularProducts(MutableLiveData<List<WoocommerceBody>> popularProducts) {
        mPopularProducts = popularProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> getRatedProducts() {
        return mRatedProducts;
    }

    public void setRatedProducts(MutableLiveData<List<WoocommerceBody>> ratedProducts) {
        mRatedProducts = ratedProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> getSearchedProducts() {
        return mSearchedProducts;
    }

    public void setSearchedProducts(MutableLiveData<List<WoocommerceBody>> searchedProducts) {
        mSearchedProducts = searchedProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> getAllProducts() {
        return mAllProducts;
    }

    public void setAllProducts(MutableLiveData<List<WoocommerceBody>> allProducts) {
        mAllProducts = allProducts;
    }

    public MutableLiveData<List<WoocommerceBody>> getRelatedProducts() {
        return mRelatedProducts;
    }

    public void setRelatedProducts(MutableLiveData<List<WoocommerceBody>> relatedProducts) {
        mRelatedProducts = relatedProducts;
    }

    public MutableLiveData<WoocommerceBody> getProductById() {
        return mProductById;
    }

    public void setProductById(MutableLiveData<WoocommerceBody> productById) {
        mProductById = productById;
    }

    public List<CategoriesBody> getFilteredCategoriesItems() {
        mFilteredCategoriesItems=new ArrayList<>();
        for(CategoriesBody categoriesBody:mCategoriesItems)
        {
            if(categoriesBody.getParent()==0)
                mFilteredCategoriesItems.add(categoriesBody);
        }
        return mFilteredCategoriesItems;
    }

    public List<ShoppingBag> getShoppingBags() {
        return mShoppingBagDao.loadAll();
    }

    public void addBag(int id) {
        List<ShoppingBag> checkList=mShoppingBagDao
        .queryBuilder()
                .where(ShoppingBagDao.Properties.MProductId.eq(Integer.toString(id)))
                .list();
        Log.d("tag",checkList.toString());
        if (checkList != null && checkList.size() > 0)
            return;

            Log.d("tag","addbag"+"null");
            ShoppingBag shoppingBag=new ShoppingBag();
            shoppingBag.setMProductId(Integer.toString(id));
            mShoppingBagDao.insert(shoppingBag);

    }

    public void deleteBag(String id) {
        List<ShoppingBag> result = mShoppingBagDao.queryBuilder()
                .where(ShoppingBagDao.Properties.MProductId.eq(id))
                .list();
        ShoppingBag bag = result.get(0);
        mShoppingBagDao.delete(bag);
    }

    public ShoppingBag getBag(String id) {
        return mShoppingBagDao.queryBuilder()
                .where(ShoppingBagDao.Properties.MProductId.eq(id))
                .unique();
    }

    public List<String> getBagsIds() {
        List<ShoppingBag> checkList=mShoppingBagDao
               .loadAll();
        List<String> mIds = new ArrayList<>();
        for (ShoppingBag shoppingBag :checkList) {
            mIds.add(shoppingBag.getMProductId());
        }
        return mIds;
    }
    public boolean isRepositoryNull() {
        if (mRatedProducts == null && mPopularProducts == null && mNewestProducts == null && mCategoriesItems == null)
            return true;

        return false;
    }
    public int getPosition(int id) {
        for (int i = 0; i < mFilteredCategoriesItems.size(); i++) {
            if (mFilteredCategoriesItems.get(i).getId()==id)
                return i;
        }

        return 0;
    }
    public List<CategoriesBody> getSubCategory(int id) {
        mSubCategoriesItems=new ArrayList<>();
        for (int i = 0; i < mCategoriesItems.size(); i++) {
            if (mCategoriesItems.get(i).getParent() == id) {
                mSubCategoriesItems.add(mCategoriesItems.get(i));
            }
        }
Log.d("sub",mSubCategoriesItems.size()+"");
        return mSubCategoriesItems;
    }
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
                    response.body().remove(0);
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
                    response.body().remove(0);
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
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getWooCommerceBody(mQueries);
        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if(response.isSuccessful())
                {
                    response.body().remove(0);
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
    public MutableLiveData<List<WoocommerceBody>>  getSortedBaseProducts(Map<String, String> queries)throws IOException
    {
        Call<List<WoocommerceBody>> call = mWoocommerceApi.getSortedBaseProducts(queries);

        call.enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if(response.isSuccessful())
                {
                    response.body().remove(0);
                    Repository.getInstance().getSortedProducts().setValue(response.body());

                }else
                {
                    Repository.getInstance().setSortedProducts(null);
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
                Repository.getInstance().setSortedProducts(null);
            }
        });


        return Repository.getInstance().getSortedProducts();
    }
}
