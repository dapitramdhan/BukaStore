package com.bukastore.mhr.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.bukastore.mhr.adapter.MainPageAdapter;
import com.bukastore.mhr.databinding.LayoutFragmentKategoriBinding;
import android.view.View;
import com.bukastore.mhr.R;
import com.bukastore.mhr.ui.fragmentrail.OneFragmentRail;
import com.bukastore.mhr.ui.fragmentrail.ThreeFragmentRail;
import com.bukastore.mhr.ui.fragmentrail.TwoFragmentRail;

public class LayoutFragmentKategori extends Fragment {
	private LayoutFragmentKategoriBinding binding;

	public static LayoutFragmentKategori newInstance() {
		return new LayoutFragmentKategori();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle arg2) {

		binding = LayoutFragmentKategoriBinding.inflate(inflater, container, false);
		((AppCompatActivity) getActivity()).getDelegate().setSupportActionBar(binding.toolbar);
		setUpViewPager();
		return binding.getRoot();
	}

	private void setUpViewPager() {
		MainPageAdapter mainPageAdapter = new MainPageAdapter(getChildFragmentManager());
		mainPageAdapter.addFragment(new OneFragmentRail());
		mainPageAdapter.addFragment(new TwoFragmentRail());
		mainPageAdapter.addFragment(new ThreeFragmentRail());
		binding.viewPager.setAdapter(mainPageAdapter);
		binding.tabLayout.setupWithViewPager(binding.viewPager);
	}
	
	private void hideToltip(){
		//for (int i = 0; i < binding.tabLayout.getm)
	}

}