package com.bukastore.mhr.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import android.graphics.drawable.LayerDrawable;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import com.bukastore.mhr.R;
import com.bukastore.mhr.custom.BadgeDrawable;

public final class TintHelper {

	@CheckResult
	@Nullable
	public static Drawable createTintedDrawable(@Nullable Drawable drawable, @ColorInt int color) {
		if (drawable == null)
			return null;
		drawable = DrawableCompat.wrap(drawable.mutate());
		DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
		DrawableCompat.setTint(drawable, color);
		return drawable;
	}

	// This returns a NEW Drawable because of the mutate() call. The mutate() call is necessary because Drawables with the same resource have shared states otherwise.
	@CheckResult
	@Nullable
	public static Drawable createTintedDrawable(@Nullable Drawable drawable, @NonNull ColorStateList sl) {
		if (drawable == null)
			return null;
		drawable = DrawableCompat.wrap(drawable.mutate());
		DrawableCompat.setTintList(drawable, sl);
		return drawable;
	}

	public static void setBadgeCount(Context context, LayerDrawable icon, String count) {
		BadgeDrawable badge;
		// Reuse drawable if possible
		Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
		if (reuse != null && reuse instanceof BadgeDrawable) {
			badge = (BadgeDrawable) reuse;
		} else {
			badge = new BadgeDrawable(context);
		}

		badge.setCount(count);
		icon.mutate();
		icon.setDrawableByLayerId(R.id.ic_badge, badge);
	}

	@Nullable
	public static Drawable changeBackgroundColor(@Nullable LayerDrawable drawable, @ColorInt int color) {
		if (drawable == null)
			return null;
		Drawable oval = drawable.findDrawableByLayerId(R.id.oval_ground);
		oval = DrawableCompat.wrap(oval.mutate());
		DrawableCompat.setTintMode(oval, PorterDuff.Mode.SRC_IN);
		DrawableCompat.setTint(oval, color);
		drawable.setDrawableByLayerId(R.id.oval_ground, oval);
		return drawable;
	}

	public static Drawable setOverflowButtonColor(final Toolbar toolbar, final int color) {
		Drawable drawable = toolbar.getOverflowIcon();
		if (drawable != null) {
			drawable = DrawableCompat.wrap(drawable);
			DrawableCompat.setTint(drawable.mutate(), color);
			toolbar.setOverflowIcon(drawable);
		}
		return drawable;
	}
}