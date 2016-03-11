package com.batikapp.model;

public class CartModel {
	String vkode;
	String vnama;
	String vharga;
	String vqty;
	String vtotal;
	String vukuran;
	public static String totalbayar;

	public CartModel(String vkode, String vnama, String vharga, String vqty,
			String vtotal,String vukuran) {
		super();
		this.vkode = vkode;
		this.vnama = vnama;
		this.vharga = vharga;
		this.vqty = vqty;
		this.vtotal = vtotal;
		this.vukuran=vukuran;
		
	}
	public CartModel() {
		// TODO Auto-generated constructor stub
	}

	public String getVkode() {
		return vkode;
	}

	public void setVkode(String vkode) {
		this.vkode = vkode;
	}

	public String getVnama() {
		return vnama;
	}

	public void setVnama(String vnama) {
		this.vnama = vnama;
	}

	public String getVharga() {
		return vharga;
	}

	public void setVharga(String vharga) {
		this.vharga = vharga;
	}

	public String getVqty() {
		return vqty;
	}

	public void setVqty(String vqty) {
		this.vqty = vqty;
	}

	public String getVtotal() {
		return vtotal;
	}

	public void setVtotal(String vtotal) {
		this.vtotal = vtotal;
	}

	public String getVukuran() {
		return vukuran;
	}

	public void setVukuran(String vukuran) {
		this.vukuran = vukuran;
	}

	public static String getTotalbayar() {
		return totalbayar;
	}

	public static void setTotalbayar(String totalbayar) {
		CartModel.totalbayar = totalbayar;
	}
	
	

}
