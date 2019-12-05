package Woo.Repository;

import android.util.Log;

import com.example.digikala.model.CategoriesItem;
import com.example.digikala.model.DaoSession;
import com.example.digikala.model.ShoppingBag;
import com.example.digikala.model.ShoppingBagDao;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.model.categoriesmodel.CategoriesBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {
    private static Repository repository;
    private List<WoocommerceBody> mNewestProducts;
    private List<WoocommerceBody> mPopularProducts;
    private List<WoocommerceBody> mRatedProducts;
    private List<WoocommerceBody> mSearchedProducts;
    private List<WoocommerceBody> mAllProducts;
    private List<WoocommerceBody> mRelatedProducts;
    private WoocommerceBody mProductById;
    private List<CategoriesBody> mCategoriesItems;
    private List<CategoriesBody> mFilteredCategoriesItems;
    private List<CategoriesBody> mSubCategoriesItems;
    private List<ShoppingBag> mShoppingBags=new ArrayList<>();
    private DaoSession daoSession;
    private ShoppingBagDao mShoppingBagDao;

    private Repository() {
        daoSession = DBApplication.getInstance().getDaoSession();
        mShoppingBagDao = daoSession.getShoppingBagDao();
    }


    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
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

    public List<WoocommerceBody> getSearchedProducts() {
        return mSearchedProducts;
    }

    public void setSearchedProducts(List<WoocommerceBody> searchedProducts) {
        mSearchedProducts = searchedProducts;
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

    public List<WoocommerceBody> getRelatedProducts() {
        return mRelatedProducts;
    }

    public void setRelatedProducts(List<WoocommerceBody> relatedProducts) {
        mRelatedProducts = null;
        mRelatedProducts = relatedProducts;
    }

    public List<WoocommerceBody> getAllProducts() {
        return mAllProducts;
    }

    public void setAllProducts(List<WoocommerceBody> allProducts) {
        mAllProducts = allProducts;
    }

    public WoocommerceBody getProductById() {
        return mProductById;
    }

    public void setProductById(WoocommerceBody productById) {
        mProductById = null;
        mProductById = productById;

    }

    public List<CategoriesBody> getCategoriesItems() {
        return mCategoriesItems;
    }

    public void setCategoriesItems(List<CategoriesBody> categoriesItems) {
        mCategoriesItems = null;
        mCategoriesItems = categoriesItems;
    }

    public List<WoocommerceBody> getNewestProducts() {
        return mNewestProducts;
    }

    public void setNewestProducts(List<WoocommerceBody> newestProducts) {
        mNewestProducts = null;
        mNewestProducts = newestProducts;

    }

    public List<WoocommerceBody> getPopularProducts() {
        return mPopularProducts;
    }

    public void setPopularProducts(List<WoocommerceBody> popularProducts) {
        mPopularProducts = null;
        mPopularProducts = popularProducts;

    }

    public List<WoocommerceBody> getRatedProducts() {
        return mRatedProducts;
    }

    public void setRatedProducts(List<WoocommerceBody> ratedProducts) {
        mRatedProducts = null;
        mRatedProducts = ratedProducts;

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

        return mSubCategoriesItems;
    }
}
