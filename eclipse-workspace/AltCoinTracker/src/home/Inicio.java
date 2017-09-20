package home;

import java.io.IOException;

import org.json.JSONArray;

import connection.API;

public class Inicio {

	public static void main(String[] args) {
//		Core core = new Core();
		
		try {
			JSONArray arry = API.getMarketSummary("FTC");
			System.out.println("arry: " + arry.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public static void testAConnection() throws IOException {
//		// ProxySelector.setDefault(new ProxySelector() {
//		//
//		// @Override
//		// public void connectFailed(URI uri, SocketAddress sa, IOException ioe)
//		// {
//		// throw new RuntimeException("Proxy connect failed", ioe);
//		// }
//		//
//		// @Override
//		// public List select(URI uri) {
//		// return Arrays.asList(new Proxy(Proxy.Type.HTTP, new
//		// InetSocketAddress("10.2.248.246", 8080)));
//		// }
//		// });
//		// System.setProperty("https.proxyHost", "bittrex.com");
//		// System.setProperty("http.proxyPort", "8080");
//
//		String url = "https://bittrex.com/api/v1.1/public/getmarkets", proxy = "10.2.248.246", port = "8080";
//		URL server = new URL(url);
//		Properties systemProperties = System.getProperties();
//		systemProperties.setProperty("http.proxyHost", proxy);
//		systemProperties.setProperty("http.proxyPort", port);
//		HttpURLConnection connection = (HttpURLConnection) server.openConnection();
//		connection.connect();
//		InputStream in = connection.getInputStream();
//		System.out.println("lala: " + in.toString());
//	}

}
