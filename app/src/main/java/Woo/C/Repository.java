package Woo.C;

import com.example.digikala.model.WoocommerceBody;

import java.util.List;

public class Repository {
    private static Repository repository;
    private List<WoocommerceBody> mWoocommerceBodies;

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    public void setWoocommerceBodies(List<WoocommerceBody> woocommerceBodies) {
        mWoocommerceBodies = woocommerceBodies;
    }

    public List<WoocommerceBody> getWoocommerceBodies() {
        return mWoocommerceBodies;
    }
}
