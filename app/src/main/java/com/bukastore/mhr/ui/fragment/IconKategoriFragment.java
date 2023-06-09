package com.bukastore.mhr.ui.fragment;

import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bukastore.mhr.adapter.IconKategoriAdapter;
import com.bukastore.mhr.data.MPConstants;

import com.bukastore.mhr.databinding.HomeIconFragmentBinding;
import com.bukastore.mhr.model.IconKategoriModel;
import java.util.ArrayList;
import java.util.List;

public class IconKategoriFragment extends Fragment{
	private HomeIconFragmentBinding binding;
	private IconKategoriAdapter iconKategoriAdapter;
	private List<IconKategoriModel> listIcon;
	
	public static IconKategoriFragment newInstance(){
		return new IconKategoriFragment();
	}
	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		binding = HomeIconFragmentBinding.inflate(arg0,arg1,false);
		setUpRecyclerView();
		return binding.getRoot();
	}
	
	private void setUpRecyclerView(){
		binding.recyclerviewIconKategori.setHasFixedSize(true);
		binding.recyclerviewIconKategori.setLayoutManager(new GridLayoutManager(requireActivity(),5));
		listIcon = new ArrayList<>();
		listIcon.add(new IconKategoriModel(MPConstants.url1,"Games"));
		listIcon.add(new IconKategoriModel(MPConstants.url2,"Pulsa"));
		listIcon.add(new IconKategoriModel(MPConstants.url1,"Pakaian"));
		listIcon.add(new IconKategoriModel(MPConstants.url2,"Rumah Tangga"));
		listIcon.add(new IconKategoriModel(MPConstants.url1,"Wifi"));
		listIcon.add(new IconKategoriModel(MPConstants.url2,"Sepatu"));
		listIcon.add(new IconKategoriModel(MPConstants.url1,"Peralatan Masak"));
		listIcon.add(new IconKategoriModel(MPConstants.url2,"tes"));
		listIcon.add(new IconKategoriModel(MPConstants.url1,"tes"));
		listIcon.add(new IconKategoriModel(MPConstants.url2,"tes"));
		iconKategoriAdapter = new IconKategoriAdapter(requireContext(),listIcon);
		binding.recyclerviewIconKategori.setAdapter(iconKategoriAdapter);
	}
}