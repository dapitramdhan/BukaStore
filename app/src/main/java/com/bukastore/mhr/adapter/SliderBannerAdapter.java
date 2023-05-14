package com.bukastore.mhr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bukastore.mhr.model.SliderBanner;
//import com.smarteist.autoimageslider.SliderViewAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;
import com.bukastore.mhr.R;

public class SliderBannerAdapter extends SliderViewAdapter<SliderBannerAdapter.SliderAdapterViewHolder> {

	// list for storing urls of images.
	private List<SliderBanner> mSliderItems;
	Context context;

	// Constructor
	public SliderBannerAdapter(Context context, List<SliderBanner> sliderDataArrayList) {
		this.mSliderItems = sliderDataArrayList;
		this.context = context;
	}

	// We are inflating the slider_layout
	// inside on Create View Holder method.
	@Override
	public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
		View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_slider, null);
		return new SliderAdapterViewHolder(inflate);
	}

	public void renewItems(List<SliderBanner> sliderItems) {
		this.mSliderItems = sliderItems;
		notifyDataSetChanged();
	}

	public void deleteItem(int position) {
		this.mSliderItems.remove(position);
		notifyDataSetChanged();
	}

	public void addItem(SliderBanner sliderItem) {
		this.mSliderItems.add(sliderItem);
		notifyDataSetChanged();
	}

	// Inside on bind view holder we will
	// set data to item of Slider View.
	@Override
	public void onBindViewHolder(SliderAdapterViewHolder viewHolder, int position) {

		SliderBanner sliderItem = mSliderItems.get(position);

		String imgUrl = sliderItem.getImgUrl();
		// Glide is use to load image
		// from url in your imageview.

		Glide.with(viewHolder.itemView).load(imgUrl).into(viewHolder.imageViewBackground);
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "slider" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	// this method will return
	// the count of our list.
	@Override
	public int getCount() {
		return mSliderItems.size();
	}

	public class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
		// Adapter class for initializing
		// the views of our slider view.
		View itemView;
		ImageView imageViewBackground;

		public SliderAdapterViewHolder(View itemView) {
			super(itemView);
			imageViewBackground = itemView.findViewById(R.id.slider_banner_image);
			this.itemView = itemView;
		}
	}
}
