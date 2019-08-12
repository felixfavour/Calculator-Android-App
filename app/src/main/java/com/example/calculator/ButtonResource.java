package com.example.calculator;

import android.app.Activity;
import android.widget.Button;

public class ButtonResource extends MainActivity {
    Button zero = (Button) findViewById(R.id.number0);
    Button one = (Button) findViewById(R.id.number1);
    Button two = (Button) findViewById(R.id.number2);
    Button three = (Button) findViewById(R.id.number3);
    Button four = (Button) findViewById(R.id.number4);
    Button five = (Button) findViewById(R.id.number5);
    Button six = (Button) findViewById(R.id.number6);
    Button seven = (Button) findViewById(R.id.number7);
    Button eight = (Button) findViewById(R.id.number8);
    Button nine = (Button) findViewById(R.id.number9);
    Button DOT = (Button) findViewById(R.id.numberDot);
    Button plus = (Button) findViewById(R.id.operatorAddition);
    Button times = (Button) findViewById(R.id.operatorMultiplication);
    Button divide = (Button) findViewById(R.id.operatorDivision);
    Button subtract = (Button) findViewById(R.id.operatorSubtraction);
    Button log = (Button) findViewById(R.id.operatorLog);
    Button sin = (Button) findViewById(R.id.operatorSin);
    Button cos = (Button) findViewById(R.id.operatorCos);
    Button tan = (Button) findViewById(R.id.operatorTan);
    Button sqrRoot = (Button) findViewById(R.id.operatorSqrRoot);

    Button[] CALCBUTTONS = {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, PLUS, TIMES, DIVIDE, SUBTRACT, LOG, SIN, COS ,TAN, SQR_ROOT};
}
