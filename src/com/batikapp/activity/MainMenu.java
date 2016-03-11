package com.batikapp.activity;

import java.util.ArrayList;

import com.batikapp.adapter.MenuAdapter;
import com.batikapp.config.Config;
import com.batikapp.model.MenuModel;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainMenu extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<MenuModel> navDrawerItems;
	private MenuAdapter adapter;

	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		actionBar = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#67b0d6"));
		actionBar.setBackgroundDrawable(colorDrawable);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.menu_item);

		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(R.array.menu_icon);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<MenuModel>();

		// Halaman Depan
		navDrawerItems.add(new MenuModel(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		
		// Produk
		navDrawerItems.add(new MenuModel(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		
		// Login
		navDrawerItems.add(new MenuModel(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		
		// Registrasi
		navDrawerItems.add(new MenuModel(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		
		// konfirmasi
		navDrawerItems.add(new MenuModel(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		
		// petunjuk penggunaan
		navDrawerItems.add(new MenuModel(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		
		// Tentang
		navDrawerItems.add(new MenuModel(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		
		//Amikom
		navDrawerItems.add(new MenuModel(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));

		// Keluar
		navDrawerItems.add(new MenuModel(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));

		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		adapter = new MenuAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mian_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.chart:
			if (Config.getSession() == null) {
				Toast.makeText(getApplicationContext(),
						"Anda harus login dulu..!!", Toast.LENGTH_LONG).show();
			} else {
				startActivity(new Intent(getApplicationContext(), Cart.class));
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HalamanDepan();
			break;
		case 1:
			fragment = new Produk();
			break;

		case 2:
			fragment = new Login();
			break;
		case 3:
			fragment = new Registrasi();
			break;
		case 4:
			if (Config.getSession() == null) {
				Toast.makeText(getApplicationContext(),
						"Anda harus login dulu..!!", Toast.LENGTH_LONG).show();
			} else {
				fragment = new Konfirmasi();
			}
			break;

		case 5:
			fragment = new Petunjuk();
			break;
			
		
		case 6:
			fragment = new Tentang();
			break;
			
		case 7:
			fragment = new Amikompwt();
			break;
		
		case 8:
			finish();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
