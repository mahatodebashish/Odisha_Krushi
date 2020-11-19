package com.odishakrushi.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.odishakrushi.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;

public class FullScreenViewer extends Activity {

    String url="";
    ImageView img_back;
    private static final int STORAGE_PERMISSION_CODE = 1;
    File file;
    String dirPath, fileName;
    TextView txtDownloadStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_viewer);

        checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_CODE);



        // Find the WebView by its unique ID
        WebView w = (WebView) findViewById(R.id.webView);
        txtDownloadStatus= findViewById(R.id.txtDownloadStatus);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            url = bundle.getString("URL");
           // url= "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
            //url= "http://techslides.com/demos/sample-videos/small.mp4";
        }

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
        }
        if(url.contains(".mp4")){
           // https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4
            //Getting the current date
            Date date = new Date();
            //This method returns the time in millis
            long timeMilli = date.getTime();
            fileName = "ok_VID"+timeMilli+".mp4";
        }

        //file Creating With Folder & Fle Name
        file = new File(dirPath, fileName);


        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
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

        findViewById(R.id.img_download).setOnClickListener(new View.OnClickListener() {
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

        //For image
        WebSettings webSettings = w.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setSupportZoom(true) ;
        webSettings.setBuiltInZoomControls(true);
        w.setBackgroundColor(Color.TRANSPARENT);
        w.getSettings().setUseWideViewPort (true);
        w.getSettings().setLoadWithOverviewMode(true);

        Toast.makeText(this, "YOU CAN PINCH TO ZOOM IN/ZOOM OUT", Toast.LENGTH_SHORT).show();

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        w.setWebViewClient(new WebViewClient());
    }


    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                FullScreenViewer.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            FullScreenViewer.this,
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
                Toast.makeText(FullScreenViewer.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(FullScreenViewer.this,
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
                        Toast.makeText(FullScreenViewer.this, "DownLoad Complete File at"+dirPath+"/"+fileName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(FullScreenViewer.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
