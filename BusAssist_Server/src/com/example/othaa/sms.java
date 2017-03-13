package com.example.othaa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class sms extends BroadcastReceiver {
	String address,smsBody;
	 static String a,s;
	 String b="find";
	 

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
                a=smsBody;
                s=address;
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();
           
           
            //this will update the UI with message
            SmsActivity inst = SmsActivity.instance();
            inst.updateList(s);
        }
    }
}