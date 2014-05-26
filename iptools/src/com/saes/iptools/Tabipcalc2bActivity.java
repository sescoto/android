package com.saes.iptools;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tabipcalc2bActivity extends Activity {
	
    /** Called when the activity is first created. */
	private LogTextBox mText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2ipcalc);

        final EditText edittext = (EditText) findViewById(R.id.entry);

        final EditText edittextPort = (EditText) findViewById(R.id.entryPort);

        final Button button = (Button) findViewById(R.id.ok);

        final Button buttonC = (Button) findViewById(R.id.clear);
        
        mText = (LogTextBox) findViewById(R.id.text);


        
     	   
        button.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		mText.append("Inicia escaneo de puertos...\n");
        		String ip_server = null;
                String port_server = null;
                int i,j;
                int[][] qPts;
        		try {
        			ip_server = edittext.getText().toString();
        			port_server = edittextPort.getText().toString();
        			
        			//List<Integer> rangos = new ArrayList<Integer>();
        			mText.append("antes del token");
        			StringTokenizer ids = null;
        			try {
        			ids = new StringTokenizer(port_server, "[,|\b|\t|:]");
        			}
        			catch(Exception e) {
        				e.printStackTrace();
        			}
        			
        			// 21-23
        			// 80
        			
        		
        			
        			
        			
        			socketConnect c1 = new socketConnect();
        		//	parserRangos pR = new parserRangos();
        	//		pR.setArgs(port_server);
        		//	qPts = pR.getRangos();
        			c1.sIP(ip_server);
        			//int pt = Integer.parseInt(port_server);getNumArgs
        			
        			mText.append("parser tiene " + ids.countTokens());
        			
        		/*	mText.append("Argumentos: "+pR.getMsgTxt()+"\n");
        			mText.append("nArgumentos: "+pR.getNumArgs()+"\n");
        			mText.append("rangos: "+pR.getNumRangos()+"\n");
        			mText.append("Error: "+pR.getError()+"\n");
        			
        			*/
        			
        			while(ids.hasMoreTokens()){
		  				
        				String [] tmp = ids.nextToken().split("-",2);
        				
        				if (tmp.length == 1){
        				
        					//rangos.add(Integer.parseInt(tmp[0]));
        				//	c1.wait(1000);
        					//c1.sPuerto(Integer.parseInt(tmp[0]));
        				//	c1.connSocket();
        					
        					
              			///  	if(c1.gEstado()){
              			  	//	mText.append("\n"+c1.gEstadoTxt()+"\n\n");
              			  //		c1.cierraSocket();
              			 // 	}else{
              			  //		mText.append(c1.gEstadoTxt());
              			  //	}
        					mText.append("puerto = "  + tmp[0] + "\n");
        				
        				}
        				else {
        					int tmp1 = Integer.parseInt(tmp[0]);
        					int tmp2 = Integer.parseInt(tmp[1]);
        					
        					for (int k = tmp1; k <= tmp2; k++) {
        				//		rangos.add(k);
        						
        					    mText.append("puerto = " + k);
            				//	c1.sPuerto(k);
            					//c1.connSocket();
                  			//  	if(c1.gEstado()){
                  			 // 		mText.append("\n"+c1.gEstadoTxt()+"\n\n");
                  			  //		c1.cierraSocket();
                  			  //	}else{
                  			  //		mText.append(c1.gEstadoTxt());
                  			  //	}
        					
        					
        					}
        					
        					
        				}
        			  				
        				
        				
        				
        			
        				
        				
        				
        			}
        			
        			
        			/*
        			for(i=1; i <= pR.getNumRangos(); i++){
        				mText.append("Min: "+qPts[i][0]);
        				mText.append("Max: "+qPts[i][1]);
        				for(j=qPts[i][0];j<=qPts[i][1];j++){
        					
        					c1.sPuerto(j);
        					c1.connSocket();
              			  	if(c1.gEstado()){
              			  		mText.append("\n"+c1.gEstadoTxt()+"\n\n");
              			  		c1.cierraSocket();
              			  	}else{
              			  		mText.append(c1.gEstadoTxt());
              			  	}
        					//TO DO ... !!!
        				}
        			}
        			//*/
        			
        			
                }
                catch (Exception e) {
                	Tabipcalc2bActivity.this.showMessage("Port Scan: Error en dato introducido ...");
                }
            }
        });    

        
        buttonC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
            		edittext.setText("");
            		edittextPort.setText("");
            		mText.setText("");
            	}
            	catch (Exception e) {
            		Tabipcalc2bActivity.this.showMessage("ip-Calc: Sin datos que borrar ...");
            	}
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
