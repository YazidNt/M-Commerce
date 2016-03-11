package com.batikapp.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import com.batikapp.config.Config;
import com.batikapp.library.CustomHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Konfirmasi extends Fragment {

	@SuppressLint("NewApi")
	EditText txtkodepemesanan, txttgltransfer, txtjumlahtransfer,
			txtnamapemilikrek, txtketerangan;
	Spinner spbank;
	Button btnkirim,btnhitory;
	String[] bank = { "--Pilih Bank--", "BRI", "BNI", "BCA" };
	String url = Config.URL + "konfirmasi.php";
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.activity_konfirmasi, container,
				false);

		txtkodepemesanan = (EditText) v.findViewById(R.id.konkdpemesanan);
		txttgltransfer = (EditText) v.findViewById(R.id.kontglpememsanan);
		txtjumlahtransfer = (EditText) v.findViewById(R.id.konjmltransfer);
		txtnamapemilikrek = (EditText) v.findViewById(R.id.konnamapemilikrek);
		txtketerangan = (EditText) v.findViewById(R.id.konketerangan);

		spbank = (Spinner) v.findViewById(R.id.konspbank);
		spbank.setAdapter(new ArrayAdapter<Object>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, bank));
		
		btnhitory = (Button) v.findViewById(R.id.btnhistory);
		btnhitory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(getActivity(),HistoryPemesanan.class));
				
			}
		});
		
		btnkirim = (Button) v.findViewById(R.id.konkirim);
		btnkirim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				data.add(new BasicNameValuePair("kd_pemesanan",
						txtkodepemesanan.getText().toString()));
				data.add(new BasicNameValuePair("tgl_transfer",
						txttgltransfer.getText().toString()));
				data.add(new BasicNameValuePair("bank",
						spbank.getSelectedItem().toString()));
				data.add(new BasicNameValuePair("jml_transfer",
						txtjumlahtransfer.getText().toString()));
				data.add(new BasicNameValuePair("nama_pemilikrek",
						txtnamapemilikrek.getText().toString()));
				data.add(new BasicNameValuePair("catatan",
						txtketerangan.getText().toString()));
				
				new inserKonfirmasi().execute(url);
			}
		});

		return v;
	}

	private class inserKonfirmasi extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(getActivity());
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
				Toast.makeText(getActivity(), "Error Internet Connection",
						Toast.LENGTH_LONG).show();
			} else {
				if (result.contains("1")) {

					Toast.makeText(getActivity(), "Konfirmasi Berhasil",
							Toast.LENGTH_SHORT).show();
					txtkodepemesanan.setText("");
					txttgltransfer.setText("");
					txtjumlahtransfer.setText("");
					txtnamapemilikrek.setText("");
					txtketerangan.setText("");
					txtkodepemesanan.requestFocus();
				} else {
					Toast.makeText(getActivity(), "Konfirmasi Gagal ",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}

}
