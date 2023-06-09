package com.bukastore.mhr.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.bukastore.mhr.R;
import com.bukastore.mhr.custom.FaddingEffect.ObservableScrollable;
import com.bukastore.mhr.custom.FaddingEffect.OnScrollChangedCallback;
import com.bukastore.mhr.data.MPConstants;
import com.bukastore.mhr.databinding.ActivityDetailProdukBinding;
import com.bukastore.mhr.helper.TintHelper;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class ActivityDetailProduk extends AppCompatActivity implements OnScrollChangedCallback {

	ActivityDetailProdukBinding binding;
	private Drawable toolbarBackground, tintMenuIcon;
	private Drawable tintIconBack, ovalIcon, tintIconShare, tintIconCart, tintIconMore;
	private ObservableScrollable scrollable;
	private View mHeader;
	private ImageView gambarProduk;
	private MaterialCardView cardViewSearch;
	private TextView namaProduk, hargaProduk;
	private Menu mMenu;
	public static Context context;
	private int mLastVerticalOffset = 0;

	Drawable mOriginalOverflowIcon;
	AppCompatImageView mOverflowView;
	ImageButton btnBack, btnShare, btnCart, btnMore;
	FrameLayout frameBtnBack;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		binding = ActivityDetailProdukBinding.inflate(getLayoutInflater());
		View view = binding.getRoot();
		Window window = this.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(view);

		context = getApplicationContext();
		mHeader = binding.gambarProdukDetail;
		gambarProduk = binding.gambarProdukDetail;
		namaProduk = binding.namaProdukDetail;
		hargaProduk = binding.hargaProdukDetail;
		cardViewSearch = binding.cardviewSearch;
		btnBack = binding.btnBack;
		btnShare = binding.btnShare;
		btnCart = binding.btnCart;
		btnMore = binding.btnMore;
		setSupportActionBar(binding.toolbar);

		Intent intent = getIntent();
		String imgUrl = intent.getStringExtra(MPConstants.IMAGE_URL);
		String namaProdukDetail = intent.getStringExtra(MPConstants.NAME_URL);
		int hargaProdukDetail = intent.getIntExtra(MPConstants.HARGA_URL, 0);
		Glide.with(this).load(imgUrl).centerCrop().into(gambarProduk);
		namaProduk.setText(namaProdukDetail);
		hargaProduk.setText("Rp. " + hargaProdukDetail);
		tintIconBack = getResources().getDrawable(R.drawable.ic_arrow_back_white);
		tintIconShare = getResources().getDrawable(R.drawable.ic_share);
		tintIconCart = getResources().getDrawable(R.drawable.ic_cart);
		tintIconMore = getResources().getDrawable(R.drawable.ic_more);
		ovalIcon = getResources().getDrawable(R.drawable.oval_rounded);

		binding.btnBack.setOnClickListener(v -> this.onBackPressed());
		setUpBeforeToolbarBackground();
		onScroll(0, 0);

		cardViewSearch.setOnClickListener(this::setUpSearchBar);
	}

	/*
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_detail, menu);
		MenuItem item = menu.findItem(R.id.action_cart);
		LayerDrawable icon = (LayerDrawable) item.getIcon();
		TintHelper.setBadgeCount(this, icon, "8");
		//binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
		//binding.toolbar.setNavigationOnClickListener(v -> this.onBackPressed());
		return true;
	}
	*/

	private void setUpSearchBar(View view) {
		startActivity(new Intent(this, SearchActivity.class));
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	// Toolbar Fade On Scrolling
	@Override
	public void onScroll(int l, int t) {
		View decor = getWindow().getDecorView(); // status bar white to black
		int headerHeight = mHeader.getHeight() - binding.toolbar.getHeight();
		float afterAlpha = Math.min(Math.max(t, 0f), headerHeight) / headerHeight;
		int tintColor = ColorUtils.blendARGB(ContextCompat.getColor(this, R.color.white),
				ContextCompat.getColor(this, R.color.colorPrimary), afterAlpha);
		//
		int tintColor1 = ColorUtils.blendARGB(ContextCompat.getColor(this, R.color.gray_alpha),
				ContextCompat.getColor(this, R.color.color_transparent), afterAlpha);
		// Tint Navigation Icon
		if (tintMenuIcon == null) {
			tintMenuIcon = binding.toolbar.getNavigationIcon();
		}
		if (tintMenuIcon != null) {
			binding.toolbar.setNavigationIcon(TintHelper.createTintedDrawable(tintMenuIcon, tintColor));
		}
		// Set Alpha 0 SearchView and StatusBar
		if (t == 0) {
			cardViewSearch.setAlpha(0);		
			decor.setSystemUiVisibility(0);
		} else {
			cardViewSearch.setAlpha(afterAlpha);
			cardViewSearch.setOnClickListener(this::setUpSearchBar);
			decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		}
		// Tint Overflow
		if (mOriginalOverflowIcon == null) {
			final ArrayList<View> overflows = new ArrayList<>();
			@SuppressLint("PrivateResource")
			final String overflowDescription = context.getString(R.string.abc_action_menu_overflow_description);
			binding.toolbar.findViewsWithText(overflows, overflowDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);

			if (!overflows.isEmpty()) {
				mOverflowView = (AppCompatImageView) overflows.get(0);
				mOriginalOverflowIcon = mOverflowView.getDrawable();
			}
		}

		if (mOverflowView != null)
			mOverflowView.setImageDrawable(TintHelper.createTintedDrawable(mOriginalOverflowIcon, tintColor));

		if (t > 0 && headerHeight > 0) {
			btnBack.setImageDrawable(TintHelper.createTintedDrawable(tintIconBack, tintColor));
			btnBack.setBackground(TintHelper.createTintedDrawable(ovalIcon, tintColor1));
			btnShare.setImageDrawable(TintHelper.createTintedDrawable(tintIconShare, tintColor));
			btnShare.setBackground(TintHelper.createTintedDrawable(ovalIcon, tintColor1));
			btnCart.setImageDrawable(TintHelper.createTintedDrawable(tintIconCart, tintColor));
			btnCart.setBackground(TintHelper.createTintedDrawable(ovalIcon, tintColor1));
			btnMore.setImageDrawable(TintHelper.createTintedDrawable(tintIconMore, tintColor));
			btnMore.setBackground(TintHelper.createTintedDrawable(ovalIcon, tintColor1));
		}

		setUpAfterToolbarBackground(afterAlpha);
		// Tint Action button
		tintMenu(binding.toolbar, mMenu, tintColor);

	}

	private void setUpBeforeToolbarBackground() {
		toolbarBackground = getResources().getDrawable(R.drawable.warna_utama_white);
		binding.toolbar.setBackgroundDrawable(toolbarBackground);
		setSupportActionBar(binding.toolbar);
		scrollable = (ObservableScrollable) binding.scrollFade;
		scrollable.setOnScrollChangedCallback(this);
	}

	private void setUpAfterToolbarBackground(float verticalOffset) {
		int newAlpha = (int) (verticalOffset * 255);
		toolbarBackground.setAlpha(newAlpha);
		binding.toolbar.setBackground(toolbarBackground);
	}

	public void tintMenu(Toolbar toolbar, Menu menu, final int color) {
		try {
			final Field field = Toolbar.class.getDeclaredField("toolbarIcon");
			field.setAccessible(true);
			Drawable collapseIcon = (Drawable) field.get(toolbar);
			if (collapseIcon != null) {
				field.set(toolbar, TintHelper.createTintedDrawable(collapseIcon, color));
				field.set(toolbar, TintHelper.setOverflowButtonColor(toolbar, color));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// credits: https://snow.dog/blog/how-to-dynamicaly-change-android-toolbar-icons-color/
		final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
		for (int i = 0; i < toolbar.getChildCount(); i++) {
			final View v = toolbar.getChildAt(i);
			// We can't iterate through the toolbar.getMenu() here, because we need the ActionMenuItemView. ATEActionMenuItemView is overriding the item icon tint color.
			if (v instanceof ActionMenuView) {
				for (int j = 0; j < ((ActionMenuView) v).getChildCount(); j++) {
					final View innerView = ((ActionMenuView) v).getChildAt(j);
					if (innerView instanceof ActionMenuItemView) {
						int drawablesCount = ((ActionMenuItemView) innerView).getCompoundDrawables().length;
						for (int k = 0; k < drawablesCount; k++) {
							if (((ActionMenuItemView) innerView).getCompoundDrawables()[k] != null) {
								((ActionMenuItemView) innerView).getCompoundDrawables()[k].setColorFilter(colorFilter);
							}
						}
					}

				}
			}
		}

		if (menu == null)
			menu = toolbar.getMenu();
		if (menu != null && menu.size() > 0) {
			for (int i = 0; i < menu.size(); i++) {
				MenuItem item = menu.getItem(i);

				// We must iterate through the toolbar.getMenu() too, to keep the tint when resuming the paused activity.
				if (item.getIcon() != null) {
					item.setIcon(TintHelper.createTintedDrawable(item.getIcon(), color));
					//item.setIcon(TintHelper.setOverflowButtonColor(item.getIcon(),color));
				}

			}
		}
	}
}