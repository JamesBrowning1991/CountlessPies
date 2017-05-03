package com.example.jamesbrowning.canvaspractise;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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


    public MySurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        ross = BitmapFactory.decodeResource(getResources(), R.drawable.ross);
        nathan_left = BitmapFactory.decodeResource(getResources(), R.drawable.nathan);
        nathan_right = BitmapFactory.decodeResource(getResources(), R.drawable.nathan_right);
    }

    @Override
    public void run() {
        while (isItOk) {
            if (!surfaceHolder.getSurface().isValid())
                continue;

            //  perform canvas drawing
            canvas = surfaceHolder.lockCanvas();
            this.setBackground();
            Movement.updateMovement(this);
            surfaceHolder.unlockCanvasAndPost(canvas);
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
