package com.bytopia.abalone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity {

	Activity mainMenuActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mainMenuActivity = this;

		setContentView(R.layout.menu);
		Button newGameButtonHuman = (Button) findViewById(R.id.n_game_human);
		newGameButtonHuman.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent("com.bytopia.abalone.GAME");
				intent.putExtra("vs", "human");
				intent.putExtra("type", "new");
				startActivity(intent);

			}
		});

		Button newGameButtonCpu = (Button) findViewById(R.id.n_game_cpu);
		newGameButtonCpu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent("com.bytopia.abalone.GAME");
				// intent.getExtras().putString("side", "BLACK");
				intent.putExtra("vs", "cpu");
				intent.putExtra("type", "new");
				startActivity(intent);

			}
		});

		Button newGameButtonCpuSpecial = (Button) findViewById(R.id.n_game_cpu_s);
		newGameButtonCpuSpecial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new AlertDialog.Builder(MainMenuActivity.this)
						.setTitle(getResources().getString(R.string.cpu_type))
						.setItems(R.array.bot_names,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										Intent intent = new Intent(
												"com.bytopia.abalone.GAME");
										intent.putExtra("side", "BLACK");
										intent.putExtra("vs", "cpu");
										intent.putExtra("type", "new");
										String[] values = getResources()
												.getStringArray(
														R.array.bot_values);
										String choise = values[which];
										intent.putExtra("cpu_type", choise);
										startActivity(intent);

									}
								}).create().show();

			}
		});

		Button resumeButton = (Button) findViewById(R.id.resume_game);
		resumeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent("com.bytopia.abalone.RESUMEGAME");
				intent.putExtra("type", "resume");
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.rules_menu:
			Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.rules);
			dialog.setTitle(getString(R.string.rules_title));
			dialog.show();
			break;
		case R.id.preferences_menu:
			Intent preferencesIntent = new Intent(
					"com.bytopia.abalone.PREFERENCES");
			startActivity(preferencesIntent);
			break;
		}

		return true;
	}

	static final int RULESDIALOG = 1;

}
