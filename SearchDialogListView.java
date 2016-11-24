package com.tutorials.hp.bottomnavrecycler.SearchDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tutorials.hp.bottomnavrecycler.R;

/*
* 2016/09/29
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class SearchDialogListView extends LinearLayout {
	private TextView mText01;
	private TextView mText02;
	private TextView mText03;

	public SearchDialogListView(Context context, SearchDialogListItem aItem) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.search_dialog_item, this, true);

		mText01 = (TextView) findViewById(R.id.search_dialog_Item_subjectname);
		mText01.setText(aItem.getData(1));

		mText02 = (TextView) findViewById(R.id.search_dialog_Item_classnum);
		mText02.setText(aItem.getData(2));

		mText03 = (TextView) findViewById(R.id.search_dialog_Item_prof);
		mText03.setText(aItem.getData(3));
	}

	public void setText(int index, String data) {
		if (index == 0) {
			mText01.setText(data);
		} else if (index == 1) {
			mText02.setText(data);
		} else if (index == 2) {
			mText03.setText(data);
		} else {
			throw new IllegalArgumentException();
		}
	}
}
