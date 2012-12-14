package com.ssalim.thredup.thredupgroup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class ListMenuFragment extends Fragment {
	private static Group mGroup;

	@Override
	public View getView() {
		return super.getView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.group_list_menu, container, false);
		
		View messageView = view.findViewById(R.id.menu1);
		messageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getActivity(), "Message Button is pressed", Toast.LENGTH_LONG);
				toast.show();
			}
		});
		
		View addView = view.findViewById(R.id.menu2);
		addView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getActivity(), "Add Button is pressed", Toast.LENGTH_LONG);
				toast.show();
			}
		});
		
		View donateView = view.findViewById(R.id.menu3);
		donateView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getActivity(), "Donate Button is pressed", Toast.LENGTH_LONG);
				toast.show();
			}
		});
		return view;
	}

	public static void setGroup(Group g){
		mGroup = g;
	}
}
