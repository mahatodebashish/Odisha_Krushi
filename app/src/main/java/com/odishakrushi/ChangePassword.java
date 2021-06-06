package com.odishakrushi;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {

    AppCompatEditText oldPass,newPass,reenternewPass;
    Button btnSubmit;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        oldPass=findViewById(R.id.oldPass);
        newPass=findViewById(R.id.newPass);
        reenternewPass=findViewById(R.id.reenternewPass);
        btnSubmit=findViewById(R.id.btnSubmit);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        user_id=sharedpreferences.getString("FLAG", null);
        Log.d("user_id:",user_id);
        editor.commit(); // commit changes

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApiChangePassword();
            }
        });
    }

    private void callApiChangePassword() {
        if (Utils.hasNetwork(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.pleasewait));
            progressDialog.show();

            AndroidNetworking.post(Config.changePassword)

                    .addBodyParameter("user_id", user_id)
                    .addBodyParameter("oldpass", oldPass.getText().toString())
                    .addBodyParameter("newpass", newPass.getText().toString())
                    .addBodyParameter("repass", reenternewPass.getText().toString())
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.hide();

                            oldPass.setText("");
                            newPass.setText("");
                            reenternewPass.setText("");

                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String message=jsonObject.optString("message");
                                Toast.makeText(ChangePassword.this, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(ChangePassword.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                        }
                    });


        }
        else Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
    }
}