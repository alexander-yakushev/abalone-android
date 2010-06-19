package org.kpi;

import java.util.ArrayList;

import mechanics.Board;
import mechanics.Cell;
import mechanics.Layout;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View {

	static final float borderSize = 10;
	static final float SQRT3_2 = (float) Math.sqrt(3) / 2f;
	int size = 320;
	Paint defaultPaint, blackP, whiteP, emptyP;
	private Board board;
	boolean animation = false;
	
	PointF testCircle = null;
	
	class Ball {
		float x, y;
		int state;

		public Ball(float x, float y, int state) {
			this.x = x;
			this.y = y;
			this.state = state;
		}
	}

	ArrayList<Ball> balls;

	private float ballSize;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BoardView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setFocusable(true);
		Resources r = getResources();
		defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		defaultPaint.setColor(r.getColor(R.color.defaultColor));
		blackP = new Paint(Paint.ANTI_ALIAS_FLAG);
		setBackgroundColor(Color.BLUE);
		blackP.setColor(Color.BLACK);
		whiteP = new Paint(Paint.ANTI_ALIAS_FLAG);
		whiteP.setColor(Color.WHITE);
		emptyP = new Paint(Paint.ANTI_ALIAS_FLAG);
		emptyP.setColor(Color.GRAY);
	}

	private int measure(int measureSpec) {
		int result = 0;

		// Decode the measurement specifications.
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.UNSPECIFIED) {
			// Return a default size of 200 if no bounds are specified.
			result = 200;
		} else {
			// As you want to fill the available space
			// always return the full available bounds.
			result = specSize;
		}
		return result;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = measure(widthMeasureSpec);
		int measuredHeight = measure(heightMeasureSpec);

		int d = Math.min(measuredWidth, measuredHeight);

		setMeasuredDimension(d, d);
		size = d;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawLine(0, 0, 100, 100, defaultPaint);
		Log.d("draw", "height = " + getHeight());
		Log.d("draw", "width = " + getWidth());

		// TODO boar edges

		// TODO cells
		Paint curPaint = null;
		if (balls != null) {
			for (Ball ball : balls) {
				// canvas.drawRect(ball.x, ball.y, ball.x + ballSize, ball.y
				// + ballSize, defaultPaint);
				switch (ball.state) {
				case Layout.B:
					curPaint = blackP;
					break;
				case Layout.W:
					curPaint = whiteP;
					break;
				case Layout.E:
					curPaint = emptyP;
					break;
				}
				canvas.drawCircle(ball.x, ball.y, ballSize / 2f, curPaint);
			}
		}
		
		if(testCircle!=null){
			canvas.drawCircle(testCircle.x, testCircle.y, 2*ballSize, defaultPaint);
		}
	}

	public void drawBoard(Board board) {
		invalidate();
		this.board = board;

		balls = new ArrayList<Ball>();
		// FIXME get real size
		// size = 320;
		ballSize = ((float) size - 2 * borderSize) / 9f;
		for (int i = 1; i <= 9; i++) {
			float shift = (5f - i) * ballSize / 2f;
			float x, y;
			for (int j = 1; j <= 9; j++) {
				int state = board.getState(i, j);
				if (state != Layout.N) {
					x = borderSize + shift + (j - 1) * ballSize + ballSize / 2f;
					y = (float) (borderSize + (i - 1) * ballSize * SQRT3_2)
							+ ballSize / 2f;
					balls.add(new Ball(x, y, state));
				}
			}
		}

		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN){
			Cell cell = getCell(e.getX(), e.getY());
			testCircle = getPointByCell(cell);
			invalidate();
		}

		return true;
	}
	

	public PointF getPointByCell(Cell cell) {

		float shift = (5f - cell.getRow()) * ballSize / 2f;
		float x, y;

		x = borderSize + shift + (cell.getColumn() - 1) * ballSize + ballSize
				/ 2f;
		y = (float) (borderSize + (cell.getRow() - 1) * ballSize * SQRT3_2)
				+ ballSize / 2f;

		return new PointF(x, y);
	}

	public Cell getCell(float x, float y) {

		int row = (int) ((y - borderSize - (1 - SQRT3_2) * ballSize) / ((size
				- 2 * borderSize - 2 * (1 - SQRT3_2) * ballSize)
				* SQRT3_2 / 9f)) + 1;
		if (row > 9) {
			row = 9;
		} else if (row < 1) {
			row = 1;
		}
		int column = (int) ((x - borderSize - (5 - row) * ballSize / 2f)
				/ ballSize + 1);
		if (row <= 5) {
			if (column < 1)
				column = 1;
			else if (column > 4 + row) {
				column = 4 + row;
			}
		} else {
			if (column > 9) {
				column = 9;
			} else if (column < (row - 4)) {
				column = row - 4;
			}
		}
		Log.d("draw", "row = " + row);
		Log.d("draw", "column = " + column);
		return new Cell(row, column);
	}
}
