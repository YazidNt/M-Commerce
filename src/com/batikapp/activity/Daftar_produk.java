package com.batikapp.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.batikapp.adapter.ProdukAdapter;
import com.batikapp.config.Config;
import com.batikapp.library.CustomHttpClient;
import com.batikapp.model.ProdukModel;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Daftar_produk extends Activity implements OnItemClickListener {

	private String[] kd_barang;
	private String[] nm_barang;
	private String[] bahan;
	private String[] hrg_barang;
	private String[] stok;
	private String[] deskripsi;
	private String[] gambar;
	private String[] kd_kategori;

	private ListView list_daftar_produk;
	private String URL = Config.URL + "getProduk.php";
	ProdukAdapter pa;
	List<ProdukModel> tampungdata;
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	ActionBar actionBar;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actionBar = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#67b0d6"));		
		actionBar.setBackgroundDrawable(colorDrawable);

		setContentView(R.layout.activity_daftar_produk);

		tampungdata = new ArrayList<ProdukModel>();
		list_daftar_produk = (ListView) findViewById(R.id.list_daftar_produk);
		list_daftar_produk.setOnItemClickListener(this);
		
		new tampilproduk().execute(URL);
	}

	private class tampilproduk extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(Daftar_produk.this);
		String Content;
		String Error = null;
		JSONObject jObject;

		@Override
		protected String doInBackground(String... params) {
			try {
				if (Config.getKode().equalsIgnoreCase("1")) {
					data.add(new BasicNameValuePair("kd_kategori", "1"));
				}
				if (Config.getKode().equalsIgnoreCase("2")) {
					data.add(new BasicNameValuePair("kd_kategori", "2"));

				}
				if (Config.getKode().equalsIgnoreCase("3")) {
					data.add(new BasicNameValuePair("kd_kategori", "3"));

				}
				if (Config.getKode().equalsIgnoreCase("4")) {
					data.add(new BasicNameValuePair("kd_kategori", "4"));

				}
				if (Config.getKode().equalsIgnoreCase("5")) {
					data.add(new BasicNameValuePair("kd_kategori", "5"));

				}
				Content = CustomHttpClient.executeHttpPost(URL, data);
			} catch (ClientProtocolException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (IOException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Content;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.dialog.setMessage("Loading Data..");
			this.dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			this.dialog.dismiss();
			if (Error != null) {
				Toast.makeText(getBaseContext(), "Error Connection Internet",
						Toast.LENGTH_LONG).show();
			} else {
				// Toast.makeText(getBaseContext(),Content,
				// Toast.LENGTH_LONG).show();
				try {
					jObject = new JSONObject(Content);
					JSONArray item = jObject.getJSONArray("produk");

					kd_barang = new String[item.length()];
					nm_barang = new String[item.length()];
					bahan = new String[item.length()];
					hrg_barang = new String[item.length()];
					stok = new String[item.length()];
					deskripsi = new String[item.length()];
					gambar = new String[item.length()];
					kd_kategori = new String[item.length()];

					for (int i = 0; i < item.length(); i++) {
						kd_barang[i] = item.getJSONObject(i)
								.getString("kd_barang").toString();

						nm_barang[i] = item.getJSONObject(i)
								.getString("nm_barang").toString();

						bahan[i] = item.getJSONObject(i).getString("bahan")
								.toString();

						hrg_barang[i] = item.getJSONObject(i)
								.getString("hrg_barang").toString();

						stok[i] = item.getJSONObject(i).getString("stok")
								.toString();

						deskripsi[i] = item.getJSONObject(i)
								.getString("deskripsi").toString();

						gambar[i] = item.getJSONObject(i).getString("gambar")
								.toString();

						kd_kategori[i] = item.getJSONObject(i)
								.getString("kd_kategori").toString();

						ProdukModel pm = new ProdukModel(nm_barang[i],
								hrg_barang[i], gambar[i]);
						tampungdata.add(pm);

					}
					ProdukAdapter adapter = new ProdukAdapter(getBaseContext(),
							tampungdata);
					list_daftar_produk.setAdapter(adapter);

				} catch (JSONException ex) {
					Logger.getLogger(Daftar_produk.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.daftar_produk, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

		ProdukModel.setKd_barang(kd_barang[pos]);
		ProdukModel.setNm_barang(nm_barang[pos]);
		ProdukModel.setHrg_barang(hrg_barang[pos]);
		ProdukModel.setBahan(bahan[pos]);
		ProdukModel.setDeskripsi(deskripsi[pos]);
		ProdukModel.setGambar(gambar[pos]);
		ProdukModel.setStok(stok[pos]);

		startActivity(new Intent(getBaseContext(), Detail_Produk.class));
		finish();

	}

}
