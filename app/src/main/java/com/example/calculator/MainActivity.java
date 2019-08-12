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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //UI Fields
    LinearLayout resultViewLayout;
    LinearLayout buttonPadLayout;
    EditText resultView;
    ButtonResource resource;
    String resultViewContent;

    //Button fields
    Button ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DOT, EQUALS, PLUS, TIMES, DIVIDE, SUBTRACT, LOG, SIN, COS, TAN, SQR_ROOT, BACKSPACE;
    Button[] CALC_BUTTONS;
    Button[] SCI_CALC_BUTTONS;

    //CalculatorMechanism Fields
    Pattern resultView_pattern;
    Matcher resultView_matcher;
    int result;
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

        // Object Creation
        resultView = (EditText) findViewById(R.id.resultView);

        ZERO = (Button) findViewById(R.id.number0);
        ONE = (Button) findViewById(R.id.number1);
        TWO = (Button) findViewById(R.id.number2);
        THREE = (Button) findViewById(R.id.number3);
        FOUR = (Button) findViewById(R.id.number4);
        FIVE = (Button) findViewById(R.id.number5);
        SIX = (Button) findViewById(R.id.number6);
        SEVEN = (Button) findViewById(R.id.number7);
        EIGHT = (Button) findViewById(R.id.number8);
        NINE = (Button) findViewById(R.id.number9);
        DOT = (Button) findViewById(R.id.numberDot);
        PLUS = (Button) findViewById(R.id.operatorAddition);
        EQUALS = (Button) findViewById(R.id.operatorEquality);
        TIMES = (Button) findViewById(R.id.operatorMultiplication);
        DIVIDE = (Button) findViewById(R.id.operatorDivision);
        SUBTRACT = (Button) findViewById(R.id.operatorSubtraction);
        LOG = (Button) findViewById(R.id.operatorLog);
        SIN = (Button) findViewById(R.id.operatorSin);
        COS = (Button) findViewById(R.id.operatorCos);
        TAN = (Button) findViewById(R.id.operatorTan);
        SQR_ROOT = (Button) findViewById(R.id.operatorSqrRoot);
        BACKSPACE = (Button) findViewById(R.id.backspace);

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
                resultView_pattern = Pattern.compile("([\\d]+)|([-+x÷])");
                resultView_matcher = resultView_pattern.matcher(resultViewContent);  //error here
                while (resultView_matcher.find()) {
                    list.add(resultView_matcher.group());  //error here
                }

                result = Integer.valueOf(list.get(0));

                if (result == Integer.valueOf(list.get(0))) {
                    for (int i = 0; i<list.size()-1; i++) {
                        if(list.get(i).equals("+")){
                            result += Integer.valueOf(list.get(i+1));
                        }
                        else if(list.get(i).equals("-")){
                            result -= Integer.valueOf(list.get(i+1));
                        }
                        else if(list.get(i).equals("x")){
                            result *= Integer.valueOf(list.get(i+1));
                        }
                        else if(list.get(i).equals("÷")){
                            result /= Integer.valueOf(list.get(i+1));
                        }
                    }
                }
                resultView.setText(String.valueOf(result));
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