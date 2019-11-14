package com.example.digikala.fragments;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.example.digikala.ProductRecyclerView;
import com.example.digikala.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private ViewPager mViewPager;
    private CircleIndicator mDotsIndicator;
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private List<String> mStrings=new ArrayList<>();
    private ProductAdaptor mProductAdaptor;
    private RecyclerView mRecyclerView2;
    private List<String> mStrings2=new ArrayList<>();
    private ProductRecyclerView mNewestProductAdaptor;
    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        mStrings.add("کالای دیجیتال");
        mStrings.add("آرایشی، بهداشتی و سلامت");
        mStrings.add("خودرو، ابزار و اداری");
        mStrings.add("مد و پوشاک");
        mStrings.add("خانه و آشپزخانه");
        mStrings.add("کتاب، لوازم التحریر و هنر");
        mStrings.add("اسباب بازی، کودک و نوزاد");
        mStrings.add("ورزش و سفر");
        mStrings.add("خوردنی و آشامیدنی");
        mStrings.add("کارت هدیه");

        mStrings2.add("دوربین");
        mStrings2.add("گوشی");
        mStrings2.add("خودرو");
        mStrings2.add(" پوشاک");
        mStrings2.add("توپ");
        mStrings2.add("کتاب");
        mStrings2.add("اسباب بازی");
        mStrings2.add("کتونی");
        mStrings2.add("چیپس");
        mStrings2.add("کارت هدیه");
        mNewestProductAdaptor=new ProductRecyclerView(mStrings2,getContext());
        mRecyclerView2.setAdapter(mNewestProductAdaptor);
        mProductAdaptor=new ProductAdaptor(mStrings);
        mRecyclerView.setAdapter(mProductAdaptor);
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
        mRecyclerView = view.findViewById(R.id.fragment_main_recycler);
        mRecyclerView2=view.findViewById(R.id.fragment_main_newest_product_recycler);

    }

//    private class NewestProductHolder extends RecyclerView.ViewHolder {
//        private TextView mTextView;
//
//        public NewestProductHolder(@NonNull View itemView) {
//            super(itemView);
//            mTextView = itemView.findViewById(R.id.list_newest_products_text_view);
//
//        }
//
//
//    }
//
//    private class NewestProductAdaptor extends RecyclerView.Adapter<NewestProductHolder> {
//        private List<String> mToDoList;
//
//        public NewestProductAdaptor(List<String> toDoList) {
//            mToDoList = toDoList;
//        }
//
//        @NonNull
//        @Override
//        public NewestProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_newest_products, parent, false);
//            NewestProductHolder newestProductHolder = new NewestProductHolder(view);
//            return newestProductHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull NewestProductHolder holder, int position) {
//            holder.mTextView.setText(mToDoList.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return mToDoList.size();
//        }
//    }
    private class ProductHolder extends RecyclerView.ViewHolder {
        private Button mButton;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.product_items);

        }


    }

    private class ProductAdaptor extends RecyclerView.Adapter<ProductHolder> {
        private List<String> mToDoList;

        public ProductAdaptor(List<String> toDoList) {
            mToDoList = toDoList;
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
            holder.mButton.setText(mToDoList.get(position));
        }

        @Override
        public int getItemCount() {
            return mToDoList.size();
        }
    }
}