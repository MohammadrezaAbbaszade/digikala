package com.example.digikala.views.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.CommentsRcyclerView;
import com.example.digikala.model.reviewsmodels.ReviewBody;
import com.example.digikala.viewmodels.CommentsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment {
    private static final String PRODUCT_ID = "productId";
    private CommentsViewModel mCommentsViewModel;
    private int productId;
    private TextView emptyComment;
    private RelativeLayout parentRelative;
    private ProgressBar mProgressBar;
    private RecyclerView mCommentsRecyclerView;
    private CommentsRcyclerView mCommentsAdapter;

    public static CommentsFragment newInstance(int productId) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        productId = getArguments().getInt(PRODUCT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        init(view);
        mProgressBar.setVisibility(View.VISIBLE);
        mCommentsViewModel.getComments(productId).observe(this, new Observer<List<ReviewBody>>() {
            @Override
            public void onChanged(List<ReviewBody> reviewBodies) {
                if (reviewBodies.isEmpty()) {
                    emptyComment.setVisibility(View.VISIBLE);
                    parentRelative.setVisibility(View.GONE);
                } else {
                    emptyComment.setVisibility(View.GONE);
                }
                mProgressBar.setVisibility(View.GONE);
                initRecyclerView(reviewBodies);
            }
        });

        return view;
    }

    private void init(View view) {
        emptyComment = view.findViewById(R.id.empty_comment);
        parentRelative = view.findViewById(R.id.parent_relative);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mCommentsRecyclerView = view.findViewById(R.id.comments_recyclerView);

    }

    private void initRecyclerView(List<ReviewBody> reviewBodies) {
        mCommentsAdapter= new CommentsRcyclerView(getActivity(), reviewBodies);
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommentsRecyclerView.setAdapter(mCommentsAdapter);
    }
}
