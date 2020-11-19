package com.odishakrushi.Farmer_ViewQuestion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.R;

public class View_AskedQuestion_Detail extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    Button buttonStop,buttonStart ;
    MediaPlayer mediaplayer;
    LinearLayout layout_audio_get;


    String dropdown="",questext="",question_date="",url="";
    String fileextension="";

    private VideoView myVideoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;

    ImageView image;
    TextView txtquestext,txtquesdate,txtdropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__asked_question__detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("View Question Asked- Farmer"); //Ask a
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            dropdown = bundle.getString("DROPDOWN");
            questext = bundle.getString("QUESTEXT");
            question_date = bundle.getString("QUESTION_DATE");
            url = bundle.getString("URL");

        }

        txtquestext=findViewById(R.id.questext);
        txtquesdate=findViewById(R.id.quesdate);
        txtdropdown=findViewById(R.id.dropdown);

        image=findViewById(R.id.image);
        // Find your VideoView in your video_main.xml layout
        myVideoView = (VideoView) findViewById(R.id.video_view);
        buttonStart = (Button)findViewById(R.id.button1);
        buttonStop = (Button)findViewById(R.id.button2);
        layout_audio_get=findViewById(R.id.layout_audio_get);

        txtquestext.setText(questext);
        txtquesdate.setText("Date: "+question_date);
        txtdropdown.setText("Tag: "+dropdown);
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
                        mediaControls = new MediaController(View_AskedQuestion_Detail.this);
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


}
