 package com.project.BusAssist_server;

 import java.io.IOException;
 import java.util.List;
 import java.util.Locale;
 import android.os.Bundle;
 import android.app.Activity;
 import android.content.Context;
 import android.content.Intent;
 import android.content.pm.PackageManager;
 import android.location.Address;
 import android.location.Geocoder;
 import android.location.Location;
 import android.location.LocationListener;
 import android.location.LocationManager;
 import android.widget.TextView;
 import android.widget.Toast;
 import android.app.Activity;
 import android.view.Menu;
 import android.telephony.gsm.SmsManager;
 import android.util.Log;

 public class SmsActivity extends Activity implements LocationListener {


  protected LocationManager locationManager;
  protected LocationListener locationListener;
  protected Context context;
  TextView txtLat, txtadd, t;
  String lat;
  private static SmsActivity inst;
  boolean sms = true;
  String provider;
  protected String latitude, longitude;
  protected boolean gps_enabled, network_enabled;
  double LAT, LON;
  String userPhoneNo = "";
  public static SmsActivity instance() {
   return inst;
  }

  @Override
  public void onStart() {
   super.onStart();
   inst = this;
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.two);
   txtLat = (TextView) findViewById(R.id.textView1);
   txtadd = (TextView) findViewById(R.id.textView2);
   t = (TextView) findViewById(R.id.textView3);
  }
  public void intentMethod(String no) {
   userPhoneNo = no;
   locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
   locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
  }

  @Override
  public void onLocationChanged(Location location) {

   LAT = location.getLatitude();
   LON = location.getLongitude();
   String lats = Double.toString(LAT);
   String lons = Double.toString(LON);
   txtLat.setText("Latitude:" + lats + ", Longitude:" + lons);
   t.setText(userPhoneNo);
   getAddrFromGeocoords(LAT, LON);
  }
  public void getAddrFromGeocoords(double lat, double lon) {
   try {
    Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

    //Place your latitude and longitude
    List < Address > addresses = geocoder.getFromLocation(lat, lon, 1);

    if (addresses != null) {

     Address fetchedAddress = addresses.get(0);
     StringBuilder strAddress = new StringBuilder();
	 
     for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
      strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
     }
	 
     txtadd.setText("Current Position is: " + strAddress.toString());
     String finalAddr = strAddress.toString();
     sendSMS(finalAddr);
    }
	else
     txtadd.setText("No location found..!");
   } 
   catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    Toast.makeText(getApplicationContext(), "Could not get address..!", Toast.LENGTH_LONG).show();
   }
  }

  public void sendSMS(String addr) {
   if (sms == true) {
    try {
     SmsManager smsManager = SmsManager.getDefault();
     smsManager.sendTextMessage(userPhoneNo, null, addr, null, null);
     sms = false;
     // Inflate the Layout
     Toast.makeText(getApplicationContext(), "Response sent Successfully!", Toast.LENGTH_LONG).show();
     String packageName = "com.project.BusAssist_server";
     PackageManager packageManager = getPackageManager();
     Intent intent = packageManager.getLaunchIntentForPackage(packageName);
     startActivity(intent);
    } catch (Exception ex) {
     Toast.makeText(getApplicationContext(),
      ex.getMessage().toString(),
      Toast.LENGTH_LONG).show();
     ex.printStackTrace();

    }
   }
  }
  @Override
  public void onProviderDisabled(String provider) {
   Log.d("Latitude", "disable");
  }

  @Override
  public void onProviderEnabled(String provider) {
   Log.d("Latitude", "enable");
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
   Log.d("Latitude", "status");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
   // Inflate the menu; this adds items to the action bar if it is present.
   getMenuInflater().inflate(R.menu.main, menu);
   return true;
  }

 }