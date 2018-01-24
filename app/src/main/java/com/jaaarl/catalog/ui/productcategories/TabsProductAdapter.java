package com.jaaarl.catalog.ui.productcategories;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jaaarl.catalog.ui.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bohdan on 29.10.2017.
 */

public class TabsProductAdapter extends FragmentPagerAdapter {

    FragmentManager mFragmentManager;

    private Map<Integer, BaseFragment> tabsMap = new HashMap<>();

    TabsProductAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
        initTabsMap();
    }

    @Override
    public Fragment getItem(int position) {
        return tabsMap.get(position);
    }

    @Override
    public int getCount() {
        return tabsMap.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabsMap.get(position).getTitle();
    }

    private void initTabsMap() {
        tabsMap.put(0, ProductTabFragment.Companion.newInstance().setTitle("Beer"));
        tabsMap.put(1, ProductTabFragment.Companion.newInstance().setTitle("Wine"));
        tabsMap.put(2, ProductTabFragment.Companion.newInstance().setTitle("Spirits"));
    }
}
