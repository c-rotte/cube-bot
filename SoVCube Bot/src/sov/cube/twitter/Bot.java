package sov.cube.twitter;

import java.io.InputStream;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public abstract class Bot {

	private Twitter twitter;
	
	public Bot(String[] auth) {
		
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(auth[0], auth[1]);
		twitter.setOAuthAccessToken(new AccessToken(auth[2], auth[3]));
		
	}
	
	public void tweet(String text, InputStream inputStream) {
		
		StatusUpdate update = new StatusUpdate(text);
		if(inputStream != null) {
			update.setMedia("img.png", inputStream);
		}
		try {
			twitter.updateStatus(update);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
	}
	
}
