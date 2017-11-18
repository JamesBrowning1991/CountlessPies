package com.example.jamesbrowning.countlesspies;


import java.util.Random;

public class Positions {
    public static float ROSS_X;
    public static float ROSS_Y;
    public static float NATHAN_X;
    public static float NATHAN_Y;

    public static float rossX;
    public static float rossY;
    public static float rossXMid;
    public static float rossYMid;

    public static float nathanX;
    public static float nathanY;

    public static float pie1X;
    public static float pie1Y;

    public static void setStartingPositions(MySurfaceView msv) {
        ROSS_X = (float)(msv.w * 0.2);
        ROSS_Y = (float)(msv.h * 0.15);
        NATHAN_X = (float)(msv.w * 0.7);
        NATHAN_Y = (float)(msv.h * 0.8);
    }

    public static void setRandomPiePostion(MySurfaceView msv) {
        Random random = new Random();
        pie1X = random.nextInt(msv.w);
        pie1Y = random.nextInt(msv.h);
    }
}
