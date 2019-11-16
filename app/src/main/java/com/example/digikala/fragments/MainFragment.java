package com.example.digikala.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.digikala.RecyclersViews.NewestProductRecyclerView;
import com.example.digikala.R;
import com.example.digikala.RecyclersViews.PopularProductRecyclerViews;
import com.example.digikala.RecyclersViews.RatedRecyclerViews;
import com.example.digikala.model.CategoriesItem;
import com.example.digikala.model.WoocommerceBody;
import com.example.digikala.network.WooCommerce;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Woo.C.Repository;
import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment  {
    private static final String STATE = "state";
    private static final String WOOCOMMERCE_BODY = "woocommercebody";
    private ViewPager mViewPager;
    private CircleIndicator mDotsIndicator;
    private ImageView mImageView;
    private RecyclerView mCategoryRecyclerView;
    private RecyclerView mRatedRecyclerView;
    private RecyclerView mPopularRecyclerView;
    private List<String> mStrings = new ArrayList<>();
    private ProductAdaptor mProductAdaptor;
    private RecyclerView mRecentRecyclerView;
    private List<String> mStrings2 = new ArrayList<>();
    private NewestProductRecyclerView mNewestProductAdaptor;
    private PopularProductRecyclerViews mPopularProductAdaptor;
    private RatedRecyclerViews mRatedRecyclerAdaptor;
    private WooCommerce mWooCommerce = new WooCommerce();
    private int state;
    private  changeFragment mChangeFragment;
    private List<WoocommerceBody> items;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isNetworkConnected()) {
            mChangeFragment.changeFragment(false);
        }
        Log.d("tag","onResume");
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        updateAdaptor();
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.image_view, null);
                ImageView imageView = view.findViewById(R.id.image);
                imageView.setImageDrawable(getActivity().getResources().getDrawable(getImageAt(position)));
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

            private int getImageAt(int position) {
                switch (position) {
                    case 0:
                        return R.drawable.add;
                    case 1:
                        return R.drawable.add;
                    case 2:
                        return R.drawable.add;
                    case 3:
                        return R.drawable.add;
                    default:
                        return R.drawable.add;
                }
            }
        });
        mDotsIndicator.setViewPager(mViewPager);


        return view;
    }


    private void init(View view) {
        mViewPager = view.findViewById(R.id.view_pager);
        mDotsIndicator = view.findViewById(R.id.dots_indicator);
        mCategoryRecyclerView = view.findViewById(R.id.fragment_main_recycler);
        mRecentRecyclerView = view.findViewById(R.id.fragment_main_newest_product_recycler);
        mPopularRecyclerView = view.findViewById(R.id.fragment_main_popular_product_recycler);
        mRatedRecyclerView = view.findViewById(R.id.fragment_main_rated_product_recycler);

    }


    public void updateAdaptor() {
        mNewestProductAdaptor = new NewestProductRecyclerView(Repository.getInstance().getNewestProducts(), getActivity());
        mPopularProductAdaptor = new PopularProductRecyclerViews(Repository.getInstance().getPopularProducts(), getActivity());
        mRatedRecyclerAdaptor = new RatedRecyclerViews(Repository.getInstance().getRatedProducts(), getActivity());
//        mProductAdaptor = new ProductAdaptor(items.get(0).getCategories());
//        mCategoryRecyclerView.setAdapter(mProductAdaptor);
        mRecentRecyclerView.setAdapter(mNewestProductAdaptor);
        mPopularRecyclerView.setAdapter(mPopularProductAdaptor);
        mRatedRecyclerView.setAdapter(mRatedRecyclerAdaptor);
    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        private Button mButton;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.product_items);

        }

        void bind(CategoriesItem categoriesItem) {
            mButton.setText(categoriesItem.getName());
        }

    }

    private class ProductAdaptor extends RecyclerView.Adapter<ProductHolder> {
        private List<CategoriesItem> mCategoriesItems;

        public ProductAdaptor(List<CategoriesItem> categoriesItems) {
            mCategoriesItems = categoriesItems;
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_items_product, parent, false);
            ProductHolder productHolder = new ProductHolder(view);
            return productHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            holder.bind(mCategoriesItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mCategoriesItems.size();
        }
    }



    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}