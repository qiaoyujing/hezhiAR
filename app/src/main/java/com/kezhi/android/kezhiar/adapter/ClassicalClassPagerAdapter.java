 package com.kezhi.android.kezhiar.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.fragment.ARModelFragment;
import com.kezhi.android.kezhiar.fragment.ExploreFragment;
import com.kezhi.android.kezhiar.fragment.HighQualityClassFragment;
import com.kezhi.android.kezhiar.fragment.ShortVideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:10:31
 * Email: song511653502@gmail.com
 * Description: ClassicalClass中ViewPager的适配器
 */

public class ClassicalClassPagerAdapter extends FragmentPagerAdapter {

    private List<String> mFragmentTitles;
    private List<Fragment> mFragments;

    public ClassicalClassPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mFragmentTitles = getFragmentTitles(context);
        mFragments = getFragments();
    }

    public List<String> getFragmentTitles(Context context) {
        List<String> fragmentTitles = new ArrayList<>();
        Resources resources = context.getResources();
        fragmentTitles.add(resources.getString(R.string.ar_model));
        fragmentTitles.add(resources.getString(R.string.short_video));
        fragmentTitles.add(resources.getString(R.string.high_quality_class));
        fragmentTitles.add(resources.getString(R.string.explore));
        return fragmentTitles;
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ARModelFragment.newInstance());
        fragments.add(ShortVideoFragment.newInstance());
        fragments.add(HighQualityClassFragment.newInstance());
        fragments.add(ExploreFragment.newInstance());
        return fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
