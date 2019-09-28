package sov.cube;

import sov.cube.eth.TokenData;
import sov.cube.eth.TokenData.Transaction;
import sov.cube.twitter.CubeBot;

public class Main {

	public static void main(String[] args) {
		
		for(String arg : args) {
			System.out.println("Using: " + arg);
		}
		
		TokenData data = new TokenData(args[0]);
		String[] twitterCreds = {args[1], args[2], args[3], args[4]};
		
		CubeBot bot = new CubeBot(twitterCreds);
		
		while(true) {
			
			double total = data.getTotal();
			
			if(total != -1) {
				Transaction[] ts = data.getNewStores(1 / 60D);
				if(ts.length > 0) {
					for(Transaction t : ts) {
						bot.update(t, total);
					}
				}else {
					System.out.println("No transactions found. (Total: " + total + ")");
				}
			}
			
			try {
				Thread.sleep(1000 * 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
