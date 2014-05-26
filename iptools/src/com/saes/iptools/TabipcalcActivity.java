package com.saes.iptools ;

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
import java.net.*;

public class TabipcalcActivity extends Activity {
	
	private TextView sMask4oct;
	private TextView sNumHosts;
	private TextView sNumAddres;
	private TextView sNumAddresIni;
	private TextView sNumAddresEnd;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1ipcalc);

        final EditText edittext = (EditText) findViewById(R.id.entry); 
		
		final EditText ipaddr = (EditText) findViewById(R.id.addrbase);

        final Button button = (Button) findViewById(R.id.ok);

        final Button buttonC = (Button) findViewById(R.id.clear);
		
        
     	   
        button.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		//Toast.makeText(IpsncActivity.this, edittext.getText().toString(), Toast.LENGTH_LONG).show();
                int numNodos = 0;
				InetAddress ipAddr = null;
				InetAddress ipAddrIni = null;
				InetAddress ipAddrEnd = null;
        		try {
            	    numNodos = Integer.parseInt(edittext.getText().toString());
                }
				catch (Exception e) {
                	TabipcalcActivity.this.showMessage("ip-Calc: Error en dato MASCARA ... ");
                }
				if(numNodos>=1 && numNodos <=32){
					double nNod;
					double nAddr;	
					if(32 - numNodos > 1){
                        nNod = Math.pow(2,(32 - numNodos)) -2;
						nAddr = nNod + 2;
					}else{
						nNod = 33 - numNodos;
						nAddr = nNod;
					}
					
					String sNod;
					if(nNod > 0){
						sNod = ""+nNod;
					}else{
						sNod = "1";
					}
					String sAddr = ""+(int)nAddr;
					byte [] bip = {0,0,0,0};
					byte [] bipi = {0,0,0,0};
					byte [] bipe = {0,0,0,0};
					byte [] bmask = {0,0,0,0};
					try{
						bip = TabipcalcActivity.this.sip2bip(ipaddr.getText().toString());
						bmask = TabipcalcActivity.this.sip2bip(ipMask(numNodos));
					}
					catch (Exception e) {
						TabipcalcActivity.this.showMessage("ip-Calc: Error en dato IP ...");
					}
					try{ //ipAddrIni
					    ipAddr = InetAddress.getByAddress(bip);
					    for(int i=0; i<=3; i++){
					    	bipi[i] = (byte) (bip[i] & bmask[i]);
							bipe[i] = (byte) (bip[i] | ~bmask[i]);
							//TabipcalcActivity.this.showMessage("bipi["+i+"]: "+bipi[i]+"  bmask["+i+"]: "+bmask[i]);
					    }
					    ipAddrIni = InetAddress.getByAddress(bipi);
						ipAddrEnd = InetAddress.getByAddress(bipe);
					}
					catch (Exception e) {
						TabipcalcActivity.this.showMessage("ip-Calc: Error creando InetAddress ...");
					}
                	sMask4oct = (TextView) findViewById(R.id.mask4oct);
                	sMask4oct.setText(ipMask(numNodos));
                	sMask4oct.setVisibility(View.VISIBLE);
                	sNumHosts = (TextView) findViewById(R.id.numHost);
                	sNumHosts.setText(sNod.substring(0, sNod.length()-2));
                	sNumHosts.setVisibility(View.VISIBLE);
					sNumAddres = (TextView) findViewById(R.id.numAddr);
					sNumAddres.setText(sAddr);
					sNumAddres.setVisibility(View.VISIBLE);
					sNumAddresIni = (TextView) findViewById(R.id.numAddrIni);	
					sNumAddresIni.setText(ipAddrIni.getHostAddress());
					sNumAddresIni.setVisibility(View.VISIBLE);
					sNumAddresEnd = (TextView) findViewById(R.id.numAddrEnd);	
					sNumAddresEnd.setText(ipAddrEnd.getHostAddress());
					sNumAddresEnd.setVisibility(View.VISIBLE);					
                }else{
                	TabipcalcActivity.this.showMessage("ip-Calc: Numero de bits debe ser \nentre 1 y 32 ...");
                	edittext.setText("");
                	sNumHosts.setVisibility(View.INVISIBLE);
                	sMask4oct.setVisibility(View.INVISIBLE);
					sNumAddres.setVisibility(View.INVISIBLE);
					sNumAddresIni.setVisibility(View.INVISIBLE);
					sNumAddresEnd.setVisibility(View.INVISIBLE);
                }
            }
        });    

        
        buttonC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
            		edittext.setText("");
					ipaddr.setText(R.string.ipaddr_def);
            		sNumHosts.setVisibility(View.INVISIBLE);
            		sMask4oct.setVisibility(View.INVISIBLE);
					sNumAddres.setVisibility(View.INVISIBLE);
					sNumAddresIni.setVisibility(View.INVISIBLE);
					sNumAddresEnd.setVisibility(View.INVISIBLE);
            	}
            	catch (Exception e) {
            		TabipcalcActivity.this.showMessage("ip-Calc: Sin datos que borrar ...");
            	}
            }
        });    
        
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
