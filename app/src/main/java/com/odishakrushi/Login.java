package com.odishakrushi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.odishakrushi.PreferenceManager.Preferences;
import com.pixplicity.easyprefs.library.Prefs;
import com.tapadoo.alerter.Alerter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.odishakrushi.NavDrawer.NavDrawer;
import com.odishakrushi.NavDrawer.NavDrawerAdminManager;
import com.odishakrushi.NavDrawer.NavDrawerBusiness;
import com.odishakrushi.NavDrawer.NavDrawerStudentR;
import com.odishakrushi.NavDrawer.NavdrawerRP;
//import spencerstudios.com.bungeelib.Bungee;

public class Login extends AppCompatActivity {

    SharedPreferences sharedpreferences1;
    public static final String mypreference1 = "BUSINESS_PRODUCT_DEALS_IN";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    String login_user="";
    Button btnlogin;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    AppCompatEditText input_login,input_pass;
    TextView txtGuestRegister;
    String strusername,strpass,user_name="",district;
    int user_group_id=0;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Snackbar snackbar;

    // first time login
    Boolean isFirstTime;
    String strlogout="false";

    Context context = this;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String flag="";

    //sharedpref for language
    String str_language="English";
    String langdata="";

    // button onTouch animation
    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.2F);//  1F, 0.8F
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //-------------------EasyPrefs---------------------
        langdata = Prefs.getString("language", "");
        if(langdata.equals("en")||langdata.equals("or"))
        {
            // Toast.makeText(this," Language -"+ langdata, Toast.LENGTH_SHORT).show();
            LanguageChange.changeLanguage(this,langdata);
            setContentView(R.layout.activity_login);
        }
        else
        {
            setContentView(R.layout.activity_login);
            showRadioButtonDialog();
        }



      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
       // toolbar.setBackgroundColor(Color.parseColor("#23783D")); // theme color


        // PERMISSION
        int Permission_All = 1;

        String[] Permissions = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA, };
        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }


        input_login=findViewById(R.id.input_login);
        input_pass=findViewById(R.id.input_pass);
        btnlogin=findViewById(R.id.btnlogin);
        txtGuestRegister=findViewById(R.id.txtGuestRegister);

        txtGuestRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,NavDrawerGuest.class);
                startActivity(intent);
                finish();
            }
        });
        //btnlogin.setBackgroundColor(Color.parseColor("#23783D"));// added by deb
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/


        // CODE FOR ONE TIME LOGIN

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("LOGGED_IN_AS", "");
        editor.commit(); // commit changes

        if (/*login_user.equals("1")*/ login_user.equals("7")){startActivity(new Intent(this,NavDrawerAdminManager.class));finish();}
        if (login_user.equals("2")){startActivity(new Intent(this,NavDrawer.class));finish();}
        if (login_user.equals("3")){startActivity(new Intent(this,NavdrawerRP.class));finish();}
        if (login_user.equals("4")){startActivity(new Intent(this,NavDrawerBusiness.class));finish();}
        if (login_user.equals("5")){startActivity(new Intent(this,NavDrawerStudentR.class));finish();}
        if (login_user.equals("6")){startActivity(new Intent(this,NavDrawerGuest.class));finish();}
    }
    public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }


    public void onClickSkip(View view)
    {
        startActivity(new Intent(this,NavDrawer.class));
    }
    public void gotoForgot(View view)
    {
        startActivity(new Intent(Login.this,ForgotPass.class));//
    }
    public void gotoRegister(View view)
    {
       // startActivity(new Intent(Login.this,Register.class));

        view.startAnimation(buttonClick);
        startActivity(new Intent(Login.this,SignUpAll.class));
//        Bungee.slideDown(this);
    }


    public void onClickLogin(View view )
    {

        view.startAnimation(buttonClick);
        strusername=String.valueOf(input_login.getText());
        strpass=String.valueOf(input_pass.getText());


        if (strusername.equals(""))
        {
            //Toast.makeText(this, strusername, Toast.LENGTH_SHORT).show();
        input_login.setError("Empty Email");
            input_login.setFocusable(true);
                if(strpass.equals(""))
                {
                    input_pass.setError("Empty Password");
                    input_pass.setFocusable(true);
                    //Toast.makeText(this, strpass, Toast.LENGTH_SHORT).show();
                }
        }
      /*  else if (!strusername.matches(emailPattern))
        {

            input_login.setError("Invalid Email");
        }*/
        else
        {
            new AsyncLogInDetails().execute();
        }
    }


    private  class AsyncLogInDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="",user_id="",deals_in_product="",user_phone="",profile_image="",zone_id="";
        boolean status=true;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Logging In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void  doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_name",strusername ));
            cred.add(new BasicNameValuePair("user_pwd",strpass ));

         //   String urlRouteList="http://demo.ratnatechnology.co.in/api/api/user/login";
            String urlRouteList=Config.farmerapplogin;
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);
                Log.v(" ", "Response is " + route_response);

                success = route_response.trim();
                JSONObject jsonObject = new JSONObject(success);
                status =jsonObject.getBoolean("status");
                message = jsonObject.getString("message");

            } catch (Exception e)

            {
                try {
                    String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);
                    Log.v(" ", "Response is " + route_response);

                    success = route_response.trim();
                    JSONObject jsonObject = new JSONObject(success);
                    user_name = jsonObject.optString("user_name");
                  //  district = jsonObject.getString("district");
                    user_group_id=jsonObject.optInt("user_group_id");
                    user_phone=jsonObject.optString("user_phone");
                    zone_id=jsonObject.optString("zone_id");
                    user_id=jsonObject.optString("user_id");
                    profile_image=jsonObject.optString("profile_image");
                    if(user_group_id==4)
                    {
                        deals_in_product = jsonObject.optString("deals_in_product");
                        sharedpreferences1 = getSharedPreferences(mypreference1,
                                Context.MODE_MULTI_PROCESS);
                        SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                        editor1.putString("DEALS_IN_PRODUCT", deals_in_product);
                        editor1.commit();
                    }

                    Log.d("USERID", user_id);
                    sharedpreferences = getSharedPreferences(mypreference,
                            Context.MODE_MULTI_PROCESS);
                    SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                    editor.putString("FLAG", user_id);
                    editor.commit();

                    Preferences.setZone(Login.this,zone_id);
                    Preferences.setGroupID(Login.this,""+user_group_id);
                    Preferences.setDistrictID(Login.this,""+district);

                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

            }return null;
        }

        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            if (status == false)
            {
                /*Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                snackbar.show();*/

                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();

              /*  Alerter.create(Login.this)
                 .setTitle(message)
                   // .setText("Alert text...")
                    .show();*/
            }
            else {
                // get Connectivity Manager object to check connection
                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = connec.getActiveNetworkInfo();

                // Check for network connections
                if (/*connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED*/

                        activeNetwork != null && activeNetwork.isConnectedOrConnecting()
                ) {


                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    if(/*user_group_id==1*/ user_group_id==7)
                    {
                        sharedpreferences = getSharedPreferences(mypreference,
                                Context.MODE_MULTI_PROCESS);
                        SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                        editor.putString("LOGGED_IN_AS", "7");
                        editor.putString("LOGIN_NAME",user_name);
                        editor.putString("USER_ID",user_id);
                        editor.putString("PHONE",user_phone);

                        editor.commit();

                        Intent intent = new Intent(Login.this, NavDrawerAdminManager.class);
                        intent.putExtra("keyName", user_name);
                        intent.putExtra("PROF_IMG",profile_image);
                       // intent.putExtra("USER_ID",user_id);
                        startActivity(intent);
                        finish();
                    }
                  if(user_group_id==2)
                  {
                      sharedpreferences = getSharedPreferences(mypreference,
                              Context.MODE_MULTI_PROCESS);
                      SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                      editor.putString("LOGGED_IN_AS", "2");
                      editor.putString("LOGIN_NAME",user_name);
                      editor.putString("USER_ID",user_id);
                      editor.putString("PHONE",user_phone);
                      editor.putString("PROF_IMG",profile_image);
                      editor.commit();

                      Intent intent = new Intent(Login.this, NavDrawer.class);
                      intent.putExtra("keyName", user_name);
                      intent.putExtra("PROF_IMG",profile_image);
                     // intent.putExtra("USER_ID",user_id);
                      startActivity(intent);
                       finish();
                  }
                  else if(user_group_id==3)
                  {
                      sharedpreferences = getSharedPreferences(mypreference,
                              Context.MODE_MULTI_PROCESS);
                      SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                      editor.putString("LOGGED_IN_AS", "3");
                      editor.putString("LOGIN_NAME",user_name);
                      editor.putString("USER_ID",user_id);
                      editor.putString("PHONE",user_phone);
                      editor.putString("PROF_IMG",profile_image);
                      editor.commit();

                      Intent intent = new Intent(Login.this, NavdrawerRP.class);
                      intent.putExtra("keyName", user_name);
                      intent.putExtra("PROF_IMG",profile_image);
                      //intent.putExtra("USER_ID",user_id);
                      startActivity(intent);
                      finish();
                  }
                  else if(user_group_id==4)
                  {
                      sharedpreferences = getSharedPreferences(mypreference,
                              Context.MODE_MULTI_PROCESS);
                      SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                      editor.putString("LOGGED_IN_AS", "4");
                      editor.putString("LOGIN_NAME",user_name);
                      editor.putString("USER_ID",user_id);
                      editor.putString("PHONE",user_phone);
                      editor.putString("PROF_IMG",profile_image);
                      editor.commit();

                      Intent intent = new Intent(Login.this, NavDrawerBusiness.class);
                      intent.putExtra("keyName", user_name);
                      intent.putExtra("PROF_IMG",profile_image);
                     // intent.putExtra("USER_ID",user_id);
                      startActivity(intent);
                      finish();
                  }
                  else if(user_group_id==5)
                  {
                      sharedpreferences = getSharedPreferences(mypreference,
                              Context.MODE_MULTI_PROCESS);
                      SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                      editor.putString("LOGGED_IN_AS", "5");
                      editor.putString("LOGIN_NAME",user_name);
                      editor.putString("USER_ID",user_id);
                      editor.putString("PHONE",user_phone);
                      editor.putString("PROF_IMG",profile_image);
                      editor.commit();

                      Intent intent = new Intent(Login.this, NavDrawerStudentR.class);
                      intent.putExtra("keyName", user_name);
                      intent.putExtra("PROF_IMG",profile_image);
                    //  intent.putExtra("USER_ID",user_id);
                      startActivity(intent);
                      finish();
                  }
                  else if(user_group_id==6)
                  {
                      sharedpreferences = getSharedPreferences(mypreference,
                              Context.MODE_MULTI_PROCESS);
                      SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                      editor.putString("LOGGED_IN_AS", "6");
                      editor.putString("LOGIN_NAME",user_name);
                      editor.putString("USER_ID",user_id);
                      editor.putString("PHONE",user_phone);
                      editor.putString("PROF_IMG",profile_image);
                      editor.commit();

                      Intent intent = new Intent(Login.this, NavDrawerGuest.class);
                      intent.putExtra("keyName", user_name);
                      intent.putExtra("PROF_IMG",profile_image);
                    //  intent.putExtra("USER_ID",user_id);
                      startActivity(intent);
                      finish();
                  }
                } else {


                    Toast toast = Toast.makeText(getApplicationContext(),
                            "No Internet Available", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }


        }



    }
    public void gotoResourcePerson(View view)
    {
        Intent intent=new Intent(this,NavdrawerRP.class);
        startActivity(intent);
    }

    public void gotoBusiness(View view)
    {
        Intent intent=new Intent(this,NavDrawerBusiness.class);
        startActivity(intent);
    }

    private void showRadioButtonDialog() {

        // Calling Application class (see application tag in AndroidManifest.xml)
       // final MyApplication globalVariable = (MyApplication) getApplicationContext();


        final  String[] singleChoiceItems = getResources().getStringArray(R.array.toppings);
        final int itemSelected = 0;
        new AlertDialog.Builder(this)
                .setTitle("Choose Your Language")
                .setCancelable(false)
                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        str_language= singleChoiceItems[selectedIndex];


                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(str_language.equals("English"))
                        {
                          //  Toast.makeText(Login.this,, Toast.LENGTH_SHORT).show();

                            Prefs.putString("language", "en");//-------------------EasyPrefs---------------------
                            LanguageChange.changeLanguage(getApplicationContext(),"en");
                            setContentView(R.layout.activity_login);

                            finish();
 //                           Bungee.fade(Login.this); //  overridePendingTransition( 0, 0);
                            startActivity(getIntent());
//                            Bungee.fade(Login.this); //overridePendingTransition( 0, 0);
                        }
                        else if(str_language.equals("Odia"))
                        {
                            //Toast.makeText(Login.this,str_language, Toast.LENGTH_SHORT).show();

                            Prefs.putString("language", "or");//-------------------EasyPrefs---------------------
                            LanguageChange.changeLanguage(getApplicationContext(),"or");
                            setContentView(R.layout.activity_login);

                            finish();
  //                          Bungee.fade(Login.this); //overridePendingTransition( 0, 0);
                            startActivity(getIntent());
//                            Bungee.fade(Login.this); //overridePendingTransition( 0, 0);
                        }
                    }
                })
               /* .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })*/
                .show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.theme_changer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.theme_change:
              startActivity(new Intent(this,ThemeSettings.class));

                return true;

            default:
                return super.onOptionsItemSelected(item);


        }

    }
}
