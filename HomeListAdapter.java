package com.tutorials.hp.bottomnavrecycler.Home;

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

public class HomeListAdapter extends BaseAdapter {
    private Context mContext;

    private List<HomeListItem> mItems = new ArrayList<HomeListItem>();

    public HomeListAdapter(Context context) {
        mContext = context;
    }

    public void addItem(HomeListItem it) {
        mItems.add(it);
    }

    public void setListItems(List<HomeListItem> lit) {
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
        HomeListView itemView;
        if (convertView == null) {
            itemView = new HomeListView(mContext, mItems.get(position));
        } else {
            itemView = (HomeListView) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
            itemView.setText(2, mItems.get(position).getData(4));
            itemView.setText(3, mItems.get(position).getData(3));
        }

        return itemView;
    }
}

















