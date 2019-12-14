package com.example.digikala.views.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.example.digikala.R;
import com.example.digikala.RecyclersViews.utils.SharedPreferencesData;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortDialogFragment extends DialogFragment {
    private static final String RADIO_ID = "radioid";
    private RadioGroup mRadioGroup;
    private int mRadioId;

    public static SortDialogFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(RADIO_ID, id);
        SortDialogFragment fragment = new SortDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SortDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNetworkConnected()) {
            getActivity().finish();
            Log.d("tag", "finished");
        }else {
            mRadioId = getArguments().getInt(RADIO_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sort_dialog, null, false);
        init(view);
        final AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        switch (mRadioId) {
            case 1:
                mRadioGroup.check(R.id.newest_check_box);

                break;
            case 2:
                mRadioGroup.check(R.id.rated_check_box);

                break;
            case 3:
                mRadioGroup.check(R.id.visited_check_box);

                break;
            case 4:
                mRadioGroup.check(R.id.high_to_low_check_box);

                break;
            case 5:
                mRadioGroup.check(R.id.low_to_high_check_box);

                break;
            default:
                mRadioGroup.check(R.id.visited_check_box);
                SharedPreferencesData.setRadioGroupId(getActivity(), 0);
        }
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d("default",String.valueOf(i));
                switch (i) {
                    case R.id.newest_check_box:
                        SharedPreferencesData.setRadioGroupId(getActivity(), 1);
                        break;
                    case R.id.rated_check_box:
                        SharedPreferencesData.setRadioGroupId(getActivity(), 2);
                        break;
                    case R.id.visited_check_box:
                        SharedPreferencesData.setRadioGroupId(getActivity(), 3);
                        break;
                    case R.id.high_to_low_check_box:
                        SharedPreferencesData.setRadioGroupId(getActivity(), 4);
                        break;
                    case R.id.low_to_high_check_box:
                        SharedPreferencesData.setRadioGroupId(getActivity(), 5);
                        break;
                }
                sendResult();
                alertDialog.dismiss();
            }
        });


        return alertDialog;
    }

    private void init(View view) {
        mRadioGroup = view.findViewById(R.id.sort_dialog_fragment_radio_group);
    }
    private void sendResult() {
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
