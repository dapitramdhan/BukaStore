package com.bukastore.mhr.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import android.view.WindowInsets;
import android.widget.RelativeLayout;

public class WindowInsetsRelativeLayout extends RelativeLayout {
	public WindowInsetsRelativeLayout(Context context) {
		super(context);
	}

	public WindowInsetsRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WindowInsetsRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
	@Override
	public WindowInsets onApplyWindowInsets(WindowInsets insets) {
		int childCount = getChildCount();
		for (int index = 0; index < childCount; index++)
			getChildAt(index).dispatchApplyWindowInsets(insets); // let children know about WindowInsets

		return insets;
	}

}