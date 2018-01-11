package com.example.jamesbrowning.countlesspies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class GameplayActivity extends Activity {

    MySurfaceView msv;
    boolean sleepOnResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay_activity);

        msv = new MySurfaceView(this);

        msv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Movement.updateTouchPoints(event);
                        break;
                    case MotionEvent.ACTION_UP:
                        Movement.stopMovingPlayer();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Movement.updateTouchPoints(event);
                        break;
                }

                return true;
            }
        });
        setContentView(msv);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("score", msv.score);
        int resultCode = msv.gameOver ? Activity.RESULT_OK : Activity.RESULT_CANCELED; // if not game over then dialog won't display
        setResult(resultCode, resultIntent);
        finish();
        overridePendingTransition(R.transition.activity_fade_out, R.transition.activity_fade_in);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sleepOnResume = true;
        msv.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        msv.resume();
        if (sleepOnResume) {
            sleepForABit(1000);
            sleepOnResume = false;
        }
    }

    public void sleepForABit(int millisecondWait) {
        try {
            Thread.sleep(millisecondWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}