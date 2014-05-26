package com.saes.iptools;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.net.wifi.*;
import android.content.Context;

public class Tabipcalc3Activity extends Activity {
	
    /** Called when the activity is first created. */
	private LogTextBox mText;
	private String intIpGw;
	InetAddress ipAddr = null;
	InetAddress ipAddrIni = null;
	InetAddress ipAddrEnd = null;
    int[][] qPts;
	String ip_server = null;
	ProgressDialog progressBar;


	
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
        
        setContentView(R.layout.tab3ipcalc);

        final EditText masktext = (EditText) findViewById(R.id.entry); 
		
		final EditText ipaddr = (EditText) findViewById(R.id.addrbase);

        final Button button = (Button) findViewById(R.id.ok);

        final Button buttonC = (Button) findViewById(R.id.clear);
        
        mText = (LogTextBox) findViewById(R.id.text);
        
        if (textIp != ""){
        	ipaddr.setText(textIp);
        }
     	   
        button.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		
                int numNodos = 0;
                int nNodos = 0;
				double nNod = 0;
        		try {
            	    numNodos = Integer.parseInt(masktext.getText().toString());
                }
				catch (Exception e) {
                	Tabipcalc3Activity.this.showMessage("ip-Scan: Error en dato MASCARA ... ");
                }
				if(numNodos>=24 && numNodos <=32){
					if(32 - numNodos > 1){
                        nNod = Math.pow(2,(32 - numNodos)) -2;
					}else{
						nNod = 33 - numNodos;
					}
                    nNodos = (int) nNod;
					
					byte [] bip = {0,0,0,0};
					byte [] bmask = {0,0,0,0};
					byte [] bipi = {0,0,0,0};
					byte [] bipe = {0,0,0,0};
					try{
						bip = Tabipcalc3Activity.this.sip2bip(ipaddr.getText().toString());
						bmask = Tabipcalc3Activity.this.sip2bip(ipMask(numNodos));
						ipAddr = InetAddress.getByAddress(bip);
					    for(int i=0; i<=3; i++){
					    	bipi[i] = (byte) (bip[i] & bmask[i]);
							bipe[i] = (byte) (bip[i] | ~bmask[i]);
	                        //mText.append("byte o: "+(int)bip[i]+"byte i: "+(int)bipi[i]+"  byte e: "+ (int)bipe[i]+"\n");
					    }
						if(32 - numNodos > 1){
	                        nNod = Math.pow(2,(32 - numNodos)) -2;
	                        //bipi[3] =(byte) + 1;
	                        //bipe[3] =(byte) - 1;
	                        //mText.append("byte inicial: "+(int)bipi[3]+"  byte final: "+ (int)bipe[3]+"\n");
						}else{
							nNod = 33 - numNodos;
						}
					    ipAddrIni = InetAddress.getByAddress(bipi);
						ipAddrEnd = InetAddress.getByAddress(bipe);
						//mText.append(ipAddrIni.getHostAddress()+"\n");
						//mText.append(ipAddrEnd.getHostAddress()+"\n");
					}
					catch (Exception e) {
						Tabipcalc3Activity.this.showMessage("ip-Scan: Error en dato IP ...");
					}
                    nNodos = (int) nNod;

            		mText.append("DHCP: "+intIpGw.toString()+"\n\n");
                    mText.append("Inicia escaneo de "+ (int) nNod + " direcciones: ...\n");
            		mText.append("IP inicial: "+ ipAddrIni.getHostAddress() + " IP final: " + ipAddrEnd.getHostAddress()+"\n");
            			
            		progressBar = new ProgressDialog(v.getContext());
        			progressBar.setCancelable(true);
        			progressBar.setMessage("Escaneo de "+ (int) nNod +" direcciones\nIP inicial: "+ ipAddrIni.getHostAddress() + " IP final: " + ipAddrEnd.getHostAddress()+"\n");
        			progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        			progressBar.setProgress(1);
        			progressBar.setMax(nNodos);
        			progressBar.show();

            		ipScan task = new ipScan();
            		task.execute(nNodos);

                }else{
                	Tabipcalc3Activity.this.showMessage("ip-Scan: Numero de bits debe ser \nentre 24 y 32 ...");
                	masktext.setText("");
                }
            }
        });    

        
        buttonC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
            		masktext.setText("");
            		mText.setText("");
					intIpGw = "";
            	}
            	catch (Exception e) {
            		Tabipcalc3Activity.this.showMessage("ip-Calc: Sin datos que borrar ...");
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

	public byte[] sip2bip(String sip){
		byte[] b = {0,1,2,3};
		byte[] bip = {0,0,0,0};
		String[] sarr = sip.split("[.]",4);
		for(int i : b){
			bip[i] = (byte)Integer.parseInt(sarr[i]);
		}
		return bip;
	}
    
    public String ipMask(int nBits){
    	String sMask = "";
    	int nSubBits = nBits % 8;
    	double decMask = 0;
    	int k = 7;
    	while((8-k)<=nSubBits) {
    		decMask = decMask + Math.pow(2,k);
    		k--;
    	}
    	int nFullOct = (nBits - (nSubBits))/8;
		int[] numbers = {1,2,3,4};
    	for(int i : numbers) {
    		if(i<=nFullOct){
    			sMask = sMask + "255";
    		}else if(i==(nFullOct+1)){
    			sMask = sMask + (int)decMask;
    		}else{
    			sMask = sMask + "0";
    		}
			if(i<4){
				sMask=sMask+".";
			}
    	}
    	return sMask.substring(0, sMask.length());
    }
    
    private class ipScan extends AsyncTask<Integer, String, String>{
    	
        protected void onPostExecute(String result) {
        	mText.append(result);
        	progressBar.dismiss();
        }
        protected void  onProgressUpdate(String... progress){
        	mText.append(progress[0].toString());
			progressBar.setProgress(Integer.parseInt(progress[1].toString()));
        	
        }
		@Override
		protected String doInBackground(Integer... nIP) {
            int i;
			String sIPAddr, sNameAddr;
			int countAddressOK = 0, p=0;
			byte [] bIP = {0,0,0,0};
			ipAddr = ipAddrIni;
			bIP = ipAddrIni.getAddress();
			for(i=1; i <= nIP[0]; i++){
				p = i; //(i*100/nIP[0]);
				int tmp = bIP[3];
				tmp += 1;
      			bIP[3] =(byte) (tmp >>> 0);
    			try{
    				ipAddr = InetAddress.getByAddress(bIP);
    			}
    			catch (Exception UnknownHostException) {
    				
    			}
    			sIPAddr = ipAddr.getHostAddress();
    			sNameAddr = ipAddr.getHostName();
    			try{
      				if(ipAddr.isReachable(500)){
      					countAddressOK++;
      					publishProgress("\n"+countAddressOK+".-   "+sIPAddr+"\t\t"+sNameAddr+"\n\n", ""+p);
      				}else{
      					publishProgress("*", ""+p);
      				}
      			}
      			catch (Exception IOException) {
      				publishProgress(".", ""+p);
      			}
			} 
    		
			return "\nFin de escaneo...\n\n";
		}
    }

}
