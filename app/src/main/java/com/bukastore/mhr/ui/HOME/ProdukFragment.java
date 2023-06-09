package com.bukastore.mhr.ui.HOME;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bukastore.mhr.adapter.ProdukAdapter;
import com.bukastore.mhr.data.MPConstants;
import com.bukastore.mhr.databinding.HomeProdukFragmentBinding;
import com.bukastore.mhr.ui.activity.ActivityDetailProduk;
import com.bukastore.mhr.model.Produk;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProdukFragment extends Fragment implements ProdukAdapter.OnItemClickListener {
	private static final String TAG = "ProdukFragment";
	private HomeProdukFragmentBinding binding;
	private List<Produk> listProduk = new ArrayList<>();
	private boolean firstTime = true;

	public static ProdukFragment newInstance() {
		return new ProdukFragment();
	}

	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		binding = HomeProdukFragmentBinding.inflate(arg0, arg1, false);
		getDataProduk();
		setUpRecyclerView();
		return binding.getRoot();
	}

	private void setUpRecyclerView() {
		ProdukAdapter produkAdapter = new ProdukAdapter(listProduk);
		produkAdapter.notifyDataSetChanged();
		produkAdapter.setOnItemClickListener(ProdukFragment.this);
		binding.recyclerviewProduk.setAdapter(produkAdapter);
		binding.recyclerviewProduk.setHasFixedSize(true);
		
		//firstTime = false;
	}

	private void getDataProduk() {
		String url = MPConstants.PRODUK_URL;
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								binding.shimmerLayout.stopShimmer();
								binding.shimmerLayout.setVisibility(View.GONE);
								binding.recyclerviewProduk.setVisibility(View.VISIBLE);
								try {
									JSONArray jsonArray = response.getJSONArray("hits");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject hit = jsonArray.getJSONObject(i);
										String creatorName = hit.getString("tags");
										String imageUrl = hit.getString("webformatURL");
										int likeCount = hit.getInt("likes");
										listProduk.add(new Produk(imageUrl, creatorName, likeCount));
									}

									setUpRecyclerView();

								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}, 3000);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(TAG, "onErrorResponse: Volley Error = " + error);
					}
				});
		requestQueue.add(jsonObjectRequest);
	}

	@Override
	public void onItemClick(int position) {
		Intent detail = new Intent(getActivity(), ActivityDetailProduk.class);
		Produk clickItem = listProduk.get(position);
		detail.putExtra(MPConstants.IMAGE_URL, clickItem.getImageProduk());
		detail.putExtra(MPConstants.NAME_URL, clickItem.getNamaProduk());
		detail.putExtra(MPConstants.HARGA_URL, clickItem.getHargaProduk());
		startActivity(detail);
	}

	@Override
	public void onResume() {
		super.onResume();
		binding.shimmerLayout.startShimmer();
	}

	@Override
	public void onPause() {
		super.onPause();
		binding.shimmerLayout.stopShimmer();
	}
}