package com.example.acalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    Settings context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.setTheme(this);

        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;


        Button btnTS = findViewById(R.id.btnSelectThemeSand);
        View.OnClickListener buttonClickListener1 = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ThemeManager.changeTo(context, ThemeManager.theme_1);
            }
        };
        btnTS.setOnClickListener(buttonClickListener1);


        Button btnTW = findViewById(R.id.btnSelectThemeWater);
        View.OnClickListener buttonClickListener2 = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ThemeManager.changeTo(context, ThemeManager.theme_2);
            }
        };
        btnTW.setOnClickListener(buttonClickListener2);
    }


    @Override
    public  boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}