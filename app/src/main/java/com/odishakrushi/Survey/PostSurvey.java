package com.odishakrushi.Survey;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.JsonObject;
import com.odishakrushi.Config;
import com.odishakrushi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;



public class PostSurvey extends AppCompatActivity {

    EditText txtPostMessage;
    TextView s_date,e_date;
    Button btnSubmit;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String strEndDate="",strStartDate="";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_survey);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //  getSupportActionBar().setTitle("Take On Hire");
        getSupportActionBar().setTitle("Post Your Survey");



        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);
        str_user_id=sharedpreferences.getString("FLAG",  "");

        txtPostMessage=findViewById(R.id.txtPostMessage);
        s_date=findViewById(R.id.s_date);
        e_date=findViewById(R.id.e_date);

        s_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PostSurvey.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                              //  strStartDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                strStartDate=year+ "-" + (monthOfYear + 1)+ "-" + dayOfMonth  ;
                                s_date.setText(strStartDate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        e_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PostSurvey.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                              //  strEndDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                strEndDate= year +"-" + (monthOfYear + 1) + "-"+dayOfMonth  ;
                                e_date.setText(strEndDate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog=new ProgressDialog(PostSurvey.this);
                progressDialog.setMessage("Posting...");
                progressDialog.show();
                AndroidNetworking.post(Config.surveyQuestion)
                        .addBodyParameter("user_id", str_user_id)
                        .addBodyParameter("question", txtPostMessage.getText().toString())
                        .addBodyParameter("start_date", strStartDate)
                        .addBodyParameter("end_date", strEndDate)
                        .setTag("tagsurveyQuestion")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsString(new StringRequestListener() {


                            @Override
                            public void onResponse(String response) {
                                progressDialog.hide();

                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String message = jsonObject.optString("message");
                                    Toast.makeText(PostSurvey.this, message, Toast.LENGTH_SHORT).show();

                                    txtPostMessage.setText("");
                                    s_date.setText("START DATE");
                                    e_date.setText("END DATE");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                progressDialog.hide();
                                Toast.makeText(PostSurvey.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
