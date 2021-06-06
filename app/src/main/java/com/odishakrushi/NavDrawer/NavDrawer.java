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
import com.google.android.material.navigation.NavigationView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.odishakrushi.Apptutorial.AppTutorial;
import com.odishakrushi.ChangePassword;
import com.odishakrushi.Notification.NotificationActivity;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.QuestionAnswerSection.QuestionAnswer;
import com.odishakrushi.ViewProfile.ViewProfileFarmer;
import com.odishakrushi.utils.Utils;
import com.squareup.picasso.Picasso;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.ChangeLanguage;
import com.odishakrushi.EditProfileActivity.EditProfileFarmer;
import com.odishakrushi.Fragments.Contact;
import com.odishakrushi.Fragments.DashBoard;
import com.odishakrushi.Fragments.KnowledgeBank;
import com.odishakrushi.Fragments.MyStory;
import com.odishakrushi.Fragments.PurchaseSellHireRepair;
import com.odishakrushi.Login;
import com.odishakrushi.Message.ViewMessages;
import com.odishakrushi.Survey.SurveyFragment;
import com.odishakrushi.MyStoryFarmer.ViewStory;
import com.odishakrushi.OpenForum.OpenForumList;
import com.odishakrushi.ProfilePic.ProfilePic;
import com.odishakrushi.R;
import com.odishakrushi.utils.CircleTransform;
//import spencerstudios.com.bungeelib.Bungee;


public class NavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Animation.AnimationListener {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    // Animation
    Animation animFadein;

    TextView textCartItemCount;
    int mCartItemCount = 10;

    String img_url="";
    String str_phone="";
    TextView keyname;
    TextView txt_mobile;
    ImageView imgeditProfile,imageView;
    String User="";
    String strsurveyboolean="true";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String logged_in_name="";

    MenuItem item;
    String user_id;

    String mystory="";
    Toolbar toolbar;

    BroadcastReceiver mProfilePicReceiver;// initialization
    String largeBannerUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);

        /* RECEIVING PART */
        // Register the local broadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(mLBannerUrlReceiver,
                new IntentFilter("large-banner-url"));

        // load the animation
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animation_fade_in);

        // set animation listener
        animFadein.setAnimationListener(this);
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

                    // Register the local broadcast
                    LocalBroadcastManager.getInstance(NavDrawer.this).registerReceiver(
                            mProfilePicReceiver,
                            new IntentFilter("set-profile-pic"));
                    // Initialize a new BroadcastReceiver instance
                    mProfilePicReceiver = new BroadcastReceiver() {
                    @Override    public void onReceive(Context context, Intent intent) {
                        String img_url = intent.getStringExtra("image_url");
                        Picasso.with(getApplicationContext()).load(img_url).transform(new CircleTransform()).into(imageView);


                    }
                };
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header=navigationView.getHeaderView(0);
        //View view=navigationView.inflateHeaderView(R.layout.nav_header_nav_drawer);
        txt_mobile= header.findViewById(R.id.txt_mobile);
        keyname = header.findViewById(R.id.keyname);
        imgeditProfile=header.findViewById(R.id.imgeditProfile);
        imgeditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavDrawer.this,EditProfileFarmer.class));
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

                Intent intent1=new Intent(NavDrawer.this,ProfilePic.class);
                intent1.putExtra("USER_ID","");
                startActivity(intent1);

            }
        });



        keyname.setText(logged_in_name);
        txt_mobile.setText(str_phone);

        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.ques_ans,item);//   ask



        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));

        System.out.print("navdrawer_");

        // added here Showcase view
      /*  new GuideView.Builder(this)
                .setTitle(getString(R.string.msgtofarmer))
                .setContentText(getString(R.string.msgtofarmerdetail))
                .setGravity(GuideView.Gravity.auto) //optional
                .setDismissType(GuideView.DismissType.anywhere) //optional - default GuideView.DismissType.targetView
                .setTargetView(toolbar.getChildAt(0))
                .setContentTextSize(12)//optional
                .setTitleTextSize(18)//optional
                .build()
                .show();*/




    }

