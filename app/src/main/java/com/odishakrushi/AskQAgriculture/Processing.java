package com.odishakrushi.AskQAgriculture;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.odishakrushi.NavDrawer.NavDrawer;
import com.tapadoo.alerter.Alerter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.FullscreenDialogFragment;
import com.odishakrushi.ModelClass.EventModel;
import com.odishakrushi.NetworkRelatedClass.NetworkCall;
import com.odishakrushi.R;
import com.odishakrushi.Utility;

public class Processing extends AppCompatActivity {

    String url="";
    Spinner subgroup;
    String strsubgroup="";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";
    String mystory="";

    String str_filePath="";
    private final int requestCode = 20;
    private static final int PICK_PHOTO = 1958;
    private static final int PICK_VIDEO= 1993;
    private static final int PICK_AUDIO= 1994;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    int SPLASH_TIME_OUT=2;
    File file;

    EditText edittext;
    String str_edittext="";
    TextView attach_file,file_path,txt_complete;
    Button btnSubmit;


    LinearLayout previewLayout;
    ImageView previewImage;
    VideoView previewVideo;
    ImageView removeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_processing);


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
       // getSupportActionBar().setTitle("Agriculture/Processing");
        getSupportActionBar().setTitle(getString(R.string.agriculture)+"/"+getString(R.string.Processing));

        //GETTING THE MYSTORY STATUS

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        mystory=sharedpreferences.getString("MY_STORY", "");
        Log.d("mystory:",mystory);
        editor.commit(); // commit changes

        if(mystory.equals("success")||mystory.equals("failure"))
        {
            url= Config.apimystory;
        }
        else
        {
            url= Config.apiaqna;
        }


        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);



        subgroup=findViewById(R.id.subgroup);
        edittext=findViewById(R.id.edittext);
        file_path=findViewById(R.id.file_path);
        txt_complete=findViewById(R.id.txt_complete);
        btnSubmit=findViewById(R.id.btnSubmit);

        previewLayout=findViewById(R.id.previewLayout);
        previewImage=findViewById(R.id.previewImage);
        previewVideo=findViewById(R.id.previewVideo);
        removeBtn=findViewById(R.id.removeBtn);

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewLayout.setVisibility(View.GONE);
                file=new File("");
                str_filePath="";
                file_path.setText("");

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                str_edittext=edittext.getText().toString();
                if(str_filePath.equals(""))
                {
                    uploadWithoutFile();
                }
                else
                {
                    upload();
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

    }


    private void uploadWithoutFile() {
        //   "http://demo.ratnatechnology.co.in/farmerapp/api/qna/setFarmerQna"

        AndroidNetworking.post(url)
                .addBodyParameter("user_id",str_user_id)
                .addBodyParameter("category_id","1")
                .addBodyParameter("sub_category_id","9")
                .addBodyParameter("dropdown",strsubgroup)
                .addBodyParameter("questext",str_edittext)
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Processing.this, "Question Submitted Successfully", Toast.LENGTH_SHORT).show();

                        txt_complete.setText("");

                        //go back
                       final android.os.Handler handler = new android.os.Handler();
                        handler.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                            //Do something after 100ms
                              Intent i = new Intent(Processing.this, NavDrawer.class);//
                              i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              startActivity(i);
                              finish();
                          }
                        }, 2000);



                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(Processing.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void upload() {

//"http://demo.ratnatechnology.co.in/farmerapp/api/qna/setFarmerQna"
        AndroidNetworking.upload(url)
                .addMultipartFile("file",file)
                .addMultipartParameter("user_id",str_user_id)
                .addMultipartParameter("category_id","1")
                .addMultipartParameter("sub_category_id","9")
                .addMultipartParameter("dropdown",strsubgroup)
                .addMultipartParameter("questext",str_edittext)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress

                        int percentage=(int)((bytesUploaded*100)/totalBytes);
                        txt_complete.setText(percentage+"% uploaded");


                    }
                })
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Processing.this, "Question Submitted Successfully", Toast.LENGTH_SHORT).show();
                        txt_complete.setText("");

                        //go back
                       final android.os.Handler handler = new android.os.Handler();
                        handler.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                            //Do something after 100ms
                              Intent i = new Intent(Processing.this, NavDrawer.class);//
                              i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              startActivity(i);
                              finish();
                          }
                        }, 2000);



                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(Processing.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void attachFile(View view)
    {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose from ");

// add a list
        String[] pic = { "Capture Photo","Pick Image from Gallery","Video Record","Pick Video from Gallery","Pick Audio from Gallery"};// "Camera",
        builder.setItems(pic, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        txt_complete.setText("");
                        Toast.makeText(Processing.this, "Capture Photo", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, requestCode);
                        break;
                    case 1:
                        txt_complete.setText("");
                        Toast.makeText(Processing.this, "Pick Image from Gallery", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent2, PICK_PHOTO);
                        break;
                    case 2:
                        txt_complete.setText("");
                        Toast.makeText(Processing.this, "Video Record", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        intent3.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
                        intent3.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                        if (intent3.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent3, REQUEST_VIDEO_CAPTURE);
                        }
                        break;
                    case 3:
                        txt_complete.setText("");
                        Toast.makeText(Processing.this, "Pick Video from Gallery", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent4, PICK_VIDEO);
                        break;
                    case 4:
                        txt_complete.setText("");
                        Toast.makeText(Processing.this, "Pick Audio from Gallery", Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent5, PICK_AUDIO);
                        break;

                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {

            Uri imageUri = data.getData();
            str_filePath = getPath(imageUri);
            file_path.setText(str_filePath);
            file=new File(str_filePath);//getting image filepath      < ------------------------------------------

            previewImage.setImageURI(imageUri);
            previewImage.setVisibility(View.VISIBLE);
            previewLayout.setVisibility(View.VISIBLE);

        }

        else if(resultCode==RESULT_OK && requestCode==PICK_VIDEO)
        {
            Uri videoUri = data.getData();
            str_filePath = getVideoPath(videoUri);
            file_path.setText(str_filePath);
            file=new File(str_filePath);  // getting video filepath  < ------------------------------------------

            previewVideo.setVisibility(View.VISIBLE);
            previewVideo.setVideoURI(videoUri);
            previewVideo.start();
            previewLayout.setVisibility(View.VISIBLE);

        }

        else if(resultCode==RESULT_OK && requestCode==PICK_AUDIO)
        {
            Uri audioUri = data.getData();
            str_filePath = getAudioPath(audioUri);
            file_path.setText(str_filePath);
            file=new File(str_filePath); // getting audio filepath  < ----------------------------------------------
        }

        else  if(this.requestCode == requestCode && resultCode == RESULT_OK)
        {


            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            Uri cameraUri= getImageUri(this,bitmap);
            str_filePath = getPath(cameraUri);
            file_path.setText(str_filePath);
            file=new File(str_filePath);  // getting image captured filepath  < ----------------------------------------

            previewImage.setImageURI(cameraUri);
            previewImage.setVisibility(View.VISIBLE);
            previewLayout.setVisibility(View.VISIBLE);
        }
        else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {


            Uri videoUri = data.getData();
            str_filePath = getVideoPath(videoUri);
            file_path.setText(str_filePath);
            file=new File(str_filePath); // getting video captured filepath  < -----------------------------------

            previewVideo.setVisibility(View.VISIBLE);
            previewVideo.setVisibility(View.VISIBLE);
            previewVideo.setVideoURI(videoUri);
            previewVideo.start();
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
    private String getVideoPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };//,Video,Audio
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);//Video,Images
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private String getAudioPath(Uri uri) {
        String[] projection = { MediaStore.Audio.Media.DATA };//,Video,Audio
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);//Video,Images
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
