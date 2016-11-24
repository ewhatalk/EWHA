package com.tutorials.hp.bottomnavrecycler.Alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tutorials.hp.bottomnavrecycler.R;

/*
* 2016/09/05
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class AlarmListView extends LinearLayout {
	private TextView mText01;
	private TextView mText03;

	public AlarmListView(Context context, AlarmListItem aItem) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.alarm_item, this, true);

		mText01 = (TextView) findViewById(R.id.dataItem01);
		mText01.setText(aItem.getData(0));

		mText03 = (TextView) findViewById(R.id.dataItem03);
		mText03.setText(aItem.getData(1));
	}

	public void setText(int index, String data) {
		if (index == 0) {
			mText01.setText(data);
		} else if (index == 1) {
			mText03.setText(data);
		} else {
			throw new IllegalArgumentException();
		}
	}
}
