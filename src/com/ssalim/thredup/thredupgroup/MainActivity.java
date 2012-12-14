package com.ssalim.thredup.thredupgroup;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static boolean scrolled = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		LocalBroadcastManager.getInstance(this).registerReceiver(mainReceiver, 
				new IntentFilter("com.ssalim.thredup.thredupgroup.MainActivity"));
		
		hideMenuFragment();
		
		View view = LayoutInflater.from(this).inflate(R.layout.action_bar, null);
		TextView title = (TextView) view.findViewById(R.id.actionBarText);
		title.setText(R.string.app_name);
		ImageView back = (ImageView) view.findViewById(R.id.actionBarBack);
		back.setVisibility(View.GONE);
		
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(view);
		
	}
	
	public void showMenuFragment(){
		ListMenuFragment menuFragment = (ListMenuFragment) getFragmentManager()
				.findFragmentById(R.id.groupMenuFragment);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.show(menuFragment);
		ft.addToBackStack(null);
		ft.commit();
		
		View view = (View) findViewById(R.id.listFragmentContainer);
		view.scrollTo(299, 0);
		scrolled = true;
	}
	
	public void hideMenuFragment(){
		ListMenuFragment menuFragment = (ListMenuFragment) getFragmentManager()
				.findFragmentById(R.id.groupMenuFragment);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.hide(menuFragment);
		ft.commit();
		
		if (scrolled){
			View view = (View) findViewById(R.id.listFragmentContainer);
			view.scrollTo(0, 0);
			scrolled = false;
		}
	}

	@Override
	protected void onDestroy() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mainReceiver);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private BroadcastReceiver mainReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			boolean show = intent.getBooleanExtra("show", false);
			if (show){
				showMenuFragment();
			} else {
				hideMenuFragment();
			}
		}
	
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast toast;
		switch (item.getItemId()) {
		case R.id.menu_settings:
			toast = Toast.makeText(this, "Settings menu is pressed", Toast.LENGTH_LONG);
			toast.show();
			break;
		case R.id.menu_my_account:
			toast = Toast.makeText(this, "My Account menu is pressed", Toast.LENGTH_LONG);
			toast.show();
			break;

		default:
			break;
		}
		return true;
	}
	
	

}
