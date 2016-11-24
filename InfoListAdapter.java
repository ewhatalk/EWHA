package com.tutorials.hp.bottomnavrecycler.info;

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

public class InfoListAdapter extends BaseAdapter {
    private Context mContext;

    private List<InfoListItem> mItems = new ArrayList<InfoListItem>();

    public InfoListAdapter(Context context) {
        mContext = context;
    }

    public void addItem(InfoListItem it) {
        mItems.add(it);
    }

    public void setListItems(List<InfoListItem> lit) {
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
        InfoListView itemView;
        if (convertView == null) {
            itemView = new InfoListView(mContext, mItems.get(position));
        } else {
            itemView = (InfoListView) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(4));
            itemView.setText(1, mItems.get(position).getData(3));
        }

        return itemView;
    }
}

















