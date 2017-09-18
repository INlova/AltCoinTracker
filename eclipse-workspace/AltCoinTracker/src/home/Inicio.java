package home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.tictactec.ta.lib.Core;

public class Inicio {

	public static void main(String[] args) {
		Core core = new Core();
		
		try {
			test();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void test() throws IOException {
		String uri = "https://bittrex.com/api/v1.1/public/getmarkets";
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
		
		InputStream is = connection.getInputStream();
		
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(is));
	    String line = "";
	    String result = "";
	    while((line = bufferedReader.readLine()) != null)
	        result += line;

	    is.close();
	    JSONObject jsonObject = new JSONObject(result);
	    System.out.println(jsonObject.toString());

		connection.disconnect();
	}

}
