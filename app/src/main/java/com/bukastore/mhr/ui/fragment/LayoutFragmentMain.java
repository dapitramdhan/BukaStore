package com.bukastore.mhr.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;

import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bukastore.mhr.R;
import com.bukastore.mhr.adapter.ProdukAdapter;
import com.bukastore.mhr.adapter.SliderBannerAdapter;

import com.bukastore.mhr.custom.FaddingEffect.ObservableScrollable;
import com.bukastore.mhr.custom.FaddingEffect.OnScrollChangedCallback;
import com.bukastore.mhr.custom.FaddingEffect.ScrollFadeOnToolbar;
import com.bukastore.mhr.custom.SplitToolbar;
import com.bukastore.mhr.data.MPConstants;
import com.bukastore.mhr.databinding.LayoutFragmentMainBinding;
import com.bukastore.mhr.helper.TintHelper;
import com.bukastore.mhr.model.Produk;
import com.bukastore.mhr.model.SliderBanner;
import com.bukastore.mhr.ui.activity.ActivityChat;
import com.bukastore.mhr.ui.activity.ActivityDetailProduk;
import com.bukastore.mhr.ui.activity.SearchActivity;

import com.bukastore.mhr.utils.IsLoading;

import com.bukastore.mhr.utils.VolleySingleton;
import com.bukastore.mhr.viewmodel.NetworkViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LayoutFragmentMain extends Fragment
		implements OnScrollChangedCallback, ProdukAdapter.OnItemClickListener, View.OnClickListener {
	private static final String TAG = "LayoutFragmentMain";

	private LayoutFragmentMainBinding binding;
	private View view, mHeader;
	private Drawable toolbarBackground;
	private SplitToolbar toolbar;
	private RecyclerView recyclerView_produk;
	private ProgressBar progress;
	private ProgressDialog progressDialog;
	private MaterialCardView cardViewSearch;
	private SwipeRefreshLayout swipeRefreshLayout;
	private CoordinatorLayout rootLayout;
	private LinearLayout containerShimmer,containerMain;
	private List<SliderBanner> sliderImageList;
	private List<Produk> listProduk = new ArrayList<>();
	private ProdukAdapter produkAdapter;
	private ShimmerFrameLayout shimmerFrameLayout;
	private FloatingActionButton backToTop;

	private RequestQueue mRequestQueue;

	ObservableScrollable scrollable;
	ScrollFadeOnToolbar nested;
	final IsLoading isLoading = new IsLoading();
	private int pageNumber = 1;
	private int offset = 20;
	private boolean hasMore = false;
	private boolean firstTime = true;
	private boolean isConnected;

	public static LayoutFragmentMain newInstance() {
		return new LayoutFragmentMain();
	}

	private final Observer<Boolean> activeNetworkStateObserver = new Observer<Boolean>() {
		@Override
		public void onChanged(Boolean isConnected) {
			showErrorConnectionInternet(isConnected);
		}
	};

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Window window = requireActivity().getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle arg2) {
		binding = LayoutFragmentMainBinding.inflate(inflater, container, false);

		toolbar = binding.toolbar;
		toolbar.inflateMenu(R.menu.menu_main);

		((AppCompatActivity) getActivity()).getDelegate().setSupportActionBar(toolbar);
		toolbar.setTitle("");

		recyclerView_produk = binding.includeRecyclerviewProduk.recyclerviewProduk;
		mHeader = binding.includeSliderBanner.slider;
		progress = binding.includeRecyclerviewProduk.progress;
		swipeRefreshLayout = binding.swipperRefresh;
		cardViewSearch = binding.cardviewSearch;
		shimmerFrameLayout = binding.shimmerLayout;
		backToTop = binding.includeRecyclerviewProduk.backToTop;
		containerShimmer = binding.containerShimmer;
		containerMain = binding.containerMain;

		cardViewSearch.setOnClickListener(this::setUpSearchBar);
		// cek apakah ada koneksi internet
		NetworkViewModel.getInstance().getNetworkConnectivityStatus().observe(requireActivity(),
				activeNetworkStateObserver);
		// 
		getChildFragmentManager().beginTransaction()
				.replace(R.id.fragment_produk_horizontal, new ProdukHorizontalFragment()).commit();
		getChildFragmentManager().beginTransaction().replace(R.id.fragment_icon_kategori, new IconKategoriFragment())
				.commit();
		setUpBeforeToolbarBackground();
		setUpSliderBanner();
		initializeWidgets();
		setUpRecyclerView();
		onScroll(0, 0);
		setHasOptionsMenu(true);
		refresh();
		return binding.getRoot();
	}

	private void showErrorConnectionInternet(boolean isConnected) {
		if (isConnected) {
			//swipeRefreshLayout.setRefreshing(false);
			//getDataProduk(pageNumber, offset);
		} else {
			/*	
			shimmerFrameLayout.setVisibility(View.VISIBLE);
			shimmerFrameLayout.startShimmer();
			recyclerView_produk.setVisibility(View.GONE);
			listProduk.clear();
			recyclerView_produk.removeAllViewsInLayout();
			*/

		}
	}

	private void setUpSearchBar(View view) {
		startActivity(new Intent(getActivity(), SearchActivity.class));
		getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	private void setUpRecyclerView() {
		
		
		produkAdapter = new ProdukAdapter(listProduk);
		recyclerView_produk.setAdapter(produkAdapter);
		produkAdapter.notifyDataSetChanged();
		produkAdapter.setOnItemClickListener(LayoutFragmentMain.this);
		recyclerView_produk.setHasFixedSize(true);
		recyclerView_produk.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if (!recyclerView.canScrollVertically(1)) {
					if (hasMore) {
						isLoading.setLoading(true);
						pageNumber += 1;
						getDataProduk(pageNumber, offset);
					} else {
						if (isLoading.isLoading())
							isLoading.setLoading(false);
					}
				}
			}
		});
		firstTime = false;
	}

	private void initializeWidgets() {
		isLoading.setListener(new IsLoading.OnLoadingListener() {
			@Override
			public void onChange() {
				if (isLoading.isLoading()) {
					Log.d(TAG, "onChange: is loading");
					progress.setVisibility(View.VISIBLE);
				} else {
					Log.d(TAG, "onChange: not loading");
					progress.setVisibility(View.GONE);
				}
			}
		});
		getDataProduk(pageNumber, offset); // List Produk
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_main, menu);
		MenuItem item = menu.findItem(R.id.action_cart);
		LayerDrawable icon = (LayerDrawable) item.getIcon();
		TintHelper.setBadgeCount(requireActivity(), icon, "8");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_chat:
			startActivity(new Intent(requireActivity(), ActivityChat.class));
			//getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void setUpSliderBanner() {
		SliderView sliderView = binding.includeSliderBanner.slider;
		sliderImageList = new ArrayList<>();
		sliderImageList.add(new SliderBanner(MPConstants.url1));
		sliderImageList.add(new SliderBanner(MPConstants.url2));
		sliderImageList.add(new SliderBanner(MPConstants.url1));
		sliderImageList.add(new SliderBanner(MPConstants.url4));
		sliderImageList.add(new SliderBanner(MPConstants.url5));
		SliderBannerAdapter sliderImageAdapter = new SliderBannerAdapter(requireActivity(), sliderImageList);
		sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
		//sliderView.setSliderTransformAnimation(SliderAnimations.);
		sliderView.setSliderAdapter(sliderImageAdapter);
		sliderView.setScrollTimeInSec(3);
		sliderView.setAutoCycle(true);
		sliderView.startAutoCycle();
	}

	@Override
	public void onScroll(int l, int t) {
		int headerHeight = mHeader.getHeight() - toolbar.getHeight() - ViewCompat.getMinimumHeight(mHeader);
		float afterAlpha = Math.min(Math.max(t, 2f), headerHeight) / headerHeight;

		setUpAfterToolbarBackground(afterAlpha);
	}

	private void setUpBeforeToolbarBackground() {
		toolbarBackground = getResources().getDrawable(R.drawable.warna_utama);
		toolbar.setBackgroundDrawable(toolbarBackground);
		scrollable = (ObservableScrollable) binding.scrollFade;
		scrollable.setOnScrollChangedCallback(this);
	}

	private void setUpAfterToolbarBackground(float verticalOffset) {
		int newAlpha = (int) (verticalOffset * 255);
		toolbarBackground.setAlpha(newAlpha);
		toolbar.setBackground(toolbarBackground);
	}

	// somewhere after views have been set.

	private void getDataProduk(int pageNumber, int offset) {
		String url = MPConstants.PRODUK_URL;
		mRequestQueue = Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								recyclerView_produk.setVisibility(View.VISIBLE);
								containerMain.setVisibility(View.VISIBLE);
								shimmerFrameLayout.stopShimmer();
								shimmerFrameLayout.setVisibility(View.GONE);

								swipeRefreshLayout.setRefreshing(false);
								if (isLoading.isLoading())
									isLoading.setLoading(false);
								Log.d(TAG, "onResponse: response = " + response);

								try {
									JSONArray jsonArray = response.getJSONArray("hits");
									for (int i = 0; i < offset; i++) {
										JSONObject hit = jsonArray.getJSONObject(i);
										String creatorName = hit.getString("tags");
										String imageUrl = hit.getString("webformatURL");
										int likeCount = hit.getInt("likes");
										listProduk.add(new Produk(imageUrl, creatorName, likeCount));
									}

									if (firstTime)
										setUpRecyclerView();

								} catch (JSONException e) {
									e.printStackTrace();
									Snackbar.make(binding.rootLayout, "Gagal menampilkan data", Snackbar.LENGTH_SHORT)
											.show();
								}
							}
						}, 5000);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						swipeRefreshLayout.setRefreshing(false);
						if (isLoading.isLoading())
							isLoading.setLoading(false);
						Log.d(TAG, "onErrorResponse: Volley Error = " + error);
					}
				});
		//VolleySingleton.getInstance().addToRequestQueue(jsonRequest);
		mRequestQueue.add(jsonRequest);
	}

	@Override
	public void onClick(View v) {

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

	private void refresh() {
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {

				shimmerFrameLayout.setVisibility(View.VISIBLE);
				shimmerFrameLayout.startShimmer();
				containerMain.setVisibility(View.GONE);
				recyclerView_produk.setVisibility(View.GONE);
				listProduk.clear();
				recyclerView_produk.removeAllViewsInLayout();
				getDataProduk(pageNumber, offset);

			}
		});
	}

	public void onResume() {
		super.onResume();
		shimmerFrameLayout.startShimmer();
	}

	public void onPause() {
		super.onPause();
		shimmerFrameLayout.stopShimmer();
	}

}