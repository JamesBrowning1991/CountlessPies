package com.example.jamesbrowning.countlesspies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class MySurfaceView extends SurfaceView implements Runnable {

    Thread thread = null;
    SurfaceHolder surfaceHolder;
    boolean isItOk = false;
    Canvas canvas;
    Bitmap ross;
    Bitmap nathan_left;
    Bitmap nathan_right;
    boolean doIntro;

    int lives = 5;
    boolean gameOver;


    public MySurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        ross = BitmapFactory.decodeResource(getResources(), R.drawable.ross);
        nathan_left = BitmapFactory.decodeResource(getResources(), R.drawable.nathan);
        nathan_right = BitmapFactory.decodeResource(getResources(), R.drawable.nathan_right);
        Movement.setPositionValues();
        gameOver = false;
        doIntro = true;
    }

    @Override
    public void run() {
        while (isItOk) {
            if (!surfaceHolder.getSurface().isValid())
                continue;

            //  perform canvas drawing
            canvas = surfaceHolder.lockCanvas();
            this.setBackground();
            if (doIntro) {
                doIntro = Movement.intro(this);
            }

            isGameOver();
            Movement.updateMovement(this);
            Movement.checkContactWithEnemy(this);
            drawScore();
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawScore() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        canvas.drawText("Lives: " + lives, 40, 100, paint);
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

    private void isGameOver() {
        if (lives <= 0) {
            gameOver = true;
            drawGameOver();
        }
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
        canvas.drawARGB(255, 139, 0, 139);
    }
}
