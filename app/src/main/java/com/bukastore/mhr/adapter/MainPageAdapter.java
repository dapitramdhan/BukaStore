package com.bukastore.mhr.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.bukastore.mhr.ui.fragment.LayoutFragmentFeed;
import com.bukastore.mhr.ui.fragment.LayoutFragmentKategori;
import com.bukastore.mhr.ui.fragment.LayoutFragmentMain;
import com.bukastore.mhr.ui.fragment.LayoutFragmentPesanan;
import com.bukastore.mhr.ui.fragment.LayoutFragmentProfile;
import java.util.ArrayList;
import java.util.List;

public class MainPageAdapter extends FragmentPagerAdapter {

	private final List<Fragment> mFragmentList = new ArrayList<>();
	private String[] tabTitle = new String[] { "Semua Kategori", "Rumah Tangga","Peralatan Dapur" };

	public MainPageAdapter(FragmentManager manager) {
		super(manager);
	}

	@Override
	public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return tabTitle[position];
	}

	public void addFragment(Fragment fragment) {
		mFragmentList.add(fragment);
	}

}