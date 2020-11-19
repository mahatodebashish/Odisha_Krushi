package com.odishakrushi.AskQSale;

import android.app.Activity;
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
import android.support.v7.widget.AppCompatSpinner;
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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.tapadoo.alerter.Alerter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.AskQAgriculture.CultivationPractise;
import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.R;
import com.odishakrushi.SignUpFarmer;
//import spencerstudios.com.bungeelib.Bungee;

public class PopUpMachineToolSale extends AppCompatActivity {

    int position=0;
    File file=new File("No file chosen") ;
    private static final int PICK_PHOTO = 1958;
    Spinner machine_condition;
    TextView machineName,responseTextView;
    String str_sale_id="",imagefilePath="",str_subsidy="No",str_machine_condition="",
            strmname="",str_yr_of_purchase="",str_make="",str_model="",str_capacity="",str_powersource="",str_suitableforcrop="",str_remarks="";
    RadioGroup radioSubsidy;
    EditText yr_of_purchase,make,model,capacity,powersource,suitableforcrop,remarks;
    ImageView imageView;
    Button addImageButton,photo_button,uploadButton;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    private final int requestCode = 20;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_pop_up_machine_tool_sale);

        machineName=findViewById(R.id.machineName);
        responseTextView=findViewById(R.id.responseTextView);
        radioSubsidy=findViewById(R.id.radioSubsidy);
        yr_of_purchase=findViewById(R.id.yr_of_purchase);
        make=findViewById(R.id.make);
        model=findViewById(R.id.model);
        capacity=findViewById(R.id.capacity);
        powersource=findViewById(R.id.powersource);
        suitableforcrop=findViewById(R.id.suitableforcrop);
        remarks=findViewById(R.id.remarks);
        machine_condition=(Spinner)findViewById(R.id.machine_condition);
        imageView = (ImageView) findViewById(R.id.imageView);
        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            strmname = bundle.getString("PROD_NAME"); machineName.setText(strmname);
            str_sale_id = bundle.getString("SALE_ID");
            str_yr_of_purchase = bundle.getString("PURCHASE_YR");yr_of_purchase.setText(str_yr_of_purchase);
            str_make = bundle.getString("MAKE");make.setText(str_make);
            str_model = bundle.getString("MODEL");model.setText(str_model);
            str_capacity = bundle.getString("CAPACITY");capacity.setText(str_capacity);
            str_powersource = bundle.getString("POWER_SOURCE");powersource.setText(str_powersource);
            str_suitableforcrop = bundle.getString("SUITABLE_FOR_CROP");suitableforcrop.setText(str_suitableforcrop);
            str_remarks = bundle.getString("REMARK");remarks.setText(str_remarks);

            str_subsidy = bundle.getString("SUBSIDY");
            if(str_subsidy.equals("Yes"))
            {
                radioSubsidy.check(R.id.radioButtonSubsidyYes);
            }
            else if(str_subsidy.equals("No"))
            {
                radioSubsidy.check(R.id.radioButtonSubsidyNo);
            }
            else if(str_subsidy.equals(""))
            {
                radioSubsidy.check(R.id.radioButtonSubsidyNo);
            }
            str_machine_condition = bundle.getString("MACHINE_CONDITION");
            if(str_machine_condition.equals("Old"))
                position=0;
            else if(str_machine_condition.equals("New"))
                position=1;
            else if(str_machine_condition.equals("Very Good"))
                position=2;
            else if(str_machine_condition.equals("Needs Repair"))
                position=3;
            else if(str_machine_condition.equals("Scrap"))
                position=4;
            machine_condition.setSelection(position);



        }

        //For rasio button subsidy
        radioSubsidy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_subsidy=rb.getText().toString();
                    Toast.makeText(PopUpMachineToolSale.this, str_subsidy, Toast.LENGTH_SHORT).show();


                }

            }
        });

        // for spinner machine condition

        machine_condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_machine_condition= machine_condition.getSelectedItem().toString();

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
    public void onClickOk(View view)
    {
       // Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        str_yr_of_purchase=yr_of_purchase.getText().toString();
        str_make=make.getText().toString();
        str_model=model.getText().toString();
        str_capacity=capacity.getText().toString();
        str_powersource=powersource.getText().toString();
        str_suitableforcrop=suitableforcrop.getText().toString();
        str_remarks=remarks.getText().toString();
        if(str_yr_of_purchase.equals("")||str_make.equals("")||str_powersource.equals("")||
                str_model.equals("")||str_capacity.equals("")||str_suitableforcrop.equals("")||str_remarks.equals(""))
        {
            /*Alerter.create(PopUpMachineToolSale.this)
                    .setTitle("Some Field(s) are blank")
                    .show();*/

            Toast.makeText(this, "Some Field(s) are blank", Toast.LENGTH_SHORT).show();
        }
        else {

            if(str_sale_id.equals(""))
            upload(str_user_id,"Machine",strmname,str_yr_of_purchase,str_make,
                    str_model,str_machine_condition,str_capacity,str_powersource,str_suitableforcrop,str_subsidy,str_remarks,
                    file);
            else
                upload_update(str_sale_id,str_user_id,"Machine",strmname,str_yr_of_purchase,str_make,
                        str_model,str_machine_condition,str_capacity,str_powersource,str_suitableforcrop,str_subsidy,str_remarks,
                        file);

        }

    }

    public void upload(String str_user_id,String category_name,String strmname,String str_yr_of_purchase,String str_make,
                       String str_model,String str_machine_condition,String str_capacity,String str_powersource,
                       String str_suitableforcrop,String str_subsidy,String str_remarks,
                       File file)
    {
        Alerter.create(PopUpMachineToolSale.this)
                .setText("Uploading...")
                .setIcon(R.drawable.ic_upload)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setIconColorFilter(0)
                .enableProgress(true)
                .setProgressColorRes(R.color.white)
                .show();

        AndroidNetworking.upload(Config.farmersales)

                .addMultipartParameter("user_id", str_user_id)
                .addMultipartParameter("category_name",category_name)
                .addMultipartParameter("product_type", strmname)
                .addMultipartParameter("yr_of_purchase",str_yr_of_purchase)
                .addMultipartParameter("make",str_make)
                .addMultipartParameter("model",str_model)
                .addMultipartParameter("machine_condition",str_machine_condition)
                .addMultipartParameter("capacity",str_capacity)
                .addMultipartParameter("power_source",str_powersource)
                .addMultipartParameter("sutable_for_crop",str_suitableforcrop)
                .addMultipartParameter("subsidy",str_subsidy)
                .addMultipartParameter("remark",str_remarks)
                .addMultipartFile("file", file)
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

                        Alerter.create(PopUpMachineToolSale.this)
                                .setTitle(message)
                                .show();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Alerter.create(PopUpMachineToolSale.this)
                                .setTitle("Network Error")
                                .show();
                    }
                });//close of getasJSONObject

    }
    public void upload_update(String str_sale_id,String str_user_id,String category_name,String strmname,String str_yr_of_purchase,String str_make,
                       String str_model,String str_machine_condition,String str_capacity,String str_powersource,
                       String str_suitableforcrop,String str_subsidy,String str_remarks,
                              File file)
    {
        Alerter.create(PopUpMachineToolSale.this)
                .setText("Updating...")
                .setIcon(R.drawable.ic_upload)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setIconColorFilter(0)
                .enableProgress(true)
                .setProgressColorRes(R.color.white)
                .show();

        AndroidNetworking.upload(Config.farmersales)
                .addMultipartParameter("sale_id", str_sale_id)
                .addMultipartParameter("user_id", str_user_id)
                .addMultipartParameter("category_name",category_name)
                .addMultipartParameter("product_type", strmname)
                .addMultipartParameter("yr_of_purchase",str_yr_of_purchase)
                .addMultipartParameter("make",str_make)
                .addMultipartParameter("model",str_model)
                .addMultipartParameter("machine_condition",str_machine_condition)
                .addMultipartParameter("capacity",str_capacity)
                .addMultipartParameter("power_source",str_powersource)
                .addMultipartParameter("sutable_for_crop",str_suitableforcrop)
                .addMultipartParameter("subsidy",str_subsidy)
                .addMultipartParameter("remark",str_remarks)
                .addMultipartFile("file", file)
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

                        Alerter.create(PopUpMachineToolSale.this)
                                .setTitle(message)
                                .show();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Alerter.create(PopUpMachineToolSale.this)
                                .setTitle("Network Error")
                                .show();
                    }
                });//close of getasJSONObject

    }



    public void onClickCancel(View view)
    {
 //       Bungee.zoom(this);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Bungee.zoom(this);
    }
}
