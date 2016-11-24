package com.tutorials.hp.bottomnavrecycler.Alarm;

/*
* 2016/09/05
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class AlarmListItem {
    private String[] mData;
    private boolean mSelectable = true;

    public AlarmListItem(String[] obj) {
        mData = obj;
    }

    public AlarmListItem (String obj01, String obj02, String obj03,String obj04, String obj05,String obj06,String obj07,String obj08) {
        mData = new String[9];
        mData[0] = obj01;
        mData[1] = obj02;
        mData[2]=obj03;
        mData[3]=obj04;
        mData[4]=obj05;
        mData[5]=obj06;
        mData[6]=obj07;
        mData[7]=obj08;
    }

    public boolean isSelectable() {
        return mSelectable;
    }

    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    public String[] getData() {
        return mData;
    }

    public String getData(int index) {
        if (mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];
    }

    public void setData(String[] obj) {
        mData = obj;
    }

    public int compareTo(AlarmListItem other) {
        if (mData != null) {
            String[] otherData = other.getData();
            if (mData.length == otherData.length) {
                for (int i = 0; i < mData.length; i++) {
                    if (!mData[i].equals(otherData[i])) {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        } else {
            throw new IllegalArgumentException();
        }

        return 0;
    }
}

















