package com.saes.iptools;

import android.app.Activity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.*;

public class test extends Activity {

    /** Called when the activity is first created. */
	private LogTextBox mText;
	InetAddress ipAddr = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3ipcalc);

        final Button button = (Button) findViewById(R.id.ok);

        final Button buttonC = (Button) findViewById(R.id.clear);

        mText = (LogTextBox) findViewById(R.id.text);

        button.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					try
					{
						ipAddr.getByName ("192.168.1.100");
					}
					catch (UnknownHostException e)
					{}
					int numNodos = 0;
					int nNodos = 0;
					double nNod = 0;
	
					byte [] bip = {10,10,1,70};
					byte [] bmask = {0,0,0,0};
					byte [] bipi = {0,0,0,0};
					byte [] bipe = {0,0,0,0};
		

					mText.append("Inicia escaneo de "+ (int) nNod + " direcciones: ...\n");
	
				}
			});    


        buttonC.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					mText.setText("");
				}
			});    

    }

    public void showMessage(String txMsg){
    	LayoutInflater inflater = getLayoutInflater();
    	View layout = inflater.inflate(R.layout.toast_layout,
    	                               (ViewGroup) findViewById(R.id.toast_layout_root));

    	ImageView image = (ImageView) layout.findViewById(R.id.image);
    	image.setImageResource(R.drawable.icon);
    	TextView text = (TextView) layout.findViewById(R.id.text);
    	text.setText(txMsg);

    	Toast toast = new Toast(getApplicationContext());
    	toast.setGravity(Gravity.TOP, 0, 50);
    	toast.setDuration(Toast.LENGTH_LONG);
    	toast.setView(layout);
    	toast.show();
    }

}
