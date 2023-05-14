package com.bukastore.mhr.custom.FaddingEffect;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import androidx.core.widget.NestedScrollView;

public class ScrollFadeOnToolbar extends NestedScrollView implements ObservableScrollable {

	private OnScrollChangedCallback mOnScrollChangedListener;
	private boolean mDisableEdgeEffects = true;

	// Construktor
	public ScrollFadeOnToolbar(Context context) {
		super(context);
	}

	// Construktor
	public ScrollFadeOnToolbar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// Construktor
	public ScrollFadeOnToolbar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mOnScrollChangedListener != null) {
			mOnScrollChangedListener.onScroll(l, t);
		}
	}
	
	@Override
    protected float getTopFadingEdgeStrength() {
        // http://stackoverflow.com/a/6894270/244576
        if (mDisableEdgeEffects && Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return 0.0f;
        }
        return super.getTopFadingEdgeStrength();
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        // http://stackoverflow.com/a/6894270/244576
        if (mDisableEdgeEffects && Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return 0.0f;
        }
        return super.getBottomFadingEdgeStrength();
    }

	// ObservableScrollable
	@Override
	public void setOnScrollChangedCallback(OnScrollChangedCallback callback) {
		mOnScrollChangedListener = callback;
	}

}