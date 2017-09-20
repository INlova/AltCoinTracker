package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import config.Params;
import exceptions.FailedGetException;
import exceptions.InvalidParameterException;

public class API {

	private static JSONObject json = null;

	private static JSONObject connect(String uri) throws IOException {
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
		// System.out.println(jsonObject.toString());

		connection.disconnect();

		return jsonObject;
	}

	private static String getAPIKey() {
		return "lalala";
	}

	// Public Methods

	// Get todos los markets.
	public static JSONArray getMarkets() throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject("");
		else
			json = connect("https://bittrex.com/api/v1.1/public/getmarkets");

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("GetMarkets. Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Get listado de monedas
	public static JSONArray getCurrencies() throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject("");
		else
			json = connect("https://bittrex.com/api/v1.1/public/getcurrencies");

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("GetCurrencies. Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Get ticker de una moneda
	public static JSONArray getTicker(String coin) throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject("");
		else
			json = connect("https://bittrex.com/api/v1.1/public/getticker?market=BTC-" + coin);

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("GetTicker(" + coin + "). Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Get resumen de todas las monedas
	public static JSONArray getMarketSummaries() throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject();
		else
			json = connect("https://bittrex.com/api/v1.1/public/getmarketsummaries");

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("getMarketSummaries(). Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Get resumen de una moneda
	public static JSONArray getMarketSummary(String coin) throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject(
					"{\"success\":true,\"message\":\"\",\"result\":[{\"MarketName\":\"BTC-FTC\",\"High\":0.00001300,\"Low\":0.00001066,\"Volume\":2925260.02699330,\"Last\":0.00001150,\"BaseVolume\":34.01223061,\"TimeStamp\":\"2017-09-20T19:13:22.103\",\"Bid\":0.00001150,\"Ask\":0.00001162,\"OpenBuyOrders\":593,\"OpenSellOrders\":5177,\"PrevDay\":0.00001191,\"Created\":\"2014-02-13T00:00:00\"}]}");
		else
			json = connect("https://bittrex.com/api/v1.1/public/getmarketsummary?market=btc-" + coin);

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("getMarketSummary(" + coin + "). Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Get order book
	public static JSONArray getOrderBook(String coin, String type) throws IOException {
		json = null;

		if (type != "buy" && type != "sell" && type != "both") {
			try {
				throw new InvalidParameterException(
						"getOrderBook(coin, type). Type = 'buy', 'sell' or 'both'. Type = " + type);
			} catch (InvalidParameterException e) {
				return null;
			}
		}

		if (Params.TEST)
			json = new JSONObject();
		else
			json = connect("https://bittrex.com/api/v1.1/public/getorderbook?market=BTC-" + coin + "&type=" + type);

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("getOrderBook(coin, type). Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Get resumen del market
	public static JSONArray getMarketHistory(String coin) throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject();
		else
			json = connect("https://bittrex.com/api/v1.1/public/getmarkethistory?market=BTC-" + coin);

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("getMarketHistory. Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	/// MARKET API ///

	// Private Methods

	// Place buy order
	public static JSONArray buyLimit(String coin, double cantidad, double ratio) throws IOException {
		json = null;

		try {
			if (cantidad <= 0)
				throw new InvalidParameterException("buyLimit(). Cantidad no puede ser <= a 0. Cantidad: " + cantidad);
			if (ratio <= 0)
				throw new InvalidParameterException("buyLimit(). Ratio no puede ser <= a 0. Ratio: " + ratio);
		} catch (Exception | InvalidParameterException e) {
			return null;
		}

		if (Params.TEST)
			json = new JSONObject();
		else
			json = connect("https://bittrex.com/api/v1.1/market/buylimit?apikey=" + getAPIKey() + "&market=BTC-" + coin
					+ "&quantity=" + cantidad + "&rate=" + ratio);

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("buyLimit(" + coin + ", " + cantidad + ", " + ratio + "). Message = "
						+ json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Place sell order
	public static JSONArray sellLimit(String coin, double cantidad, double ratio) throws IOException {
		json = null;

		try {
			if (cantidad <= 0)
				throw new InvalidParameterException("sellLimit(). Cantidad no puede ser <= a 0. Cantidad: " + cantidad);
			if (ratio <= 0)
				throw new InvalidParameterException("sellLimit(). Ratio no puede ser <= a 0. Ratio: " + ratio);
		} catch (Exception | InvalidParameterException e) {
			return null;
		}

		if (Params.TEST)
			json = new JSONObject();
		else
			json = connect("https://bittrex.com/api/v1.1/market/selllimit?apikey=" + getAPIKey() + "&market=BTC-" + coin
					+ "&quantity=" + cantidad + "&rate=" + ratio);

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("sellLimit(" + coin + ", " + cantidad + ", " + ratio + "). Message = "
						+ json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Cancel Order
	public static JSONArray cancel(String uuid) throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject();
		else
			json = connect("https://bittrex.com/api/v1.1/market/cancel?apikey=" + getAPIKey() + "&uuid=" + uuid);

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("cancel(" + uuid + "). Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Get Open Orders
	public static JSONArray getOpenOrders(String coin) throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject();
		else
			json = connect(
					"https://bittrex.com/api/v1.1/market/getopenorders?apikey=" + getAPIKey() + "&market=" + coin);

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("getOpenOrders(" + coin + "). Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	/// ACCOUNT API ///

	// Get todos los balances
	public static JSONArray getBalances() throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject();
		else
			json = connect("https://bittrex.com/api/v1.1/account/getbalances?apikey=" + getAPIKey());

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("getBalance(). Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Get un balance
	public static JSONArray getBalance(String coin) throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject();
		else
			json = connect("https://bittrex.com/api/v1.1/account/getbalance?apikey=" + getAPIKey() + "&market=" + coin);

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("getBalance(" + coin + "). Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}

	// Get direccion de deposito de una moneda
	// public static JSONArray getDepositAddress(String coin) {
	// }

	// Sacar plata!
	// public static JSONArray withdraw(String coin, double cantidad, String
	// address) {
	// }

	// Get Order por uuid
	public static JSONArray getOrder(String uuid) throws IOException {
		json = null;

		if (Params.TEST)
			json = new JSONObject();
		else
			json = connect("https://bittrex.com/api/v1.1/account/getorder?uuid=" + uuid);

		if (json.getBoolean("success"))
			return json.getJSONArray("result");
		else {
			try {
				throw new FailedGetException("getOrder(" + uuid + "). Message = " + json.getString("message"));
			} catch (JSONException | FailedGetException e) {
				return null;
			}
		}
	}
}
