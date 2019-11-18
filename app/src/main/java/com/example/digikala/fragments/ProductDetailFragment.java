package com.example.digikala.fragments;


import android.content.Intent;
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

import com.example.digikala.R;
import com.example.digikala.activities.MainActivity;
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
    private ProductAdaptor mProductAdaptor;

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
        id = getArguments().getInt(ID);
        InitProductsAsynceTask initProductsAsynceTask = new InitProductsAsynceTask();
        initProductsAsynceTask.execute();
    }

    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        init(view);
//        mProgressBar.setVisibility(View.VISIBLE);
//        mViewPager.setVisibility(View.GONE);
//        mDotsIndicator.setVisibility(View.GONE);
//        mCardView.setVisibility(View.GONE);
        return view;
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
//        setUpAdaptor();
        mDotsIndicator.setViewPager(mViewPager);
        mTextView.setText(Repository.getInstance().getProductById().getName());
        mDiscriptionTextView.setText(Repository.getInstance().getProductById().getDescription());
    }

    private void init(View view) {
        mViewPager = view.findViewById(R.id.detail_fragment_view_pager);
        mDotsIndicator = view.findViewById(R.id.detail_fragment_dots_indicator);
        mProgressBar = view.findViewById(R.id.detal_fragment_progress_bar);
        mCardView = view.findViewById(R.id.detail_fragment_card_view);
        mTextView = view.findViewById(R.id.detal_fragment_text_view);
        mDiscriptionTextView = view.findViewById(R.id.detal_fragment_discription_text_view);
        mRelatedProductRecycler = view.findViewById(R.id.detail_fragment_related_recycler);
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
//                Repository.getInstance().setRelatedProducts(mWooCommerce.getRelatedProducts(id));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Repository.getInstance().getProductById() != null) {
//                mProgressBar.setVisibility(View.GONE);
//                mViewPager.setVisibility(View.VISIBLE);
//                mDotsIndicator.setVisibility(View.VISIBLE);
//                mCardView.setVisibility(View.VISIBLE);
                PrepareViewPager();

            } else {
                Intent intent = MainActivity.newIntent(getActivity(), 0);
                Log.d("tag", "checkNetwork" + "0");
                startActivity(intent);
                getActivity().finish();
            }
        }

    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;
        private TextView mRegularPriceTextView;
        private WoocommerceBody mWoocommerceBody;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.list_newest_products_text_view);
            mImageView = itemView.findViewById(R.id.list_newest_product_image_view);
            mRegularPriceTextView = itemView.findViewById(R.id.regular_price);

        }

        void bind(WoocommerceBody woocommerceBody) {
            mWoocommerceBody = woocommerceBody;
            if (!woocommerceBody.getName().equalsIgnoreCase("تیشرت جذاب تابستانه با تخفیف ویژه دیجیکالا!!!!!"))
                mTextView.setText(woocommerceBody.getName());
            mRegularPriceTextView.setText(woocommerceBody.getPrice() + "" + "تومان");
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

}
