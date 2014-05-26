package com.saes.iptools;

import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Tabipcalc3Activity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Other tab");
        setContentView(textview);
        
        //Intent i = new Intent("com.android.term.OPEN_NEW_WINDOW");
        //i.addCategory(Intent.CATEGORY_DEFAULT);
        //startActivity(i);
    }
}
