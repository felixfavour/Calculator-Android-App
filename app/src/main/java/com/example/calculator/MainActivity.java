package com.example.calculator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //UI Fields
    LinearLayout resultViewLayout;
    LinearLayout buttonPadLayout;
    TextView resultView;
    String resultViewContent;
    DisplayMetrics displayMetrics;

    //Button fields
    Button ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DOT, EQUALS, PLUS, TIMES, DIVIDE, SUBTRACT, LOG, SIN, COS, TAN, SQR_ROOT, BACKSPACE;
    Button[] CALC_BUTTONS;
    Button[] SCI_CALC_BUTTONS;

    //CalculatorMechanism Fields
    Pattern resultView_pattern;
    Matcher resultView_matcher;
    double result;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //UI Interaction

        // Object Creation
        resultView = (TextView) findViewById(R.id.resultView);

        ZERO = findViewById(R.id.number0);
        ONE = findViewById(R.id.number1);
        TWO = findViewById(R.id.number2);
        THREE = findViewById(R.id.number3);
        FOUR = findViewById(R.id.number4);
        FIVE = findViewById(R.id.number5);
        SIX = findViewById(R.id.number6);
        SEVEN = findViewById(R.id.number7);
        EIGHT = findViewById(R.id.number8);
        NINE = findViewById(R.id.number9);
        DOT = findViewById(R.id.numberDot);
        PLUS = findViewById(R.id.operatorAddition);
        EQUALS = findViewById(R.id.operatorEquality);
        TIMES = findViewById(R.id.operatorMultiplication);
        DIVIDE = findViewById(R.id.operatorDivision);
        SUBTRACT = findViewById(R.id.operatorSubtraction);
        LOG = findViewById(R.id.operatorLog);
        SIN = findViewById(R.id.operatorSin);
        COS = findViewById(R.id.operatorCos);
        TAN = findViewById(R.id.operatorTan);
        SQR_ROOT = findViewById(R.id.operatorSqrRoot);
        BACKSPACE = findViewById(R.id.backspace);

        CALC_BUTTONS = new Button[]{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DOT, PLUS, TIMES, DIVIDE, SUBTRACT};
        SCI_CALC_BUTTONS = new Button[]{LOG, SIN, COS, TAN, SQR_ROOT};

        list = new ArrayList<>();

        resultViewMechanism();

    }

    public void resultViewMechanism() {
        for (int i=0; i<CALC_BUTTONS.length; i++) {
            CALC_BUTTONS[i].setHint(CALC_BUTTONS[i].getText().toString());
            final int I = i;
            CALC_BUTTONS[i].setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view) {
                    resultViewContent = resultView.getText().toString();
                    if (resultViewContent.isEmpty()) {
                        resultView.setText(CALC_BUTTONS[I].getHint().toString());
                    }
                    else if(!resultViewContent.isEmpty()) {
                        resultView.setText(resultViewContent + CALC_BUTTONS[I].getHint().toString());
                    }
                }
            });
        }
        for(int i=0; i<SCI_CALC_BUTTONS.length; i++) {
            SCI_CALC_BUTTONS[i].setHint(SCI_CALC_BUTTONS[i].getText().toString() + " ");
            final int I = i;
            SCI_CALC_BUTTONS[i].setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view) {
                    resultViewContent = resultView.getText().toString();
                    if(resultViewContent.isEmpty()) {
                        resultView.setText(SCI_CALC_BUTTONS[I].getHint().toString());
                    }
                    else if (!resultViewContent.isEmpty()) {
                        resultView.setText(resultViewContent + SCI_CALC_BUTTONS[I].getHint().toString());
                    }
                }
            });
        }

        BACKSPACE.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                resultViewContent = resultView.getText().toString();
                if(!resultViewContent.isEmpty()) {
                    resultView.setText(resultViewContent.substring(0, resultViewContent.length()-1));
                }
            }
        });
        BACKSPACE.setOnLongClickListener(new Button.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!resultViewContent.isEmpty()) {
                    resultView.setText("");
                }
                return false;
            }
        });

        //Equality Mechanism

        EQUALS.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultViewContent = resultView.getText().toString();
                resultView_pattern = Pattern.compile("((?:^[-+]|)[\\d.A-Z√\\s]+)|([-+÷x])");
                resultView_matcher = resultView_pattern.matcher(resultViewContent);
                while (resultView_matcher.find()) {
                    if (!(resultView_matcher.group().contains("√ ") || resultView_matcher.group().contains("LOG ") || resultView_matcher.group().contains("SIN ")
                            || resultView_matcher.group().contains("COS ")|| resultView_matcher.group().contains("TAN "))) {
                        list.add(resultView_matcher.group());
                    }
                    else if (resultView_matcher.group().contains("√ ")) {
                        String rootValue = resultView_matcher.group().substring(resultView_matcher.group().indexOf(" ") + 1);
                        list.add(String.valueOf(Math.sqrt(Double.valueOf(rootValue))));
                    }
                    else if (resultView_matcher.group().contains("LOG ")) {
                        String logValue = resultView_matcher.group().substring(resultView_matcher.group().indexOf(" ")+1);
                        list.add(String.valueOf(Math.log10(Double.valueOf(logValue))));
                    }
                    else if (resultView_matcher.group().contains("SIN ")) {
                        String sinValue = resultView_matcher.group().substring(resultView_matcher.group().indexOf(" ")+1);
                        list.add(String.valueOf(Math.sin(Math.toRadians(Double.valueOf(sinValue)))));
                    }
                    else if (resultView_matcher.group().contains("COS ")) {
                        String cosValue = resultView_matcher.group().substring(resultView_matcher.group().indexOf(" ")+1);
                        if (Integer.valueOf(cosValue) < 90) {  //MATH ERROR EXCEPTIONS
                            list.add(String.valueOf(Math.cos(Math.toRadians(Double.valueOf(cosValue)))));
                        }
                        else {
                            list.add("MErr");
                        }
                    }
                    else if (resultView_matcher.group().contains("TAN ")) {  //MATH ERROR EXCEPTIONS
                        String tanValue = resultView_matcher.group().substring(resultView_matcher.group().indexOf(" ")+1);
                        if (Integer.valueOf(tanValue) <= 45) {
                            list.add(String.valueOf(Math.tan(Math.toRadians(Double.valueOf(tanValue)))));
                        }
                        else {
                            list.add("MErr");
                        }
                    }
                }

                if(!list.contains("MErr")) {
                    result = Double.valueOf(list.get(0));
                    for (int i = 0; i<list.size()-1; i++) {
                        switch (list.get(i)) {
                            case "+":
                                result += Double.valueOf(list.get(i + 1));
                                break;
                            case "-":
                                result -= Double.valueOf(list.get(i + 1));
                                break;
                            case "x":
                                result *= Double.valueOf(list.get(i + 1));
                                break;
                            case "÷":
                                result /= Double.valueOf(list.get(i + 1));
                                break;
                        }
                    }
                    if(String.valueOf(result).endsWith(".0")) {
                        resultView.setText(String.valueOf(result).substring(0, String.valueOf(result).indexOf(".")));
                    } else {
                        resultView.setText(String.valueOf(result));
                    }
                } else {
                    resultView.setText(String.valueOf("Math Error"));
                }

                list.clear();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
