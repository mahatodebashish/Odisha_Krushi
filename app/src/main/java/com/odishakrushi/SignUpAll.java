package com.odishakrushi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
//import spencerstudios.com.bungeelib.Bungee;

public class SignUpAll extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy


    ImageView farmeroption;
    TextView txtSignUp;
    LinearLayout call,whatsapp,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_all);
        //farmeroption=findViewById(R.id.farmerradio);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
       /* getSupportActionBar().setTitle("    "+getString(R.string.Sign_Up));
        getSupportActionBar().setIcon(R.drawable.ic_close);*/
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        txtSignUp=  findViewById(R.id.txtSignUp);
        txtSignUp.setText("     "+getString(R.string.Sign_Up));
        (findViewById(R.id.img_button_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        call=findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);//ACTION_DIAL
                intent.setData(Uri.parse("tel:" + "8763865936"));//"08763865936"
                startActivity(intent);
            }
        });

        whatsapp=findViewById(R.id.whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:" + "8763865936");
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(i, "Hi !! Odisha Krushi"));
            }
        });

        email=findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:mail@odishakrushi.in")));
            }
        });
    }
    public void farmeroption(View view)
    {
        Intent intent=new Intent(this,SignUpFarmer.class);
        startActivity(intent);
    }

    public void resourcepersonoption(View view)
    {
        Intent intent=new Intent(this,SignUpExtensionOfficer.class);//SignUpResourcePerson
        startActivity(intent);
    }
    public void businessoption(View view)
    {
        Intent intent=new Intent(this,SignUpBusiness.class);
        startActivity(intent);
    }
    public void admin_manageroption(View view)
    {
        Intent intent=new Intent(this,SignUpAdminManager.class);
        startActivity(intent);
    }
    public void studentresearcheroption(View view)
    {
        Intent intent=new Intent(this,SignUpStudentResearcher.class);
        startActivity(intent);
    }

    public void guestoption(View view)
    {
        Intent intent=new Intent(this,SignUpGuest.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
//        Bungee.slideUp(this); //fire the slide up animation
    }
}
