package com.odishakrushi.Farmer_ViewQuestion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
//import android.media.session.MediaController;
import android.widget.MediaController;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;
import com.odishakrushi.utils.FullScreenViewer;
import com.odishakrushi.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import com.odishakrushi.R;

import java.io.File;
import java.util.Date;

public class AnswerDetail extends AppCompatActivity {

    LinearLayout ratingLayout;
    RatingBar ratingBar;
    MediaPlayer mp;
    TextView dropdown,question_date, questext , anstext ,ans_dt;

    ImageView imageView;
    VideoView videoView;   private MediaController ctlr;
    Button btnaudio;

    String str_dropdown="",str_questext="", str_quesdate="", str_url="", str_anstext="", str_ansdate="",str_qna_id="",str_rating="";

    //Fullscreen ad
    String url="";
    ImageView img_back;
    private static final int STORAGE_PERMISSION_CODE = 1;
    File file;
    String dirPath, fileName;
    TextView txtDownloadStatus;
    MediaController mediaControls;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_detail);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setTitle("View Question Asked- Farmer"); //Ask a
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        ratingLayout=findViewById(R.id.ratingLayout);
        ratingBar=findViewById(R.id.ratingBar);
        dropdown=findViewById(R.id.dropdown);
        question_date=findViewById(R.id.question_date);
        questext=findViewById(R.id.questext);
        anstext=findViewById(R.id.anstext);
        ans_dt=findViewById(R.id.ans_dt);
        imageView=findViewById(R.id.imageView);
        videoView=findViewById(R.id.videoView);
        btnaudio=findViewById(R.id.btnaudio);
        if (bundle != null) {
            str_dropdown = bundle.getString("DROPDOWN");dropdown.setText(str_dropdown);
            str_questext = bundle.getString("QUESTEXT");questext.setText(str_questext);
            str_quesdate = bundle.getString("QUESTION_DATE");question_date.setText(str_quesdate);
            str_url = bundle.getString("URL");
            str_anstext = bundle.getString("ANSTEXT");
            anstext.setText(str_anstext);
            str_ansdate = bundle.getString("ANS_DATE");ans_dt.setText(str_ansdate);
            str_qna_id = bundle.getString("QNA_ID");

            try {
                str_rating = bundle.getString("RATING");
                ratingBar.setRating(Float.parseFloat(str_rating));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if(str_anstext==null || str_anstext.equals("null"))
                ratingLayout.setVisibility(View.GONE);
        }
        // initialiisng for audio play
         mp=new MediaPlayer();
        try{
            mp.setDataSource(str_url);//Write your location here
            mp.prepare();


        }catch(Exception e){
            e.printStackTrace();}



        if(str_url.contains(".jpg")||str_url.contains(".png")||str_url.contains(".bmp"))
        {
            Picasso.with(this).load(str_url).into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }

        else if(str_url.contains(".mp4")||str_url.contains(".3gp")||str_url.contains(".flv"))
        {

            Uri uri = Uri.parse(str_url);
            videoView.setVideoURI(uri);
            videoView.start();
            videoView.setVisibility(View.VISIBLE);
        }

        else if(str_url.equals("mp3"))
        {
            btnaudio.setVisibility(View.VISIBLE);
            mp.start();
            btnaudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mp.stop();
                }
            });

        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // str_url= "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
                //str_url= "http://techslides.com/demos/sample-videos/small.mp4";
                    str_url=Utils.nullCheck(str_url);
                    if(!str_url.equals("")) {
                        Intent intent1 = new Intent(AnswerDetail.this, FullScreenViewer.class);
                        intent1.putExtra("URL", str_url);
                        startActivity(intent1);
                    }

              /*  str_url= "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
                //str_url= "http://techslides.com/demos/sample-videos/small.mp4";
                if(!Utils.nullCheck(str_url).equals(""))
                showPopUp(str_url);*/
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {


             /*   Alerter.create(AnswerDetail.this)
                        .setTitle("Giving Rating...")
                        .show();*/

               Toast.makeText(AnswerDetail.this, "Giving Rating...", Toast.LENGTH_SHORT).show();

              //  AndroidNetworking.post("http://demo.ratnatechnology.co.in/farmerapp/api/qna/setrating") Doubt
                AndroidNetworking.post(Config.baseUrl+"qna/setrating")
                        .addBodyParameter("qna_id", str_qna_id)
                        .addBodyParameter("rating",String.valueOf(rating))
                        .setTag("test")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsString(new StringRequestListener() {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String message=jsonObject.getString("message");
                                    Toast.makeText(AnswerDetail.this, message, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Toast.makeText(AnswerDetail.this, "Network Error", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showPopUp(final String url) {


        checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_CODE);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.layout_fullscreen_popup, null);
        WebView w = (WebView) dialogLayout.findViewById(R.id.webView);
        ImageView imgView =dialogLayout.findViewById(R.id.imgView);
       // VideoView video_view =dialogLayout.findViewById(R.id.video_view);
        txtDownloadStatus= dialogLayout.findViewById(R.id.txtDownloadStatus);

       /* if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(AnswerDetail.this);
            mediaControls.setAnchorView(video_view);
        }
        // set the media controller for video view
        video_view.setMediaController(mediaControls);

        // set the uri for the video view
      video_view.setVideoPath(url);

        // start a video
       video_view.start();

        // implement on completion listener on video view
        video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "Video Completed...!!!", Toast.LENGTH_LONG).show(); // display a toast when an video is completed
            }
        });
        video_view.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(getApplicationContext(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show(); // display a toast when an error is occured while playing an video
                return false;
            }
        });
*/
        // Initialization Of DownLoad Button
        AndroidNetworking.initialize(getApplicationContext());

        //Folder Creating Into Phone Storage
        dirPath = Environment.getExternalStorageDirectory() + "/Odisha Krushi";


        if(url.contains(".jpg")||url.contains(".png")){
            //Getting the current date
            Date date = new Date();
            //This method returns the time in millis
            long timeMilli = date.getTime();
            fileName = "ok_IMG"+timeMilli+".png";

            imgView.setVisibility(View.VISIBLE);
            w.setVisibility(View.GONE);
            Picasso.with(this).load(url).into(imgView);
        }
        if(url.contains(".mp4")){
            imgView.setVisibility(View.GONE);
            //video_view.setVisibility(View.VISIBLE);
            w.setVisibility(View.VISIBLE);
            // https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4
            //Getting the current date
            Date date = new Date();
            //This method returns the time in millis
            long timeMilli = date.getTime();
            fileName = "ok_VID"+timeMilli+".mp4";
        }

        //file Creating With Folder & Fle Name
        file = new File(dirPath, fileName);


        dialogLayout.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * All the requests with the given tag will be cancelled , even if any
                 * percent threshold is set , it will be cancelled forcefully
                 */
                AndroidNetworking.forceCancel("downloadFile");
                txtDownloadStatus.setVisibility(View.GONE);
                onBackPressed();
            }
        });

        dialogLayout.findViewById(R.id.img_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImage(url);

            }
        });

        // loading http://www.google.com url in the the WebView.
        w.loadUrl(url);

        // this will enable the javascipt.
        w.getSettings().setJavaScriptEnabled(true);
        w.getSettings().setSupportZoom(true);
        w.getSettings().setBuiltInZoomControls(true);
        w.getSettings().setDisplayZoomControls(true);

       // Toast.makeText(this, "YOU CAN PINCH TO ZOOM IN/ZOOM OUT", Toast.LENGTH_SHORT).show();

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        w.setWebViewClient(new WebViewClient());

       /* ImageView goProDialogImage= dialogLayout.findViewById(R.id.goProDialogImage);
        goProDialogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ImageView closeAd= dialogLayout.findViewById(R.id.closeAd);
        closeAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });
       */


        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();

        Display display =((WindowManager)getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height=display.getHeight();

        Log.v("alertpop", "width="+width+" "+"height="+height);
        dialog.getWindow().setLayout(width,height);

        // start the animation
        //goProDialogImage.startAnimation(animFadein);

    }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                AnswerDetail.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            AnswerDetail.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
         /*   Toast
                    .makeText(MainActivity.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();*/
        }
    }

    // This function is called when user accept or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AnswerDetail.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(AnswerDetail.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }




    public void downloadImage(String url){

        AndroidNetworking.download(url, dirPath, fileName)
                .setTag("downloadFile")
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        // do anything with progress
                        txtDownloadStatus.setVisibility(View.VISIBLE);
                        int percentage=(int)((bytesDownloaded*100)/totalBytes);
                        txtDownloadStatus.setText("Downloading "+percentage+"% completed...");

                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        txtDownloadStatus.setVisibility(View.GONE);
                        Toast.makeText(AnswerDetail.this, "DownLoad Complete File at"+dirPath+"/"+fileName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(AnswerDetail.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
