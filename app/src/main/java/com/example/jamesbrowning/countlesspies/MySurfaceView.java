package com.example.jamesbrowning.countlesspies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputType;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.EditText;

public class MySurfaceView extends SurfaceView implements Runnable {

    Thread thread = null;
    SurfaceHolder surfaceHolder;

    protected int w;
    protected int h;

    Canvas canvas;
    Bitmap ross;
    Bitmap nathan_left;
    Bitmap nathan_right;
    Bitmap pie;

    boolean beforeCanvasInitialised = true;
    int lives = 5;
    int score = 0;
    boolean isItOk = false;
    public boolean doIntro;
    boolean gameOver;

    public MySurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        initialiseBitmaps();
        gameOver = false;
        doIntro = true;
    }

    private void initialiseBitmaps() {
        ross = BitmapFactory.decodeResource(getResources(), R.drawable.ross);
        nathan_left = BitmapFactory.decodeResource(getResources(), R.drawable.nathan);
        nathan_right = BitmapFactory.decodeResource(getResources(), R.drawable.nathan_right);
        pie = BitmapFactory.decodeResource(getResources(), R.drawable.pie1);
    }

    // supposed to be the way to get dimensions, but doesn't appear to be working correctly
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    @Override
    public void run() {
        while (isItOk) {
            if (!surfaceHolder.getSurface().isValid())
                continue;

            // so that we can set starting positions based on view dimensions
            if (beforeCanvasInitialised) {
                canvas = surfaceHolder.lockCanvas();
                surfaceHolder.unlockCanvasAndPost(canvas);
                Movement.setPositionValues(this);
                Movement.setMovementSpeeds(this);
                beforeCanvasInitialised = false;
            }

            //  perform canvas drawing
            canvas = surfaceHolder.lockCanvas();
            this.setBackground();
            if (doIntro) {
                doIntro = IntroMovement.doIntro(this);
            }
            else if (isGameOver()){
                gameOver = true;
                drawGameOver();
            }
            else {
                Movement.updateMovement(this);
                Movement.checkGotPie(this);
                Movement.checkContactWithEnemy(this);
                drawScore();
                drawLives();
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawLives() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        canvas.drawText("Lives: " + lives, 40, 100, paint);
    }

    private void drawScore() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        canvas.drawText("Score: " + score, 500, 100, paint);
    }

    private void drawGameOver() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(150);

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("Game Over", xPos, yPos, paint);
    }

    private boolean isGameOver() {
        return lives <= 0;
    }

    public void pause() {
        isItOk = false;
        while (true) {
            try {
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            break;
        }
        thread = null;
    }
    public void resume() {
        isItOk = true;
        thread = new Thread(this);
        thread.start();
    }

    private void setBackground() {
        canvas.drawARGB(255, 48, 16, 36);
    }
}
