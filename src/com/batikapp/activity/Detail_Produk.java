package com.batikapp.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Detail_Produk extends Activity {

	ImageView gbrdetail;
	TextView txtnamadetail, txtdeskripsidetail, txthargadetail, txtstok;
	Button btnpesandetail;
	String urlgambar = Config.URL + "gambar/";
	String url = Config.URL + "InsertCart.php";
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	EditText txtqty;
	Spinner sp_ukuran;
	String[] ukuran = { "-Pilih Ukuran-", "XXL", "XL", "L", "M", "S" };

	ActionBar actionBar;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail__produk);

		actionBar = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#67b0d6"));
		actionBar.setBackgroundDrawable(colorDrawable);

		gbrdetail = (ImageView) findViewById(R.id.gbrdetail);
		txtnamadetail = (TextView) findViewById(R.id.txtnamadetail);
		txtdeskripsidetail = (TextView) findViewById(R.id.txtdeskripsidetail);
		txthargadetail = (TextView) findViewById(R.id.txthargadetail);
		txtstok = (TextView) findViewById(R.id.txtstok);
		txtqty = (EditText) findViewById(R.id.editqty);
		sp_ukuran = (Spinner) findViewById(R.id.sp_ukuran);

		sp_ukuran.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
				R.layout.itemwarna, ukuran));

		btnpesandetail = (Button) findViewById(R.id.btnpesandetail);
		btnpesandetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (Config.getSession() == null) {
					Toast.makeText(getApplicationContext(),
							"Anda harus login terlebih dulu !!",
							Toast.LENGTH_LONG).show();
					startActivity(new Intent(getBaseContext(), MainMenu.class));
				} else {

					if (txtqty.getText().toString().equalsIgnoreCase("")) {
						txtqty.setError("Jumlah Pemesanan Belum Di Isi");

					} else {
						
						int qty=Integer.parseInt(txtqty.getText().toString());
						int vstok=Integer.parseInt(ProdukModel.getStok());
						
						if (qty>vstok) {
							Toast.makeText(getBaseContext(), "Maaf, Stok tidak mencukupi",Toast.LENGTH_SHORT).show();
						} else {
							data.add(new BasicNameValuePair("kd_barang",
									ProdukModel.getKd_barang().toString()));
							data.add(new BasicNameValuePair("ukuran", sp_ukuran
									.getSelectedItem().toString()));
							data.add(new BasicNameValuePair("jml", txtqty.getText()
									.toString()));
							data.add(new BasicNameValuePair("kd_member", Config
									.getSession()));

							new insertCart().execute(url);
						}
					}

				}
			}
		});

		txtnamadetail.setText(ProdukModel.getNm_barang());
		txtdeskripsidetail.setText("Deskripsi : \n"
				+ ProdukModel.getDeskripsi());
		txthargadetail.setText("Rp. "
				+ Config.setRupiah(Double.parseDouble(ProdukModel
						.getHrg_barang())));
		txtstok.setText("Stok : " + ProdukModel.getStok());
		new DownloadImageTask(gbrdetail).execute(urlgambar
				+ ProdukModel.getGambar());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail__produk, menu);
		return true;
	}

	public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				Bitmap bmp2 = Bitmap.createScaledBitmap(result, 400, 400, true);
				bmImage.setImageBitmap(bmp2);
			}

		}
	}

	private class insertCart extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(Detail_Produk.this);
		String Content;
		String Error = null;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(url, data);
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
				Toast.makeText(getBaseContext(), "Error Internet Connection",
						Toast.LENGTH_LONG).show();
			} else {
				if (result.contains("1")) {
					//
					// Toast.makeText(getApplicationContext(),
					// "Berhasil Ditambah "+Config.getSession(),
					// Toast.LENGTH_SHORT).show();

					startActivity(new Intent(getBaseContext(), Cart.class));
					finish();
				} else {
					Toast.makeText(getBaseContext(), "Gagal Tambah Data",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}
}
