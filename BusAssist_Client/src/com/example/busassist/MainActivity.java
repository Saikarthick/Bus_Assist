package com.example.busassist;
 
import android.app.Activity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
 
public class MainActivity extends Activity implements OnClickListener {
 
    // Widget GUI
    Button btnSuccess;
    
    String phoneNo ="8148894324";
    String msg = "find";
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        // Button Widget Init
        btnSuccess = (Button) findViewById(R.id.button1);
        btnSuccess.setOnClickListener(this);
 
    }
 
    public void onClick(View v) {
 
        if (v == btnSuccess) {
 
        	 try {      
        	        SmsManager smsManager = SmsManager.getDefault();
        	        smsManager.sendTextMessage(phoneNo, null, msg, null, null);    
            // Inflate the Layout
        	Toast.makeText(getApplicationContext(), "Request sent Successfully!" +
        			"Location will be fetched in few sec...", Toast.LENGTH_LONG).show();
        	 } catch (Exception ex) {
        	        Toast.makeText(getApplicationContext(),
        	         ex.getMessage().toString(),
        	         Toast.LENGTH_LONG).show();
        	        ex.printStackTrace();
        	    
        	    }
        }
    }
}