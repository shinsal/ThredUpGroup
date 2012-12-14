package com.ssalim.thredup.thredupgroup;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupDetailActivity extends Activity {

	@Override
	protected void onStart() {
		super.onStart();
		initializeUI();
	}

	private void initializeUI() {
		Bundle extras = getIntent().getExtras();
		Group group = (Group) extras.get("group");
		TextView percentView = (TextView) findViewById(R.id.item_percent);
		float percent = (float) (group.getFundCollected()/group.getFundGoal());
		String percentString = String.valueOf((int) (percent*100)) + "%"; 
		percentView.setText(percentString);
		
		TextView amountRaised = (TextView) findViewById(R.id.item_top_amount);
		String raised = numberToMoney(String.valueOf(group.getFundCollected()));
		amountRaised.setText(raised);
		
		TextView amountGoal = (TextView) findViewById(R.id.item_bottom_amount);
		String goal = numberToMoney(String.valueOf(group.getFundGoal()));
		amountGoal.setText(goal);
		
		TextView membersView = (TextView) findViewById(R.id.group_members);
		StringBuffer groups = new StringBuffer();
		for(String s: group.getMembers()){
			groups.append(s);
			groups.append('\n');
		}
		membersView.setText(groups.toString().trim());
		
		ImageView percentCircle = (ImageView) findViewById(R.id.outer_ring_view);
		int width = 180;
		int height = 146;
		float stroke = 10f;
		float radius = 60;
		Bitmap bmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		RectF oval = new RectF();
		float center_x = width/2;
		float center_y = height/2;
		oval.set(center_x - radius, 
				center_y - radius, 
				center_x + radius, 
				center_y + radius);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(stroke);
		paint.setColor(getResources().getColor(R.color.whiteSolid));
		canvas.drawArc(oval, 270, 0 - (percent*360), false, paint);
		
		percentCircle.setImageDrawable(new BitmapDrawable(getResources(), bmp));
		
		/*ShapeDrawable test = new ShapeDrawable(new ArcShape(270, 0 - (percent*360)));
		test.setIntrinsicHeight(radius*2 - 2*(int)stroke);
		test.setIntrinsicWidth(radius*2 - 2*(int)stroke);
		test.getPaint().setColor(getResources().getColor(R.color.whiteSolid));
		test.getPaint().setStyle(Paint.Style.STROKE);
		test.getPaint().setStrokeWidth(stroke);
		
		percentCircle.setImageDrawable(test);*/
		


	}
	
	private String numberToMoney(String s){
		StringBuffer result = new StringBuffer(s);
		int i = result.indexOf(".");
		if (i+2 == result.length() ){
			result.append('0');
		}
		int j = i-3;
		while (j > 0){
			result.insert(j, ',');
			j = j - 3;
		}
		result.insert(0, "$");
		return result.toString();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_detail_layout);
		
		Group g = (Group) getIntent().getExtras().get("group");
		View view = LayoutInflater.from(this).inflate(R.layout.action_bar, null);
		TextView title = (TextView) view.findViewById(R.id.actionBarText);
		title.setText(g.getName());
		ImageView back = (ImageView) view.findViewById(R.id.actionBarBack);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	            intent.addFlags(
	                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                    Intent.FLAG_ACTIVITY_NEW_TASK);
	            startActivity(intent);
	            finish();
			}
		});
		
		TextView messageView = (TextView) findViewById(R.id.message_button);
		messageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getApplicationContext(), "Message menu is pressed", Toast.LENGTH_LONG);
				toast.show();
			}
		});
		
		TextView inviteView = (TextView) findViewById(R.id.invite_button);
		inviteView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getApplicationContext(), "Invite menu is pressed", Toast.LENGTH_LONG);
				toast.show();
			}
		});
		
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(view);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast toast;
		switch (item.getItemId()){
			case (R.id.menu_settings): 
				toast = Toast.makeText(this, "Settings menu is pressed", Toast.LENGTH_LONG);
				toast.show();
	            break;
			case (R.id.menu_message):
				toast = Toast.makeText(this, "Message menu is pressed", Toast.LENGTH_LONG);
				toast.show();
				break;
			case (R.id.menu_invite):
				toast = Toast.makeText(this, "Invite menu is pressed", Toast.LENGTH_LONG);
				toast.show();
				break;
			default:
				break;
			
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_detail_layout, menu);
		return true;
	}

}
