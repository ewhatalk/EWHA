package com.tutorials.hp.bottomnavrecycler.Star;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/*
* 2016/09/08
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class StarListAdapter extends BaseAdapter {
    private Context mContext;

    private List<StarListItem> mItems = new ArrayList<StarListItem>();

    public StarListAdapter(Context context) {
        mContext = context;
    }

    public void addItem(StarListItem it) {
        mItems.add(it);
    }

    public void setListItems(List<StarListItem> lit) {
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
        StarListView itemView;
        if (convertView == null) {
            itemView = new StarListView(mContext, mItems.get(position));
        } else {
            itemView = (StarListView) convertView;

            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
        }

        return itemView;
    }
}

