/*   Commented and new code re-written below
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
       *//* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
*//*
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
       *//* Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), "Please click BACK again to exit", Snackbar.LENGTH_LONG);

        snackbar.show();*//*

        Alerter.create(NavDrawer.this)
                .setTitle(getString(R.string.press_two_times))
                .show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                //Logout session for user id
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove("USER_ID"); // will delete key email
                editor.remove("MY_STORY"); // will delete key email

                editor.commit(); // commit changes


            }
        }, 2000);
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Logout session for user id
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("USER_ID"); // will delete key email
        editor.remove("MY_STORY"); // will delete key email

        editor.commit(); // commit changes
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);//


        final MenuItem menuItem = menu.findItem(R.id.notification);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.notification_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });


       /* MenuItem surveyMenuItem = menu.findItem(R.id.survey);
        Toast.makeText(this, "Entered ", Toast.LENGTH_SHORT).show();
       // Toast.makeText(NavDrawer.this, surveyMenuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
        if(strsurveyboolean.equals("false"))
        {
           // surveyMenuItem.setVisible(false);

        }*/
        return true;
    }
    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.appTutorials)
        {
            startActivity(new Intent(NavDrawer.this, AppTutorial.class));

        }

        if(id==R.id.notification)
        {
            startActivity(new Intent(NavDrawer.this, NotificationActivity.class));//EditProfileFarmer.class));

        }
        //noinspection SimplifiableIfStatement
        else if(id==R.id.editProfile)
        {
            startActivity(new Intent(NavDrawer.this, ViewProfileFarmer.class));//EditProfileFarmer.class));

        }

        else if(id==R.id.changePassword)
        {
            startActivity(new Intent(NavDrawer.this, ChangePassword.class));//EditProfileFarmer.class));

        }
      /*  else if(id==R.id.theme)
        {
            Intent intent=new Intent(this,ThemeSettings.class);
            intent.putExtra("Theme","1");
            startActivity(intent);

        }*/
        else if(id==R.id.changelanguage)
        {
           // startActivity(new Intent(NavDrawer.this,ChangeLanguage.class));
            Intent intent=new Intent(this,ChangeLanguage.class);
            intent.putExtra("Value","1");
            startActivity(intent);
            finish();
        }
        else if (id == R.id.logout) {
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
                            editor.remove("LOGGED_IN_AS");// FLAG   SEE IN LOGIN ACTIVITY
                            editor.remove("shared_pref_img_url");
                            editor.commit(); // commit changes



                            startActivity(new Intent(NavDrawer.this,Login.class));
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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

       // item.setVisible(false);
        displaySelectedScreen(item.getItemId(),item);

        return true;
    }

    //broadcast receiver for dashboard items click
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int str = intent.getIntExtra("key",0);

                // get all your data from intent and do what you want
                displaySelectedScreen(str,item);
            }
        }
    };

    //unregister Localbroadcaste receiver
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void displaySelectedScreen(int itemId,MenuItem item) {

        //creating fragment object
        Fragment fragment = null;
            Fragment fragment2=null;
        //initializing the fragment object which is selected
        switch (itemId) {

            case R.id.dashboard:
                fragment= new DashBoard();
                break;

            /* case R.id.ask:

                // REMOVE THE MYSTORY KEY AT START

                //Logout session for user id
                if(mystory!="")
                {
                    sharedpreferences = getSharedPreferences(mypreference,
                            Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove("MY_STORY"); // will delete key email
                    editor.commit(); // commit changes
                }

               // fragment = new Ask();
                break;

           case R.id.viewaskedques:


                fragment = new ViewQuestionList_Farmer();
                break;

            case R.id.viewansbyextoff:

                fragment = new ViewAnswerByExtOff();
                break;*/

          /*  case R.id.answers:
                fragment=new AnswerList();
                break;*/

            case R.id.ques_ans:
                fragment = new QuestionAnswer();
                break;

            case R.id.hire:
                fragment = new PurchaseSellHireRepair();
                break;

            case R.id.mystory:




                final CharSequence[] items = { getString(R.string.Success_Story), getString(R.string.Failure_Story),getString(R.string.View_Story)};
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NavDrawer.this);
                alertDialog.setCancelable(false);
                // Setting Dialog Title
                alertDialog.setTitle("Select Story Type...");

                // Setting Dialog Message
                //  alertDialog.setMessage("");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.success);

                alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(getString(R.string.Success_Story)))
                        {
                            Toast.makeText(NavDrawer.this, getString(R.string.Success_Story), Toast.LENGTH_SHORT).show();
                            mystory="success";

                            sharedpreferences = getSharedPreferences(mypreference,
                                    Context.MODE_MULTI_PROCESS);
                            SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                            editor.putString("MY_STORY", mystory);
                            editor.commit();

                        }
                        else if (items[which].equals(getString(R.string.Failure_Story)))
                        {
                            Toast.makeText(NavDrawer.this, getString(R.string.Failure_Story), Toast.LENGTH_SHORT).show();
                            mystory="failure";

                            sharedpreferences = getSharedPreferences(mypreference,
                                    Context.MODE_MULTI_PROCESS);
                            SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                            editor.putString("MY_STORY", mystory);
                            editor.commit();
                        }
                        else if (items[which].equals(getString(R.string.View_Story)))
                        {
                            Toast.makeText(NavDrawer.this, getString(R.string.View_Story), Toast.LENGTH_SHORT).show();
                            Intent intent1=new Intent(NavDrawer.this, ViewStory.class);
                            startActivity(intent1);

                        }

                    }
                });

                // Showing Alert Message
                alertDialog.show();

                fragment = new MyStory();
                break;

            case R.id.knowledgebank:
                fragment = new KnowledgeBank();
                break;

            case R.id.message:
                fragment = new ViewMessages();
                break;

            case R.id.survey:
                fragment = new SurveyFragment();
                break;

            case R.id.contact:
                fragment = new Contact();
                break;
            case R.id.openforum:
                fragment = new OpenForumList();
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
                                editor.remove("LOGGED_IN_AS");// FLAG          SEE IN LOGIN ACTIVITY
                                editor.remove("shared_pref_img_url");
                                editor.commit(); // commit changes

                                startActivity(new Intent(NavDrawer.this,Login.class));
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

                    Toast.makeText(NavDrawer.this, largeBannerUrl, Toast.LENGTH_SHORT).show();

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
        String str_zone_id=Preferences.getZone(NavDrawer.this);
        String str_group_id=Preferences.getGroupID(NavDrawer.this);
        Utils.getAd(NavDrawer.this,str_zone_id,str_group_id,goProDialogImage);


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

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
