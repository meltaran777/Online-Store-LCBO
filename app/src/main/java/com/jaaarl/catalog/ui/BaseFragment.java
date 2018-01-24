package com.jaaarl.catalog.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Bohdan on 22.10.2017.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment implements IFragmentUiController {
    protected final String TAG = getClass().getName();

    protected ProgressDialog progressDialog;
    protected View     view;
    private   Unbinder unbinder;

    private String title;

    @LayoutRes
    protected abstract int getContentView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getContentView(), container, false);

        setHasOptionsMenu(true);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading ...");

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onFragmentOnScreen() {

    }

    protected void runOnUiThread(Runnable runnable) {
        if (getActivity() != null) getActivity().runOnUiThread(runnable);
    }

    protected void showProgress() {
        progressDialog.show();
    }

    protected void hideProgress() {
        progressDialog.hide();
    }

    public BaseFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }
}
