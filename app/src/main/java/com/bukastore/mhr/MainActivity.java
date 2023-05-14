package com.bukastore.mhr;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import com.bukastore.mhr.databinding.ActivityMainBinding;
import com.bukastore.mhr.ui.activity.ActivityNoConnection;
import com.bukastore.mhr.ui.fragment.LayoutFragmentFeed;
import com.bukastore.mhr.ui.fragment.LayoutFragmentKategori;
import com.bukastore.mhr.ui.fragment.LayoutFragmentMain;
import com.bukastore.mhr.ui.fragment.LayoutFragmentPesanan;
import com.bukastore.mhr.ui.fragment.LayoutFragmentProfile;
import com.bukastore.mhr.viewmodel.NetworkViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	private BottomNavigationView bottomNavigationView;
	final Fragment one = new LayoutFragmentMain();
	final Fragment two = new LayoutFragmentKategori();
	final Fragment thre = new LayoutFragmentFeed();
	final Fragment four = new LayoutFragmentPesanan();
	final Fragment five = new LayoutFragmentProfile();
	final FragmentManager fm = getSupportFragmentManager();
	boolean doubleBackToExitPressedOnce = false;
	Fragment active = one;
	MenuItem prevMenuItem;
	

	private final Observer<Boolean> activeNetworkStateObserver = new Observer<Boolean>() {
		@Override
		public void onChanged(Boolean isConnected) {
			showErrorConnectionInternet(isConnected);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		View view = binding.getRoot();
		setContentView(view);
		bottomNavigationView = binding.bottomNavigation;
		hideToltipBottomNav();
		setUpBottomNavigation();
		setFragment(one, "1", 0);

		NetworkViewModel.getInstance().getNetworkConnectivityStatus().observe(this, activeNetworkStateObserver);

	}

	private void showErrorConnectionInternet(boolean isConnected) {
		Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
		Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.slide_down);
		if (isConnected) {
			//binding.message.setVisibility(View.GONE);
			binding.cekInternet.startAnimation(animation1);
			binding.message.setVisibility(View.GONE);
		} else {
			binding.message.setText("Tidak ada Koneksi Internet");
			binding.cekInternet.setBackgroundColor(getResources().getColor(R.color.background_no_connection));
			binding.message.setVisibility(View.VISIBLE);
			binding.message.startAnimation(animation);
			binding.cekInternet.startAnimation(animation);
		}
	}

	private void showInternetDialog(boolean isConnected) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogFullWidth);
		builder.setCancelable(false);
		View view = LayoutInflater.from(this).inflate(R.layout.activity_no_connection,
				findViewById(R.id.root_connection));
		view.findViewById(R.id.muat_ulang_koneksi).setOnClickListener(v -> {
			startActivity(new Intent(getApplicationContext(), MainActivity.class));
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		});
		builder.setView(view);

		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	public boolean setFragment(Fragment fragment, String tag, int position) {
		if (fragment != null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_NONE);
			ft.replace(R.id.view_pager, fragment);
			ft.commit();
			return true;
		}
		bottomNavigationView.getMenu().getItem(position).setChecked(true);
		active = fragment;
		return false;

	}

	private void setUpBottomNavigation() {
		binding.bottomNavigation
				.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(@NonNull MenuItem item) {
						Fragment select = null;
						switch (item.getItemId()) {
						case R.id.nav_home:
							setFragment(one, "1", 0);
							return true;
						case R.id.nav_kategori:
							setFragment(two, "2", 1);
							return true;
						case R.id.nav_feed:
							setFragment(thre, "3", 2);
							return true;
						case R.id.nav_pesanan:
							setFragment(four, "4", 3);
							return true;
						case R.id.nav_profile:
							setFragment(five, "5", 4);
							return true;
						}
						return false;
					}
				});

	}

	private void hideToltipBottomNav() {
		for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
			bottomNavigationView.findViewById(bottomNavigationView.getMenu().getItem(i).getItemId())
					.setOnLongClickListener(new View.OnLongClickListener() {
						@Override
						public boolean onLongClick(View view) {
							return true;
						}
					});

		}
	}

}
