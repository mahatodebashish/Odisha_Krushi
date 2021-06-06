package com.odishakrushi;

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

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.odishakrushi.Apptutorial.AppTutorial;
import com.odishakrushi.OpenForum.OpenForumList;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.ViewProfile.ViewProfileGuest;
import com.odishakrushi.utils.Utils;
import com.squareup.picasso.Picasso;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.EditProfileActivity.EditProfileFarmer2;
import com.odishakrushi.Fragments.Contact;
import com.odishakrushi.Fragments.KnowledgeBank;
import com.odishakrushi.utils.CircleTransform;

public class NavDrawerGuest extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    TextView keyname;
    ImageView imgeditProfile,imageView;
    String User="";
    String strsurveyboolean="true";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";


    MenuItem item;
    String user_id,img_url;  String largeBannerUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_guest);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header=navigationView.getHeaderView(0);
        //View view=navigationView.inflateHeaderView(R.layout.nav_header_nav_drawer);
        keyname = header.findViewById(R.id.keyname);
        imgeditProfile=header.findViewById(R.id.imgeditProfile);
        imgeditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavDrawerGuest.this,EditProfileFarmer2.class));
            }
        });

        imageView=header.findViewById(R.id.imageView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        if(bundle != null){
            User = bundle.getString("keyName");
            img_url = bundle.getString("PROF_IMG"); Picasso.with(getApplicationContext()).load(img_url).transform(new CircleTransform()).into(imageView);
            // keyname.setText(User);
        }


        keyname.setText(User);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.knowledgebank,item);
        /* RECEIVING PART */
        // Register the local broadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(mLBannerUrlReceiver,
                new IntentFilter("large-banner-url"));

        loadAd();

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
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
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
        if(id==R.id.editProfile)
        {
            startActivity(new Intent(NavDrawerGuest.this, ViewProfileGuest.class));
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

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to log out ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Logout session for user id
                        sharedpreferences = getSharedPreferences(mypreference,
                                Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.remove("USER_ID"); // will delete key email
                        editor.remove("LOGGED_IN_AS");
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
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
                //fragment = new PurchaseSellHireRepair();
                startActivity(new Intent(this,Ask_Purchase.class));
                break;

            case R.id.knowledgebank:
               // Toast.makeText(this, "Knowledge Bank Activity", Toast.LENGTH_SHORT).show();
                fragment=new KnowledgeBank();
                break;

            case R.id.openforum:
                fragment = new OpenForumList();
                break;

            case R.id.contactus:
              //  Toast.makeText(this, "Contact us fragment", Toast.LENGTH_SHORT).show();
                fragment=new Contact();
                break;
            case R.id.logout:
                logOut();
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


    public void loadAd(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
      /*  builder.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!(largeBannerUrl.equals(""))){
                    Toast.makeText(NavDrawerGuest.this, largeBannerUrl, Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(NavDrawerGuest.this, largeBannerUrl, Toast.LENGTH_SHORT).show();

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
        String str_zone_id= Preferences.getZone(NavDrawerGuest.this);
        String str_group_id=Preferences.getGroupID(NavDrawerGuest.this);
        Utils.getAd(NavDrawerGuest.this,str_zone_id,str_group_id,goProDialogImage);
        goProDialogImage.setVisibility(View.VISIBLE);

        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();
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
