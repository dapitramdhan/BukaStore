package com.bukastore.mhr.adapter.PROFILE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bukastore.mhr.R;
import com.bukastore.mhr.adapter.PROFILE.GridPengirimanAdapter.ViewHolder;
import com.bukastore.mhr.model.GridPengirimanModel;
import java.util.List;

public class GridPengirimanAdapter extends RecyclerView.Adapter<GridPengirimanAdapter.ViewHolder> {

	private List<GridPengirimanModel> gridPengirimanModelList;
	private Context context;

	public GridPengirimanAdapter(Context context, List<GridPengirimanModel> gridPengirimanModelList) {
		this.gridPengirimanModelList = gridPengirimanModelList;
		this.context = context;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_pengiriman, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		GridPengirimanModel gridModel = gridPengirimanModelList.get(position);
		String textIcon = gridModel.getTextIconPengiriman();
		int imgIcon = gridModel.getIconPengiriman();
		holder.textView.setText(textIcon);
		holder.img.setImageResource(gridModel.getIconPengiriman());
	}

	@Override
	public int getItemCount() {
		return gridPengirimanModelList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		private ImageView img;
		private TextView textView;

		public ViewHolder(View itemView) {
			super(itemView);

			textView = itemView.findViewById(R.id.item_text_icon);
			img = itemView.findViewById(R.id.img_icon_pengiriman);
		}
	}

}