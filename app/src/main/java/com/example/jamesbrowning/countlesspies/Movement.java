package com.example.jamesbrowning.countlesspies;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public class Movement {
    public static final float ROSS_X = 200;
    public static final float ROSS_Y = 250;
    public static final float NATHAN_X = 800;
    public static final float NATHAN_Y = 1000;

    private static float fingerX;
    private static float fingerY;
    private static float rossX;
    private static float rossY;
    public static float rossXMid;
    public static float rossYMid;

    private static boolean preRossGreaterThanNathan_X;
    private static boolean preRossGreaterThanNathan_Y;
    private static boolean rossGreaterThanNathan_X;
    private static boolean rossGreaterThanNathan_Y;

    private static Bitmap nathanToUse;
    public static float nathanX;
    public static float nathanY;
    private static int delayNathanCounter = 0;
    private static int passedNathanRecentlyCounter = 0;

    public static void setPositionValues() {
        fingerX = fingerY = -1;
        rossX = ROSS_X;
        rossY = ROSS_Y;
        nathanX = NATHAN_X;
        nathanY = NATHAN_Y;
    }

    private static void setPositions(MySurfaceView msv) {
        msv.canvas.drawBitmap(msv.ross, rossXMid, rossYMid, null);
        msv.canvas.drawBitmap(nathanToUse, nathanX, nathanY, null);
    }

    public static void updateTouchPoints(MotionEvent event) {
        fingerX = event.getX();
        fingerY = event.getY();
    }

    public static void updateMovement(MySurfaceView msv) {
        if (!msv.gameOver) {
            updateRossPosition(msv);
            updateNathanPosition(msv);
            setPositions(msv);
            checkIfRossHasPassedNathan();
        }
    }

    public static void stopMovingPlayer() {
        fingerX = -1;  // what happens if event.getX()when finger is not down
        fingerY = -1;
    }

    public static void checkContactWithEnemy(MySurfaceView msv) {
        if (isThereContact(msv)) {
            setPositionValues();
            setPositions(msv);
            msv.lives = msv.lives > 0 ? msv.lives-1 : msv.lives;
        }
    }

    private static boolean isThereContact(MySurfaceView msv) {
        float xDiff = Movement.rossXMid - Movement.nathanX;
        float yDiff = Movement.rossYMid - Movement.nathanY;

        return (xDiff < 80 && xDiff > -80) && (yDiff < 180 && yDiff > -180);
    }

    private static void updateRossPosition(MySurfaceView msv) {
        float newRossX = Movement.rossX;
        float newRossY = Movement.rossY;
        int xMoveSpeed = 12;
        int yMoveSpeed = 16;

        if(fingerX < 0){}
        else if (fingerX > rossX && (fingerX - rossX) > xMoveSpeed*2) {
            float newPosition = rossX + xMoveSpeed;
            newRossX = rossX = newPosition;
        }
        else if (fingerX < rossX && (rossX - fingerX) > xMoveSpeed*2) {
            float newPosition = rossX - xMoveSpeed;
            newRossX = rossX = newPosition;
        }

        if (fingerY < 0){}
        else if (fingerY > rossY && (fingerY - rossY) > yMoveSpeed*2) {
            float newPosition = rossY + yMoveSpeed;
            newRossY = rossY = newPosition;
        }
        else if (fingerY < rossY && (rossY - fingerY) > yMoveSpeed*2) {
            float newPosition = rossY - yMoveSpeed;
            newRossY = rossY = newPosition;
        }

        rossXMid = newRossX - (msv.ross.getWidth()/2);
        rossYMid = newRossY - (msv.ross.getHeight()/2);
    }

    private static void updateNathanPosition(MySurfaceView msv) {
        int xMoveSpeed = 2;
        int yMoveSpeed = 3;

        checkIfRossHasPassedNathan();

        if (delayNathanCounter > 0) {
             delayNathanCounter--;
        } else {
            if (nathanX > rossXMid && (nathanX - rossXMid) > xMoveSpeed*2) {
                nathanX = nathanX - xMoveSpeed;
                nathanToUse = msv.nathan_left;
                subtractPassedNathanRecentlyCounter();
            }
            else if (nathanX < rossXMid && (rossXMid - nathanX) > xMoveSpeed*2) {
                nathanX = nathanX + xMoveSpeed;
                nathanToUse = msv.nathan_right;
                subtractPassedNathanRecentlyCounter();
            }
            if (nathanY > rossYMid && (nathanY - rossYMid) > yMoveSpeed*2) {
                nathanY = nathanY - yMoveSpeed;
                subtractPassedNathanRecentlyCounter();
            }
            else if ( nathanY < rossYMid && (rossYMid - nathanY) > yMoveSpeed*2) {
                nathanY = nathanY + yMoveSpeed;
                subtractPassedNathanRecentlyCounter();
            }
        }
    }

    private static void subtractPassedNathanRecentlyCounter() {
        if (passedNathanRecentlyCounter > 0)
            passedNathanRecentlyCounter--;
        else
            passedNathanRecentlyCounter = 0;
    }

    private static void checkIfRossHasPassedNathan() {

        if (rossXMid > nathanX)
            rossGreaterThanNathan_X = true;
        else if (rossXMid < nathanX)
            rossGreaterThanNathan_X = false;
        if (rossYMid > nathanY)
            rossGreaterThanNathan_Y = true;
        else if (rossYMid < nathanY)
            rossGreaterThanNathan_Y = false;

        if (rossGreaterThanNathan_X != preRossGreaterThanNathan_X ||
                rossGreaterThanNathan_Y != preRossGreaterThanNathan_Y) {
            if (passedNathanRecentlyCounter == 0) {
                delayNathanCounter = 50;
                passedNathanRecentlyCounter = 150;
            }
        }

        preRossGreaterThanNathan_X = rossGreaterThanNathan_X;
        preRossGreaterThanNathan_Y = rossGreaterThanNathan_Y;
    }

}
