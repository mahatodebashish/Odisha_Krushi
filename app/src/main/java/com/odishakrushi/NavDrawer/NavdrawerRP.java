package com.odishakrushi.NavDrawer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.odishakrushi.Apptutorial.AppTutorial;
import com.odishakrushi.ChangePassword;
import com.odishakrushi.ExtensionOffQuestionAnswer.ExtOffQuestionAnswerFragment;
import com.odishakrushi.Message.MessagingExtOff;
import com.odishakrushi.Survey.SurveyExtOff;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.ViewProfile.ViewProfileExtensionOff;
import com.odishakrushi.utils.Utils;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.ChangeLanguage;
import com.odishakrushi.ExtensionOff_ViewQuestion.ViewQuestionList_ExtOff;
import com.odishakrushi.Farmer_ViewQuestion.ViewAnswerByExtOff;
import com.odishakrushi.Fragments.Contact;
import com.odishakrushi.Fragments.KnowledgeBank;
import com.odishakrushi.Login;
import com.odishakrushi.Message.GroupMessaging;
import com.odishakrushi.Message.ViewMessages;
import com.odishakrushi.OpenForum.OpenForumList;
import com.odishakrushi.ProfilePic.ProfilePic;
import com.odishakrushi.R;
import com.odishakrushi.SignUpExtensionOfficer;
import com.odishakrushi.utils.CircleTransform;

