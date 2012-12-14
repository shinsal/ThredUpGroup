package com.ssalim.thredup.thredupgroup;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class GroupListFragment extends Fragment {
	private ArrayList<Group> mGroups;
	private ExpandableListView mGroupListView;
	private View mView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.activity_group_list, container, false);
		
		LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
		localBroadcastManager.registerReceiver(groupListReceiver, 
				new IntentFilter("com.ssalim.thredup.thredupgroup"));
		
		getGroupList();
		return mView;
	}

	private void updateUI(){
		mGroupListView = (ExpandableListView) mView.findViewById(R.id.groupListView);
		GroupListViewAdapter adapter = new GroupListViewAdapter(getActivity(),
				mGroupListView, mGroups);
		
		mGroupListView.setAdapter(adapter);
		
		if (mGroups != null && mGroups.size() > 0){
			TextView noGroupView = (TextView) mView.findViewById(R.id.empty);
			noGroupView.setVisibility(View.INVISIBLE);
		}
	}

	private void getGroupList() {
		// start the background service to do web-service call to get grouplist
		Intent intent = new Intent(getActivity(), ThredUpService.class);
		// assuming email is the id to lookup user in the server
		intent.putExtra("email", "ss@gmail.com");
		getActivity().startService(intent);
		
	}
	
	private BroadcastReceiver groupListReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			mGroups = intent.getParcelableArrayListExtra("groups");
			updateUI();
		}
		
	};

	@Override
	public void onDestroy() {
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(groupListReceiver);
		super.onDestroy();
	}

}
