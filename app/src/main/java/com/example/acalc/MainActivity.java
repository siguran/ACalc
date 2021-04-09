package com.example.acalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.content.ClipData;
import android.content.ClipboardManager;

public class MainActivity extends AppCompatActivity {

    enum Operations{Plus, Minus, Mult, Div};

    String displayText;
    Float v1 = Float.NaN;
    Float v2 = Float.NaN;
    Boolean newInput = false;
    Boolean isError = false;
    Operations operation;
    TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.setTheme(this);

        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        tvDisplay.setText("0");


        Button b = findViewById((R.id.btnOpPlus));
        b.setTag(Operations.Plus);

        b = findViewById((R.id.btnOpMinus));
        b.setTag(Operations.Minus);

        b = findViewById((R.id.btnOpMult));
        b.setTag(Operations.Mult);

        b = findViewById((R.id.btnOpDiv));
        b.setTag(Operations.Div);

    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return  true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_copy:
                copyValue();
                return  true;
            case R.id.menu_paste:
                pasteValue();
                return  true;
            case R.id.menu_settings:
                startSettings();
                return  true;
            case R.id.menu_about:
                about();
                return  true;

        }

        return  super.onOptionsItemSelected(item);
    }


    private boolean isNumeric(String s)
    {
        if (s == null) return false;
        try {Float.parseFloat(s);}
            catch (NumberFormatException e){return  false;}
        return true;
    }

    private void about()
    {
        Intent activityIntent = new Intent(getApplicationContext(), About.class);
        startActivity(activityIntent);
    }

    private void startSettings()
    {
        Intent activityIntent = new Intent(getApplicationContext(), Settings.class);
        startActivity(activityIntent);
    }

    private void pasteValue()
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null)
        {
            if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
            {
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                String pasteData = item.getText().toString();
                if (isNumeric(pasteData)) tvDisplay.setText(pasteData);
            }
        }
    }

    private void copyValue()
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null)
        {
            ClipData clip = ClipData.newPlainText("", tvDisplay.getText());
            clipboard.setPrimaryClip(clip);
        }
    }


    //цифровая кнопка
    public void ButtonNumClick(View view)
    {
        String input = (String)((Button)view).getTag();

        displayText = (String)tvDisplay.getText();

        if (newInput && (!displayText.equals("0."))) {displayText = "";}

        if (displayText.equals("0"))
        {
            if (input.equals("0")) {return;}
            tvDisplay.setText("");
            displayText = "";
        }

        displayText += input;
        tvDisplay.setText(displayText);

        newInput = false;
    }

    //кнопка "сброс"
    public void ButtonClearClick(View view)
    {
        v1 = Float.NaN;
        v2 = Float.NaN;
        newInput = false;
        isError = false;
        tvDisplay.setText("0");
    }

    //стирание одного символа
    public void ButtonBKSPClick(View view)
    {
        if (isError)
        {
            ButtonClearClick(null);
            return;
        }
        displayText = (String)tvDisplay.getText();

        if (displayText.equals("0")) return;

        if ((displayText.charAt(0) == '-') && (displayText.length() == 2))
        {
            tvDisplay.setText("0");
            return;
        }

        if (displayText.length() == 1)
        {
            tvDisplay.setText("0");
            return;
        }

        displayText = displayText.substring(0, displayText.length()-1);
        tvDisplay.setText(displayText);
    }

    //десятичный разделитель
    public void ButtonCommaClick(View view)
    {
        if (isError) return;
        displayText = (String)tvDisplay.getText();

        if (newInput) displayText = "0.";
            else if (!displayText.contains(".")) displayText += ".";

        tvDisplay.setText(displayText);
    }

    //кнопка "равно"
    public void ButtonResultClick(View view)
    {
        Calc();
        v1 = Float.NaN;
    }

    //смена знака
    public void ButtonSignClick(View view)
    {
        if (isError) return;
        displayText = (String)tvDisplay.getText();
        if (!displayText.equals("0"))
        {
            if (displayText.charAt(0) == '-') displayText = displayText.substring(1);
            else displayText = "-" + displayText;
            tvDisplay.setText(displayText);
        }
    }

    //кнопка "математическая операция"
    public void ButtonOperClick(View view)
    {
        if (isError) return;

        displayText = (String)tvDisplay.getText();

        if (!v1.isNaN()) Calc();

        newInput = true;

        if (displayText.equals("0.")) displayText = "0";

        v1 = (Float.parseFloat(displayText));

        operation = (Operations)((Button)view).getTag();
    }

    //вычисление
    void  Calc()
    {
        if (v1.isNaN()) return;

        v2 = Float.parseFloat(displayText);

        switch (operation)
        {
            case  Plus:
                v1 += v2;
                break;
            case Minus:
                v1 -= v2;
                break;
            case Mult:
                v1 *= v2;
                break;
            case Div:
                //if ((!displayText.equals("0")) && (!displayText.equals("0.")))   v1 /= v2;
                if (!v2.equals(0.0)) v1 /= v2;
                 else
                     {
                         tvDisplay.setText("Divide by 0");
                         displayText = "0";
                         isError = true;
                         return;
                     }
                break;
        }

        displayText = v1.toString();

        if (displayText.endsWith(".0"))
            displayText = displayText.substring(0, displayText.length()-2);

        tvDisplay.setText(displayText);
        v2 = Float.NaN;
        newInput = true;
    }
}