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
            rossX = 0-msv.ross.getWidth();
            rossY = ROSS_Y;
            nathanX = msv.w + 1;
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

        msv.canvas.drawBitmap(msv.ross, rossX, rossY, null);
        msv.canvas.drawBitmap(msv.nathan_left, nathanX, nathanY, null);

        return !(rossInPlace && nathanInPlace);
    }

}
