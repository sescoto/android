//package ipcalc.tabwidget;
package com.saes.iptools;

import com.saes.iptools.R;
import com.saes.iptools.TabipcalcActivity;
import com.saes.iptools.Tabipcalc2Activity;
import com.saes.iptools.Tabipcalc3Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;


public class IpcalcTabWidgetActivity extends TabActivity {
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, TabipcalcActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
//	    spec = tabHost.newTabSpec("CIDR").setIndicator("ipCalc CIDR")
//	                  .setContent(intent);
	    spec = tabHost.newTabSpec("CIDR").setIndicator("IP Calc",
                res.getDrawable(R.drawable.ic_tab_ipcalc))
            .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, Tabipcalc2Activity.class);
	    spec = tabHost.newTabSpec("scanport").setIndicator("Scan Port",
                res.getDrawable(R.drawable.ic_tab_ipcalc2))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, Tabipcalc3Activity.class);
	    spec = tabHost.newTabSpec("Terminal").setIndicator("Term",
                res.getDrawable(R.drawable.ic_tab_ipcalc3))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, Tabipcalc3Activity.class);
	    spec = tabHost.newTabSpec("IPScan").setIndicator("IP Scan",
				res.getDrawable(R.drawable.ic_tab_ipcalc3))
			          .setContent(intent);
	    tabHost.addTab(spec);
		
	    tabHost.setCurrentTab(1);
	}	
}
