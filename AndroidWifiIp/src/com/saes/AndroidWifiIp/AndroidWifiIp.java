package com.saes.AndroidWifiIp;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class AndroidWifiIp extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView textWifiManager = (TextView)findViewById(R.id.WifiManager);
        TextView textWifiInfo = (TextView)findViewById(R.id.WifiInfo);
        TextView textIp = (TextView)findViewById(R.id.Ip);
        
        WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        
        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
        int myIp = myWifiInfo.getIpAddress();
        
        textWifiManager.setText(myWifiManager.toString());
        textWifiInfo.setText(myWifiInfo.toString());
        
        int intMyIp3 = myIp/0x1000000;
        int intMyIp3mod = myIp%0x1000000;
        
        int intMyIp2 = intMyIp3mod/0x10000;
        int intMyIp2mod = intMyIp3mod%0x10000;
        
        int intMyIp1 = intMyIp2mod/0x100;
        int intMyIp0 = intMyIp2mod%0x100;
        
        textIp.setText(String.valueOf(intMyIp0)
        		+ "." + String.valueOf(intMyIp1)
        		+ "." + String.valueOf(intMyIp2)
        		+ "." + String.valueOf(intMyIp3)
        		);
    }
}