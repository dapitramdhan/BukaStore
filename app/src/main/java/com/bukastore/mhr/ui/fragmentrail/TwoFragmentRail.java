package com.bukastore.mhr.ui.fragmentrail;

import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import com.bukastore.mhr.databinding.FragmentRailTwoBinding;

public class TwoFragmentRail extends Fragment{
	private FragmentRailTwoBinding binding;
	
	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		binding = FragmentRailTwoBinding.inflate(arg0,arg1,false);
		return binding.getRoot();
	}
}