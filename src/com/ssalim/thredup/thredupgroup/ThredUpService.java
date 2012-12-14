package com.ssalim.thredup.thredupgroup;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class ThredUpService extends IntentService {
	public ThredUpService() {
		super("ThredUpService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// here do the web-service call to get the group list
		/*HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		try {
			HttpResponse response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200){
				//parse the response
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		// just to mockup, I make local made-up data for groups
		ArrayList<Group> groups = new ArrayList<Group>();
		ArrayList<String> members1 = new ArrayList<String>();
		members1.add("Taylor Swift");
		members1.add("Rihanna");
		members1.add("Beyonce Knowles");
		
		ArrayList<String> members2 = new ArrayList<String>();
		members2.add("Brian Hulme");
		members2.add("Ned Flanders");
		members2.add("Jimmy Pesto");
		members2.add("Michael Andersson");
		members2.add("Taylor Swift");
		members2.add("Rihanna");
		members2.add("Beyonce Knowles");
		
		ArrayList<String> members3 = new ArrayList<String>();
		members3.add("Owl City");
		members3.add("Jason Mraz");
		
		groups.add(new Group("Wendy's College Fund!", members1, 5000.00, 10000.00));
		groups.add(new Group("Weibel Elementary Soccer Team Fund", members2, 1720.53, 3000.00));
		groups.add(new Group("Wendy want a car more than education", members3, 0.00, 5000));
		
		LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
		Intent groupIntent = new Intent("com.ssalim.thredup.thredupgroup");
		groupIntent.putParcelableArrayListExtra("groups", groups);
		localBroadcastManager.sendBroadcast(groupIntent);
	}
	
	
}
