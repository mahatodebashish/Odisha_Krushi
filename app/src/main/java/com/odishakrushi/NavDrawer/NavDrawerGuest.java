package com.odishakrushi.NavDrawer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.navigation.NavigationView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.odishakrushi.Apptutorial.AppTutorial;
import com.odishakrushi.ChangePassword;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.utils.Utils;
import com.squareup.picasso.Picasso;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Ask_Purchase;
import com.odishakrushi.ChangeLanguage;
import com.odishakrushi.EditProfileActivity.EditProfileGuest;
import com.odishakrushi.Fragments.Contact;
import com.odishakrushi.Fragments.KnowledgeBank;
import com.odishakrushi.Login;
import com.odishakrushi.ProfilePic.ProfilePic;
import com.odishakrushi.R;
import com.odishakrushi.utils.CircleTransform;

public class NavDrawerGuest extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    String str_phone="";
    TextView txt_mobile;
    String img_url="";
    TextView keyname;
    ImageView imgeditProfile,imageView;
    String User="";
    String strsurveyboolean="true";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String logged_in_name="";
    String largeBannerUrl="";
    MenuItem item;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_guest);

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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header=navigationView.getHeaderView(0);
        //View view=navigationView.inflateHeaderView(R.layout.nav_header_nav_drawer);
        txt_mobile= header.findViewById(R.id.txt_mobile);
        txt_mobile.setText(str_phone);
        keyname = header.findViewById(R.id.keyname);
        imgeditProfile=header.findViewById(R.id.imgeditProfile);

        imgeditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(NavDrawerGuest.this,ProfilePic.class);
                intent1.putExtra("USER_ID","");
                startActivity(intent1);
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
        }        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(NavDrawerGuest.this, ProfilePic.class));
            }
        });



        keyname.setText(logged_in_name);
        txt_mobile.setText(str_phone);

        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.purchase,item);


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
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
       /* Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), "Please click BACK again to exit", Snackbar.LENGTH_LONG);

        snackbar.show();*/


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                //Logout session for user id
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove("USER_ID"); // will delete key email
                editor.commit(); // commit changes
            }
        }, 2000);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer_guest, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.appTutorials)
        {
            startActivity(new Intent(NavDrawerGuest.this, AppTutorial.class));

        }
        //noinspection SimplifiableIfStatement
        if(id==R.id.editProfile)
        {
            startActivity(new Intent(NavDrawerGuest.this,EditProfileGuest.class));
        }
        else if(id==R.id.changePassword)
        {
            startActivity(new Intent(NavDrawerGuest.this, ChangePassword.class));//EditProfileFarmer.class));

        }
        else if(id==R.id.changelanguage)
        {
            Intent intent=new Intent(this,ChangeLanguage.class);
            intent.putExtra("Value","4");
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
                            editor.remove("USER_ID");
                            editor.remove("LOGGED_IN_AS");// will delete key email
                            editor.remove("shared_pref_img_url");
                            editor.commit(); // commit changes


                            startActivity(new Intent(NavDrawerGuest.this,Login.class));
                            finish();
                       /*  Intent intent=new Intent(NavDrawer.this,Login.class);
                         intent.putExtra("LOGOUT","true");
                         startActivity(intent);
                         finish();*/
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
            alert.setTitle("Log Out");
            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        displaySelectedScreen(item.getItemId(),item);

        return true;
    }
    private void displaySelectedScreen(int itemId,MenuItem item) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.purchase:
               // fragment = new GuestCategory();
                startActivity(new Intent(this, Ask_Purchase.class));
                break;

            case R.id.knowledgebank:
                fragment=new KnowledgeBank();
                break;
            case R.id.contactus:
                fragment=new Contact();
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
                                editor.remove("LOGGED_IN_AS");
                                editor.remove("shared_pref_img_url");
                                editor.commit(); // commit changes


                                startActivity(new Intent(NavDrawerGuest.this,Login.class));
                                finish();
                       /*  Intent intent=new Intent(NavDrawer.this,Login.class);
                         intent.putExtra("LOGOUT","true");
                         startActivity(intent);
                         finish();*/
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onResume() {  // for ad
        super.onResume();

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
        goProDialogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(largeBannerUrl.equals(""))){

                    Toast.makeText(NavDrawerGuest.this, largeBannerUrl, Toast.LENGTH_SHORT).show();

                    if (!largeBannerUrl.startsWith("https://") && !largeBannerUrl.startsWith("http://")){
                        largeBannerUrl = "http://" + largeBannerUrl;
                    }
                    Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(largeBannerUrl));
                    startActivity(openUrlIntent);

                }
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
        String str_zone_id=Preferences.getZone(NavDrawerGuest.this);
        String str_group_id=Preferences.getGroupID(NavDrawerGuest.this);
        Utils.getAd(NavDrawerGuest.this,str_zone_id,str_group_id,goProDialogImage);


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
