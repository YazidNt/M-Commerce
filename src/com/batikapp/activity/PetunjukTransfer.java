package com.batikapp.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.batikapp.config.Config;
import com.batikapp.library.CustomHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class PetunjukTransfer extends Activity {

	String[] nopemesanan;
	TextView txtnopemesanan;
	private String URL = Config.URL + "getkode.php";
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_petunjuk_transfer);
		txtnopemesanan = (TextView) findViewById(R.id.txtnopemesanan);
		new getNopemesanan().execute(URL);
		Button btnselesai=(Button)findViewById(R.id.btnselesai);
		btnselesai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.petunjuk_transfer, menu);
		return true;
	}

	private class getNopemesanan extends AsyncTask<String, Void, String> {
		String Content;
		JSONObject jObject;

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(URL, data);
			} catch (ClientProtocolException e) {
				e.getMessage();
				cancel(true);
			} catch (IOException e) {
				e.getMessage();
				cancel(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Content;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				jObject = new JSONObject(Content);
				JSONArray item = jObject.getJSONArray("kode");
				nopemesanan = new String[item.length()];

				for (int i = 0; i < item.length(); i++) {
					nopemesanan[i] = item.getJSONObject(i)
							.getString("kd_pemesanan").toString();

				}
				txtnopemesanan.setText(nopemesanan[0]);

			} catch (JSONException ex) {
				Logger.getLogger(Daftar_produk.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

	}

}
