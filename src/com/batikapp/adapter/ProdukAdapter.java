package com.batikapp.adapter;

import java.util.List;


import com.batikapp.config.Config;
import com.batikapp.library.ImageLoader;
import com.batikapp.model.ProdukModel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProdukAdapter extends BaseAdapter {
	
	Context context;
	List<ProdukModel> tampungdata;
	String urlgambar =Config.URL+"gambar/";
	public ImageLoader imageLoader;
	
	public ProdukAdapter(Context context, List<ProdukModel> tampungdata) {
		super();
		this.context = context;
		this.tampungdata = tampungdata;
		imageLoader = new ImageLoader(context.getApplicationContext());
		
	}
	
	private class ViewHolder {
		ImageView gambar;
		TextView  nama_produk, harga_produk;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tampungdata.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return tampungdata.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return tampungdata.indexOf(getItem(arg0));
	}

	@Override
	public View getView(int posisi, View v, ViewGroup vg) {
		
		ViewHolder view= null;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (v== null) {
			v = inflater.inflate(com.batikapp.activity.R.layout.item_daftar_produk, null);
			
			view = new ViewHolder();
			view.nama_produk = (TextView) v.findViewById(com.batikapp.activity.R.id.nama_produk);
			view.harga_produk = (TextView) v.findViewById(com.batikapp.activity.R.id.harga_produk);
			view.gambar = (ImageView) v.findViewById(com.batikapp.activity.R.id.gambar_produk);
			v.setTag(view);
		}else {
			view = (ViewHolder)v.getTag();
		}
		ProdukModel tampungdata = (ProdukModel) getItem(posisi);
	
		view.nama_produk.setText(tampungdata.getV_namaproduk());
		view.harga_produk.setText("Rp. "+Config.setRupiah(Double.parseDouble(tampungdata.getV_hargaproduk())));
		imageLoader.displayImage(urlgambar
				+ tampungdata.getV_gambar(), view.gambar);
		
		return v;
	}

}
