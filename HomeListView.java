package com.tutorials.hp.bottomnavrecycler.Home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tutorials.hp.bottomnavrecycler.Alarm.AlarmListItem;
import com.tutorials.hp.bottomnavrecycler.R;

/*
* 2016/09/05
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class HomeListView extends LinearLayout {
	private ImageView mIcon;
	private TextView mText01;
	private TextView mText02;
	private TextView mText03;
	private TextView mText04;

	public HomeListView(Context context, HomeListItem aItem) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.home_item, this, true);

		mIcon = (ImageView) findViewById(R.id.Qicon);
		mIcon.setImageDrawable(aItem.getIcon());

		mText01 = (TextView) findViewById(R.id.subject_name);//과목명
		mText01.setText(aItem.getData(0));

		mText02 = (TextView) findViewById(R.id.subject_class);//분반번호
		mText02.setText(aItem.getData(1));

		mText03 = (TextView) findViewById(R.id.subject_question);//제목
		mText03.setText(aItem.getData(4));

		mText04 = (TextView) findViewById(R.id.date);//날짜
		mText04.setText(aItem.getData(3));
	}

	public void setText(int index, String data) {
		if (index == 0) {
			mText01.setText(data);
		} else if (index == 1) {
			mText02.setText(data);
		} else if (index == 2) {
			mText03.setText(data);
		} else if (index == 3) {
			mText04.setText(data);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void setIcon(Drawable icon) {
		mIcon.setImageDrawable(icon);
	}
}
