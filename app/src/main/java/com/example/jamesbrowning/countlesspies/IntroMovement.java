package com.example.jamesbrowning.countlesspies;

import static com.example.jamesbrowning.countlesspies.Positions.rossX;
import static com.example.jamesbrowning.countlesspies.Positions.rossY;
import static com.example.jamesbrowning.countlesspies.Positions.nathanX;
import static com.example.jamesbrowning.countlesspies.Positions.nathanY;
import static com.example.jamesbrowning.countlesspies.Positions.NATHAN_X;
import static com.example.jamesbrowning.countlesspies.Positions.NATHAN_Y;
import static com.example.jamesbrowning.countlesspies.Positions.ROSS_X;
import static com.example.jamesbrowning.countlesspies.Positions.ROSS_Y;

public class IntroMovement {
    public static boolean firstRun = true;
    private static boolean rossInPlace = false;
    private static boolean nathanInPlace = false;

    public static boolean doIntro(MySurfaceView msv) {
        if (firstRun) {
            rossX = -200;
            rossY = ROSS_Y;
            nathanX = msv.canvas.getWidth() + 100;
            nathanY = NATHAN_Y;
            firstRun = false;
        }

        if (rossX < ROSS_X)
            rossX++;
        else
            rossInPlace = true;
        if (nathanX > NATHAN_X)
            nathanX--;
        else
            nathanInPlace = true;

        float rossXMid = rossX - (msv.ross.getWidth()/2);
        float rossYMid = rossY - (msv.ross.getHeight()/2);

        msv.canvas.drawBitmap(msv.ross, rossXMid, rossYMid, null); // not ideal because can't set ross' location accurately, need to not use mid value
        msv.canvas.drawBitmap(msv.nathan_left, nathanX, nathanY, null);

        return !(rossInPlace && nathanInPlace);
    }

}
