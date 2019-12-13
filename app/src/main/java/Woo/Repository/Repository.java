package Woo.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.digikala.model.DaoSession;
import com.example.digikala.model.ShoppingBag;
import com.example.digikala.model.ShoppingBagDao;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.model.categoriesmodel.CategoriesBody;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository repository;
    private MutableLiveData<List<WoocommerceBody>> mNewestProducts;
    private MutableLiveData<List<WoocommerceBody>> mPopularProducts;
    private MutableLiveData<List<WoocommerceBody>> mRatedProducts;
    private MutableLiveData<List<WoocommerceBody>> mSearchedProducts;
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
        daoSession = DBApplication.getInstance().getDaoSession();
        mShoppingBagDao = daoSession.getShoppingBagDao();
    }


    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
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
}
