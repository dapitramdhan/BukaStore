package com.bukastore.mhr.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.bukastore.mhr.databinding.ActivityChatBinding;
import com.bukastore.mhr.R;
public class ActivityChat extends AppCompatActivity{
	
	private ActivityChatBinding binding;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		binding = ActivityChatBinding.inflate(getLayoutInflater());
		View view = binding.getRoot();
		setContentView(view);
		View decor = getWindow().getDecorView();
		decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		setSupportActionBar(binding.toolbar);
		binding.toolbar.setNavigationIcon(R.drawable.ic_arrow);
		binding.toolbar.setNavigationOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				onBackPressed();
			}
		});
	}
	
	public void onBackPressed() {
		super.onBackPressed();
		//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
}