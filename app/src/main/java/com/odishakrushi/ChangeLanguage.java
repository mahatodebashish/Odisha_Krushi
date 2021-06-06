package com.odishakrushi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pixplicity.easyprefs.library.Prefs;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.NavDrawer.*;
//import spencerstudios.com.bungeelib.Bungee;

public class ChangeLanguage extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    RadioGroup radioChangeLanguage;
    String str_language="",langdata="";
    int radioButtonID;

    String Value="";
    Intent intent;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        langdata = Prefs.getString("language", "");

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("LOGGED_IN_AS", "");
        editor.commit(); // commit changes


        Intent i = getIntent();
        Value = i.getStringExtra("Value");


        if ( Value==null && (langdata.equals("en") || langdata.equals("or"))) {

            Intent intent = new Intent(ChangeLanguage.this, WelcomeScreen.class);
            startActivity(intent);
            finish();

        }


        //-------------------EasyPrefs---------------------
            langdata = Prefs.getString("language", "");
            if (langdata.equals("en") || langdata.equals("or")) {
                // Toast.makeText(this," Language -"+ langdata, Toast.LENGTH_SHORT).show();
                LanguageChange.changeLanguage(this, langdata);
                setContentView(R.layout.activity_change_language);
            } else {
                setContentView(R.layout.activity_change_language);
                // save_load();
            }

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.backarrow);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {
                        switch (Value) {
                            case "1":
                                intent = new Intent(ChangeLanguage.this, NavDrawer.class);
                                break;
                            case "2":
                                intent = new Intent(ChangeLanguage.this, NavDrawerAdminManager.class);
                                break;
                            case "3":
                                intent = new Intent(ChangeLanguage.this, NavDrawerBusiness.class);
                                break;
                            case "4":
                                intent = new Intent(ChangeLanguage.this, NavDrawerGuest.class);
                                break;
                            case "5":
                                intent = new Intent(ChangeLanguage.this, NavdrawerRP.class);
                                break;
                            case "6":
                                intent = new Intent(ChangeLanguage.this, NavDrawerStudentR.class);
                                break;
                        }
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        onBackPressed();
                    }
                }
            });


            radioChangeLanguage = findViewById(R.id.radioChangeLanguage);

            radioChangeLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    if (null != rb && checkedId > -1) {
                        str_language = rb.getText().toString();
                        radioButtonID = radioChangeLanguage.getCheckedRadioButtonId();
                        //Toast.makeText(ChangeLanguage.this,String.valueOf(radioButtonID) , Toast.LENGTH_SHORT).show();

                        OnSave();

                    }

                }
            });




  }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        try {
            switch (Value)
            {
                case "1":
                    intent=new Intent(ChangeLanguage.this, NavDrawer.class);
                    break;
                case "2":
                    intent=new Intent(ChangeLanguage.this, NavDrawerAdminManager.class);
                    break;
                case "3":
                    intent=new Intent(ChangeLanguage.this, NavDrawerBusiness.class);
                    break;
                case "4":
                    intent=new Intent(ChangeLanguage.this, NavDrawerGuest.class);
                    break;
                case "5":
                    intent=new Intent(ChangeLanguage.this, NavdrawerRP.class);
                    break;
                case "6":
                    intent=new Intent(ChangeLanguage.this, NavDrawerStudentR.class);
                    break;
            }

            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void save_load() {
        if(radioButtonID%2==1)
        {

            Prefs.putString("language", "or");//-------------------EasyPrefs---------------------
            LanguageChange.changeLanguage(this,"or");
            setContentView(R.layout.activity_change_language);

            /*finish();
            startActivity(getIntent());*/


            try{
                Intent i=new Intent();
                switch (Value)
                {
                    case "1":
                        i=new Intent(ChangeLanguage.this, NavDrawer.class);
                        break;
                    case "2":
                        i=new Intent(ChangeLanguage.this, NavDrawerAdminManager.class);
                        break;
                    case "3":
                        i=new Intent(ChangeLanguage.this, NavDrawerBusiness.class);
                        break;
                    case "4":
                        i=new Intent(ChangeLanguage.this, NavDrawerGuest.class);
                        break;
                    case "5":
                        i=new Intent(ChangeLanguage.this, NavdrawerRP.class);
                        break;
                    case "6":
                        i=new Intent(ChangeLanguage.this, NavDrawerStudentR.class);
                        break;
                }


                // set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

                finish();
            }
            catch (Exception e){
                Intent i=new Intent(ChangeLanguage.this,WelcomeScreen.class);
                i.putExtra("FROM_CHANGELANG_ACTIVITY_TO_WELCOMESCREEN","1");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
            // ---------------------

        }
        else if(radioButtonID%2==0)
        {
            Prefs.putString("language", "en");//-------------------EasyPrefs---------------------
            LanguageChange.changeLanguage(this,"en");
            setContentView(R.layout.activity_change_language);

            /*finish();
            startActivity(getIntent());
*/
            try {
                Intent i = new Intent();
                switch (Value) {
                    case "1":
                        i = new Intent(ChangeLanguage.this, NavDrawer.class);
                        break;
                    case "2":
                        i = new Intent(ChangeLanguage.this, NavDrawerAdminManager.class);
                        break;
                    case "3":
                        i = new Intent(ChangeLanguage.this, NavDrawerBusiness.class);
                        break;
                    case "4":
                        i = new Intent(ChangeLanguage.this, NavDrawerGuest.class);
                        break;
                    case "5":
                        i = new Intent(ChangeLanguage.this, NavdrawerRP.class);
                        break;
                    case "6":
                        i = new Intent(ChangeLanguage.this, NavDrawerStudentR.class);
                        break;
                }


                // set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);


                finish();
            }catch (Exception e){
                Intent i=new Intent(ChangeLanguage.this,WelcomeScreen.class);
                i.putExtra("FROM_CHANGELANG_ACTIVITY_TO_WELCOMESCREEN","1");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        }
    }

    public void OnSave()
    {
        if(!str_language.equals(""))
        save_load();
    }
}
