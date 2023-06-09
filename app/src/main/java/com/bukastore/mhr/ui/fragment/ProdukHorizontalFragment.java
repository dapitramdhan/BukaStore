package com.bukastore.mhr.ui.fragment;

import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.bukastore.mhr.adapter.ProdukAdapter;
import com.bukastore.mhr.data.MPConstants;
import com.bukastore.mhr.databinding.ProdukHorizontalFragmentBinding;
import com.bukastore.mhr.model.Produk;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProdukHorizontalFragment extends Fragment {
	private ProdukHorizontalFragmentBinding binding;
	private List<Produk> listProduk = new ArrayList<>();
	private ProdukAdapter produkAdapter;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container, Bundle arg2) {
		binding = ProdukHorizontalFragmentBinding.inflate(inflater, container, false);
		setUpRecylerView();
		ambilDataProduk();
		return binding.getRoot();
	}

	private void setUpRecylerView() {
		
		produkAdapter = new ProdukAdapter(listProduk);
		binding.recyclerviewProdukHorizontal.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
		binding.recyclerviewProdukHorizontal.setAdapter(produkAdapter);
		produkAdapter.notifyDataSetChanged();
		binding.recyclerviewProdukHorizontal.setHasFixedSize(true);
		
	}

	private void ambilDataProduk() {
		
		String url = MPConstants.PRODUK_URL;
		RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {

								try {
									JSONArray jsonArray = response.getJSONArray("hits");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject hit = jsonArray.getJSONObject(i);
										String creatorName = hit.getString("tags");
										String imageUrl = hit.getString("webformatURL");
										int likeCount = hit.getInt("likes");
										listProduk.add(new Produk(imageUrl, creatorName, likeCount));
									}

									setUpRecylerView();

								} catch (JSONException e) {
									e.printStackTrace();

								}
							}
						}, 5000);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
		//VolleySingleton.getInstance().addToRequestQueue(jsonRequest);
		mRequestQueue.add(jsonRequest);
		
	}
}