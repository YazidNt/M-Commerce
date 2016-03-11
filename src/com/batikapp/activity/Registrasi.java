package com.batikapp.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import com.batikapp.config.Config;
import com.batikapp.library.CustomHttpClient;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Registrasi extends Fragment implements OnClickListener {

	EditText txtnama, txtno_tlp, txtemail, txtpassword;
	RadioButton rdpria, rdwanita;
	Button btnregistrasi, btncancel;
	String url = Config.URL + "Registrasi.php";
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_registrasi, container,
				false);

		txtnama = (EditText) v.findViewById(R.id.txtnama);
		txtno_tlp = (EditText) v.findViewById(R.id.txtno_tlp);
		txtemail = (EditText) v.findViewById(R.id.txtemail);
		txtpassword = (EditText) v.findViewById(R.id.txtpassword);

		rdpria = (RadioButton) v.findViewById(R.id.radiopria);
		rdwanita = (RadioButton) v.findViewById(R.id.radiowanita);
		btnregistrasi = (Button) v.findViewById(R.id.btnregistrasi);
		btnregistrasi.setOnClickListener(this);
		btncancel = (Button) v.findViewById(R.id.btncancel);
		btncancel.setOnClickListener(this);

		return v;
	}

	private class registrasi extends AsyncTask<String, Void, String> {

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

					Toast.makeText(getActivity(), "Registrasi Berhasil",
							Toast.LENGTH_SHORT).show();
					// startActivity(new Intent(getActivity(),Cart.class));
					Bersihkan();
				} else {
					Toast.makeText(getActivity(), "Registrasi Gagal ",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		String jk = "";
		if (v.getId() == R.id.btnregistrasi) {

			if (rdpria.isChecked()) {
				jk = "Pria";
			}
			if (rdwanita.isChecked()) {
				jk = "Wanita";
			}

			if (txtnama.getText().toString().equalsIgnoreCase("")) {
				txtnama.setError("Nama Tidak Boleh Kosong");

			} else if (txtno_tlp.getText().toString().equalsIgnoreCase("")) {
				txtno_tlp.setError("No Telephon Tidak Boleh Kosong");

			} else if (txtemail.getText().toString().equalsIgnoreCase("")) {
				txtemail.setError("Email Tidak Boleh Kosong");

			} else if (txtpassword.getText().toString().equalsIgnoreCase("")) {
				txtpassword.setError("Password Tidak Boleh Kosong");
				
			}else if (txtno_tlp.getText().length()>12){
				txtno_tlp.setError("Nomor telepon tidak boleh melebihi 12 karakter");
				
			}else

			{
				data.add(new BasicNameValuePair("nm_member", txtnama.getText()
						.toString()));

				data.add(new BasicNameValuePair("jk", jk));

				data.add(new BasicNameValuePair("no_tlp", txtno_tlp.getText()
						.toString()));

				data.add(new BasicNameValuePair("email", txtemail.getText()
						.toString()));

				data.add(new BasicNameValuePair("password", txtpassword
						.getText().toString()));

				new registrasi().execute(url);

			}

		}
		if (v.getId() == R.id.btncancel) {
			Bersihkan();

		}

	}

	private void Bersihkan() {
		txtnama.setText("");
		txtno_tlp.setText("");
		txtemail.setText("");
		txtpassword.setText("");
		txtnama.requestFocus();
	}
}
