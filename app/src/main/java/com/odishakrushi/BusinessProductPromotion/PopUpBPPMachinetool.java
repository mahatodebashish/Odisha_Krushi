package com.odishakrushi.BusinessProductPromotion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.R;

public class PopUpBPPMachinetool extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    TextView machineName;String strmname="";
    RadioGroup radioSubsidy;
    EditText  make, model;

    int position=0;

    String strmake,strmodel,str_suitableforcrop,str_powersource,str_capacity,str_subsidy;


    EditText suitableforcrop,powersource,capacity;

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private final int requestCode = 20;
    private static final int PICK_PHOTO = 1958;
    private static final int PICK_VIDEO= 1993;
    private static final int PICK_AUDIO= 1994;
    TextView responseTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_pop_up_bppmachinetool);


        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);

        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        machineName=findViewById(R.id.machineName);
        suitableforcrop=findViewById(R.id.suitableforcrop);
        powersource=findViewById(R.id.powersource);
        capacity=findViewById(R.id.capacity);
        radioSubsidy=findViewById(R.id.radioSubsidy);
        make=findViewById(R.id.make);
        model=findViewById(R.id.model);
        responseTextView = (TextView) findViewById(R.id.responseTextView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            strmname = bundle.getString("PROD_NAME");
          /*  strcategoryname=bundle.getString("CATEGORY_NAME");
            Log.d("strcategoryname",strcategoryname);*/
            machineName.setText(strmname);
        }

        radioSubsidy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_subsidy=rb.getText().toString();
                    Toast.makeText(PopUpBPPMachinetool.this, str_subsidy, Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button capturedImageButton = (Button)findViewById(R.id.photo_button);
        capturedImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, requestCode);
            }
        });

        final Button record_video=(Button)findViewById(R.id.record_video);
        record_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
                }
            }
        });
    }

    public void addPhoto(View view) {
        responseTextView.setText("");
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO);
    }

    public void addVideo(View view) {
        responseTextView.setText("");
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_VIDEO);
    }

    public void onClickOk(View view)
    {
        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();

        strmake=make.getText().toString().trim();
        str_powersource=powersource.getText().toString().trim();
        str_capacity=capacity.getText().toString().trim();
        str_suitableforcrop=suitableforcrop.getText().toString().trim();

        strmodel=model.getText().toString();
        new AsyncBPPMachineTool().execute();
    }
    private class AsyncBPPMachineTool extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="";

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(PopUpBPPMachinetool.this);
            pDialog.setMessage("Promoting...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void  doInBackground(Void... params) {



            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();

            cred.add(new BasicNameValuePair("user_id",str_user_id));
            cred.add(new BasicNameValuePair("machine_type",strmname));
            cred.add(new BasicNameValuePair("make",strmake));
            cred.add(new BasicNameValuePair("model",strmodel));
            cred.add(new BasicNameValuePair("capacity",str_capacity));
            cred.add(new BasicNameValuePair("power_source",str_powersource));
            cred.add(new BasicNameValuePair("suitable_for_crop",str_suitableforcrop));
            cred.add(new BasicNameValuePair("subsidy",str_subsidy));
           // String urlRouteList= "http://demo.ratnatechnology.co.in/farmerapp/api/farmer/bpp_machine_tools"; Doubt
            String urlRouteList= Config.baseUrl+"farmer/bpp_machine_tools";
            try {

                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);
                Log.v(" ", "Response is " + route_response);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);
                // status =jsonObject.getString("status");
                message = jsonObject.getString("message");

            }
            catch (Exception e)
            {
                Log.v("ONMESSAGE", e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            if(message.equals("Insert Successfull")||message.equals("Update Successfull"))
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
              /*  Intent i = new Intent(BPPPesticides.this, Login.class);
                startActivity(i);*/
                finish();


                 /* startActivity(new Intent(SignUpFarmer_2.this,Login.class));
                finish();*/
            }
            else {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
              /*  Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

                snackbar.show();*/
            }

        }



    }
    public void onClickCancel(View view)
    {
        finish();
    }
}
