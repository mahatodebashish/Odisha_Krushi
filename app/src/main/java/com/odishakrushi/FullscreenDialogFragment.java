package com.odishakrushi;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;

import com.odishakrushi.AskQAgriculture.OnDataPass;

public class FullscreenDialogFragment extends DialogFragment {

    private Chronometer chronometer;
   // private ImageButton playstop;
    private boolean isStart;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    File mAudioFile;
    boolean isRecording = false;

    ToggleButton recordBtn;
    Button playBtn;

    String audiofilePath="";

    OnDataPass dataPasser;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dataPasser = (OnDataPass) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.full_screen_dialog, container,
                false);
        (rootView.findViewById(R.id.button_close)).setOnClickListener(new
          View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  dismiss();
              }
          });

        (rootView.findViewById(R.id.ticksave)).setOnClickListener(new
          View.OnClickListener() {
              @Override
              public void onClick(View v)
              {
                  //audiofilePath="/storage/emulated/0/media/audio/ringtones/Backbone.mp3";
                //  audiofilePath= "/storage/emulated/0/Android/data/com.odishakrushi/files/Ringtones/OdishaKrushi515489084.mp3";
                  Toast.makeText(getActivity(), audiofilePath, Toast.LENGTH_SHORT).show();
                 if (!audiofilePath.equals(""))
                 {

                     dataPasser.onDataPass(audiofilePath);
                     dismiss();
                 }
              }
          });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // getActivity().setTitle("Ask Question");
        chronometer = getView().findViewById(R.id.chronometer);
        recordBtn=getView().findViewById(R.id.recordBtn);
      //  playstop= getView().findViewById(R.id.playstop);
        playBtn=getView().findViewById(R.id.playBtn);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometerChanged) {
                chronometer = chronometerChanged;
            }
        });

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlaying();
            }
        });




        try {
            mAudioFile = createAudioFile(getActivity(), "OdishaKrushi");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static File createAudioFile(Context context, String audioName) throws IOException {
      /*  File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES);
        File audio = File.createTempFile(
                audioName, //  prefix
                ".mp3",         // suffix
                storageDir     //  directory
        );
*/

      File storageDir=context.getExternalFilesDir("OdishaKrushi");
      File audio=File.createTempFile(audioName,".mp3",storageDir);
        return audio;
    }
    public void startRecording() {
        requestAudioPermissions();
    }

    private void recordAudio() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            audiofilePath=mAudioFile.getAbsolutePath();
            mRecorder.setOutputFile(audiofilePath);

            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            //starting chronometer
            if(isStart){
                chronometer.stop();
                isStart = false;
              //  playstop.setImageResource(R.drawable.ic_start);


            }else{
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                isStart = true;
             //   (playstop).setImageResource(R.drawable.ic_stop);

            }
        }

        if (!isRecording) {
            try {
                mRecorder.prepare();
                mRecorder.start();
                isRecording = true;
            } catch (IOException e) {
                Log.e("Audio", "prepare() failed");
            }
        } else if (isRecording) {
            isRecording = false;
            //stoping chronometer
            if(isStart){
                chronometer.stop();
                isStart = false;
             //   playstop.setImageResource(R.drawable.ic_start);


            }else{
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                isStart = true;
              //  (playstop).setImageResource(R.drawable.ic_stop);

            }
            stopRecording();
        }
    }

    public void stopRecording() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(audiofilePath);//mAudioFile.getAbsolutePath()
            Log.d("path",audiofilePath);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("Audio", "prepare() failed");
        }
    }

    public void stopPlaying() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onDestroy() {

        if (mPlayer != null) {
            stopPlaying();
        }

        if (mRecorder != null) {
            stopRecording();
        }

        super.onDestroy();
    }

    //Requesting run-time permissions

    //Create placeholder for user's consent to record_audio permission.
    //This will be used in handling callback
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;

    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(getActivity(), "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now
            recordAudio();
        }
    }

    //Handling callback
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    recordAudio();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


}