public class NavdrawerRP extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    String str_phone="";
    TextView txt_mobile;
    String img_url="";
    TextView keyname;
    String User="";
    String largeBannerUrl="";
    ImageView imgeditProfile,imageView;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String logged_in_name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawer_rp);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        /* RECEIVING PART */
        // Register the local broadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(mLBannerUrlReceiver,
                new IntentFilter("large-banner-url"));
        loadAd();

        //get user name

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        logged_in_name=sharedpreferences.getString("LOGIN_NAME", "");
        str_phone=sharedpreferences.getString("PHONE", "");
        editor.commit(); // commit changes


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(drawer.isDrawerOpen(GravityCompat.START))) {
                    drawer.openDrawer(GravityCompat.START);


                    /**
                     * getting img_url
                     */

                    sharedpreferences = getSharedPreferences(mypreference,
                            Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor2 = sharedpreferences.edit();
                    img_url=sharedpreferences.getString("shared_pref_img_url", "");
                    editor2.commit(); // commit changes
                    if (!img_url.equals(""))
                        Picasso.with(getApplicationContext()).load(img_url).transform(new CircleTransform()).into(imageView);
                }
            }
        });

       /* NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header=navigationView.getHeaderView(0);
        //View view=navigationView.inflateHeaderView(R.layout.nav_header_nav_drawer);
        txt_mobile= header.findViewById(R.id.txt_mobile);
        keyname = header.findViewById(R.id.keyname);
        imgeditProfile=header.findViewById(R.id.imgeditProfile);
        imgeditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(NavdrawerRP.this,SignUpExtensionOfficer.class));//EditProfileExtoff
                Intent intent =new Intent(NavdrawerRP.this,SignUpExtensionOfficer.class);
                intent.putExtra("CHECK_VALUE_FOR_EDIT_PROFILE_EXTOFF","getname_and_changetitle");
                startActivity(intent);
            }
        });
        imageView=header.findViewById(R.id.imageView);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        if(bundle != null){
            User = bundle.getString("keyName");
            img_url = bundle.getString("PROF_IMG");
            sharedpreferences = getSharedPreferences(mypreference,
                    Context.MODE_MULTI_PROCESS);
            SharedPreferences.Editor editor2 = sharedpreferences.edit();
            editor2.putString("shared_pref_img_url", img_url);
            editor2.commit();
            // keyname.setText(User);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);

                Intent intent1=new Intent(NavdrawerRP.this,ProfilePic.class);
                intent1.putExtra("USER_ID","");
                startActivity(intent1);
            }
        });

        keyname.setText(logged_in_name);
        txt_mobile.setText(str_phone);

        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.question_answer);//home
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
*/
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, getString(R.string.press_two_times), Toast.LENGTH_SHORT).show();

        /*       SnackBar snackbar = Snackbar
                .make(findViewById(android.R.id.content), getString(R.string.press_two_times), Snackbar.LENGTH_LONG);

        snackbar.show();*/

       /* Alerter.create(NavdrawerRP.this)
                .setTitle(getString(R.string.press_two_times))
                .show();*/

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                //Logout session for user id
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove("USER_ID"); // will delete key email
                editor.remove("EXTOFF_TRACKER");  // will delete key tracker
                editor.commit(); // commit changes
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navdrawer_r, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.appTutorials)
        {
            startActivity(new Intent(NavdrawerRP.this, AppTutorial.class));

        }

        if(id==R.id.editProfile)
        {
            /*Intent intent =new Intent(NavdrawerRP.this,SignUpExtensionOfficer.class);
            intent.putExtra("CHECK_VALUE_FOR_EDIT_PROFILE_EXTOFF","getname_and_changetitle");
            startActivity(intent);*/

            Intent intent=new Intent(this,ViewProfileExtensionOff.class);
            startActivity(intent);
        }
        else if(id==R.id.changePassword)
        {
            startActivity(new Intent(NavdrawerRP.this, ChangePassword.class));//EditProfileFarmer.class));

        }

        else if(id==R.id.changelanguage)
        {
            Intent intent=new Intent(this,ChangeLanguage.class);
            intent.putExtra("Value","5");
            startActivity(intent);
            finish();
        }
        else
        if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //Uncomment the below code to Set the message and title from the strings.xml file
            //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

            //Setting message manually and performing action on button click
            builder.setMessage(getString(R.string.logoutmsg))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //Logout session for user id
                            sharedpreferences = getSharedPreferences(mypreference,
                                    Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.remove("USER_ID"); // will delete key email
                            editor.remove("EXTOFF_TRACKER");  // will delete key tracker
                            editor.remove("LOGGED_IN_AS");
                            editor.remove("shared_pref_img_url");
                            editor.commit(); // commit changes

                            startActivity(new Intent(NavdrawerRP.this,Login.class));
                            finish();
                            // android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    })
                    .setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });

            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle(getString(R.string.Log_Out));
            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {

            case R.id.question_answer:
                /*Intent intent=new Intent(this, ViewQuestionList_ExtOff.class);
                startActivity(intent);*/

              fragment = new ExtOffQuestionAnswerFragment();

                break;

          /*  case R.id.qna:
              *//*  Intent intent=new Intent(this, ViewQuestionList_ExtOff.class);
                startActivity(intent);*//*

              //  fragment = new ViewQuestionList_ExtOff();

                break;
            case R.id.ans_given_to_farmer:
              *//*  Intent intent2=new Intent(this, ViewAnswerByExtOff.class);
                intent2.putExtra("USER_IND","resource");
                startActivity(intent2);*//*

               // fragment = new ViewAnswerByExtOff();
                break;*/

            case R.id.message:
                fragment=new MessagingExtOff();
                break;

            /*case R.id.viewmessage:
                fragment=new ViewMessages();
                break;*/

            case R.id.survey:
                fragment=new SurveyExtOff();
                break;
            case R.id.openforum:
                fragment=new OpenForumList();
                break;
            case R.id.contact:
                fragment=new Contact();
                break;

            case R.id.knowledgebank:
                fragment = new KnowledgeBank();
                break;

            case R.id.logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //Uncomment the below code to Set the message and title from the strings.xml file
                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage(getString(R.string.logoutmsg))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                //Logout session for user id
                                sharedpreferences = getSharedPreferences(mypreference,
                                        Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.remove("USER_ID"); // will delete key email
                                editor.remove("EXTOFF_TRACKER");  // will delete key tracker
                                editor.remove("LOGGED_IN_AS");
                                editor.remove("shared_pref_img_url");
                                editor.commit(); // commit changes

                                startActivity(new Intent(NavdrawerRP.this,Login.class));
                                finish();
                                // android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle(getString(R.string.Log_Out));
                alert.show();

                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
    /*    AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.go_pro_dialog_layout, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();*/
    }


    public void loadAd(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        /*builder.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!(largeBannerUrl.equals(""))){
                    Toast.makeText(NavDrawer.this, largeBannerUrl, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(largeBannerUrl));
                    startActivity(intent);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });*/


        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.go_pro_dialog_layout, null);
        ImageView goProDialogImage= dialogLayout.findViewById(R.id.goProDialogImage);
        Button btnknowMore= dialogLayout.findViewById(R.id.btnknowMore);
        Button btnCall= dialogLayout.findViewById(R.id.btnCall);
        Button btnWhatsapp= dialogLayout.findViewById(R.id.btnWhatsapp);
        Button btnClose= dialogLayout.findViewById(R.id.btnClose);

        btnknowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(largeBannerUrl.equals(""))){

                    Toast.makeText(NavdrawerRP.this, largeBannerUrl, Toast.LENGTH_SHORT).show();

                    if (!largeBannerUrl.startsWith("https://") && !largeBannerUrl.startsWith("http://")){
                        largeBannerUrl = "http://" + largeBannerUrl;
                    }
                    Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(largeBannerUrl));
                    startActivity(openUrlIntent);

                }
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);//ACTION_DIAL
                intent.setData(Uri.parse("tel:" + "8763865936"));//"08763865936"
                startActivity(intent);
            }
        });

        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:" + "8763865936");
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(i, "Hi !! Odisha Krushi"));
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ImageView closeAd= dialogLayout.findViewById(R.id.closeAd);
        closeAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });
        /**
         * @desc: Calling getAd Api for loading Full-Screen Ad..
         */
        String str_zone_id=Preferences.getZone(NavdrawerRP.this);
        String str_group_id=Preferences.getGroupID(NavdrawerRP.this);
        Utils.getAd(NavdrawerRP.this,str_zone_id,str_group_id,goProDialogImage);


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

        goProDialogImage.setVisibility(View.VISIBLE);
        //closeAd.setVisibility(View.VISIBLE);
    }
    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mLBannerUrlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            largeBannerUrl = intent.getStringExtra("large_url");

        }
    };


}
