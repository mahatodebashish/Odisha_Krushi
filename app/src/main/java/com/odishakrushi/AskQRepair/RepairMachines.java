package com.odishakrushi.AskQRepair;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.odishakrushi.AskQSale.PopUpMachineToolSale;
import com.odishakrushi.AskQSale.PopUpSaleAnimal;
import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.R;
import com.odishakrushi.SignUpFarmer;
import com.tapadoo.alerter.Alerter;

public class RepairMachines extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    Spinner subgroup;
    RadioGroup rgProblem;
    EditText description,yr_of_purchase,model,make;
    String str_problem,strsubgroup,strdescription,stryr_of_purchase,strmodel,strmake,imagefilePath="";
    ImageView imageView;
    private final int requestCode = 20;
    File file=new File("No file chosen") ;
    private static final int PICK_PHOTO = 1958;
    TextView responseTextView;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_repair_machines);

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
      //  getSupportActionBar().setTitle("Repair");
        getSupportActionBar().setTitle(getString(R.string.Repair));

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        subgroup=findViewById(R.id.subgroup);
        rgProblem=findViewById(R.id.rgProblem);
        description=findViewById(R.id.description);
        yr_of_purchase=findViewById(R.id.yr_of_purchase);
        model=findViewById(R.id.model);
        make=findViewById(R.id.make);
        imageView = (ImageView) findViewById(R.id.imageView);
        responseTextView=findViewById(R.id.responseTextView);

        rgProblem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_problem=rb.getText().toString();
                    Toast.makeText(RepairMachines.this, str_problem, Toast.LENGTH_SHORT).show();


                }

            }
        });

        subgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strsubgroup= subgroup.getSelectedItem().toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
    }

    public void addPhoto(View view) {
        responseTextView.setText("");
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {

            Uri imageUri = data.getData();
            imagefilePath = getPath(imageUri);
            imageView.setImageURI(imageUri);
            imageView.setVisibility(View.VISIBLE);

            /*removeBtn.setVisibility(View.VISIBLE);
            linearframelayoutimage.setVisibility(View.VISIBLE);*/

            file=new File(imagefilePath);//getting image filepath      < ------------------------------------------
        }


        else if(this.requestCode == requestCode && resultCode == RESULT_OK)
        {
            // linearframelayoutimage.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            //  removeBtn.setVisibility(View.VISIBLE);

            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            Uri cameraUri= getImageUri(this,bitmap);
            imagefilePath = getPath(cameraUri);
            Log.d("imagefilePath",imagefilePath);

            file=new File(imagefilePath);  // getting image captured filepath  < ----------------------------------------
        }


    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };//,Video,Audio
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Video
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public void onClickSubmit(View view)
    {
        strdescription=description.getText().toString();
        stryr_of_purchase=yr_of_purchase.getText().toString();
        strmodel=model.getText().toString();
        strmake=make.getText().toString();

     //   new AsyncRepairPost().execute();
        upload( strsubgroup, strmake, stryr_of_purchase, str_problem, strdescription,
                 str_user_id, file);
    }
    public void upload(String strsubgroup,String strmake,String stryr_of_purchase,String str_problem,String strdescription,
                       String str_user_id,File file)
    {
        Alerter.create(RepairMachines.this)
                .setText("Uploading...")
                .setIcon(R.drawable.ic_upload)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setIconColorFilter(0)
                .enableProgress(true)
                .setProgressColorRes(R.color.white)
                .show();

        AndroidNetworking.upload(Config.baseUrl+"farmer/repair_machine")

                .addMultipartParameter("machine_type",strsubgroup)
                .addMultipartParameter("make",strmake)
                .addMultipartParameter("model",strmodel)
                .addMultipartParameter("yr_of_purchase",stryr_of_purchase)
                .addMultipartParameter("problem",str_problem)
                .addMultipartParameter("description",strdescription)
                .addMultipartParameter("user_id",str_user_id)
                .addMultipartFile("image_url",file) // Doubt with the key
                .setTag("uploadFile")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        int percentage=(int)((bytesUploaded*100)/totalBytes);
                        responseTextView.setText(percentage+"% uploaded");




                    }
                })//close of setUploadProgressListener
                .getAsString(new StringRequestListener() {


                    @Override
                    public void onResponse(String response) {
                        String message="";
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            message = jsonObject.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        /*Alerter.create(RepairMachines.this)
                                .setTitle(message)
                                .show();*/

                        Toast.makeText(RepairMachines.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Alerter.create(RepairMachines.this)
                                .setTitle("Network Error")
                                .show();
                    }
                });//close of getasJSONObject

    }

 /*   private class AsyncRepairPost extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="";

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(RepairMachines.this);
            pDialog.setMessage("Submitting...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void  doInBackground(Void... params) {



            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();

            cred.add(new BasicNameValuePair("machine_type",strsubgroup));
            cred.add(new BasicNameValuePair("make",strmake));
            cred.add(new BasicNameValuePair("model",strmodel));
            cred.add(new BasicNameValuePair("yr_of_purchase",stryr_of_purchase));
            cred.add(new BasicNameValuePair("image_url",null));
            cred.add(new BasicNameValuePair("problem",str_problem));
            cred.add(new BasicNameValuePair("description",strdescription));
            cred.add(new BasicNameValuePair("user_id",str_user_id));


           // String urlRouteList= "http://demo.ratnatechnology.co.in/farmerapp/api/farmer/repair_machine"; Doubt
            String urlRouteList= Config.baseUrl+"farmer/repair_machine";
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
                Intent i = new Intent(RepairMachines.this, Addr_Contact_ServiceCenter.class);
                i.putExtra("MACHINE_TYPE",strsubgroup);

                startActivity(i);
                finish();


                 *//* startActivity(new Intent(SignUpFarmer_2.this,Login.class));
                finish();*//*
            }
            else {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
              *//*  Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

                snackbar.show();*//*
            }

        }



    }*/
}
