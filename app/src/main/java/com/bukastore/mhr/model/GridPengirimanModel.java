package com.bukastore.mhr.model;

public class GridPengirimanModel {

	private int iconUrl;
	private String textIcon;

	public GridPengirimanModel(int mIconUrl, String mTextIcon) {
		this.iconUrl = mIconUrl;
		this.textIcon = mTextIcon;
	}

	public int getIconPengiriman() {
		return iconUrl;
	}

	public void setIconPengiriman(int mImgUrl) {
		this.iconUrl = mImgUrl;
	}

	public String getTextIconPengiriman() {
		return textIcon;
	}

}