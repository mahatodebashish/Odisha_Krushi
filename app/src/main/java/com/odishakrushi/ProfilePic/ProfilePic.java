package com.odishakrushi.ProfilePic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
//import com.odishakrushi.NavDrawer.NavDrawerBusiness;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.odishakrushi.Config;
import com.odishakrushi.Login;
import com.odishakrushi.R;


public class ProfilePic extends AppCompatActivity {

    String message="",image_url="",pic_url="";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="",str_user_id_getExtra="";

    TextView textResponse;
    private String imagefilePath="";
    File file;
    ImageView imageView;
    Button uploadImage;
    private final int requestCode = 20;
    private static final int PICK_PHOTO = 1958;
    BroadcastReceiver mProfilePicReceiver;// initialization

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_profile_pic);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null)
        {
            str_user_id_getExtra= bundle.getString("USER_ID");

        }


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        str_user_id=sharedpreferences.getString("USER_ID", "");
        pic_url=sharedpreferences.getString("PROF_IMG", "");
        editor.commit(); //
      //  Toast.makeText(this, str_user_id, Toast.LENGTH_SHORT).show();

        if(str_user_id.equalsIgnoreCase(""))
            str_user_id=str_user_id_getExtra;  // when signup

        imageView=findViewById(R.id.imageView);

        if(!pic_url.equals("")&&str_user_id_getExtra.equals("")&&!pic_url.equals("null"))
        Picasso.with(this).load(pic_url).into(imageView);
        else
            Picasso.with(this).load(R.drawable.photo_icon).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        textResponse=findViewById(R.id.textResponse);
        uploadImage=findViewById(R.id.uploadImage);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imagefilePath.equals(""))
                {

                        AndroidNetworking.upload(Config.profile_image_upload)
                                .addMultipartFile("file", file)
                                .addMultipartParameter("user_id", str_user_id)
                                .setTag("prof_pic")
                                .setPriority(Priority.HIGH)
                                .build()
                                .setUploadProgressListener(new UploadProgressListener() {
                                    @Override
                                    public void onProgress(long bytesUploaded, long totalBytes) {
                                        // do anything with progress
                                        textResponse.setVisibility(View.VISIBLE);
                                        int percentage = (int) ((bytesUploaded * 100) / totalBytes);
                                        textResponse.setText(percentage + "% uploaded");
                                    }
                                })
                                .getAsString(new StringRequestListener() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            message = jsonObject.getString("message");
                                            image_url = jsonObject.getString("file");

                                            Picasso.with(ProfilePic.this).load(image_url).into(imageView);
                                            Toast.makeText(ProfilePic.this, message, Toast.LENGTH_SHORT).show();
                                            textResponse.setVisibility(View.GONE);

                                            sharedpreferences = getSharedPreferences(mypreference,
                                                    Context.MODE_MULTI_PROCESS);
                                            SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                                            editor.putString("PROF_IMG", image_url);
                                            editor.commit();


                                            /* LOCALBROADCAST SENDING PART */
                                            // Register the local broadcast
                                            LocalBroadcastManager.getInstance(ProfilePic.this).registerReceiver(
                                            mProfilePicReceiver,
                                                    new IntentFilter("set-profile-pic"));

                                        // Initialize a new intent instance
                                            Intent intent = new Intent("profilepic");
                                            intent.putExtra("image_url",image_url);
                                            // Send the broadcastLocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                                            new Handler().postDelayed(new Runnable() {


                                                @Override
                                                public void run() {

                                                  /*  if(NavDrawerBusiness.navDrawerBusiness!=null){
                                                       finish();
                                                    }
                                                    else {*/
                                                        // This method will be executed once the timer is over
                                                        Intent i = new Intent(ProfilePic.this, Login.class);//
                                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(i);
                                                        finish();
                                                   // }
                                                }
                                            }, 3);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        Toast.makeText(ProfilePic.this, "error", Toast.LENGTH_SHORT).show();
                                    }


                                });

                }
            }
        });
    }

   public void showDialog()
   {

       // setup the alert builder
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle("Choose from ");

// add a list
       String[] pic = { "Gallery"};// "Camera",
       builder.setItems(pic, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               switch (which) {
                   case 0:
                       Toast.makeText(ProfilePic.this, "Camera", Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                               startActivityForResult(intent, requestCode);


                   case 1:
                       Toast.makeText(ProfilePic.this, "Gallery", Toast.LENGTH_SHORT).show();
                       Intent intent2 = new Intent(Intent.ACTION_PICK,
                               android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                       startActivityForResult(intent2, PICK_PHOTO);
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
            imagefilePath = getPath(imageUri);
            imageView.setImageURI(imageUri);
            file=new File(imagefilePath);//getting image filepath      < ------------------------------------------
        }


        else if(this.requestCode == requestCode && resultCode == RESULT_OK)
        {



            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            Uri cameraUri= getImageUri(this,bitmap);
            imagefilePath = getPath(cameraUri);
            Log.d("imagefilePath",imagefilePath);

            file=new File(imagefilePath);  // getting image captured filepath  < ----------------------------------------
        }


    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };//,Video,Audio
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Video
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void onClickSkip(View view)
    {
        if(!str_user_id_getExtra.equals("")) // for skiping while in Signup
        {
            startActivity(new Intent(this, Login.class));
            finish();
        }
        else
        {
            finish();  // for skip after login
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this, Login.class));
        finish();
    }
}
