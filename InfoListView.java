package com.tutorials.hp.bottomnavrecycler.info;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tutorials.hp.bottomnavrecycler.R;

/*
* 2016/09/05
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class InfoListView extends LinearLayout {
	private ImageView mIcon;
	private TextView mText01;
	private TextView mText02;

	public InfoListView(Context context, InfoListItem aItem) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.info_item, this, true);

		mIcon = (ImageView) findViewById(R.id.Qicon2);
		mIcon.setImageDrawable(aItem.getIcon());

		mText01 = (TextView) findViewById(R.id.subject_question_info);//제목
		mText01.setText(aItem.getData(4));

		mText02 = (TextView) findViewById(R.id.date2);//날짜
		mText02.setText(aItem.getData(3));
	}

	public void setText(int index, String data) {
		if (index == 0) {
			mText01.setText(data);
		} else if (index == 1) {
			mText02.setText(data);
			} else {
			throw new IllegalArgumentException();
		}
	}

	public void setIcon(Drawable icon) {
		mIcon.setImageDrawable(icon);
	}
}
