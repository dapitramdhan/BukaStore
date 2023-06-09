package com.bukastore.mhr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.bukastore.mhr.R;
import com.bukastore.mhr.adapter.IconKategoriAdapter.IconKategoriViewHolder;
import com.bukastore.mhr.model.IconKategoriModel;
import com.bumptech.glide.Glide;
import java.util.List;

public class IconKategoriAdapter extends RecyclerView.Adapter<IconKategoriAdapter.IconKategoriViewHolder> {

	private List<IconKategoriModel> listIcon;
	private Context context;

	public IconKategoriAdapter(Context context,List<IconKategoriModel> mListIcon) {
		this.listIcon = mListIcon;
		this.context = context;
	}

	@Override
	public IconKategoriViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon_kategori, parent, false);
		return new IconKategoriViewHolder(view);
	}

	@Override
	public void onBindViewHolder(IconKategoriViewHolder holder, int position) {
		IconKategoriModel iconKategoriModel = listIcon.get(position);

		String textIcon = iconKategoriModel.getTextIconKategori();
		String imgIcon = iconKategoriModel.getIconKategori();
		holder.textIcon.setText(textIcon);
		Glide.with(holder.itemView).load(imgIcon).into(holder.imgIcon);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Icon" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public int getItemCount() {
		return listIcon.size();
	}

	public class IconKategoriViewHolder extends RecyclerView.ViewHolder {
		ImageView imgIcon;
		TextView textIcon;

		public IconKategoriViewHolder(View itemView) {
			super(itemView);

			imgIcon = itemView.findViewById(R.id.img_icon_kategori);
			textIcon = itemView.findViewById(R.id.item_text_icon);
		}
	}

}