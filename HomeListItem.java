package com.tutorials.hp.bottomnavrecycler.Home;

import android.graphics.drawable.Drawable;

/*
* 2016/09/05
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class HomeListItem {
    private Drawable mIcon;
    private String[] mData;
    private boolean mSelectable = true;

    public HomeListItem(Drawable icon, String[] obj) {
        mIcon = icon;
        mData = obj;
    }

    public HomeListItem (Drawable icon, String obj01, String obj02, String obj03, String obj04, String obj05, String obj06, String obj07) {
        mIcon = icon;

        mData = new String[7];
        mData[0] = obj01;//subj_name
        mData[1] = obj02;//class_num
        mData[2] = obj03;//user_id
        mData[3] = obj04;//date
        mData[4] = obj05;//title
        mData[5] = obj06;//content
        mData[6] = obj07;//content
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

    public void setIcon(Drawable icon) {
        mIcon = icon;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public int compareTo(HomeListItem other) {
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

















