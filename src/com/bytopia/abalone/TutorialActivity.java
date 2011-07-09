package com.bytopia.abalone;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.bytopia.abalone.mechanics.Board;
import com.bytopia.abalone.mechanics.Cell;
import com.bytopia.abalone.mechanics.ClassicLayout;
import com.bytopia.abalone.mechanics.Direction;
import com.bytopia.abalone.mechanics.EmptyLayout;
import com.bytopia.abalone.mechanics.Group;
import com.bytopia.abalone.mechanics.Move;
import com.bytopia.abalone.mechanics.Side;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TutorialActivity extends Activity {

	private TextView textView;
	private int currentActIndex = 0;
	private Scenario[] piece;
	private int[] scripts;
	private Button butNext, butPrev;
	private TutorialBoardView tutorialBoardView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tutorial);

		textView = (TextView) findViewById(R.id.turorial_text);

		butPrev = (Button) findViewById(R.id.prev_step);
		butPrev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				prevAct();
			}
		});

		butNext = (Button) findViewById(R.id.next_step);
		butNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nextAct();
			}
		});

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tutorial_board_layout);
		// Board board = new Board(new ClassicLayout(), Side.BLACK);
		tutorialBoardView = new TutorialBoardView(getApplicationContext());
		tutorialBoardView.setParent(this);

		initPiece();
		currentActIndex = 0;
		updateTutorial();

		linearLayout.addView(tutorialBoardView);

		// Board initialBoard = board.clone();
		// Scenario scenario1 = new Scenario(tutorialBoardView);
		// scenario1.SetActions(new Scenario.SelectGroup().construct(
		// tutorialBoardView, new Group(Cell.storage[7][5],
		// Cell.storage[7][7]), Scenario.PAUSE),
		// new Scenario.SelectGroup().construct(tutorialBoardView, null,
		// Scenario.PAUSE), new Scenario.MakeMove().construct(
		// tutorialBoardView, new Move(new Group(
		// Cell.storage[7][5], Cell.storage[7][7]),
		// Direction.NorthWest, Side.BLACK), 0),
		// new Scenario.UpdateBoard().construct(tutorialBoardView,
		// initialBoard, Scenario.PAUSE));
		// scenario1.start();
		// currentScenario = scenario1;
	}

	private void initPiece() {
		Board initialBoard = new Board();
		Scenario scIntro = new Scenario(tutorialBoardView, initialBoard);

		Scenario scWhatIs = new Scenario(tutorialBoardView, initialBoard);

		Board brdMoveSingle = new Board(new EmptyLayout(), Side.BLACK);
		brdMoveSingle.setState(Cell.storage[6][5], Side.BLACK);
		Scenario scMoveSingle = new Scenario(tutorialBoardView, brdMoveSingle);
		scMoveSingle.SetActions(scMoveSingle.new SelectGroup().construct(
				new Group(Cell.storage[6][5]), Scenario.HALF_PAUSE),
				scMoveSingle.new MakeMove().construct(new Move(new Group(
						Cell.storage[6][5]), Direction.NorthWest, Side.BLACK),
						Scenario.PAUSE),

				scMoveSingle.new SelectGroup().construct(new Group(
						Cell.storage[5][4]), Scenario.HALF_PAUSE),
				scMoveSingle.new MakeMove().construct(new Move(new Group(
						Cell.storage[5][4]), Direction.North, Side.BLACK),
						Scenario.PAUSE),

				scMoveSingle.new SelectGroup().construct(new Group(
						Cell.storage[4][4]), Scenario.HALF_PAUSE),
				scMoveSingle.new MakeMove().construct(new Move(new Group(
						Cell.storage[4][4]), Direction.East, Side.BLACK),
						Scenario.PAUSE),

				scMoveSingle.new SelectGroup().construct(new Group(
						Cell.storage[4][5]), Scenario.HALF_PAUSE),
				scMoveSingle.new MakeMove().construct(new Move(new Group(
						Cell.storage[4][5]), Direction.SouthEast, Side.BLACK),
						Scenario.PAUSE),

				scMoveSingle.new SelectGroup().construct(new Group(
						Cell.storage[5][6]), Scenario.HALF_PAUSE),
				scMoveSingle.new MakeMove().construct(new Move(new Group(
						Cell.storage[5][6]), Direction.South, Side.BLACK),
						Scenario.PAUSE),

				scMoveSingle.new SelectGroup().construct(new Group(
						Cell.storage[6][6]), Scenario.HALF_PAUSE),
				scMoveSingle.new MakeMove().construct(new Move(new Group(
						Cell.storage[6][6]), Direction.West, Side.BLACK),
						Scenario.HALF_PAUSE));

		Board brdSelGroup = new Board(new EmptyLayout(), Side.BLACK);
		putMarbles(brdSelGroup, new int[] { 3, 3, 4, 4, 3, 6, 3, 7, 4, 8, 7, 5,
				7, 6, 7, 7, 7, 8 }, Side.WHITE);
		Scenario scSelGroup = new Scenario(tutorialBoardView, brdSelGroup);
		scSelGroup.SetActions(scSelGroup.new SelectGroup().construct(new Group(
				Cell.storage[7][5], Cell.storage[7][7]), Scenario.PAUSE),
				scSelGroup.new SelectGroup().construct(null,
						Scenario.HALF_PAUSE),

				scSelGroup.new SelectGroup()
						.construct(new Group(Cell.storage[7][6],
								Cell.storage[7][8]), Scenario.PAUSE),
				scSelGroup.new SelectGroup().construct(null,
						Scenario.HALF_PAUSE),

				scSelGroup.new SelectGroup()
						.construct(new Group(Cell.storage[3][3],
								Cell.storage[4][4]), Scenario.PAUSE),
				scSelGroup.new SelectGroup().construct(null,
						Scenario.HALF_PAUSE),

				scSelGroup.new SelectGroup()
						.construct(new Group(Cell.storage[3][6],
								Cell.storage[3][7]), Scenario.PAUSE),
				scSelGroup.new SelectGroup().construct(null,
						Scenario.HALF_PAUSE),

				scSelGroup.new SelectGroup()
						.construct(new Group(Cell.storage[3][7],
								Cell.storage[4][8]), Scenario.PAUSE),
				scSelGroup.new SelectGroup().construct(null,
						Scenario.HALF_PAUSE));

		Board brdLeapMove = new Board(new EmptyLayout(), Side.BLACK);
		putMarbles(brdLeapMove, new int[] { 4, 5, 5, 5, 6, 5 }, Side.BLACK);
		Scenario scLeapMove = new Scenario(tutorialBoardView, brdLeapMove);

		scLeapMove.SetActions(scLeapMove.new SelectGroup().construct(new Group(
				Cell.storage[4][5], Cell.storage[6][5]), Scenario.HALF_PAUSE),
				scLeapMove.new MakeMove().construct(new Move(new Group(
						Cell.storage[4][5], Cell.storage[6][5]),
						Direction.West, Side.BLACK), Scenario.PAUSE),

				scLeapMove.new SelectGroup().construct(new Group(
						Cell.storage[4][4], Cell.storage[6][4]),
						Scenario.HALF_PAUSE), scLeapMove.new MakeMove()
						.construct(new Move(new Group(Cell.storage[4][4],
								Cell.storage[6][4]), Direction.NorthWest,
								Side.BLACK), Scenario.PAUSE),

				scLeapMove.new SelectGroup().construct(new Group(
						Cell.storage[4][3], Cell.storage[5][3]),
						Scenario.HALF_PAUSE), scLeapMove.new MakeMove()
						.construct(
								new Move(new Group(Cell.storage[4][3],
										Cell.storage[5][3]), Direction.East,
										Side.BLACK), Scenario.PAUSE),

				scLeapMove.new SelectGroup().construct(new Group(
						Cell.storage[4][4], Cell.storage[5][4]),
						Scenario.HALF_PAUSE), scLeapMove.new MakeMove()
						.construct(new Move(new Group(Cell.storage[4][4],
								Cell.storage[5][4]), Direction.SouthEast,
								Side.BLACK), Scenario.PAUSE),

				scLeapMove.new SelectGroup().construct(new Group(
						Cell.storage[5][5], Cell.storage[6][5]),
						Scenario.HALF_PAUSE), scLeapMove.new MakeMove()
						.construct(
								new Move(new Group(Cell.storage[5][5],
										Cell.storage[6][5]), Direction.West,
										Side.BLACK), Scenario.PAUSE),

				scLeapMove.new SelectGroup().construct(new Group(
						Cell.storage[5][4], Cell.storage[6][4]),
						Scenario.HALF_PAUSE), scLeapMove.new MakeMove()
						.construct(new Move(new Group(Cell.storage[5][4],
								Cell.storage[6][4]), Direction.NorthWest,
								Side.BLACK), Scenario.PAUSE),

				scLeapMove.new SelectGroup().construct(new Group(
						Cell.storage[3][3], Cell.storage[5][3]),
						Scenario.HALF_PAUSE), scLeapMove.new MakeMove()
						.construct(
								new Move(new Group(Cell.storage[3][3],
										Cell.storage[5][3]), Direction.East,
										Side.BLACK), Scenario.PAUSE),

				scLeapMove.new SelectGroup().construct(new Group(
						Cell.storage[3][4], Cell.storage[5][4]),
						Scenario.HALF_PAUSE), scLeapMove.new MakeMove()
						.construct(new Move(new Group(Cell.storage[3][4],
								Cell.storage[5][4]), Direction.SouthEast,
								Side.BLACK), Scenario.PAUSE));

		// Board brdPushMove = new Board(new EmptyLayout(), Side.BLACK);
		// putMarbles(brdPushMove, new int[] { 4, 5, 5, 5, 6, 5 }, Side.BLACK);
		// Scenario scLeapMove = new Scenario(tutorialBoardView, brdLeapMove);

		piece = new Scenario[] { scIntro, scWhatIs, scMoveSingle, scSelGroup,
				scLeapMove };
		scripts = new int[] { R.string.tutorial_step1_intro,
				R.string.tutorial_step2_whatis,
				R.string.tutorial_step3_movesingle,
				R.string.tutorial_step4_selectgroup,
				R.string.tutorial_step5_moveleap };
	}

	public void nextAct() {
		piece[currentActIndex].stop();
		currentActIndex++;
		updateTutorial();
	}

	public void prevAct() {
		piece[currentActIndex].stop();
		currentActIndex--;
		updateTutorial();
	}

	public void updateTutorial() {
		if (currentActIndex == piece.length) {
			startActivity(new Intent("com.bytopia.abalone.MAINMENU"));
			finish();
			return;
		}
		if (currentActIndex == piece.length - 1) {
			butNext.setText(R.string.finish);
		} else {
			butNext.setText(R.string.next);
			if (currentActIndex == 0) {
				butPrev.setEnabled(false);
			} else {
				butPrev.setEnabled(true);
			}
		}
		piece[currentActIndex].start();
		textView.setText(scripts[currentActIndex]);
	}

	@Override
	protected void onPause() {
		Log.d("state", "paused");
		if (currentActIndex != piece.length) {
			piece[currentActIndex].stop();
		}
		super.onPause();
	}

	public void putMarbles(Board b, int[] coords, byte side) {
		for (int i = 0; i < coords.length - 1; i += 2) {
			b.setState(Cell.storage[coords[i]][coords[i + 1]], side);
		}
	}

}
