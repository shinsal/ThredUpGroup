package com.ssalim.thredup.thredupgroup;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class GroupListViewAdapter extends BaseExpandableListAdapter{
	private Context mCtx;
	private ExpandableListView mGroupListView;
	private List<Group> mGroups;
	private int[] groupStatus;
	private boolean showGroup = false;

	public GroupListViewAdapter(Context ctx, ExpandableListView groupListView, List<Group> groups){
		mCtx = ctx;
		mGroupListView = groupListView;
		mGroups = groups;
		groupStatus = new int[mGroups.size()];
		
		setListEvent();
	}
	
	private void setListEvent() {
		mGroupListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				//reset others
				for(int i = 0; i< mGroups.size(); i++){
					if (i != groupPosition){
						mGroupListView.collapseGroup(i); 
					}
				}
				groupStatus[groupPosition] = 1;
				notifyDataSetChanged();
			}
		});
		
		mGroupListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			
			@Override
			public void onGroupCollapse(int groupPosition) {
				groupStatus[groupPosition] = 0;
			}
		});
		
		mGroupListView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		});
		
		mGroupListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// show detail page
				Intent intent = new Intent(mCtx, GroupDetailActivity.class);
				Group group =  mGroups.get(groupPosition);
				intent.putExtra("group", group);
				mCtx.startActivity(intent);
				return false;
			}
		});
	}

	@Override
	public Group getChild(int arg0, int arg1) {
		return mGroups.get(arg0);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg0;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		if (arg3 == null){
			arg3 = LayoutInflater.from(mCtx).inflate(R.layout.group_list_item, null);
		}
		TextView percentView = (TextView) arg3.findViewById(R.id.item_percent);
		TextView amountRaised = (TextView) arg3.findViewById(R.id.item_top_amount);
		TextView amountGoal = (TextView) arg3.findViewById(R.id.item_bottom_amount);
		
		Group child = getChild(arg0, arg1);
		float percent = (float) (child.getFundCollected() / child.getFundGoal());
		String percentString = String.valueOf((int)(percent*100)) + "%"; 
		percentView.setText(percentString);
		
		String raised = numberToMoney(String.valueOf(child.getFundCollected()));
		amountRaised.setText(raised);
		
		String goal = numberToMoney(String.valueOf(child.getFundGoal()));
		amountGoal.setText(goal);
		
		ImageView percentCircle = (ImageView) arg3.findViewById(R.id.outer_ring_view);
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
		paint.setColor(mCtx.getResources().getColor(R.color.whiteSolid));
		canvas.drawArc(oval, 270, 0 - (percent*360), false, paint);
		
		percentCircle.setImageDrawable(new BitmapDrawable(mCtx.getResources(), bmp));
		
		return arg3;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return 1;
	}

	@Override
	public Group getGroup(int arg0) {
		return mGroups.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return mGroups.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		if (arg2 == null){
			arg2 = LayoutInflater.from(mCtx).inflate(R.layout.group_list, null);
		}
		
		TextView listItem = (TextView) arg2.findViewById(R.id.groupListText);
		listItem.setText(mGroups.get(arg0).getName());
		listItem.setAlpha(0.9f);
		
		final int pos = arg0;
		ImageView imageView = (ImageView) arg2.findViewById(R.id.arrow_drawer);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				ListMenuFragment.setGroup(mGroups.get(pos));
				// update boolean value
				if (showGroup){
					showGroup = false;
				} else {
					showGroup = true;
				}
				// fix arrow direction n color
				changeArrowDirection(view, pos);
				// show/hide groupMenuFragment
				Intent intent2 = new Intent("com.ssalim.thredup.thredupgroup.MainActivity");
				intent2.putExtra("show", showGroup);
				LocalBroadcastManager.getInstance(mCtx).sendBroadcast(intent2);
			}
		});
		
		if(mGroupListView.isGroupExpanded(arg0)){
			
			listItem.setBackgroundColor(mCtx.getResources().getColor(R.color.teal));
			listItem.setTextColor(mCtx.getResources().getColor(R.color.white));
		} else {
			listItem.setBackgroundColor(mCtx.getResources().getColor(R.color.white));
			listItem.setTextColor(mCtx.getResources().getColor(R.color.black));
		}
		
		changeArrowDirection(arg2, arg0);
		
		return arg2; 
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
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
	
	private void changeArrowDirection(View v, int pos){
		ImageView arrow = (ImageView) v.findViewById(R.id.arrow_drawer);
		if(groupStatus[pos]== 1 ){ //mGroupListView.isGroupExpanded(pos)
			if (showGroup)
				arrow.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.left_arrow_white));
			else 
				arrow.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.right_arrow_white));
		} else {
			if (showGroup)
				arrow.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.left_arrow_black));
			else
				arrow.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.right_arrow_black));
		}
	}
}
