package com.batikapp.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.batikapp.adapter.CartAdapter;
import com.batikapp.config.Config;
import com.batikapp.library.CustomHttpClient;
import com.batikapp.model.CartModel;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Cart extends Activity implements OnItemClickListener {

	private String[] kd_barang;
	private String[] nm_barang;
	private String[] harga;
	private String[] qty;
	private String[] total;
	private String[] ukuran;

	Button btnselesai;

	TextView txttotalbayar;

	private ListView list_cart;
	private String URL = Config.URL + "getCart.php";
	CartAdapter ca;
	List<CartModel> tampungdata;
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	ActionBar actionBar;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		actionBar = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#67b0d6"));
		actionBar.setBackgroundDrawable(colorDrawable);

		tampungdata = new ArrayList<CartModel>();
		list_cart = (ListView) findViewById(R.id.listcart);
		list_cart.setOnItemClickListener(this);
		new tampilcart().execute(URL);

		btnselesai = (Button) findViewById(R.id.btnselesai);
		btnselesai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						DataPenerima.class));
				finish();
			}
		});
		

		txttotalbayar = (TextView) findViewById(R.id.txttotalbayar);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cart, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	double totalbayar;

	private class tampilcart extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(Cart.this);
		String Content;
		String Error = null;
		JSONObject jObject;

		@Override
		protected String doInBackground(String... params) {
			try {
//				data.add(new BasicNameValuePair("kd_member", Config.getSession()));
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
					JSONArray item = jObject.getJSONArray("cart");

					kd_barang = new String[item.length()];
					nm_barang = new String[item.length()];
					harga = new String[item.length()];
					qty = new String[item.length()];
					total = new String[item.length()];
					ukuran = new String[item.length()];

					for (int i = 0; i < item.length(); i++) {
						kd_barang[i] = item.getJSONObject(i)
								.getString("kd_barang").toString();

						nm_barang[i] = item.getJSONObject(i)
								.getString("nm_barang").toString();

						harga[i] = item.getJSONObject(i)
								.getString("hrg_barang").toString();

						qty[i] = item.getJSONObject(i).getString("jml")
								.toString();

						total[i] = item.getJSONObject(i).getString("total")
								.toString();

						ukuran[i] = item.getJSONObject(i).getString("ukuran")
								.toString();

						CartModel pm = new CartModel(kd_barang[i],
								nm_barang[i], harga[i], qty[i], total[i],
								ukuran[i]);

						totalbayar = totalbayar + Double.parseDouble(total[i]);
						txttotalbayar.setText("Rp. "+String.valueOf(Config.setRupiah(totalbayar)));
						tampungdata.add(pm);
						CartModel.setTotalbayar(String.valueOf(totalbayar));						

					}
					CartAdapter pm = new CartAdapter(getBaseContext(),
							tampungdata);
					list_cart.setAdapter(pm);

				} catch (JSONException ex) {
					Logger.getLogger(Daftar_produk.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}

	}

}
