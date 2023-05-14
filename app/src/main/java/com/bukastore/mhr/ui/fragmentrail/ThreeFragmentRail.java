package com.bukastore.mhr.ui.fragmentrail;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.bukastore.mhr.databinding.FragmentRailTreeBinding;

public class ThreeFragmentRail extends Fragment{
	FragmentRailTreeBinding binding;
	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		binding = FragmentRailTreeBinding.inflate(arg0,arg1,false);
		return binding.getRoot();
	}
}