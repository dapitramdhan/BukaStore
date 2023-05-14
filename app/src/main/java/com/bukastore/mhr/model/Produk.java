package com.bukastore.mhr.model;

public class Produk {

	private String gambarProduk;
	private String namaProduk;
	private int mLikes;

	public Produk(String gambarProduk, String namaProduk, int likes) {
		this.gambarProduk = gambarProduk;
		this.namaProduk = namaProduk;
		mLikes = likes;

	}

	public String getImageProduk() {
		return gambarProduk;
	}

	public String getNamaProduk() {
		return namaProduk;
	}

	public int getHargaProduk() {
		return mLikes;
	}
}