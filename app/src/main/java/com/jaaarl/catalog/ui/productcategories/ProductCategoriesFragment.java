package com.jaaarl.catalog.ui.productcategories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaaarl.catalog.R;
import com.jaaarl.catalog.ui.BaseFragment;

import butterknife.BindView;

/**
 * Created by Bohdan on 22.10.2017.
 */

public class ProductCategoriesFragment extends BaseFragment {

    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;


    public static BaseFragment newInstance() {
        return new ProductCategoriesFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.product_by_categories_fragment_layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.d(TAG, "onViewCreated: ProductCategoriesFragment");

        viewPager.setOffscreenPageLimit(2);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d(TAG, "onPageSelected: " + position);
            }
        });

        TabsProductAdapter adapter = new TabsProductAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
