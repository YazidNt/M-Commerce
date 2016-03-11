package com.batikapp.adapter;

import java.util.List;


import com.batikapp.config.Config;
import com.batikapp.model.CartModel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CartAdapter extends BaseAdapter {
	Context context;
	List<CartModel> tampungdata;
	
	public CartAdapter(Context context, List<CartModel> tampungdata) {
		super();
		this.context = context;
		this.tampungdata = tampungdata;
	}
	private class ViewHolder {
		
		TextView itemkode,itemnama,itemharga,itemqty,itemtotal,itemukuran;
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
		return tampungdata.indexOf(arg0);
	}

	@Override
	public View getView(int posisi, View v, ViewGroup vg) {
	
		ViewHolder view= null;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (v== null) {
			v = inflater.inflate(com.batikapp.activity.R.layout.item_cart, null);
			
			view = new ViewHolder();
			view.itemkode = (TextView) v.findViewById(com.batikapp.activity.R.id.itemkode);
			view.itemnama = (TextView) v.findViewById(com.batikapp.activity.R.id.itemnama);
			view.itemharga = (TextView) v.findViewById(com.batikapp.activity.R.id.itemharga);
			view.itemqty = (TextView) v.findViewById(com.batikapp.activity.R.id.itemqty);
			view.itemtotal = (TextView) v.findViewById(com.batikapp.activity.R.id.itemtotal);
			view.itemukuran = (TextView) v.findViewById(com.batikapp.activity.R.id.itemukuran);
			v.setTag(view);
		}else {
			view = (ViewHolder)v.getTag();
		}
		CartModel tampungdata = (CartModel) getItem(posisi);
	
		view.itemkode.setText(tampungdata.getVkode());
		view.itemnama.setText(tampungdata.getVnama());
		view.itemukuran.setText(tampungdata.getVukuran());
		view.itemharga.setText(Config.setRupiah(Double.parseDouble(tampungdata.getVharga())));
		view.itemqty.setText(tampungdata.getVqty());
		view.itemtotal.setText(Config.setRupiah(Double.parseDouble(tampungdata.getVtotal())));
		
		return v;
	}

}
