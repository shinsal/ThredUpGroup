package com.ssalim.thredup.thredupgroup;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Group implements Parcelable {

	private String mName;
	private List<String> mMembers = new ArrayList<String>();
	private double mFundGoal;
	private double mFundCollected;
	
	public Group(){};
	
	public Group(String name, List<String> members, double collected, double goal){
		mName = name;
		mMembers = members;
		mFundGoal = goal;
		mFundCollected = collected;
	}
	
	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public List<String> getMembers() {
		return mMembers;
	}

	public void setMembers(List<String> mMembers) {
		this.mMembers = mMembers;
	}

	public double getFundGoal() {
		return mFundGoal;
	}

	public void setFundGoal(double mFundGoal) {
		this.mFundGoal = mFundGoal;
	}

	public double getFundCollected() {
		return mFundCollected;
	}

	public void setFundCollected(double mFundCollected) {
		this.mFundCollected = mFundCollected;
	}
	
	public void addMember(String name){
		mMembers.add(name);
	}
	
	public boolean removeMember(String name){
		return mMembers.remove(name);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(mName);
		dest.writeStringList(mMembers);
		dest.writeDouble(mFundCollected);
		dest.writeDouble(mFundGoal);
		
	}
	
	public static final Creator<Group> CREATOR = new Creator<Group>() {
        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    private Group(Parcel source) {
        mName = source.readString();
        source.readStringList(mMembers);
        mFundCollected = source.readDouble();
        mFundGoal = source.readDouble();
    }
}
