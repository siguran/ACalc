package com.example.acalc;


import android.app.Activity;
import android.content.Intent;

public class ThemeManager
{
    final static int theme_1 = 0;
    final static int theme_2 = 1;

    private static  int mTheme = theme_1;

    static  void changeTo(Activity activity, int theme)
    {
        if (mTheme != theme)
        {
            mTheme = theme;

            activity.finish();

            Intent intent1 = new Intent(activity.getApplicationContext(), About.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent1);

            Intent intent2 = new Intent(activity.getApplicationContext(), MainActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent2);
        }
    }

    static void setTheme(Activity activity)
    {
        switch (mTheme)
        {
            default:
            case theme_1:
                activity.setTheme(R.style.ThemeSand);
                break;
            case theme_2:
                activity.setTheme(R.style.ThemeWater);
                break;
        }
    }


}
