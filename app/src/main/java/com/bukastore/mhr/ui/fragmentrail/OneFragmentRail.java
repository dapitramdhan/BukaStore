package com.bukastore.mhr.ui.fragmentrail;

import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.bukastore.mhr.databinding.FragmentRailOneBinding;

public class OneFragmentRail extends Fragment{
	private FragmentRailOneBinding binding;
	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		binding = FragmentRailOneBinding.inflate(arg0,arg1,false);
		return binding.getRoot();
	}
}