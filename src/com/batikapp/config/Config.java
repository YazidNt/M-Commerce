package com.batikapp.config;

import java.text.DecimalFormat;

public class Config {

//	public static String URL = "http://192.168.43.246/Nurulkays/batik/";

	public static String URL = "http://192.168.56.1:92/batik/";
	
	public static String kode = null;
	public static String session = null;
	public static String getKode() {
		return kode;
	}
	public static void setKode(String kode) {
		Config.kode = kode;
	}
	public static String setRupiah(double angka) {
		DecimalFormat df = new DecimalFormat("#,###");
		double a = angka;
		String bil = String.valueOf(df.format(a));
		return bil;
	}
	public static String getSession() {
		return session;
	}
	public static void setSession(String session) {
		Config.session = session;
	}
}
