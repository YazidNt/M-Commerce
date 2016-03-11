package com.batikapp.adapter;

import java.util.ArrayList;

import com.batikapp.activity.R;
import com.batikapp.model.MenuModel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<MenuModel> menumodel;

	public MenuAdapter(Context context, ArrayList<MenuModel> menumodel) {
		super();
		this.context = context;
		this.menumodel = menumodel;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menumodel.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return menumodel.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View view, ViewGroup viewgroup) {
		// TODO Auto-generated method stub
		if (view == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_menu,null);	

		}
		ImageView icon =(ImageView)view.findViewById(R.id.icon);
		TextView judul=(TextView)view.findViewById(R.id.judul);
		
		icon.setImageResource(menumodel.get(pos).getIcon());
		judul.setText(menumodel.get(pos).getJudul());
		
		
		return view;
	}

}
