package com.example.acalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    enum Operations{Plus, Minus, Mult, Div};

    String displayText;
    //BigDecimal v1 = BigDecimal.ZERO;
    //BigDecimal v2 = BigDecimal.ZERO;
    Double v1 = Double.NaN;
    Double v2 = Double.NaN;
    Boolean newInput = false;
    Boolean isError = false;
    Operations operation;
    TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        v1 = Double.NaN;
        v2 = Double.NaN;
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
        v1 = Double.NaN;
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

        v1 = (Double.parseDouble(displayText));

        operation = (Operations)((Button)view).getTag();
    }

    //вычисление
    void  Calc()
    {
        if (v1.isNaN()) return;

        v2 = Double.parseDouble(displayText);

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
                if ((!displayText.equals("0")) && (!displayText.equals("0.")))   v1 /= v2;
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
        v2 = Double.NaN;
        newInput = true;
    }
}