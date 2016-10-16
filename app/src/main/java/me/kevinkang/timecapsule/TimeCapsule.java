package me.kevinkang.timecapsule;

import android.app.Application;
import android.content.Context;

/**
 * Created by Work on 10/15/2016.
 */

public class TimeCapsule extends Application {

    private static Context applicationContext;

    public static Context getContext(){
        return applicationContext;
    }

    @Override
    public void onCreate(){
        applicationContext = getContext();
    }

}
