package com.example.jamesbrowning.canvaspractise;

import android.graphics.Bitmap;
import android.os.Handler;
import android.view.MotionEvent;

public class Movement {
    private static float fingerX = 400;
    private static float fingerY = 400;
    private static float rossX = 400;
    private static float rossY = 400;
    private static float rossXMid;
    private static float rossYMid;

    private static boolean preRossGreaterThanNathan_X;
    private static boolean preRossGreaterThanNathan_Y;
    private static boolean rossGreaterThanNathan_X;
    private static boolean rossGreaterThanNathan_Y;

    private static Bitmap nathanToUse;
    private static float nathanX = 600;
    private static float nathanY = 600;
    private static int delayNathanCounter = 0;
    private static int passedNathanRecentlyCounter = 0;


    public static void updateTouchPoints(MotionEvent event) {
        fingerX = event.getX();
        fingerY = event.getY();
    }

    public static void updateMovement(MySurfaceView msv) {
        updateRossPosition(msv);
        updateNathanPosition(msv);
        checkIfRossHasPassedNathan();
    }

    public static void stopMovingPlayer() {
        fingerX = -1;  // what happens if event.getX()when finger is not down
        fingerY = -1;
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

        msv.canvas.drawBitmap(msv.ross, rossXMid, rossYMid, null);
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

        msv.canvas.drawBitmap(nathanToUse, nathanX, nathanY, null);
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

        if (rossGreaterThanNathan_X != preRossGreaterThanNathan_X || rossGreaterThanNathan_Y != preRossGreaterThanNathan_Y) {
            if (passedNathanRecentlyCounter == 0) {
                delayNathanCounter = 50;
                passedNathanRecentlyCounter = 150;
            }
        }

        preRossGreaterThanNathan_X = rossGreaterThanNathan_X;
        preRossGreaterThanNathan_Y = rossGreaterThanNathan_Y;
    }

    private static void checkContact() {

    }

}
