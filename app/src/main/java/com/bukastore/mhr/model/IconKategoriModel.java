package com.bukastore.mhr.model;

public class IconKategoriModel {
	private String iconUrl;
	private String textIcon;

	public IconKategoriModel(String mIconUrl, String mTextIcon) {
		this.iconUrl = mIconUrl;
		this.textIcon = mTextIcon;
	}

	public String getIconKategori() {
		return iconUrl;
	}

	public void setIconKategori(String mImgUrl) {
		this.iconUrl = mImgUrl;
	}

	public String getTextIconKategori() {
		return textIcon;
	}
}