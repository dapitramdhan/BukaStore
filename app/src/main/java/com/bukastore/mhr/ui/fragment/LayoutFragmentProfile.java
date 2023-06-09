package com.bukastore.mhr.ui.fragment;

import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bukastore.mhr.R;
import com.bukastore.mhr.adapter.PROFILE.GridPengirimanAdapter;
import com.bukastore.mhr.databinding.LayoutFragmentProfileBinding;
import com.bukastore.mhr.model.GridPengirimanModel;
import java.util.ArrayList;
import java.util.List;

public class LayoutFragmentProfile extends Fragment {
	private LayoutFragmentProfileBinding binding;
	private List<GridPengirimanModel> list;
	private GridPengirimanAdapter adapter;

	public static LayoutFragmentProfile newInstance() {
		return new LayoutFragmentProfile();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle arg2) {
		binding = LayoutFragmentProfileBinding.inflate(inflater, container, false);
		recyclerPengiriman();
		return binding.getRoot();
	}

	private void recyclerPengiriman() {
		//binding.recyclerviewGridPengiriman.setHasFixedSize(true);
		binding.recyclerviewGridPengiriman.setLayoutManager(new GridLayoutManager(requireActivity(), 4));
		list = new ArrayList<>();
		list.add(new GridPengirimanModel(R.drawable.ic_wallet, "Belum Bayar"));
		list.add(new GridPengirimanModel(R.drawable.ic_cart, "Di Proses"));
		list.add(new GridPengirimanModel(R.drawable.ic_cart, "Di Kirim"));
		list.add(new GridPengirimanModel(R.drawable.ic_cart, "Selesai"));
		adapter = new GridPengirimanAdapter(requireContext(),list);
		binding.recyclerviewGridPengiriman.setAdapter(adapter);

	}
}