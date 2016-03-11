package com.batikapp.model;

public class MenuModel {
	String judul;
	int icon;

	public MenuModel(String judul, int icon) {
		super();
		this.judul = judul;
		this.icon = icon;
	}

	public String getJudul() {
		return judul;
	}

	public void setJudul(String judul) {
		this.judul = judul;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

}
