package com.example.edward.std0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {
    int a, b, res, style;
    boolean c = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void OnClk_btn0(View v){
        ((TextView)findViewById(R.id.lcd)).setText("0");
        if(c)a = 0;
        else b = 0;
    }
    public void OnClk_btn1(View v){
        if(c)a = 1;
        else b = 1;
        ((TextView)findViewById(R.id.lcd)).setText("1");
    }
    public void OnClk_btn2(View v){
        if(c)a = 2;
        else b = 2;
        ((TextView)findViewById(R.id.lcd)).setText("2");
    }
    public void OnClk_btn3(View v){
        if(c)a = 3;
        else b = 3;
        ((TextView)findViewById(R.id.lcd)).setText("3");
    }
    public void OnClk_btn4(View v){
        if(c)a = 4;
        else b = 4;
        ((TextView)findViewById(R.id.lcd)).setText("4");
    }
    public void OnClk_btn5(View v){
        if(c)a = 5;
        else b = 5;
        ((TextView)findViewById(R.id.lcd)).setText("5");
    }
    public void OnClk_btn6(View v){
        if(c)a = 6;
        else b = 6;
        ((TextView)findViewById(R.id.lcd)).setText("6");
    }
    public void OnClk_btn7(View v){
        if(c)a = 7;
        else b = 7;
        ((TextView)findViewById(R.id.lcd)).setText("7");
    }
    public void OnClk_btn8(View v){
        if(c)a = 8;
        else b = 8;
        ((TextView)findViewById(R.id.lcd)).setText("8");
    }
    public void OnClk_btn9(View v){
        if(c)a = 9;
        else b = 9;
        ((TextView)findViewById(R.id.lcd)).setText("9");
    }
    public void OnClk_btn_add(View v){
        style = 0;
        c = !c;
    }
    public void OnClk_btn_sub(View v){
        style = 1;
        c = !c;
    }
    public void OnClk_btn_mul(View v){
        style = 2;
        c = !c;
    }
    public void OnClk_btn_div(View v){
        style = 3;
        c = !c;
    }
    public void OnClk_btn_res(View v){
        if(style == 0)res = a + b;
        if(style == 1)res = a - b;
        if(style == 2)res = a * b;
        if(style == 3)res = a / b;
        ((TextView)findViewById(R.id.lcd)).setText(String.valueOf(res));
    }
    public void OnClk_btn_point(View v){
        try {
            Intent intent = new Intent();
            intent.setClass(this, player.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
