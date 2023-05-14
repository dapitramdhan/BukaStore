package com.bukastore.mhr.ui.fragment;

import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.bukastore.mhr.databinding.LayoutFragmentPesananBinding;

public class LayoutFragmentPesanan extends Fragment {
	private LayoutFragmentPesananBinding binding;
	
	public static LayoutFragmentPesanan newInstance(){
		return new LayoutFragmentPesanan();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle arg2) {
		binding = LayoutFragmentPesananBinding.inflate(inflater, container, false);
		((AppCompatActivity) getActivity()).getDelegate().setSupportActionBar(binding.toolbar);
		return binding.getRoot();
	}
}