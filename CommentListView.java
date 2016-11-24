package com.tutorials.hp.bottomnavrecycler.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tutorials.hp.bottomnavrecycler.R;

/*
* 2016/09/05
* Copyright ⓒ HyunJung Kim All Rights Reserved.
*/

public class CommentListView extends LinearLayout {
	private TextView mText01;
	private TextView mText02;
	private TextView mText03;

	public CommentListView(Context context, CommentListItem aItem) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.comment_item, this, true);

		mText01 = (TextView) findViewById(R.id.comment_user_id);
		mText01.setText(aItem.getData(0));

		mText02 = (TextView) findViewById(R.id.comment_date);
		mText02.setText(aItem.getData(1));

		mText03 = (TextView) findViewById(R.id.comment_content);
		mText03.setText(aItem.getData(2));
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
