package com.bukastore.mhr.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bukastore.mhr.model.Produk;
import com.bukastore.mhr.R;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> {

	private List<Produk> listProduk;
	private OnItemClickListener mListener;

	public interface OnItemClickListener {
		void onItemClick(int position);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		mListener = listener;

	}

	public ProdukAdapter(List<Produk> mListProduk) {
		this.listProduk = mListProduk;
	}

	@Override
	public ProdukViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk, parent, false);
		return new ProdukViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ProdukViewHolder holder, int position) {
		Produk listItem = listProduk.get(position);

		String imgProduk = listItem.getImageProduk();
		String namaProduk = listItem.getNamaProduk();
		int hargaProduk = listItem.getHargaProduk();

		holder.namaProduk.setText(namaProduk);
		holder.hargaProduk.setText("Rp." + hargaProduk);
		Glide.with(holder.itemView).load(imgProduk).into(holder.imgProduk);
		//Picasso.get().load(imgProduk).error(R.drawable.ic_no_image).fit().centerInside().into(holder.imgProduk);
	}

	@Override
	public int getItemCount() {
		return listProduk.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		return position;
	}

	public class ProdukViewHolder extends RecyclerView.ViewHolder {

		public ImageView imgProduk;
		public TextView namaProduk;
		public TextView hargaProduk;

		public ProdukViewHolder(View itemView) {
			super(itemView);

			imgProduk = itemView.findViewById(R.id.imageProduk);
			namaProduk = itemView.findViewById(R.id.namaProduk);
			hargaProduk = itemView.findViewById(R.id.hargaProduk);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mListener != null) {
						int position = getAdapterPosition();
						if (position != RecyclerView.NO_POSITION) {
							mListener.onItemClick(position);
						}
					}
				}
			});
		}
	}

}