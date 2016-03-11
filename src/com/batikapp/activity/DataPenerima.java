package com.batikapp.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.batikapp.config.Config;
import com.batikapp.library.CustomHttpClient;
import com.batikapp.model.CartModel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DataPenerima extends Activity {

	String[] kd_provinsi;
	String[] provinsi;

	String[] kd_kabupaten;
	String[] kabupaten;

	String[] kecamatan;
	String[] vkodepemesanan;

	EditText txtnama, txtkodepos, txtalamat, txtnohp;
	Spinner sp_provinsi, sp_kabupaten, sp_kecamatan;
	Button btnok;

	String URL_getKode = Config.URL + "getkode.php";
	String URL_Provinsi = Config.URL + "getprovinsi.php";
	String URL_Kabupaten = Config.URL + "getkabupaten.php";
	String URL_Kecamatan = Config.URL + "getkecamatan.php";
	String URL_insert = Config.URL + "insertpenerima.php";
	String URL_pemesanan = Config.URL + "insertpemesanan.php";
	String URL_detailpemesanan = Config.URL + "insertdetail.php";
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	ArrayList<NameValuePair> datainsert = new ArrayList<NameValuePair>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_penerima);

		txtnama = (EditText) findViewById(R.id.txtnamapenerima);
		txtkodepos = (EditText) findViewById(R.id.txtkodepos);
		txtalamat = (EditText) findViewById(R.id.txtalamatpenerima);
		txtnohp = (EditText) findViewById(R.id.txttelppenerima);
		sp_provinsi = (Spinner) findViewById(R.id.sp_provinsi);
		sp_kabupaten = (Spinner) findViewById(R.id.sp_kabupaten);
		sp_kecamatan = (Spinner) findViewById(R.id.sp_kecamatan);

		new getProvinsi().execute(URL_Provinsi);

		sp_provinsi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				data.add(new BasicNameValuePair("kd_provinsi", kd_provinsi[pos]));
				new getKabupaten().execute(URL_Kabupaten);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		sp_kabupaten.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				data.add(new BasicNameValuePair("kd_kabupaten",
						kd_kabupaten[pos]));
				new getKecamatan().execute(URL_Kecamatan);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		sp_kecamatan.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		btnok = (Button) findViewById(R.id.btnokpenerima);
		btnok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (txtnama.getText().toString().equalsIgnoreCase("")) {
					txtnama.setError("Nama Belum Di Isi");

				} else if (txtkodepos.getText().toString().equalsIgnoreCase("")) {
					txtkodepos.setError("Kode Pos Belum Di Isi");

				} else if (txtalamat.getText().toString().equalsIgnoreCase("")) {
					txtalamat.setError("Alamat Belum Di Isi");

				} else if (txtnohp.getText().toString().equalsIgnoreCase("")) {
					txtnohp.setError("No Telepon Belum Di Isi");

				} else {
					datainsert.add(new BasicNameValuePair("nm_penerima",
							txtnama.getText().toString()));
					datainsert.add(new BasicNameValuePair("kode_pos",
							txtkodepos.getText().toString()));
					datainsert.add(new BasicNameValuePair("provinsi",
							sp_provinsi.getSelectedItem().toString()));
					datainsert.add(new BasicNameValuePair("kabupaten",
							sp_kabupaten.getSelectedItem().toString()));
					datainsert.add(new BasicNameValuePair("kecamatan",
							sp_kecamatan.getSelectedItem().toString()));
					datainsert.add(new BasicNameValuePair("alamat", txtalamat
							.getText().toString()));
					datainsert.add(new BasicNameValuePair("no_hp", txtnohp
							.getText().toString()));

					new inserPenerima().execute(URL_insert);

					datainsert.add(new BasicNameValuePair("tgl_pemesanan",
							giveDate().toString()));
					datainsert.add(new BasicNameValuePair("kd_member", Config
							.getSession()));
					datainsert.add(new BasicNameValuePair("status",
							"Belum dikonfirmasi"));
					datainsert.add(new BasicNameValuePair("jml_bayar",
							CartModel.getTotalbayar()));

					new inserPemesanan().execute(URL_pemesanan);

				}

			}
		});

	}

	// Menampilkan Provinsi
	private class getProvinsi extends AsyncTask<String, Void, String> {
		String Content;
		String Error = null;
		JSONObject jObject;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(URL_Provinsi, data);
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
		protected void onPostExecute(String result) {
			if (Error != null) {
				Toast.makeText(getBaseContext(), "Error Connection Internet",
						Toast.LENGTH_LONG).show();
			} else {
				try {
					jObject = new JSONObject(Content);
					JSONArray menuitemArray = jObject.getJSONArray("provinsi");
					kd_provinsi = new String[menuitemArray.length()];
					provinsi = new String[menuitemArray.length()];
					for (int i = 0; i < menuitemArray.length(); i++) {
						kd_provinsi[i] = menuitemArray.getJSONObject(i)
								.getString("kd_provinsi").toString();
						provinsi[i] = menuitemArray.getJSONObject(i)
								.getString("provinsi").toString();
					}
					sp_provinsi.setAdapter(new ArrayAdapter<Object>(
							getBaseContext(),
							android.R.layout.simple_spinner_dropdown_item,
							provinsi));
				} catch (JSONException ex) {
					Logger.getLogger(DataPenerima.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}

	}

	// Menampilkan Kabupaten
	private class getKabupaten extends AsyncTask<String, Void, String> {
		String Content;
		String Error = null;
		JSONObject jObject;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(URL_Kabupaten, data);
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
		protected void onPostExecute(String result) {
			if (Error != null) {
				Toast.makeText(getBaseContext(), "Error Connection Internet",
						Toast.LENGTH_LONG).show();
			} else {
				try {
					jObject = new JSONObject(Content);
					JSONArray menuitemArray = jObject.getJSONArray("kabupaten");
					kd_kabupaten = new String[menuitemArray.length()];
					kabupaten = new String[menuitemArray.length()];
					for (int i = 0; i < menuitemArray.length(); i++) {
						kd_kabupaten[i] = menuitemArray.getJSONObject(i)
								.getString("kd_kabupaten").toString();
						kabupaten[i] = menuitemArray.getJSONObject(i)
								.getString("kabupaten").toString();
					}
					sp_kabupaten.setAdapter(new ArrayAdapter<Object>(
							getBaseContext(),
							android.R.layout.simple_spinner_dropdown_item,
							kabupaten));
				} catch (JSONException ex) {
					Logger.getLogger(DataPenerima.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}

	}

	// Menampilkan Kecamatan
	private class getKecamatan extends AsyncTask<String, Void, String> {
		String Content;
		String Error = null;
		JSONObject jObject;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(URL_Kecamatan, data);
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
		protected void onPostExecute(String result) {
			if (Error != null) {
				Toast.makeText(getBaseContext(), "Error Connection Internet",
						Toast.LENGTH_LONG).show();
			} else {
				try {
					jObject = new JSONObject(Content);
					JSONArray menuitemArray = jObject.getJSONArray("kecamatan");
					kecamatan = new String[menuitemArray.length()];
					for (int i = 0; i < menuitemArray.length(); i++) {
						kecamatan[i] = menuitemArray.getJSONObject(i)
								.getString("kecamatan").toString();
					}
					sp_kecamatan.setAdapter(new ArrayAdapter<Object>(
							getBaseContext(),
							android.R.layout.simple_spinner_dropdown_item,
							kecamatan));
				} catch (JSONException ex) {
					Logger.getLogger(DataPenerima.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}

	}

	// Menyimpan Penerima
	private class inserPenerima extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(DataPenerima.this);
		String Content;
		String Error = null;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(URL_insert,
						datainsert);
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

					Toast.makeText(getBaseContext(), "Berhasil disimpan",
							Toast.LENGTH_SHORT).show();
					// startActivity(new Intent(getActivity(),Cart.class));
					// Bersihkan();
				} else {
					Toast.makeText(getBaseContext(), "Gagal Simpan",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	// Menyimpan Pemesanan
	private class inserPemesanan extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(DataPenerima.this);
		String Content;
		String Error = null;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(URL_pemesanan,
						datainsert);
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

					Toast.makeText(getBaseContext(), "Berhasil disimpan",
							Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getBaseContext(),
							PetunjukTransfer.class));
					finish();
				} else {
					Toast.makeText(getBaseContext(), "Gagal Simpan",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	@SuppressLint("SimpleDateFormat")
	public String giveDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.data_penerima, menu);
		return true;
	}

}
