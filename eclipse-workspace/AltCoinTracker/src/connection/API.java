package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class API {

	private JSONObject connect(String uri) throws IOException {
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");

		InputStream is = connection.getInputStream();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		is.close();
		JSONObject jsonObject = new JSONObject(result);
//		System.out.println(jsonObject.toString());

		connection.disconnect();
		
		return jsonObject;
	}
	
	public JSONObject getMarketSummary(String coin) throws IOException {
		JSONObject json = connect("https://bittrex.com/api/v1.1/public/getmarketsummary?market=btc-" + coin);
//		JSONObject json = new JSONObject("{\"success\":true,"message":"","result":[{"MarketName":"BTC-FTC","High":0.00001571,"Low":0.00001132,"Volume":13215330.92756795,"Last":0.00001180,"BaseVolume":173.44845758,"TimeStamp":"2017-09-19T19:18:49.89","Bid":0.00001180,"Ask":0.00001191,"OpenBuyOrders":662,"OpenSellOrders":5113,"PrevDay":0.00001345,"Created":"2014-02-13T00:00:00"}]});
	}
}
