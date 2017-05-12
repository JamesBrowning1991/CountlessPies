package com.example.jamesbrowning.countlesspies;

public class IntroMovement {
    private static float rossX;
    private static float rossY;
    private static float nathanX;
    private static float nathanY;
    public static boolean firstRun = true;
    private static boolean rossInPlace = false;
    private static boolean nathanInPlace = false;

    public static boolean doIntro(MySurfaceView msv) {
        if (firstRun) {
            rossX = -200;
            rossY = Movement.ROSS_Y;
            nathanX = msv.canvas.getWidth() + 100;
            nathanY = Movement.NATHAN_Y;
            firstRun = false;
        }

        if (rossX < Movement.ROSS_X)
            rossX++;
        else
            rossInPlace = true;
        if (nathanX > Movement.NATHAN_X)
            nathanX--;
        else
            nathanInPlace = true;

        float rossXMid = rossX - (msv.ross.getWidth()/2);
        float rossYMid = rossY - (msv.ross.getHeight()/2);

        msv.canvas.drawBitmap(msv.ross, rossXMid, rossYMid, null); // not ideal because can't set ross' location accurately, need to not use mid value
        msv.canvas.drawBitmap(msv.nathan_left, nathanX, nathanY, null);

        if (rossInPlace && nathanInPlace){
            nathanInPlace = true;
            return false;

        }
        else
            return true;
    }

}
