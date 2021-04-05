package com.odishakrushi.Message;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;
import com.odishakrushi.Login;
import com.odishakrushi.R;
import com.odishakrushi.SignUpAdminManager;
import com.odishakrushi.ViewProfile.ViewProfileExtensionOff;
import com.odishakrushi.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressCustom;

import static com.odishakrushi.Config.getAdminManagerProfileData;
import static com.odishakrushi.Config.getBusinessManProfileData;
import static com.odishakrushi.Config.getExtensionOfficerProfileData;

public class SendMessageDetail extends AppCompatActivity {

    Spinner spinnerValues;
    EditText message;
    Button btnOK;
    String login_user="";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    String service_status_str="";
    String str_dropdown="";
    String end_point="";

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.Message));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinnerValues=findViewById(R.id.spinnerValues);
        message=findViewById(R.id.message);
        btnOK=findViewById(R.id.btnOK);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("LOGGED_IN_AS", "");
        service_status_str=sharedpreferences.getString("SERVICE_STATUS", "");
        user_id=sharedpreferences.getString("FLAG",  "");
        editor.commit(); // commit changes




        // TODO: get the stakeholder and service status
        if (login_user.equals("3") && service_status_str.equals("In-Service")){ // Extension Officer
            List<String> list = Arrays.asList(getResources().getStringArray(R.array.ext_msg_groups_inservice));

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerValues.setAdapter(spinnerAdapter);
            spinnerAdapter.notifyDataSetChanged();
        }
        // TODO: get the stakeholder and retired status
        else  if (login_user.equals("3") && service_status_str.equals("Retired")) {// Extension Officer
            List<String> list = Arrays.asList(getResources().getStringArray(R.array.ext_msg_groups_retired));

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerValues.setAdapter(spinnerAdapter);
            spinnerAdapter.notifyDataSetChanged();
        }

        //Admin Manager
        // TODO: get the stakeholder and service status
        if (login_user.equals("7") && service_status_str.equals("In-Service")){ // Admin Manager
            List<String> list = Arrays.asList(getResources().getStringArray(R.array.admin_msg_groups_inservice));

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerValues.setAdapter(spinnerAdapter);
            spinnerAdapter.notifyDataSetChanged();
        }
        // TODO: get the stakeholder and retired status
        else  if (login_user.equals("7") && service_status_str.equals("Retired")) {// Admin Manager
            List<String> list = Arrays.asList(getResources().getStringArray(R.array.admin_msg_groups_retired));

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerValues.setAdapter(spinnerAdapter);
            spinnerAdapter.notifyDataSetChanged();
        }

        //BusinessMan
        if (login_user.equals("4")){
            List<String> list = Arrays.asList(getResources().getStringArray(R.array.business_msg_groups));

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerValues.setAdapter(spinnerAdapter);
            spinnerAdapter.notifyDataSetChanged();
        }

        spinnerValues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               str_dropdown= spinnerValues.getSelectedItem().toString();

               if(str_dropdown.equals("All Users"))
                   end_point="message/allUser";
               else if(str_dropdown.equals("All Farmers"))
                   end_point="message/allfarmer";
               else if(str_dropdown.equals("All Businessmen"))
                   end_point="message/allBusinessmen";
               else if(str_dropdown.equals("All Students/Researchers"))
                   end_point="message/allStudent";
               else if(str_dropdown.equals("All Guests"))
                   end_point="message/allGuest";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!message.getText().toString().equals("")) {

                    progressDialog = new ProgressDialog(SendMessageDetail.this);
                    progressDialog.setMessage(getString(R.string.pleasewait));
                    progressDialog.show();
                    sendMessage(end_point);
                }
            }
        });
    }

    private void sendMessage(String end_point) {

        AndroidNetworking.post(Config.baseUrl+end_point)
                .addBodyParameter("sender_id", user_id)
                .addBodyParameter("questext", message.getText().toString())
                .addBodyParameter("dropdown", str_dropdown)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();
                        try {
                            Toast.makeText(SendMessageDetail.this, response, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.hide();
                        Toast.makeText(SendMessageDetail.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
    }

}