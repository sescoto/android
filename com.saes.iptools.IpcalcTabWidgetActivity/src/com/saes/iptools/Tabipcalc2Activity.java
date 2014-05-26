package com.saes.iptools;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringTokenizer;

import android.app.Activity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.wifi.*;
import android.content.Context;

public class Tabipcalc2Activity extends Activity {
	
    /** Called when the activity is first created. */
	private LogTextBox mText;
	private String intIpGw;
	boolean chkbFail = false;
    int[][] qPts;
	String ip_server = null;

	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putCharSequence("LOG_TEXT", mText.getText());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
    	this.mText.append(savedInstanceState.getCharSequence("LOG_TEXT")); 
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		String textIp = "";
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE); 
    	WifiInfo wifiInfo = wifiManager.getConnectionInfo(); 
	    intIpGw = wifiManager.getDhcpInfo().toString();	

    	int ipAddress = wifiInfo.getIpAddress();
        int intIp3 = ipAddress/0x1000000;
        int intIp3mod = ipAddress%0x1000000;

        int intIp2 = intIp3mod/0x10000;
        int intIp2mod = intIp3mod%0x10000;

        int intIp1 = intIp2mod/0x100;
        int intIp0 = intIp2mod%0x100;

        textIp = String.valueOf(intIp0)
			+ "." + String.valueOf(intIp1)
			+ "." + String.valueOf(intIp2)
			+ "." + String.valueOf(intIp3)
			;
        
        setContentView(R.layout.tab2ipcalc);

        final EditText edittext = (EditText) findViewById(R.id.entry);

        final EditText edittextPort = (EditText) findViewById(R.id.entryPort);

        final Button button = (Button) findViewById(R.id.ok);

        final Button buttonC = (Button) findViewById(R.id.clear); // @+id/checkBox1
        
        final CheckBox chk01 = (CheckBox) findViewById(R.id.checkBox1);
        
        final EditText avanceOk = (EditText) findViewById(R.id.editProgessOk);
        
        final EditText avance = (EditText) findViewById(R.id.editProgess);

        if (textIp != ""){
        	edittext.setText(textIp);
        }
		
        mText = (LogTextBox) findViewById(R.id.text);
		
		chkbFail = false;
     	   
        button.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		
                String port_server = null;
                mText.append("DHCP: "+intIpGw.toString()+"\n\n");
                try {
        			ip_server = edittext.getText().toString();
        			port_server = edittextPort.getText().toString();
            		mText.append("Inicia escaneo de puertos en host: "+ip_server+" ...\n");
                }
                catch (Exception e) {
                	Tabipcalc2Activity.this.showMessage("Port Scan: Error en dato introducido ...");
                }
        			
        		parserRangos pR = new parserRangos();
        		pR.setArgs(port_server);
        		mText.append("Rangos: "+pR.getNumRangos()+"\n");

        		qPts = new int[pR.getNumRangos()][2];
    			qPts = pR.getRangos();
    			
    			if (chk01.isChecked()){
    				chkbFail = true;
    			}else{
					chkbFail = false;
				}
	
        		portScan task = new portScan();
        		task.execute(pR.getNumRangos());

        		//mText.append("\nFin de escaneo...\n\n");
            }
        });    

        
        buttonC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
            		edittext.setText("");
            		edittextPort.setText("");
            		mText.setText("");
                	avance.setText("");
                	avanceOk.setText("");
            	}
            	catch (Exception e) {
            		Tabipcalc2Activity.this.showMessage("ip-Calc: Sin datos que borrar ...");
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
    
    
    private class portScan extends AsyncTask<Integer, String, String>{
    	
        protected void onPostExecute(String result) {
        	mText.append(result);
        }
        protected void  onProgressUpdate(String... progress){
            final EditText avance = (EditText) findViewById(R.id.editProgess);
            final EditText avanceOk = (EditText) findViewById(R.id.editProgessOk);
        	avance.setText(progress[0].toString());
        	avanceOk.setText(progress[1].toString());
        	mText.append(progress[2].toString());
        	//Tabipcalc3Activity.this.showMessage("MSG: "+progress[0].toString());
        	
        }
		@Override
		protected String doInBackground(Integer... nRangos) {
            int i,j;
        	String pText = null;
//            pText = ""+nRangos[0];
//            publishProgress(" "+qPts.length);
			
			socketConnect c1 = new socketConnect();
			c1.sIP(ip_server);
			
			int sumaRangos = 0;
			int cuentaPuertos = 0;
			int cuentaPuertosOk = 0;
			for(i=0; i < nRangos[0]; i++){
				sumaRangos += qPts[i][1]-qPts[i][0]+1;
			}
			
			for(i=0; i < nRangos[0]; i++){
				pText = "Rango: "+qPts[i][0] +" - "+qPts[i][1]+"\n";
	            publishProgress("Escaneo: "+cuentaPuertos+" / "+sumaRangos, "Abiertos: "+cuentaPuertosOk, pText);
				for(j=qPts[i][0];j<=qPts[i][1];j++){
					cuentaPuertos++;
					c1.sPuerto(j);
					c1.connSocket();
      			  	if(c1.gEstado()){
      			  		pText = "\n"+c1.gEstadoTxt()+"\n\n";
    					cuentaPuertosOk++;
      			  		c1.cierraSocket();
      			  	}else{
      			  		if (chkbFail){
      			  			pText = c1.gEstadoTxt();
      			  		}else{
      			  			pText = "";
      			  		}
      			  	}
		            publishProgress("Escaneo: "+cuentaPuertos+" / "+sumaRangos, "Abiertos: "+cuentaPuertosOk, pText);
					//TO DO ... !!!
				}
				pText = "\n";
	            publishProgress("Escaneo: "+cuentaPuertos+" / "+sumaRangos, "Abiertos: "+cuentaPuertosOk, pText);
			} 
    		
			return "\nFin de escaneo...\n\n";
		}
    }

}
