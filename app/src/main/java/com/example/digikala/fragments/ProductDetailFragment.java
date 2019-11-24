package com.example.digikala.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digikala.R;
import com.example.digikala.activities.MainActivity;
import com.example.digikala.activities.ProductDetailActivity;
import com.example.digikala.activities.ShopBagFragmentActivity;
import com.example.digikala.activities.SplashActivity;
import com.example.digikala.model.CategoriesItem;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.network.RetrofitInstance;
import com.example.digikala.network.WooCommerce;
import com.example.digikala.network.WoocommerceService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import Woo.Repository.Repository;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    private static final String ID = "id";
    private int id;
    private WooCommerce mWooCommerce = new WooCommerce();
    private ViewPager mViewPager;
    private CircleIndicator mDotsIndicator;
    private ProgressBar mProgressBar;
    private CardView mCardView;
    private TextView mTextView;
    private RecyclerView mRelatedProductRecycler;
    private TextView mDiscriptionTextView;
    private TextView mRegularPriceTextView;
    private TextView mBudgetPriceTextView;
    private ProductAdaptor mProductAdaptor;
    private Button mBuyButton;
    public static final int REQUEST_CODE_FOR_SHOP_BAG_FRAGMENT = 1;
    public static final String SHOP_BAG_FRAGMENT_TAG = "shopbagfragmenttag";
    private String[] realtedIds;
    private changeFragment mChangeFragment;

    public static ProductDetailFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ID, id);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isNetworkConnected()) {
            id = getArguments().getInt(ID);
            startAsynkRequest();
        } else {
//            Intent intent = MainActivity.newIntent(getActivity(), 0);
//            Log.d("tag", "checkNetwork" + "0");
//            startActivity(intent);
            getActivity().finish();
            Log.d("tag", "finished");
        }

    }

    private void startAsynkRequest() {
        InitProductsAsynceTask initProductsAsynceTask = new InitProductsAsynceTask();
        initProductsAsynceTask.execute();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof changeFragment) {
            mChangeFragment = (changeFragment) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mChangeFragment = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        init(view);
        mProgressBar.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        mDotsIndicator.setVisibility(View.GONE);
        mCardView.setVisibility(View.GONE);
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductToBag();
              Intent intent= ShopBagFragmentActivity.newIntent(getActivity(),id,1);
              startActivity(intent);
//                ShopBagFragment shopBagFragment = ShopBagFragment.newInstance(id, 1);
////                shopBagFragment.setTargetFragment(ProductDetailFragment.this, REQUEST_CODE_FOR_SHOP_BAG_FRAGMENT);
//                shopBagFragment.show(getFragmentManager(), SHOP_BAG_FRAGMENT_TAG);
            }
        });
        return view;
    }

    private void addProductToBag() {
        Repository.getInstance().addBag(Repository.getInstance().getProductById().getId());
    }

    private void PrepareViewPager() {


        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Repository.getInstance().getProductById().getImages().size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.image_view, null);
                ImageView imageView = view.findViewById(R.id.image);
                Picasso.with(getActivity()).load(Repository.getInstance().getProductById().getImages().get(position).getSrc()).placeholder(R.drawable.digikala)
                        .into(imageView);

                container.addView(view);
                return view;

            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object view) {
                container.removeView((View) view);
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return object == view;
            }

        });
        mDotsIndicator.setViewPager(mViewPager);
        mTextView.setText(Repository.getInstance().getProductById().getName());
        mDiscriptionTextView.setText(Repository.getInstance().getProductById().getDescription());
        mRegularPriceTextView.setText(Repository.getInstance().getProductById().getPrice() + " " + "تومان");
        mBudgetPriceTextView.setText(Repository.getInstance().getProductById().getRegularPrice() + " " + "تومان");
    }

    private void PrepareRelatedProducts() {
        List<Integer> integers = Repository.getInstance().getProductById().getRelatedIds();

//        realtedIds = new String[Repository.getInstance().getProductById().getRelatedIds().size()];
//        for (int i = 0; i < Repository.getInstance().getProductById().getRelatedIds().size(); i++) {
//            realtedIds[i] = String.valueOf(Repository.getInstance().getProductById().getRelatedIds().get(i));
//        }
        mWooCommerce.getWoocommerceApi().getReleatedProducts(integers.toString()).enqueue(new Callback<List<WoocommerceBody>>() {
            @Override
            public void onResponse(Call<List<WoocommerceBody>> call, Response<List<WoocommerceBody>> response) {
                if (response.isSuccessful()) {
                    Repository.getInstance().setRelatedProducts(response.body());
                    setUpAdaptor();
                }
            }

            @Override
            public void onFailure(Call<List<WoocommerceBody>> call, Throwable t) {
//                Intent intent = MainActivity.newIntent(getActivity(), 0);
//                startActivity(intent);
//                getActivity().finish();

            }
        });

    }

    private void init(View view) {
        mViewPager = view.findViewById(R.id.detail_fragment_view_pager);
        mDotsIndicator = view.findViewById(R.id.detail_fragment_dots_indicator);
        mProgressBar = view.findViewById(R.id.detal_fragment_progress_bar);
        mCardView = view.findViewById(R.id.detail_fragment_card_view);
        mTextView = view.findViewById(R.id.detal_fragment_text_view);
        mDiscriptionTextView = view.findViewById(R.id.detal_fragment_discription_text_view);
        mRelatedProductRecycler = view.findViewById(R.id.detail_fragment_related_recycler);
        mBudgetPriceTextView = view.findViewById(R.id.detail_fragment_budget_price_textView);
        mRegularPriceTextView = view.findViewById(R.id.detail_fragment_regular_price_textView);
        mBuyButton = view.findViewById(R.id.detail_fragment_buy_button);
    }

    private void setUpAdaptor() {
        mProductAdaptor = new ProductAdaptor(Repository.getInstance().getRelatedProducts());
        mRelatedProductRecycler.setAdapter(mProductAdaptor);
    }

    private class InitProductsAsynceTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Repository.getInstance().setProductById(mWooCommerce.getProductById(id));

            } catch (IOException e) {
                e.printStackTrace();
                getActivity().finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Repository.getInstance().getProductById() != null) {
                mProgressBar.setVisibility(View.GONE);
                mViewPager.setVisibility(View.VISIBLE);
                mDotsIndicator.setVisibility(View.VISIBLE);
                mCardView.setVisibility(View.VISIBLE);
                PrepareViewPager();
                PrepareRelatedProducts();

            } else {
                mProgressBar.setVisibility(View.GONE);
//                Intent intent = MainActivity.newIntent(getActivity(), 0);
//                Log.d("tag", "checkNetwork" + "0");
//                startActivity(intent);
//                getActivity().finish();
                startAsynkRequest();
            }
        }

    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;
        private TextView mRegularPriceTextView;
        private TextView mBudgetPriceTextView;
        private WoocommerceBody mWoocommerceBody;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.list_newest_products_text_view);
            mImageView = itemView.findViewById(R.id.list_newest_product_image_view);
            mRegularPriceTextView = itemView.findViewById(R.id.regular_price);
            mBudgetPriceTextView = itemView.findViewById(R.id.budget_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ProductDetailActivity.newIntent(getActivity(), mWoocommerceBody.getId(), mWoocommerceBody.getName());
                    startActivity(intent);
                }
            });

        }

        void bind(WoocommerceBody woocommerceBody) {
            mWoocommerceBody = woocommerceBody;
            if (!woocommerceBody.getName().equalsIgnoreCase("تیشرت جذاب تابستانه با تخفیف ویژه دیجیکالا!!!!!"))
                mTextView.setText(woocommerceBody.getName());
            mRegularPriceTextView.setText(woocommerceBody.getPrice() + " " + "تومان");
            mBudgetPriceTextView.setText(woocommerceBody.getRegularPrice() + " " + "تومان");
            Picasso.with(getActivity()).load(woocommerceBody.getImages().get(0).getSrc()).placeholder(R.drawable.digikala)
                    .into(mImageView);
        }

    }

    private class ProductAdaptor extends RecyclerView.Adapter<ProductHolder> {
        private List<WoocommerceBody> mWoocommerceBodies;

        public ProductAdaptor(List<WoocommerceBody> woocommerceBodies) {
            mWoocommerceBodies = woocommerceBodies;
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_newest_products, parent, false);
            ProductHolder productHolder = new ProductHolder(view);
            return productHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            holder.bind(mWoocommerceBodies.get(position));
        }

        @Override
        public int getItemCount() {
            return mWoocommerceBodies.size();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
