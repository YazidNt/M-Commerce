package com.batikapp.activity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class Petunjuk extends Fragment {
	public Petunjuk () {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v= inflater.inflate(R.layout.activity_petunjuk, container,false);
		
		return v;	
	}

}
