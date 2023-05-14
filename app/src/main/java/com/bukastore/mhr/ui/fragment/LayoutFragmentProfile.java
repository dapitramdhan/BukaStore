package com.bukastore.mhr.ui.fragment;

import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import com.bukastore.mhr.databinding.LayoutFragmentProfileBinding;

public class LayoutFragmentProfile extends Fragment {
	private LayoutFragmentProfileBinding binding;
	
	public static LayoutFragmentProfile newInstance(){
		return new LayoutFragmentProfile();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle arg2) {
		binding = LayoutFragmentProfileBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}
}