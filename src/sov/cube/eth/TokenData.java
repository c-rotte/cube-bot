package sov.cube.eth;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sov.cube.Utils;

public class TokenData {

	private String etherscanKey;
	
	public TokenData(String etherscanKey) {
		
		this.etherscanKey = etherscanKey;
		
	}
	
	public Transaction[] getNewStores(double d) {
		
		int latestBlock = getCurrentBlock();
		int blockLastHour = (int) (latestBlock - (d * 60 * 60) / 10);
		
		System.out.println("Block: " + blockLastHour);
		
		String url = "http://api.etherscan.io/api?"
				+ "module=account&"
				+ "action=tokentx&"
				+ "contractaddress=0x26946ada5ecb57f3a1f91605050ce45c482c9eb1&"
				+ "address=0x19E6BF254aBf5ABC925ad72d32bac44C6c03d3a4"
				+ "&startblock=" + blockLastHour + "&"
				+ "endblock=999999999&"
				+ "sort=asc&"
				+ "apikey=" + etherscanKey;
		
		JSONObject o = null;
		try {
			o = Utils.readJsonFromUrl(url);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Transaction> transactions = new ArrayList<>();
		
		JSONArray arr = o.getJSONArray("result");
		for(int i = 0; i < arr.length(); i++) {
			JSONObject transaction = arr.getJSONObject(i);
			
			long timeStamp = Long.parseLong(transaction.getString("timeStamp")) * 1000;
			
			if(System.currentTimeMillis() - timeStamp <= 1000 * 60 * 60 * d) {
				transactions.add(new Transaction(transaction.getString("hash"), timeStamp, transaction.getInt("value") / ((float) Math.pow(10, 8))));
			}
		}
		
		return transactions.toArray(new Transaction[transactions.size()]);
		
	}
	
	public double getTotal() {
	
		String totalURL = "https://api.etherscan.io/api"
				+ "?module=account"
				+ "&action=tokenbalance"
				+ "&contractaddress=0x26946ada5ecb57f3a1f91605050ce45c482c9eb1"
				+ "&address=0x19E6BF254aBf5ABC925ad72d32bac44C6c03d3a4"
				+ "&tag=latest"
				+ "&apikey=" + etherscanKey;
		
		try {
			return Long.parseLong(Utils.readJsonFromUrl(totalURL).getString("result")) / 100000000D;
		} catch (NumberFormatException | JSONException | IOException e) {
			e.printStackTrace();
		}
		
		return -1;
		
	}
	
	public int getCurrentBlock() {
		
		String url = "https://api.etherscan.io/api?"
				+ "module=proxy&"
				+ "action=eth_blockNumber&"
				+ "apikey=" + etherscanKey;
		
		JSONObject o = null;
		try {
			o = Utils.readJsonFromUrl(url);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		return Integer.decode(o.getString("result"));
		
	}
	
	public class Transaction {
		
		private String hash;
		private long timeStamp;
		private float amount;
		
		public Transaction(String hash, long timeStamp, float amount) {
			this.hash = hash;
			this.timeStamp = timeStamp;
			this.amount = amount;
		}
		
		public String getHash() {
			return hash;
		}
		
		public long getTimeStamp() {
			return timeStamp;
		}
		
		public float getAmount() {
			return amount;
		}
		
	}
	
}
