package com.bukastore.mhr.ui.HOME;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bukastore.mhr.R;
import androidx.fragment.app.Fragment;
import com.bukastore.mhr.custom.FaddingEffect.ObservableScrollable;
import com.bukastore.mhr.helper.TintHelper;
import com.bukastore.mhr.custom.FaddingEffect.OnScrollChangedCallback;
import com.bukastore.mhr.data.MPConstants;
import com.bukastore.mhr.adapter.SliderBannerAdapter;
import com.bukastore.mhr.ui.activity.SearchActivity;
import com.bukastore.mhr.ui.fragment.IconKategoriFragment;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.bukastore.mhr.databinding.LayoutFragmentHomeBinding;
import java.util.List;
import com.bukastore.mhr.model.SliderBanner;
import java.util.ArrayList;
import com.smarteist.autoimageslider.SliderView;

public class LayoutFragmentHome extends Fragment implements OnScrollChangedCallback {

	private LayoutFragmentHomeBinding binding;
	private View mHeader;
	private Drawable toolbarBackground;
	private SliderBannerAdapter sliderBannerAdapter;
	private List<SliderBanner> sliderList;
	private ObservableScrollable scrollable;
	private Toolbar toolbar;

	public static LayoutFragmentHome newInstance() {
		return new LayoutFragmentHome();
	}

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Window window = getActivity().getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle arg2) {
		binding = LayoutFragmentHomeBinding.inflate(inflater, container, false);

		((AppCompatActivity) getActivity()).getDelegate().setSupportActionBar(binding.toolbar);
		binding.toolbar.setTitle("");

		mHeader = binding.sliderBanner.slider;
		toolbar = binding.toolbar;

		setUpSliderHome();
		setUpFragment();
		refresh();
		setUpBeforeToolbarBackground();
		onScroll(-1, 0);

		setHasOptionsMenu(true); // menu toolbar
		
		binding.cardviewSearch.setOnClickListener(this::setUpSearchBar);
		return binding.getRoot();
	}
	
	private void setUpSearchBar(View view) {
		startActivity(new Intent(getActivity(), SearchActivity.class));
		getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	private void setUpSliderHome() {
		SliderView sliderView = binding.sliderBanner.slider;
		sliderList = new ArrayList<>();
		sliderList.add(new SliderBanner(MPConstants.url1));
		sliderList.add(new SliderBanner(MPConstants.url2));
		sliderList.add(new SliderBanner(MPConstants.url1));
		sliderList.add(new SliderBanner(MPConstants.url4));
		sliderList.add(new SliderBanner(MPConstants.url5));

		sliderBannerAdapter = new SliderBannerAdapter(requireActivity(), sliderList);
		sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
		//sliderView.setSliderTransformAnimation(SliderAnimations.TOSSTRANSFORMATION);
		sliderView.setSliderAdapter(sliderBannerAdapter);
		sliderView.setScrollTimeInSec(3);
		sliderView.setAutoCycle(true);
		sliderView.startAutoCycle();
	}

	private void setUpFragment() {
		getChildFragmentManager().beginTransaction()
				.replace(R.id.fragment_container_icon_kategori, IconKategoriFragment.newInstance()).commit();

		getChildFragmentManager().beginTransaction()
				.replace(R.id.fragment_container_produk, ProdukFragment.newInstance()).commit();
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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_main, menu);
		MenuItem item = menu.findItem(R.id.action_cart);
		LayerDrawable icon = (LayerDrawable) item.getIcon();
		TintHelper.setBadgeCount(requireActivity(), icon, "8");
		super.onCreateOptionsMenu(menu, inflater);
	}

	private void refresh() {
		binding.swiperRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.md_theme_light_onSurface);
		binding.swiperRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {

			}
		});
	}


}