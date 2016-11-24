package com.tutorials.hp.bottomnavrecycler.Alarm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/*
* 2016/09/05
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class AlarmListAdapter extends BaseAdapter {
    private Context mContext;

    private List<AlarmListItem> mItems = new ArrayList<AlarmListItem>();

    public AlarmListAdapter(Context context) {
        mContext = context;
    }

    public void addItem(AlarmListItem it) {
        mItems.add(it);
    }

    public void setListItems(List<AlarmListItem> lit) {
        mItems = lit;
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public boolean isSelectable(int position) {
        try {
            return mItems.get(position).isSelectable();
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AlarmListView itemView;
        if (convertView == null) {
            itemView = new AlarmListView(mContext, mItems.get(position));
        } else {
            itemView = (AlarmListView) convertView;

            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(2, mItems.get(position).getData(2));

        }

        return itemView;
    }
}

















