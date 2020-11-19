package com.odishakrushi.Survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;
import com.odishakrushi.R;
import com.odishakrushi.SignUpFarmer_2;

import org.json.JSONException;
import org.json.JSONObject;

public class GiveUrOpinionFarmer extends AppCompatActivity {

    TextView id,question;
    RadioGroup radioOption;
    RadioButton opt_1,opt_2,opt_3,opt_4,opt_5;
    Button btnSubmit;

    String str_id,str_question,str_opt_1,str_opt_2,str_opt_3,str_opt_4,str_opt_5;
    String str_selected_option="";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_ur_opinion_farmer);

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);
        str_user_id=sharedpreferences.getString("FLAG",  "");


        id=findViewById(R.id.id);
        question=findViewById(R.id.question);
        radioOption=findViewById(R.id.radioOption);
        opt_1=findViewById(R.id.opt_1);
        opt_2=findViewById(R.id.opt_2);
        opt_3=findViewById(R.id.opt_3);
        opt_4=findViewById(R.id.opt_4);
        opt_5=findViewById(R.id.opt_5);
        btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(str_selected_option.equals("")){
                    Toast.makeText(GiveUrOpinionFarmer.this, "Select a option", Toast.LENGTH_SHORT).show();
                }
                else
                postSurvey();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            str_id = bundle.getString("id");
            id.setText("S"+str_id + " :");
            str_question = bundle.getString("question");
            question.setText(str_question);
            str_opt_1 = bundle.getString("opt_1");
            opt_1.setText(str_opt_1);
            str_opt_2 = bundle.getString("opt_2");
            opt_2.setText(str_opt_2);
            str_opt_3 = bundle.getString("opt_3");
            opt_3.setText(str_opt_3);
            str_opt_4 = bundle.getString("opt_4");
            opt_4.setText(str_opt_4);
            str_opt_5 = bundle.getString("opt_5");
            opt_5.setText(str_opt_5);

        }

        radioOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    //str_selected_option=rb.getText().toString();
                    str_selected_option="3";
                    Toast.makeText(GiveUrOpinionFarmer.this, str_selected_option, Toast.LENGTH_SHORT).show();


                }

            }
        });
    }

    private void postSurvey() {
        final ProgressDialog progressDialog=new ProgressDialog(GiveUrOpinionFarmer.this);
        progressDialog.setMessage("Posting...");
        progressDialog.show();
        AndroidNetworking.post(Config.surveyAnswer)
                .addBodyParameter("user_id", str_user_id)
                .addBodyParameter("option",str_selected_option )
                .addBodyParameter("question_id", str_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {


                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();

                        Toast.makeText(GiveUrOpinionFarmer.this, response, Toast.LENGTH_SHORT).show();
                       /* try {
                            JSONObject jsonObject=new JSONObject(response);
                            String message = jsonObject.optString("message");
                            Toast.makeText(GiveUrOpinionFarmer.this, message, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.hide();
                        Toast.makeText(GiveUrOpinionFarmer.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
