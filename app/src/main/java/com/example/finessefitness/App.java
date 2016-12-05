package com.example.finessefitness;

import android.app.Application;
import android.content.Context;

/**
 * Created by tnl11 on 12/4/2016.
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

}
