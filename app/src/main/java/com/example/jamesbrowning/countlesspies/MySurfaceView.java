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
                beforeCanvasInitialised = false;
            }

            //  perform canvas drawing
            canvas = surfaceHolder.lockCanvas();
            this.setBackground();
            if (doIntro) {
                doIntro = IntroMovement.doIntro(this);
            }
            else {
                isGameOver();  // might need to re-arrange here to stop flicker on game over
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
