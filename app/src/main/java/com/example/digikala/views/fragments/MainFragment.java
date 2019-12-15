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
import com.example.digikala.model.WoocommerceBody;
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
        mMainViewModel.loadNewestProducts();
        mMainViewModel.loadPopularProducts();
        mMainViewModel.loadRatedProducts();

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
        mMainViewModel.getPopularProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if(woocommerceBodies!=null) {
                    initSliderView();
                    updateAdaptor(woocommerceBodies, 1);
                }else {
                    getActivity().finish();
                }
            }
        });
        mMainViewModel.getNewestProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if(woocommerceBodies!=null) {
                    mSliderView.setVisibility(View.VISIBLE);
                    initSliderView();
                    updateAdaptor(woocommerceBodies, 0);
                }else {
                    getActivity().finish();
                }
            }
        });
        mMainViewModel.getRatedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if(woocommerceBodies!=null) {
                    updateAdaptor(woocommerceBodies, 2);
                    mProgressBar.setVisibility(View.GONE);
                    mPopularProductsTextView.setVisibility(View.VISIBLE);
                    mRatedProductsTextView.setVisibility(View.VISIBLE);
                    mNewProductsTextView.setVisibility(View.VISIBLE);
                    mSliderView.setVisibility(View.VISIBLE);
                }else {
                    getActivity().finish();
                }
            }
        });
        return view;
    }

    private void initSliderView() {
        mSliderAdaptor = new SliderAdaptor(mMainViewModel.getNewestProducts().getValue(), getActivity());
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
    }


    public void updateAdaptor(List<WoocommerceBody> woocommerceBodies, int state) {

        if (state == 0) {
            Log.d("recy", "0");
            mNewestProductAdaptor = new ProductsRecyclerView(woocommerceBodies, getActivity());
        } else if (state == 1) {

            Log.d("recy", "1");
            mPopularProductAdaptor = new ProductsRecyclerView(woocommerceBodies, getActivity());
        } else {
            Log.d("recy", "2");
            mRatedRecyclerAdaptor = new ProductsRecyclerView(woocommerceBodies, getActivity());
        }

        Log.d("recy", "3");
        mProductAdaptor = new ProductAdaptor(mMainViewModel.getFilteredCategoriesItems());
        mCategoryRecyclerView.setAdapter(mProductAdaptor);
        mRecentRecyclerView.setAdapter(mNewestProductAdaptor);
        mPopularRecyclerView.setAdapter(mPopularProductAdaptor);
        mRatedRecyclerView.setAdapter(mRatedRecyclerAdaptor);
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