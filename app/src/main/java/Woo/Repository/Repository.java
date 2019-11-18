package Woo.Repository;

import com.example.digikala.model.CategoriesItem;
import com.example.digikala.model.WoocommerceBody;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository repository;
    private List<WoocommerceBody> mNewestProducts;
    private List<WoocommerceBody> mPopularProducts;
    private List<WoocommerceBody> mRatedProducts;
    private List<WoocommerceBody> mAllProducts;
    private List<WoocommerceBody> mRelatedProducts;
    private WoocommerceBody mProductById;
    private List<CategoriesItem> mCategoriesItems;
    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    public List<WoocommerceBody> getRelatedProducts() {
        return mRelatedProducts;
    }

    public void setRelatedProducts(List<WoocommerceBody> relatedProducts) {
        mRelatedProducts=null;
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
        mProductById=null;
        mProductById = productById;

    }

    public List<CategoriesItem> getCategoriesItems() {
        return mCategoriesItems;
    }

    public void setCategoriesItems(List<CategoriesItem> categoriesItems) {
        mCategoriesItems=null;
        mCategoriesItems = categoriesItems;
    }

    public List<WoocommerceBody> getNewestProducts() {
        return mNewestProducts;
    }

    public void setNewestProducts(List<WoocommerceBody> newestProducts) {
        mNewestProducts=null;
        mNewestProducts = newestProducts;

    }

    public List<WoocommerceBody> getPopularProducts() {
        return mPopularProducts;
    }

    public void setPopularProducts(List<WoocommerceBody> popularProducts) {
        mPopularProducts=null;
        mPopularProducts = popularProducts;

    }

    public List<WoocommerceBody> getRatedProducts() {
        return mRatedProducts;
    }

    public void setRatedProducts(List<WoocommerceBody> ratedProducts) {
        mRatedProducts=null;
        mRatedProducts = ratedProducts;

    }
    public boolean isRepositoryNull()
    {
        if(mRatedProducts==null||mPopularProducts==null||mNewestProducts==null||mCategoriesItems==null)
            return true;

        return false;
    }
//    private List<String> getRelatedProductsId(String id)
//    {
//        List<String> relatedIds=new ArrayList<>();
//        for(WoocommerceBody woocommerceBody:mAllProducts)
//        {
//            if(woocommerceBody.getI)
//        }
//    }
}
