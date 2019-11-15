package com.example.digikala.fragments;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements WooCommerce.WooCommerceCallback {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWooCommerce.setCallback(this);
        updateUi();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);

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

    public void updateUi() {
        mWooCommerce.productRecentPhotosAsync();
        mWooCommerce.productPopularityAsync();
    }

    public void updateAdaptor(List<WoocommerceBody> items) {
        mNewestProductAdaptor = new NewestProductRecyclerView(items, getActivity());
        mPopularProductAdaptor = new PopularProductRecyclerViews(items, getActivity());
        mRatedRecyclerAdaptor = new RatedRecyclerViews(items, getActivity());
        mProductAdaptor = new ProductAdaptor(items.get(0).getCategories());
        mCategoryRecyclerView.setAdapter(mProductAdaptor);
        mRecentRecyclerView.setAdapter(mNewestProductAdaptor);
        mPopularRecyclerView.setAdapter(mPopularProductAdaptor);
        mRatedRecyclerView.setAdapter(mRatedRecyclerAdaptor);


    }

    @Override
    public void onRetrofitResponse(List<WoocommerceBody> items) {
        updateAdaptor(items);
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
}