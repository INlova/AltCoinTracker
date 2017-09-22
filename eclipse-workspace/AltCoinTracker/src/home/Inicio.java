package home;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;

import connection.API;

public class Inicio {

	public static boolean finish = false;

	public static void main(String[] args) {
		// Core core = new Core();
		try {
			testHash();
		} catch (InvalidKeyException | NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		System.exit(0);

		try {
			while (!finish) {
				JSONObject arry = API.getTicker("XZC");
				System.out.println("TEST - " + Calendar.getInstance().getTimeInMillis() + ": " + arry.toString());

				Thread.sleep(5000);
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}

	private static void testHash() throws NoSuchAlgorithmException, InvalidKeyException {
		String api = "621c153e515843d8a853741185803b93";
		String apiS = "796df535dcdd425c90cf73258e43fd45";
		Long nonce = Calendar.getInstance().getTimeInMillis();
		String uri = "https://bittrex.com/api/v1.1/market/getopenorders?apikey=" + api + "&nonce=" + nonce;
		// initialize a Mac instance using a signing key from the password
		SecretKeySpec signingKey = new SecretKeySpec(uri.getBytes(), "HmacSHA512");
		Mac mac = Mac.getInstance("HmacSHA512");
		mac.init(signingKey);
		mac.doFinal();
	}
}
