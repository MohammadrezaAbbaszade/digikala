package com.example.digikala.views.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;
import com.example.digikala.model.ProductAttributeData;
import com.example.digikala.model.productsModels.WoocommerceBody;
import com.example.digikala.viewmodels.ListProductsViewModel;
import com.example.digikala.views.activities.FilterActivity;
import com.example.digikala.views.activities.ProductDetailActivity;
import com.example.digikala.views.changeFragment;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListProductsFragment extends Fragment {
    private int newestPage = 1;
    private int popularPage = 1;
    private int ratedPage = 1;
    private int categoryPage = 1;
    private int filteredPage = 1;
    private int searchPage = 1;
    private ListProductsViewModel mListProductViewModel;
    private static final String STATE = "state";
    private static final String CATEGORY_ID = "categoryId";
    public static final String CONSUMER_KEY = "ck_9fc06c2a7292f136b852aceda63740458feb20e1";
    public static final String CONSUMER_SECRET = "cs_5608c8ad5f3ce5b02ac5c629fcad909da759f63a";
    private Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);

        }
    };
    private boolean isListEmpty = false;
    private ProductAttributeData mProductAttributeData;
    private ProductAdaptor mProductAdaptor;
    private List<WoocommerceBody> mNewProductList;
    private RecyclerView mListProductsRecycler;
    private TextView mTextView;
    private TextView mSortTextView;
    private ProgressBar mProgressBar;
    private ProgressBar mSecondProgressBar;
    private TextView mSubSortTextView;
    private TextView mFilterTextView;
    private int state;
    private int categoryId;
    private List<WoocommerceBody> mAllProducts;
    public static final int REQUEST_CODE_FOR_SORT_DIALOG_FRAGMENT = 0;
    public static final String SORT_DIALOG_FRAGMENT_TAG = "sortdialogfragmenttag";
    private changeFragment mChangeFragment;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void filterProductsByAttributes(ProductAttributeData event) {
        Log.e("eventaa", SharedPreferencesData.getRadioGroupId(getActivity()) + "");
        mProductAdaptor = null;
        mListProductsRecycler.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.GONE);


        switch (SharedPreferencesData.getRadioGroupId(getActivity())) {
            case 1:
                event.setOrderby("date");
                event.setOrder("desc");
                event.setSearch("");
                event.setPage(1);
                break;
            case 2:
                event.setOrderby("popularity");
                event.setOrder("desc");
                event.setSearch("");
                event.setPage(1);
                break;
            case 3:
                event.setOrderby("rating");
                event.setOrder("desc");
                event.setSearch("");
                event.setPage(1);
                break;
            case 4:
                event.setOrderby("price");
                event.setOrder("desc");
                event.setSearch("");
                event.setPage(1);
                break;
            case 5:
                event.setOrderby("price");
                event.setOrder("asc");
                event.setSearch("");
                event.setPage(1);
                break;
        }
        mProductAttributeData = event;
        Log.e("eventaa", mProductAttributeData.getOrderby()+""+ mProductAttributeData.getOrder() + "");
        if(state==4&&SharedPreferencesData.getRadioGroupId(getActivity())==0)
        {
            event.setOrder("desc");
            event.setPage(1);
            event.setSearch(SharedPreferencesData.getQuery(getActivity()));
            Log.e("eventaa", "new model" + mProductAttributeData.getSearch() + "");
            mListProductViewModel.loadFilteredAndSortedProducts(event);
            initSortedAndFIlteredObserver();
        }else {
            if (state == 4) {
                event.setSearch(SharedPreferencesData.getQuery(getActivity()));
                Log.e("eventaa", "new model" + mProductAttributeData.getSearch() + "");
                mListProductViewModel.loadFilteredAndSortedProducts(event);
                initSortedAndFIlteredObserver();
            } else {
                Log.e("eventaa", "new model" + mProductAttributeData.getOrder() + "");
                mListProductViewModel.loadFilteredAndSortedProducts(event);
                initSortedAndFIlteredObserver();
            }
        }
    }

    public static ListProductsFragment newInstance(int state, int categoryId) {

        Bundle args = new Bundle();
        args.putInt(STATE, state);
        if (state == 5)
            args.putInt(CATEGORY_ID, categoryId);
        ListProductsFragment fragment = new ListProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ListProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNetworkConnected()) {
            getActivity().finish();
            Log.d("tag", "finished");
        }
        mListProductViewModel = ViewModelProviders.of(this).get(ListProductsViewModel.class);
        state = getArguments().getInt(STATE);
        categoryId = getArguments().getInt(CATEGORY_ID);
        switch (state) {
            case 1:
                mListProductViewModel.loadPopularProducts(popularPage);
                break;
            case 2:
                mListProductViewModel.loadRatedProducts(ratedPage);
                break;
            case 3:
                mListProductViewModel.loadNewestProducts(newestPage);
                break;
            case 4:
                mQueries.put("search", SharedPreferencesData.getQuery(getActivity()));
                mListProductViewModel.loadSearchedProducts(mQueries, 1);
                break;
            case 5:
                Log.d("categoryId", String.valueOf(categoryId));
                mListProductViewModel.loadSubCategoriesProducts(String.valueOf(categoryId), 1);
                break;
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        isListEmpty = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_products, container, false);
        init(view);
        mTextView.setVisibility(View.GONE);
        mListProductsRecycler.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        if (state == 1) {
            mSubSortTextView.setText(R.string.check_box_rated);
            SharedPreferencesData.setRadioGroupId(getActivity(), 2);
        } else if (state == 2) {
            mSubSortTextView.setText(R.string.check_box_selles);
            SharedPreferencesData.setRadioGroupId(getActivity(), 3);
        } else if (state == 3) {
            mSubSortTextView.setText(R.string.check_box_newest);
            SharedPreferencesData.setRadioGroupId(getActivity(), 1);
        } else {
            mSubSortTextView.setText(R.string.check_box_selles);
            SharedPreferencesData.setRadioGroupId(getActivity(), 0);
        }
        mFilterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FilterActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        mSortTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortDialogFragment sortDialogFragment = SortDialogFragment.newInstance(SharedPreferencesData.getRadioGroupId(getContext()));
                sortDialogFragment.setTargetFragment(ListProductsFragment.this, REQUEST_CODE_FOR_SORT_DIALOG_FRAGMENT);
                sortDialogFragment.show(getFragmentManager(), SORT_DIALOG_FRAGMENT_TAG);
            }
        });
        Log.d("tag", "onCreateViewL");

        mListProductViewModel.getPopularProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if (woocommerceBodies != null) {
                    Log.d("pop", "first");
                    mProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.GONE);
                    mSecondProgressBar.setVisibility(View.GONE);
                    initAdaptor(woocommerceBodies);
                } else {

                    getActivity().finish();
                }
            }
        });
        mListProductViewModel.getNewestProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if (woocommerceBodies != null) {
                    mProgressBar.setVisibility(View.GONE);
                    mSecondProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.GONE);
                    if (woocommerceBodies.isEmpty())
                        isListEmpty = true;
                    initAdaptor(woocommerceBodies);
                } else {
                    getActivity().finish();
                }
            }
        });
        mListProductViewModel.getRatedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if (woocommerceBodies != null) {
                    mProgressBar.setVisibility(View.GONE);
                    mSecondProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.GONE);
                    initAdaptor(woocommerceBodies);
                } else {
                    getActivity().finish();
                }
            }
        });
        mListProductViewModel.getSearchedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if (!woocommerceBodies.isEmpty()) {
                    Log.e("n", "enteredSearched");
                    initAdaptor(woocommerceBodies);
                    mProgressBar.setVisibility(View.GONE);
                    mListProductsRecycler.setVisibility(View.VISIBLE);
                    mSecondProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.GONE);
                } else {
                    Log.e("n", "NotenteredSearched");
                    isListEmpty = true;
                    mProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.VISIBLE);
                    mListProductsRecycler.setVisibility(View.VISIBLE);
                }
            }
        });
        if (state == 5) {
            mListProductViewModel.getSubCategoriesProducts().observe(this, new Observer<List<WoocommerceBody>>() {
                @Override
                public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                    if (!woocommerceBodies.isEmpty()) {
                        Log.d("categoryId", String.valueOf(woocommerceBodies.size()));
                        Log.d("categoryreqAga", "newRecieved");
                        initAdaptor(woocommerceBodies);
                        mTextView.setVisibility(View.GONE);
                        mSecondProgressBar.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.GONE);
                        mListProductsRecycler.setVisibility(View.VISIBLE);
                    } else {
                        Log.d("categoryreqAga", "NotnewRecieved");
                        Log.d("categoryId", String.valueOf(woocommerceBodies.size()));
                        isListEmpty = true;
                        mProgressBar.setVisibility(View.GONE);
                        mTextView.setVisibility(View.VISIBLE);
                        mListProductsRecycler.setVisibility(View.VISIBLE);
                    }

                }
            });
        }
        return view;
    }

    private void initNewPageSubCategories() {
        mListProductViewModel.getSubCategoriesProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if (!woocommerceBodies.isEmpty()) {
                    Log.d("categoryId", String.valueOf(woocommerceBodies.size()));
                    Log.d("categoryreqAga", "newRecieved");
                    initAdaptor(woocommerceBodies);
                    mTextView.setVisibility(View.GONE);
                    mSecondProgressBar.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                    mListProductsRecycler.setVisibility(View.VISIBLE);
                } else {
                    Log.d("categoryreqAga", "NotnewRecieved");
                    Log.d("categoryId", String.valueOf(woocommerceBodies.size()));
                    mSecondProgressBar.setVisibility(View.GONE);
                    isListEmpty = true;
                    mProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.VISIBLE);
                    mListProductsRecycler.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void initSortedAndFIlteredObserver() {
        mListProductViewModel.getFilteredAndSortedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if (!woocommerceBodies.isEmpty()) {
                    Log.d("filteredP", "first" + woocommerceBodies.size());
                    mProgressBar.setVisibility(View.GONE);
                    mSecondProgressBar.setVisibility(View.GONE);
                    initAdaptorFromFilter(woocommerceBodies);
                } else {
                    Log.d("filteredP", "first");
                    mProgressBar.setVisibility(View.GONE);
                    mSecondProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.VISIBLE);
                    isListEmpty = true;
                }
            }
        });
    }

    private void initAdaptorFromFilter(final List<WoocommerceBody> woocommerceBodies) {
        mListProductsRecycler.setVisibility(View.VISIBLE);
        if (mProductAdaptor == null) {
            Log.e("recycler", "null");
            mProductAdaptor = new ProductAdaptor(woocommerceBodies);
            mListProductsRecycler.setAdapter(mProductAdaptor);
            mListProductsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == woocommerceBodies.size() - 1) {
                        if (!isListEmpty) {

                            mSecondProgressBar.setVisibility(View.VISIBLE);
                            if (state == 4) {
                                mProductAttributeData.setPage(++searchPage);
                            } else {
                                mProductAttributeData.setPage(++filteredPage);
                            }

                            Log.e("eventaa", isListEmpty + "" + mProductAttributeData.getPage() + "first");
                            mListProductViewModel.loadFilteredAndSortedProducts(mProductAttributeData);
                            initSortedAndFIlteredObserver();
                        } else {
                            Log.e("eventaa", isListEmpty + "false");
                            mSecondProgressBar.setVisibility(View.GONE);
                        }

                    }
                }
            });
        } else {
            Log.e("recycler", "Notnull");
            mProductAdaptor.setWoocommerceBodies(woocommerceBodies);
            mProductAdaptor.notifyDataSetChanged();
        }


    }

    private void initSearchObserver() {
        mListProductViewModel.getSearchedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if (!woocommerceBodies.isEmpty()) {
                    Log.e("n", "enteredSearched");
                    initAdaptor(woocommerceBodies);
                    mProgressBar.setVisibility(View.GONE);
                    mListProductsRecycler.setVisibility(View.VISIBLE);
                    mTextView.setVisibility(View.GONE);
                } else {
                    Log.e("n", "NotenteredSearched");
                    isListEmpty = true;
                    mProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.VISIBLE);
                    mListProductsRecycler.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initAdaptor(final List<WoocommerceBody> woocommerceBodies) {
        mListProductsRecycler.setVisibility(View.VISIBLE);
        if (mProductAdaptor == null) {
            Log.e("recycler", "null");
            mProductAdaptor = new ProductAdaptor(woocommerceBodies);
            mListProductsRecycler.setAdapter(mProductAdaptor);
            mListProductsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == woocommerceBodies.size() - 1) {
                        if (!isListEmpty&&state!=5) {
                            mSecondProgressBar.setVisibility(View.VISIBLE);
                            switch (state) {
                                case 1:
                                    mListProductViewModel.loadPopularProducts(++popularPage);
                                    break;
                                case 2:
                                    mListProductViewModel.loadRatedProducts(++ratedPage);
                                    break;
                                case 3:
                                    mListProductViewModel.loadNewestProducts(++newestPage);
                                    break;
                                case 4:
                                    mQueries.put("search", SharedPreferencesData.getQuery(getActivity()));
                                    mListProductViewModel.loadSearchedProducts(mQueries, ++searchPage);
                                    initSearchObserver();
                                    break;

                            }

                        } else {
                            mSecondProgressBar.setVisibility(View.GONE);
                            mTextView.setVisibility(View.GONE);
                        }

                    }
                }
            });
        } else {
            Log.e("recycler", "Notnull");
            mProductAdaptor.setWoocommerceBodies(woocommerceBodies);
            mProductAdaptor.notifyDataSetChanged();
        }

    }

    private void init(View view) {
        mProgressBar = view.findViewById(R.id.list_products_fragment_progress_bar);
        mListProductsRecycler = view.findViewById(R.id.list_products_fragment_recycler);
        mTextView = view.findViewById(R.id.list_products_fragment_text_view);
        mSortTextView = view.findViewById(R.id.list_product_fragment_sort_text_view);
        mSubSortTextView = view.findViewById(R.id.list_product_fragment_sub_sort_text_view);
        mFilterTextView = view.findViewById(R.id.list_product_fragment_sub_filter_text_view);
        mSecondProgressBar = view.findViewById(R.id.list_products_fragment_second_progress_bar);
    }

    private void setSortTextView() {
        switch (SharedPreferencesData.getRadioGroupId(getActivity())) {
            case 1:
                mSubSortTextView.setText(R.string.check_box_newest);
                break;
            case 2:
                mSubSortTextView.setText(R.string.check_box_rated);
                break;
            case 3:
                mSubSortTextView.setText(R.string.check_box_selles);
                break;
            case 4:
                mSubSortTextView.setText(R.string.check_box_cost_high_low);
                break;
            case 5:
                mSubSortTextView.setText(R.string.check_box_cost_low_high);
                break;
            default: {
                Log.d("default", String.valueOf(SharedPreferencesData.getRadioGroupId(getActivity())));
                if (state == 1) {
                    mSubSortTextView.setText(R.string.check_box_rated);
                    SharedPreferencesData.setRadioGroupId(getActivity(), 2);
                } else if (state == 2) {
                    mSubSortTextView.setText(R.string.check_box_selles);
                    SharedPreferencesData.setRadioGroupId(getActivity(), 3);
                } else if (state == 3) {
                    mSubSortTextView.setText(R.string.check_box_newest);
                    SharedPreferencesData.setRadioGroupId(getActivity(), 1);
                } else if (state == 4) {
                    SharedPreferencesData.setRadioGroupId(getActivity(), 4);
                }else {
                    mSubSortTextView.setText(R.string.check_box_selles);
                    SharedPreferencesData.setRadioGroupId(getActivity(), 0);
                }

            }
        }
    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mBudgetTextView;
        private ImageView mImageView;
        private TextView mRegularPriceTextView;
        private WoocommerceBody mWoocommerceBody;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.list_products_text_view);
            mImageView = itemView.findViewById(R.id.list_products_image_view);
            mRegularPriceTextView = itemView.findViewById(R.id.list_products_regular_price);
            mBudgetTextView = itemView.findViewById(R.id.list_products_budget_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isNetworkConnected()) {
                        getActivity().finish();
                    } else {
                        Intent intent = ProductDetailActivity.newIntent(getActivity(), mWoocommerceBody.getId(), mWoocommerceBody.getName());
                        startActivity(intent);
                    }
                }
            });
        }

        void bind(WoocommerceBody woocommerceBody) {
            mWoocommerceBody = woocommerceBody;
            if (!woocommerceBody.getName().equalsIgnoreCase("تیشرت جذاب تابستانه با تخفیف ویژه دیجیکالا!!!!!"))
                mNameTextView.setText(woocommerceBody.getName());
            mRegularPriceTextView.setText(woocommerceBody.getPrice() + "" + "تومان");
            mBudgetTextView.setText(woocommerceBody.getRegularPrice() + "تومان");
            if (woocommerceBody.getImages().size() > 0)
                Picasso.with(getActivity()).load(woocommerceBody.getImages().get(0).getSrc()).placeholder(R.drawable.digikala)
                        .into(mImageView);
        }

    }

    private class ProductAdaptor extends RecyclerView.Adapter<ProductHolder> {
        private List<WoocommerceBody> mWoocommerceBodies;

        public ProductAdaptor(List<WoocommerceBody> woocommerceBodies) {
            Log.e("recycler", "null");
            mWoocommerceBodies = woocommerceBodies;
            mAllProducts = woocommerceBodies;
        }

        public void setWoocommerceBodies(List<WoocommerceBody> woocommerceBodies) {
            Log.e("recycler", "Notnull");
            mWoocommerceBodies.addAll(woocommerceBodies);
            mAllProducts.addAll(woocommerceBodies);
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.products_linear_items, parent, false);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_FOR_SORT_DIALOG_FRAGMENT) {
            mListProductsRecycler.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            setSortTextView();
            if (state == 5) {
                requestForNewCategoriesProductsSort();
            } else {
                requestForNewSort();
            }
        }

    }

    private void requestForNewCategoriesProductsSort() {
        mProductAdaptor = null;
        switch (SharedPreferencesData.getRadioGroupId(getActivity())) {
            case 1:
                mQueries.put("orderby", "date");
                mQueries.put("order", "desc");
                mQueries.put("category", String.valueOf(categoryId));
                break;
            case 2:
                mQueries.put("orderby", "popularity");
                mQueries.put("order", "desc");
                mQueries.put("category", String.valueOf(categoryId));
                break;
            case 3:
                mQueries.put("orderby", "rating");
                mQueries.put("order", "desc");
                mQueries.put("category", String.valueOf(categoryId));
                break;
            case 4:
                mQueries.put("orderby", "price");
                mQueries.put("order", "desc");
                mQueries.put("category", String.valueOf(categoryId));
                break;
            case 5:
                mQueries.put("orderby", "price");
                mQueries.put("order", "asc");
                mQueries.put("category", String.valueOf(categoryId));
                break;
        }

        mListProductViewModel.loadSortedProducts(mQueries);

        mListProductViewModel.getSortedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
            @Override
            public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                if (woocommerceBodies != null) {

                    initAdaptor(woocommerceBodies);
                    mListProductsRecycler.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void requestForNewSort() {
        mProductAdaptor = null;
        switch (SharedPreferencesData.getRadioGroupId(getActivity())) {
            case 1:
                mQueries.put("orderby", "date");
                mQueries.put("order", "desc");
                break;
            case 2:
                mQueries.put("orderby", "popularity");
                mQueries.put("order", "desc");
                break;
            case 3:
                mQueries.put("orderby", "rating");
                mQueries.put("order", "desc");
                break;
            case 4:
                mQueries.put("orderby", "price");
                mQueries.put("order", "desc");
                break;
            case 5:
                mQueries.put("orderby", "price");
                mQueries.put("order", "asc");
                break;
        }
        if (state == 4) {
//            if (mProductAttributeData == null) {
//                Log.d("n", "state4");
//                mQueries.put("search", SharedPreferencesData.getQuery(getActivity()));
                mTextView.setVisibility(View.GONE);
//                mTextView.setVisibility(View.GONE);
//                mListProductViewModel.loadSearchedProducts(mQueries, 1);
//                initSearchObserver();
//            } else {
            if(mProductAttributeData==null) {
                mProductAttributeData = new ProductAttributeData();
            }
            mProductAttributeData.setSearch(SharedPreferencesData.getQuery(getActivity()));
            filterProductsByAttributes(mProductAttributeData);

        } else if (mProductAttributeData != null && state != 4) {
            filterProductsByAttributes(mProductAttributeData);
        } else {
            newestPage = 1;
            popularPage = 1;
            ratedPage = 1;
            Log.d("n", "stateNot4");
            mListProductViewModel.loadSortedProducts(mQueries);
            mListProductViewModel.getSortedProducts().observe(this, new Observer<List<WoocommerceBody>>() {
                @Override
                public void onChanged(List<WoocommerceBody> woocommerceBodies) {
                    if (woocommerceBodies != null) {
                        initAdaptor(woocommerceBodies);
                        mListProductsRecycler.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
