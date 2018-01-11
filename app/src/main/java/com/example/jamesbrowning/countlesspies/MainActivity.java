package com.example.jamesbrowning.countlesspies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button launchGameButton;
    Button resetScoresButton;
    TextView displayScores;
    static MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHandler = new MyDBHandler(this, null, null, 1);

        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        launchGameButton = (Button) findViewById(R.id.launchGameButton);
        resetScoresButton = (Button) findViewById(R.id.resetScores);
        displayScores = (TextView) findViewById(R.id.displayScores);

        initialiseScoreText();

        launchGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, GameplayActivity.class);
                startActivityForResult(myIntent, 666);
            }
        });

        resetScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.deleteAll();
                initialiseScoreText();
            }
        });
    }

    private void initialiseScoreText() {
        String dbScores = dbHandler.databaseToString();
        String scoresText = dbScores.isEmpty() ? "No Scores" : dbScores;
        displayScores.setText("Highscores:\n " + scoresText);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (666) : {
                if (resultCode == Activity.RESULT_OK) {
                    displaySaveUserAlert(data.getIntExtra("score", 0));
                }
                break;
            }
        }
    }

    public void displaySaveUserAlert(final int score) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Score");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input and then configure
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Enter player name");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputName = input.getText().toString();
                if (inputName.equals("")) {
                }
                if (!inputName.equals("")) {
                    dbHandler.addPlayer(new Player(input.getText().toString(), score));
                    initialiseScoreText();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show keybaord automatically
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        // disable OK button when no text
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }
        });

        // Set textchange listener for edittext to enable OK button
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(input.getText().toString().equals(""))
                    ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                else
                    ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
            }
        });

        dialog.show();
    }
}
