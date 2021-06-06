package com.odishakrushi.ExtensionOff_ViewQuestion;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.ModelClass.EventModel;
import com.odishakrushi.NetworkRelatedClass.NetworkCall;
import com.odishakrushi.R;

public class ViewQuestionDetail_ExtOff extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    Button buttonStop,buttonStart ;
    MediaPlayer mediaplayer;
    LinearLayout layout_audio_get;

    int SPLASH_TIME_OUT=3;
    ImageView image;
    String fileextension="",url="";

    private VideoView myVideoView;
    private int position = 0;

    private MediaController mediaControls;

    String questxt="";
    static final int REQUEST_VIDEO_CAPTURE = 1;
    public static final int ACTIVITY_RECORD_SOUND = 0;
    private final int requestCode = 20;

    String urlRouteList;  //<--------------------------------------------
    EditText edittext;
    RadioGroup rgMyStory;
    String str_mystory_type="";
    Spinner subgroup;
    String strsubgroup="";
    int my_story_id=0;  // <-----------------------------------------------
    String qna_id="";
    StringBuilder encodedImage;

    private static final int CAMERA_REQUEST = 1888;



    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";
    String mystory="";  //<------------------------------------------------------

    ProgressDialog progressDialog;

    //Added for upload section - image video audio
    String user_id="";
    EditText editqid_mystoryid;  // <--------------------------------------------------
    private VideoView videoView;
    private EditText nameEditText;
    private EditText ageEditText;
    private ImageView imageView;
    private Button uploadButton;
    private TextView responseTextView,txtAudioPath;

    private String imagefilePath="",videofilePath="",audiofilePath="";
    private static final int PICK_PHOTO = 1958;
    private static final int PICK_VIDEO= 1993;
    private static final int PICK_AUDIO= 1994;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventModel event) throws ClassNotFoundException {
        if (event.isTagMatchWith("response")) {
            String responseMessage = event.getMessage();
            responseTextView.setText(responseMessage);
            progressDialog.hide();
        }
    }
    TextView dropdown,datetime,questext;
    String str_qna_id,string_user_id,str_dropdown,str_questext,str_ques_datetime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_view_question_detail__ext_off);

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

        editqid_mystoryid=(EditText) findViewById(R.id.editqid);  //   <--------------------------------------------------
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        ageEditText = (EditText) findViewById(R.id.ageEditText);
        imageView = (ImageView) findViewById(R.id.imageView);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        responseTextView = (TextView) findViewById(R.id.responseTextView);
        videoView=(VideoView) findViewById(R.id.videoView);
        txtAudioPath=(TextView)findViewById(R.id.txtAudioPath);
        subgroup=findViewById(R.id.subgroup);
        edittext = findViewById(R.id.edittext);

        dropdown=findViewById(R.id.dropdown);
        datetime=findViewById(R.id.datetime);
        questext=findViewById(R.id.questext);

        myVideoView=findViewById(R.id.video_view);
        image=findViewById(R.id.image);

        buttonStart = (Button)findViewById(R.id.button1);
        buttonStop = (Button)findViewById(R.id.button2);
        layout_audio_get=findViewById(R.id.layout_audio_get);

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null)
        {
            str_qna_id = bundle.getString("QNA_ID");
            Log.d("QNA_ID",str_qna_id);
           /* string_user_id = bundle.getString("USER_ID");
            Log.d("USER_ID",string_user_id);*/
            str_dropdown = bundle.getString("DROPDOWN");
            str_questext = bundle.getString("QUESTEXT");
            str_ques_datetime=bundle.getString("QUESTION_DATE");
            url = bundle.getString("URL");
            dropdown.setText("Tag: "+str_dropdown);
            datetime.setText(str_ques_datetime);
            questext.setText("Ques: "+str_questext);
        }

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

        final Button record_audio=(Button)findViewById(R.id.record_audio);
        record_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, ACTIVITY_RECORD_SOUND);

            }
        });


        //getting image or video-------------------------------------------------------------------------
        //extracting file extension from a string
        fileextension=url.substring(url.lastIndexOf(".") + 1);
        Log.d("fileextension",fileextension);


        if(fileextension.equals("jpg")||fileextension.equals("png")||fileextension.equals("jpeg")||fileextension.equals("bmp"))
        {
            image.setVisibility(View.VISIBLE);
            Picasso.with(this).load(url).into(image);
        }
        else if(fileextension.equals("mp3"))
        {
            Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
            layout_audio_get.setVisibility(View.VISIBLE);

            mediaplayer = new MediaPlayer();
            mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            buttonStart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    try {

                        mediaplayer.setDataSource(url);
                        mediaplayer.prepare();


                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    mediaplayer.start();


                }
            });

            buttonStop.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub


                    mediaplayer.stop();


                }
            });
        }
        else if(fileextension.equals("avi")||fileextension.equals("mp4")||fileextension.equals("wmv")
                ||fileextension.equals("flv")||fileextension.equals("mov")||fileextension.equals("3gp"))
        {

            myVideoView.setVisibility(View.VISIBLE);
            if (mediaControls == null) {
                mediaControls = new MediaController(ViewQuestionDetail_ExtOff.this);
            }




                   /* // Create a progressbar
                    progressDialog = new ProgressDialog(View_AskedQuestion_Detail.this);
                    // Set progressbar title
                   // progressDialog.setTitle(" Android Video View Example");
                    // Set progressbar message
                    progressDialog.setMessage("Loading...");

                    progressDialog.setCancelable(false);
                    // Show progressbar
                    progressDialog.show();*/

            try {
                myVideoView.setMediaController(mediaControls);
                //myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kitkat));
                if(url.equals("")||url.equals(null))
                {
                    Toast.makeText(this, "No Url found", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Uri uri=Uri.parse(url);
                    myVideoView.setVideoURI(uri);
                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            myVideoView.requestFocus();
            myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    // progressDialog.dismiss();
                    myVideoView.seekTo(position);
                    if (position == 0) {
                        myVideoView.start();
                    } else {
                        myVideoView.pause();
                    }
                }
            });

        } // end-else

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
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

    public void addAudio(View view) {
        responseTextView.setText("");
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_AUDIO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {
            Uri imageUri = data.getData();
            imagefilePath = getPath(imageUri);
            // Log.d("imagefilePath",imagefilePath);
            imageView.setImageURI(imageUri);
            imageView.setVisibility(View.VISIBLE);
        }
        else if(resultCode==RESULT_OK && requestCode==PICK_VIDEO)
        {
            Uri videoUri = data.getData();
            videofilePath = getVideoPath(videoUri);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
        else if(resultCode==RESULT_OK && requestCode==PICK_AUDIO)
        {
            Uri audioUri = data.getData();
            audiofilePath = getAudioPath(audioUri);
            txtAudioPath.setVisibility(View.VISIBLE);
            txtAudioPath.setText(audiofilePath);
        }

        else if(this.requestCode == requestCode && resultCode == RESULT_OK)
        {

            imageView.setVisibility(View.VISIBLE);
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            Uri cameraUri= getImageUri(this,bitmap);
            imagefilePath = getPath(cameraUri);
            Log.d("imagefilePath",imagefilePath);
        }
        else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.setVisibility(View.VISIBLE);
            videoView.start();
            videofilePath = getVideoPath(videoUri);
        }

        else if (requestCode == ACTIVITY_RECORD_SOUND && resultCode == RESULT_OK) {
            Uri audioUri = data.getData();
            audiofilePath = getAudioPath(audioUri);
            txtAudioPath.setVisibility(View.VISIBLE);
            txtAudioPath.setText(audiofilePath);
        }

    }

    public void uploadIButtonClicked(View view)
    {

        String name = nameEditText.getText().toString();
        int age = Integer.parseInt(ageEditText.getText().toString());
        //NetworkCall.fileUpload(filePath, new ImageSenderInfo(name,age));

        //user_id=edituserid.getText().toString().trim();
        if(imagefilePath.equals(""))
        {
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
        }
        else
        {
                                                                   // <------------------------------------
                NetworkCall.fileUpload(imagefilePath,str_qna_id,"0");

            progressDialog= new ProgressDialog(this);
            progressDialog.setMessage("Uploading Image");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

    }

    public void uploadVButtonClicked(View view)
    {

        String name = nameEditText.getText().toString();
        int age = Integer.parseInt(ageEditText.getText().toString());
        //NetworkCall.fileUpload(filePath, new ImageSenderInfo(name,age));

        // user_id=edituserid.getText().toString().trim();

        if(videofilePath.equals(""))
        {
            Toast.makeText(this, "Select Video", Toast.LENGTH_SHORT).show();
        }
        else
        {
                                                           // <------------------------------------------------
                NetworkCall.fileUpload(videofilePath,str_qna_id,"0");

            progressDialog= new ProgressDialog(this);
            progressDialog.setMessage("Uploading Video");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

    }

    public void uploadAButtonClicked(View view)
    {

        String name = nameEditText.getText().toString();
        int age = Integer.parseInt(ageEditText.getText().toString());
        //NetworkCall.fileUpload(filePath, new ImageSenderInfo(name,age));

        //user_id=edituserid.getText().toString().trim();
        if(audiofilePath.equals(""))
        {
            Toast.makeText(this, "Select Audio", Toast.LENGTH_SHORT).show();
        }
        else
        {
                                                          // <------------------------------------------------
                NetworkCall.fileUpload(audiofilePath,str_qna_id,"0");

            progressDialog= new ProgressDialog(this);
            progressDialog.setMessage("Uploading Audio");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void onClickSubmit(View view)
    {

        questxt=edittext.getText().toString();

        Log.d("questxt",questxt);
        if(questxt.equals(""))
        {
            Toast.makeText(ViewQuestionDetail_ExtOff.this,"Nothing typed. Please type something",Toast.LENGTH_LONG).show();
        }
        else
           new AsyncAnswertoQ().execute();

    }



    public void onClickFarmerPlusOpenforum(View view)
    {

      //  AndroidNetworking.post("http://demo.ratnatechnology.co.in/farmerapp/api/qna/open_forum_question") Doubt
       AndroidNetworking.post(Config.baseUrl+"qna/open_forum_question")

                .addBodyParameter("user_id", str_user_id)
                .addBodyParameter("qna_id", str_qna_id)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                      /*  Alerter.create(ViewQuestionDetail_ExtOff.this)
                                .setTitle(response)
                                .show();*/

                        Toast.makeText(ViewQuestionDetail_ExtOff.this, response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Alerter.create(ViewQuestionDetail_ExtOff.this)
                                .setTitle(getString(R.string.Network_Error))
                                .show();
                    }
                } );
    }



    private  class AsyncAnswertoQ extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null;
        boolean status=false;
        String message="";
        @Override
        protected void onPreExecute() {



            pDialog = new ProgressDialog(ViewQuestionDetail_ExtOff.this);
            pDialog.setMessage("Answering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void  doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",str_user_id));
            cred.add(new BasicNameValuePair("qna_id",str_qna_id));
            cred.add(new BasicNameValuePair("anstext",questxt));


            urlRouteList= Config.giveAnswertoFarmer;

            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);
                Log.v(" ", "Response is " + route_response);

                success = route_response;

                JSONObject jsonObject = new JSONObject(success);
                status =jsonObject.getBoolean("status");
                qna_id = jsonObject.getString("qna_id");
                Log.d("qnaId", String.valueOf(qna_id));
                message=jsonObject.getString("message");

                Alerter.create(ViewQuestionDetail_ExtOff.this)
                        .setTitle(message)
                        .show();


            } catch (Exception e)

            {
                Log.v("ONMESSAGE", e.toString());
                Alerter.create(ViewQuestionDetail_ExtOff.this)
                        .setTitle(message)
                        .show();

            }


            return null;

        }

        protected void onPostExecute(Void result) {
            pDialog.dismiss();

           /* if(status)
            {
               *//* Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                snackbar.show();*//*

                Alerter.create(ViewQuestionDetail_ExtOff.this)
                        .setTitle(message)
                        .show();
                new Handler().postDelayed(new Runnable() {

            *//*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             *//*

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over


                        finish();
                    }
                }, SPLASH_TIME_OUT);
                finish();


            }
            else
            {
               *//* Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                snackbar.show();*//*
                Alerter.create(ViewQuestionDetail_ExtOff.this)
                        .setTitle(message)
                        .show();

                new Handler().postDelayed(new Runnable() {

            *//*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             *//*

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over



                        finish();
                    }
                }, SPLASH_TIME_OUT);

            */

            }


        }



    }


