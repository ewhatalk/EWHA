package com.tutorials.hp.bottomnavrecycler.SearchDialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/*
* 2016/09/29
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class SearchDialogListAdapter extends BaseAdapter {
    private Context mContext;

    private List<SearchDialogListItem> mItems = new ArrayList<SearchDialogListItem>();

    public void clear() {
        mItems.clear();
    }

    public SearchDialogListAdapter(Context context) {
        mContext = context;
    }

    public void addItem(SearchDialogListItem it) {
        mItems.add(it);
    }

    public void setListItems(List<SearchDialogListItem> lit) {
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
        SearchDialogListView itemView;
        if (convertView == null) {
            itemView = new SearchDialogListView(mContext, mItems.get(position));
        } else {
            itemView = (SearchDialogListView) convertView;

            itemView.setText(0, mItems.get(position).getData(1));
            itemView.setText(1, mItems.get(position).getData(2));
            itemView.setText(2, mItems.get(position).getData(3));
        }

        return itemView;
    }
}

















