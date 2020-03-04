package sov.cube.twitter;

import sov.cube.eth.TokenData.Transaction;

public class CubeBot extends Bot {

	public CubeBot(String[] auth) {
		super(auth);
	}

	public void update(Transaction t, double total) {
		tweet(t.getAmount() + " BSOV were cubed:\n"
				+ "https://etherscan.io/tx/" + t.getHash() + "\n\n"
				+ "Total: " + total + " BSOV", null);
	}
	
}
