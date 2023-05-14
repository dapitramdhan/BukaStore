package com.bukastore.mhr.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.bukastore.mhr.MainActivity;
import com.bukastore.mhr.R;
import com.bukastore.mhr.databinding.SplashScreenBinding;

public class SplashScreen extends AppCompatActivity {
	SplashScreenBinding binding;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		binding = SplashScreenBinding.inflate(getLayoutInflater());
		View view = binding.getRoot();
		setContentView(view);
		
		View decor = getWindow().getDecorView();
		decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashScreen.this, MainActivity.class));
				overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
				finish();
			}
		}, 2000);
	}
}