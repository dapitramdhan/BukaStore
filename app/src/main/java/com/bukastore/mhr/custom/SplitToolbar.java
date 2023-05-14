package com.bukastore.mhr.custom;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.ActionMenuView;
import android.view.ViewGroup;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

public class SplitToolbar extends Toolbar {
	public SplitToolbar(Context context) {
		super(context);
	}

	public SplitToolbar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SplitToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void addView(View child, ViewGroup.LayoutParams params) {
		if (child instanceof ActionMenuView) {
			params.width = LayoutParams.WRAP_CONTENT;
		}
		super.addView(child, params);
	}
}