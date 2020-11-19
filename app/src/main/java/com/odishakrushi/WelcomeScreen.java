package com.odishakrushi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressCustom;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class WelcomeScreen extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    int SPLASH_TIME_OUT=5;
    /*String str_language="English";
    String langdata="";*/
    TextView countAdmin,countGuest,countStudent,countExtensionOff,countBusinessPerson,countFarmer;
    ImageView refresh;
    ACProgressCustom dialog;

    StringRequest stringRequest;

    RequestQueue queue;
    String REQUEST_TAG="volley_tag";

    String flag="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_screen);

        countAdmin=findViewById(R.id.countAdmin);
        countGuest=findViewById(R.id.countGuest);
        countStudent=findViewById(R.id.countStudent);
        countExtensionOff=findViewById(R.id.countExtensionOff);
        countBusinessPerson=findViewById(R.id.countBusinessPerson);
        countFarmer=findViewById(R.id.countFarmer);
        refresh=findViewById(R.id.refresh);

        loadData();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        try {
            Intent i = getIntent();
            flag = i.getStringExtra("FROM_CHANGELANG_ACTIVITY_TO_WELCOMESCREEN");

            if(flag.equals("1"))
                onBackPressed();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadData()
    {

        dialog = new ACProgressCustom.Builder(this)
                .useImages(R.drawable.logo_new_5th_april)
                .build();
        dialog.show();

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = Config.getUserCount;


        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        String farmer_user,business_person_user,extension_officer_user,student_user,others_user,admin_user;
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Log.d("response::",""+response);
                            String data= jsonObject.getString("data");
                            JSONObject jsonObject1=new JSONObject(data);
                            farmer_user=jsonObject1.getString("farmer_user");
                            business_person_user=jsonObject1.getString("business_person_user");
                            extension_officer_user=jsonObject1.getString("extension_officer_user");
                            student_user=jsonObject1.getString("student_user");
                            admin_user=jsonObject1.getString("admin_user");
                            others_user=jsonObject1.getString("others_user");

                            countAdmin.setText(admin_user);
                            countFarmer.setText(farmer_user);
                            countBusinessPerson.setText(business_person_user);
                            countExtensionOff.setText(extension_officer_user);
                            countStudent.setText(student_user);
                            countGuest.setText(others_user);
                            dialog.hide();




                            new Handler().postDelayed(new Runnable() {


                          /*   * Showing splash screen with a timer. This will be useful when you
                             * want to show case your app logo / company*/


                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over

                                    Intent i = new Intent(WelcomeScreen.this, WelcomeActivity.class);// Login
                                    startActivity(i);
                                    finish();
                                }
                            }, SPLASH_TIME_OUT);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               Toast.makeText(WelcomeScreen.this,"Not Connected to Internet",Toast.LENGTH_LONG);

                new Handler().postDelayed(new Runnable() {


                    /*   * Showing splash screen with a timer. This will be useful when you
                     * want to show case your app logo / company*/


                    @Override
                    public void run() {
                        // This method will be executed once the timer is over

                        Intent i = new Intent(WelcomeScreen.this, WelcomeActivity.class);// Login
                        startActivity(i);
                        finish();
                    }
                }, SPLASH_TIME_OUT);
                dialog.hide();


            }
        });

// Add the request to the RequestQueue.

        queue.add(stringRequest);
        stringRequest.setTag(REQUEST_TAG);// Adding tag
    }


    @Override
    protected void onPause() {
        queue.cancelAll(REQUEST_TAG);
        super.onPause();
        Log.d("lifecycle","onStart invoked");
    }
    @Override
    public void onStop(){

        super.onStop();
        if(dialog != null)
            dialog.dismiss();

    }

    public void refresh(View view)
    {
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        refresh.startAnimation(rotate);
        loadData();

    }

    @Override
    protected void onDestroy() {
        queue.cancelAll(REQUEST_TAG);
        super.onDestroy();
    }
}
