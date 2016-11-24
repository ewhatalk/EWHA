package com.tutorials.hp.bottomnavrecycler.Star;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tutorials.hp.bottomnavrecycler.R;

/*
* 2016/09/08
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class StarListView extends LinearLayout {
	private TextView mText01;
	private TextView mText02;
	private TextView mText03;

	public StarListView(Context context, StarListItem aItem) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.star_item, this, true);

		mText01 = (TextView) findViewById(R.id.dataItem_subject);
		mText01.setText(aItem.getData(0));

		mText02 = (TextView) findViewById(R.id.dataItem_class);
		mText02.setText(aItem.getData(1));
	}

	public void setText(int index, String data) {
		if (index == 0) {
			mText01.setText(data);
		} else if (index == 1) {
			mText02.setText(data);
		}
			else
		 {
			throw new IllegalArgumentException();
		}
	}
}
