package com.batikapp.activity;

import com.batikapp.config.Config;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


@SuppressLint("NewApi") 
public class Produk extends Fragment implements OnItemClickListener {
	
	private String [] katagori = {"Baju Batik Couple","Baju Batik Pria",
			"Baju Batik Wanita","Baju Batik Anak Laki-laki","Baju Batik Anak Perempuan"};
	
	ListView list_produk;
	
	
	
	
	public Produk() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v= inflater.inflate(R.layout.activity_produk, container,false);
		
		list_produk = (ListView) v.findViewById(R.id.list_katagori);
		
		list_produk.setAdapter(new ArrayAdapter<Object>(getActivity(),
				android.R.layout.simple_list_item_1,katagori));
		list_produk.setOnItemClickListener(this);
		return v;	
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int posisi, long arg3) {
		
		Bundle b = new Bundle();
		Intent intent;
		if (posisi==0) {
			intent = new Intent(getActivity(), Daftar_produk.class);
			b.putString("kode", "1");
			Config.setKode("1");
			intent.putExtras(intent);
			startActivity(intent);
			
		}
		if (posisi==1) {
			intent = new Intent(getActivity(), Daftar_produk.class);
			b.putString("kode", "2");
			Config.setKode("2");
			intent.putExtras(intent);
			startActivity(intent);
			
		}
		if (posisi==2) {
			intent = new Intent(getActivity(), Daftar_produk.class);
			b.putString("kode", "3");
			Config.setKode("3");
			intent.putExtras(intent);
			startActivity(intent);
			
		}
		if (posisi==3) {
			intent = new Intent(getActivity(), Daftar_produk.class);
			b.putString("kode", "4");
			Config.setKode("4");
			intent.putExtras(intent);
			startActivity(intent);
			
		}
		if (posisi==4) {
			intent = new Intent(getActivity(), Daftar_produk.class);
			b.putString("kode", "5");
			Config.setKode("5");
			intent.putExtras(intent);
			startActivity(intent);
			
		}
		
		
		// TODO Auto-generated method stub
		
	}
}
