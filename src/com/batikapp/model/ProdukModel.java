package com.batikapp.model;

public class ProdukModel {
	
	String v_namaproduk;
	String v_hargaproduk;
	String v_gambar;
	
	static String kd_barang;
	static String nm_barang;
	static String bahan;
	static String hrg_barang;
	static String stok;
	static String deskripsi;
	static String gambar;
	static String kd_kategori;
	
	
	
	
	
	public ProdukModel() {
		// TODO Auto-generated constructor stub
	}
	
	public ProdukModel(String v_namaproduk, String v_hargaproduk,
			String v_gambar) {
		super();
		this.v_namaproduk = v_namaproduk;
		this.v_hargaproduk = v_hargaproduk;
		this.v_gambar = v_gambar;
	}



	public String getV_namaproduk() {
		return v_namaproduk;
	}



	public void setV_namaproduk(String v_namaproduk) {
		this.v_namaproduk = v_namaproduk;
	}



	public String getV_hargaproduk() {
		return v_hargaproduk;
	}



	public void setV_hargaproduk(String v_hargaproduk) {
		this.v_hargaproduk = v_hargaproduk;
	}



	public String getV_gambar() {
		return v_gambar;
	}



	public void setV_gambar(String v_gambar) {
		this.v_gambar = v_gambar;
	}

	public static String getKd_barang() {
		return kd_barang;
	}

	public static void setKd_barang(String kd_barang) {
		ProdukModel.kd_barang = kd_barang;
	}

	public static String getNm_barang() {
		return nm_barang;
	}

	public static void setNm_barang(String nm_barang) {
		ProdukModel.nm_barang = nm_barang;
	}

	public static String getBahan() {
		return bahan;
	}

	public static void setBahan(String bahan) {
		ProdukModel.bahan = bahan;
	}

	public static String getHrg_barang() {
		return hrg_barang;
	}

	public static void setHrg_barang(String hrg_barang) {
		ProdukModel.hrg_barang = hrg_barang;
	}

	public static String getStok() {
		return stok;
	}

	public static void setStok(String stok) {
		ProdukModel.stok = stok;
	}

	public static String getDeskripsi() {
		return deskripsi;
	}

	public static void setDeskripsi(String deskripsi) {
		ProdukModel.deskripsi = deskripsi;
	}

	public static String getGambar() {
		return gambar;
	}

	public static void setGambar(String gambar) {
		ProdukModel.gambar = gambar;
	}

	public static String getKd_kategori() {
		return kd_kategori;
	}

	public static void setKd_kategori(String kd_kategori) {
		ProdukModel.kd_kategori = kd_kategori;
	}
	
	
	
	

}
