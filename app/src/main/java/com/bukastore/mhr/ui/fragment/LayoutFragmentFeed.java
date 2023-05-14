package com.bukastore.mhr.ui.fragment;

import android.view.View;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.bukastore.mhr.databinding.LayoutFragmentFeedBinding;

public class LayoutFragmentFeed extends Fragment {
	private LayoutFragmentFeedBinding binding;
	
	public static LayoutFragmentFeed newInstance(){
		return new LayoutFragmentFeed();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle arg2) {
		binding = LayoutFragmentFeedBinding.inflate(inflater, container, false);
		((AppCompatActivity) getActivity()).getDelegate().setSupportActionBar(binding.toolbar);
		return binding.getRoot();
	}
}