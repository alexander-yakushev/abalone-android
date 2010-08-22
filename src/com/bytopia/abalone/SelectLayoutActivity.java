package com.bytopia.abalone;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.bytopia.abalone.mechanics.ArtificialIntilligence;
import com.bytopia.abalone.mechanics.Board;
import com.bytopia.abalone.mechanics.ClassicLayout;
import com.bytopia.abalone.mechanics.Layout;
import com.bytopia.abalone.mechanics.Side;

public class SelectLayoutActivity extends Activity {

	List<Layout> layouts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		layouts = new ArrayList<Layout>();
		for (String layout : getResources().getStringArray(R.array.game_layouts)) {
			String tempAi = "com.bytopia.abalone.mechanics." + layout;
			try {
				Class layoutClass = Class.forName(tempAi);
				Layout l = (Layout)layoutClass.newInstance();
				layouts.add(l);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		setContentView(R.layout.layout_selecting);
		BoardView boardView = new BoardView(getApplicationContext()) {
			@Override
			public boolean onTouchEvent(MotionEvent e) {
				return true;
			}
		};
		boardView.drawBoard(new Board(new ClassicLayout(), Side.BLACK));
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_selecting_layout);
		linearLayout.addView(boardView);
	}
}
