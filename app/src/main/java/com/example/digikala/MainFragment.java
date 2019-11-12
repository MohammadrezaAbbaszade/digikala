package com.example.digikala;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private ViewPager mViewPager;
    private CircleIndicator mDotsIndicator;
    private ImageView mImageView;

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
                        return R.drawable.ic_launcher_background;
                    case 1:
                        return R.drawable.ic_launcher_background;
                    case 2:
                        return R.drawable.ic_launcher_background;
                    case 3:
                        return R.drawable.ic_launcher_background;
                    default:
                        return R.drawable.ic_launcher_background;
                }
            }
        });
        mDotsIndicator.setViewPager(mViewPager);
        return view;
    }


        private void init (View view){
            mViewPager = view.findViewById(R.id.view_pager);
            mDotsIndicator = view.findViewById(R.id.dots_indicator);
        }
}