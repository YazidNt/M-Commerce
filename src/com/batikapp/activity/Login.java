 package com.batikapp.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

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
import android.widget.Toast;

@SuppressLint("NewApi")
public class Login extends Fragment implements OnClickListener {

	EditText txtusername, txtpassword;
	Button btnlogin, btnbatal;
	String url = Config.URL + "cek_login.php";
	ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

	public Login() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.activity_login, container, false);

		txtusername = (EditText) v.findViewById(R.id.txtuser);
		txtpassword = (EditText) v.findViewById(R.id.txtpas);

		btnlogin = (Button) v.findViewById(R.id.btnlogin);
		btnbatal = (Button) v.findViewById(R.id.btnbatal);
		btnlogin.setOnClickListener(this);
		btnbatal.setOnClickListener(this);
		return v;
	}

	String[] nm_member;
	String[] kd_member;

	private class login extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = new ProgressDialog(getActivity());
		String Content;
		String Error = null;
		JSONObject jObject;

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
				// Toast.makeText(getActivity(),"id : "+ result,
				// Toast.LENGTH_LONG).show();
				if (result.contains("0")) {
					Toast.makeText(getActivity(), "Login Gagal",
							Toast.LENGTH_LONG).show();

				} else {
					Toast.makeText(getActivity(), "Login Sukses",
							Toast.LENGTH_LONG).show();
					Config.setSession(result);
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		
		if (v.getId() == R.id.btnlogin) {

			if (txtusername.getText().toString().equalsIgnoreCase("")) {
				txtusername.setError("Username Belum Di Isi");
				
			} else if (txtpassword.getText().toString().equalsIgnoreCase("")) {
				txtpassword.setError("Password belum Di Isi");
				
			} else {
				data.add(new BasicNameValuePair("email", txtusername.getText()
						.toString()));
				data.add(new BasicNameValuePair("password", txtpassword
						.getText().toString()));
				new login().execute(url);

			}

		}
		if (v.getId() == R.id.btnbatal) {

		}
	}
}
