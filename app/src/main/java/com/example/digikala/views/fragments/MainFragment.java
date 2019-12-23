package com.example.digikala.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.digikala.RecyclersViews.ProductsRecyclerView;
import com.example.digikala.R;
import com.example.digikala.RecyclersViews.SliderAdaptor;
import com.example.digikala.model.productsModels.WoocommerceBody;
import com.example.digikala.model.categoriesmodel.CategoriesBody;
import com.example.digikala.viewmodels.MainViewModel;
import com.example.digikala.views.activities.CategoriesViewPagerActivity;
import com.example.digikala.views.changeFragment;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private int newestPage=1;
    private int popularPage=1;
    private int ratedPage=1;
    private RecyclerView mCategoryRecyclerView;
    private RecyclerView mRatedRecyclerView;
    private RecyclerView mPopularRecyclerView;
    private ProductAdaptor mProductAdaptor;
    private TextView mNewProductsTextView;
    private TextView mPopularProductsTextView;
    private TextView mRatedProductsTextView;
    private RecyclerView mRecentRecyclerView;
    private ProductsRecyclerView mNewestProductAdaptor;
    private ProductsRecyclerView mPopularProductAdaptor;
    private ProductsRecyclerView mRatedRecyclerAdaptor;
    private SliderView mSliderView;
    private changeFragment mChangeFragment;
    private SliderAdaptor mSliderAdaptor;
    private MainViewModel mMainViewModel;
    private ProgressBar mProgressBar;
    private ProgressBar mNewestRecyclerProgressBar;
    private ProgressBar mPopularRecyclerProgressBar;
    private ProgressBar mRatedRecyclerProgressBar;
    private List<WoocommerceBody> mPopularProductsForSlider;
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
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.loadNewestProducts(newestPage);
        mMainViewModel.loadPopularProducts(popularPage);
        mMainViewModel.loadRatedProducts(ratedPage);
        mMainViewModel.loadSpecialProducts();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isNetworkConnected()) {
            mChangeFragment.changeFragment(false);
        }
        Log.d("tag", "onResume");
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
        mProgressBar.setVisibility(View.VISIBLE);
        mPopularProductsTextView.setVisibility(View.GONE);
        mRatedProductsTextView.setVisibility(View.GONE);
        mNewProductsTextView.setVisibility(View.GONE);
        mSliderView.setVisibility(View.GONE);
        mMainViewModel.getSpecialProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if(woocommerceBodies!=null) {
                    Log.d("tag","first");
                    initSliderView(woocommerceBodies);
                }
                else {
                    getActivity().finish();
                }
            }
        });
        mMainViewModel.getPopularProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if(woocommerceBodies!=null) {
                    Log.d("pop","first");
                    mPopularRecyclerProgressBar.setVisibility(View.GONE);
                    updatePopularProductsAdaptor(woocommerceBodies);
                }
                else {
                    getActivity().finish();
                }
            }
        });
        mMainViewModel.getNewestProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if(woocommerceBodies!=null) {
                    mSliderView.setVisibility(View.VISIBLE);
                    mNewestRecyclerProgressBar.setVisibility(View.GONE);
                    updateNewestProductsAdaptor(woocommerceBodies);
                }
                else {
                    getActivity().finish();
                }
            }
        });
        mMainViewModel.getRatedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if(woocommerceBodies!=null) {
                    updateRatedProductsAdaptor(woocommerceBodies);
                    mProgressBar.setVisibility(View.GONE);
                    mPopularProductsTextView.setVisibility(View.VISIBLE);
                    mRatedProductsTextView.setVisibility(View.VISIBLE);
                    mNewProductsTextView.setVisibility(View.VISIBLE);
                    mSliderView.setVisibility(View.VISIBLE);
                    mRatedRecyclerProgressBar.setVisibility(View.GONE);
                }
                else {
                    getActivity().finish();
                }
            }
        });
        return view;
    }

    private void initSliderView(List<WoocommerceBody> woocommerceBodyLis) {
            mSliderAdaptor = new SliderAdaptor(woocommerceBodyLis, getActivity());
            mSliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
            mSliderView.setSliderAdapter(mSliderAdaptor);

    }

    private void init(View view) {
        mSliderView = view.findViewById(R.id.imageSlider);
        mCategoryRecyclerView = view.findViewById(R.id.fragment_main_recycler);
        mRecentRecyclerView = view.findViewById(R.id.fragment_main_newest_product_recycler);
        mPopularRecyclerView = view.findViewById(R.id.fragment_main_popular_product_recycler);
        mRatedRecyclerView = view.findViewById(R.id.fragment_main_rated_product_recycler);
        mNewProductsTextView = view.findViewById(R.id.new_product_textview);
        mRatedProductsTextView = view.findViewById(R.id.rated_product_textview);
        mPopularProductsTextView = view.findViewById(R.id.popular_product_textview);
        mProgressBar = view.findViewById(R.id.main_fragment_progress_bar);
        mNewestRecyclerProgressBar=view.findViewById(R.id.main_fragment_newest_progress_bar);
        mPopularRecyclerProgressBar=view.findViewById(R.id.main_fragment_popular_progress_bar);
        mRatedRecyclerProgressBar=view.findViewById(R.id.main_fragment_rated_progress_bar);
    }


    public void updateNewestProductsAdaptor(final List<WoocommerceBody> woocommerceBodies) {
            if(mNewestProductAdaptor==null) {
                mNewestProductAdaptor = new ProductsRecyclerView(woocommerceBodies, getActivity());
                mRecentRecyclerView.setAdapter(mNewestProductAdaptor);
                mRecentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == woocommerceBodies.size() - 1) {
                            mNewestRecyclerProgressBar.setVisibility(View.VISIBLE);
                            mMainViewModel.loadNewestProducts(++newestPage);
                        }
                    }
                });
            }else
            {
                mNewestProductAdaptor.setProductList(woocommerceBodies);
                mNewestProductAdaptor.notifyDataSetChanged();
            }
        mProductAdaptor = new ProductAdaptor(mMainViewModel.getFilteredCategoriesItems());
        mCategoryRecyclerView.setAdapter(mProductAdaptor);
    }
    public void updatePopularProductsAdaptor(final List<WoocommerceBody> woocommerceBodies) {
            if(mPopularProductAdaptor==null) {
                mPopularProductAdaptor = new ProductsRecyclerView(woocommerceBodies, getActivity());
                mPopularRecyclerView.setAdapter(mPopularProductAdaptor);
                mPopularRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == woocommerceBodies.size() - 1) {
                            mPopularRecyclerProgressBar.setVisibility(View.VISIBLE);
                            mMainViewModel.loadPopularProducts(++popularPage);
                        }
                    }
                });
            }else
            {
                mPopularProductAdaptor.setProductList(woocommerceBodies);
                mPopularProductAdaptor.notifyDataSetChanged();
            }
    }
    public void updateRatedProductsAdaptor(final List<WoocommerceBody> woocommerceBodies) {

        if(mRatedRecyclerAdaptor==null) {
            mRatedRecyclerAdaptor = new ProductsRecyclerView(woocommerceBodies, getActivity());
            mRatedRecyclerView.setAdapter(mRatedRecyclerAdaptor);
            mRatedRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == woocommerceBodies.size() - 1) {
                        mRatedRecyclerProgressBar.setVisibility(View.VISIBLE);
                        mMainViewModel.loadRatedProducts(++ratedPage);
                    }
                }
            });
        }else
        {
            mRatedRecyclerAdaptor.setProductList(woocommerceBodies);
            mRatedRecyclerAdaptor.notifyDataSetChanged();
        }
    }
    private class ProductHolder extends RecyclerView.ViewHolder {
        private Button mButton;
        private CategoriesBody mCategoriesBody;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.product_items);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = CategoriesViewPagerActivity.newIntent(getActivity(), mCategoriesBody.getId());
                    startActivity(intent);
                }
            });

        }

        void bind(CategoriesBody categoriesItem) {
            mCategoriesBody = categoriesItem;
            mButton.setText(categoriesItem.getName());
        }

    }

    private class ProductAdaptor extends RecyclerView.Adapter<ProductHolder> {
        private List<CategoriesBody> mCategoriesItems;

        public ProductAdaptor(List<CategoriesBody> categoriesItems) {
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