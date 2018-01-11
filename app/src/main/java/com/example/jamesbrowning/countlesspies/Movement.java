package com.example.jamesbrowning.countlesspies;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import static com.example.jamesbrowning.countlesspies.Positions.*;

public class Movement {
    private static float fingerX;
    private static float fingerY;

    private static Bitmap nathanToUse;

    private static int rossXMoveSpeed;
    private static int rossYMoveSpeed;

    public static boolean preRossGreaterThanNathan_X;
    public static boolean preRossGreaterThanNathan_Y;
    public static boolean rossGreaterThanNathan_X;
    public static boolean rossGreaterThanNathan_Y;

    private static int delayNathanCounter = 0;
    private static int passedNathanRecentlyCounter = 0;

    public static void setPositionValues(MySurfaceView msv) {
        Positions.setStartingPositions(msv);
        rossX = ROSS_X;
        rossY = ROSS_Y;
        nathanX = NATHAN_X;
        nathanY = NATHAN_Y;
        setRandomPiePosition(msv);
        fingerX = fingerY = -1;
    }

    public static void setMovementSpeeds(MySurfaceView msv) {
        //todo: make speed relative to canvas size
        rossXMoveSpeed = 30;
        rossYMoveSpeed = 36;
    }

    private static void setPositions(MySurfaceView msv) {
        msv.canvas.drawBitmap(msv.ross, rossX, rossY, null);
        msv.canvas.drawBitmap(nathanToUse, nathanX, nathanY, null);
        msv.canvas.drawBitmap(msv.pie, pie1X, pie1Y, null);
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
            checkIfRossHasPassedNathan(msv);
        }
    }

    public static void stopMovingPlayer() {
        fingerX = -1;
        fingerY = -1;
    }

    public static void checkGotPie(MySurfaceView msv) {
        if (isTherePieContact(msv)) {
            msv.score++;
            setRandomPiePosition(msv);
        }
    }

    public static void checkContactWithEnemy(MySurfaceView msv) {
        if (isThereEnemyContact(msv)) {
            msv.lives = msv.lives > 0 ? msv.lives-1 : msv.lives;
            if (msv.lives > 0){
                setPositionValues(msv);
                setPositions(msv);
            }
        }
    }

    private static boolean isTherePieContact(MySurfaceView msv) {
        float pieWidth = msv.pie.getWidth();
        float pieHeight = msv.pie.getHeight();
        float xDiff = getRossMidX(msv) - getPieMidX(msv);
        float yDiff = getRossMidY(msv) - getPieMidY(msv);

        return (xDiff < (pieWidth/2) && xDiff > (0-pieWidth/2)) && (yDiff < (pieHeight/2) && yDiff > (0-pieHeight/2));
    }

    private static boolean isThereEnemyContact(MySurfaceView msv) {
        float nathanWidth = nathanToUse.getWidth();
        float nathanHeight = nathanToUse.getHeight();
        float xDiff = rossX - nathanX;
        float yDiff = rossY - nathanY;

        return (xDiff < (nathanWidth/2)  && xDiff > (0-nathanWidth/2)) && (yDiff < (nathanHeight/2) && yDiff > (0-nathanHeight/2));
    }

    private static void updateRossPosition(MySurfaceView msv) {
        if(fingerX < 0){return;}
        else if (fingerX > getRossMidX(msv) && (fingerX - getRossMidX(msv)) > rossXMoveSpeed*2) {
            rossX = rossX + rossXMoveSpeed;
        }
        else if (fingerX < getRossMidX(msv) && (getRossMidX(msv) - fingerX) > rossXMoveSpeed*2) {
            rossX = rossX - rossXMoveSpeed;
        }

        if (fingerY < 0){return;}
        else if (fingerY > getRossMidY(msv) && (fingerY - getRossMidY(msv)) > rossYMoveSpeed*2) {
            rossY = rossY + rossYMoveSpeed;
        }
        else if (fingerY < getRossMidY(msv) && (getRossMidY(msv) - fingerY) > rossYMoveSpeed*2) {
            rossY = rossY - rossYMoveSpeed;
        }
    }

    private static void updateNathanPosition(MySurfaceView msv) {
        int speedBoost = msv.score < 10 ? 1 : Integer.parseInt(String.valueOf(msv.score).substring(0,1))+1;
        int xMoveSpeed = 2 * speedBoost;
        int yMoveSpeed = 2 * speedBoost;

        checkIfRossHasPassedNathan(msv);

        if (delayNathanCounter > 0) {
             delayNathanCounter--;
        } else {
            if (nathanX > rossX && (nathanX - rossX) > xMoveSpeed*2) {
                nathanX = nathanX - xMoveSpeed;
                nathanToUse = msv.nathan_left;
            }
            else if (nathanX < rossX && (rossX - nathanX) > xMoveSpeed*2) {
                nathanX = nathanX + xMoveSpeed;
                nathanToUse = msv.nathan_right;
            }
            if (nathanY > rossY && (nathanY - rossY) > yMoveSpeed*2) {
                nathanY = nathanY - yMoveSpeed;
            }
            else if ( nathanY < rossY && (rossY - nathanY) > yMoveSpeed*2) {
                nathanY = nathanY + yMoveSpeed;
            }
            subtractPassedNathanRecentlyCounter();
        }
    }

    private static void subtractPassedNathanRecentlyCounter() {
        if (passedNathanRecentlyCounter > 0)
            passedNathanRecentlyCounter--;
        else
            passedNathanRecentlyCounter = 0;
    }

    private static void checkIfRossHasPassedNathan(MySurfaceView msv) {
        if (getRossMidX(msv) > nathanX)
            rossGreaterThanNathan_X = true;
        else if (getRossMidX(msv) < nathanX)
            rossGreaterThanNathan_X = false;
        if (getRossMidY(msv) > nathanY)
            rossGreaterThanNathan_Y = true;
        else if (getRossMidY(msv) < nathanY)
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
