package com.bukastore.mhr.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import com.bukastore.mhr.databinding.ActivitySearchBinding;
import com.bukastore.mhr.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
	private ActivitySearchBinding binding;
	private SearchView searchView;
	boolean is = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		binding = ActivitySearchBinding.inflate(getLayoutInflater());
		View view = binding.getRoot();
		setContentView(view);

		View decor = getWindow().getDecorView();
		decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

		binding.toolbar.setTitle("");
		binding.searchView.setIconified(false);
		binding.cancel.setOnClickListener(v -> this.onBackPressed());
		setSupportActionBar(binding.toolbar);
	}

	@Override
	public void onClick(View v) {
	}

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
}