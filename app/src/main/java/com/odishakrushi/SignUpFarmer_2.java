package com.odishakrushi;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Browser;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.odishakrushi.Adapter.AddAnimalAdapter;
import com.odishakrushi.Model.AddAnimalModel;
import com.odishakrushi.utils.Utils;
import com.odishakrushi.utils.multiselectspinner.MultiSpinner;
import com.pixplicity.easyprefs.library.Prefs;
import com.tapadoo.alerter.Alerter;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.odishakrushi.ProfilePic.ProfilePic;

import static android.content.DialogInterface.*;
import static com.odishakrushi.Config.ODIA_DISTRICTS;
import static com.odishakrushi.Config.signup;

public class SignUpFarmer_2 extends AppCompatActivity implements MultiSpinner.MultiSpinnerListener {



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy


    CheckBox termcondition;          //###############
    Button register;                    //###################

    TextView readTermCondition;         // ###################
    AutoCompleteTextView autoTextView;
    //AutoCompleteTextView village;//AppCompatEditText



    private static final String URL_DATA = Config.baseUrl+"commons/districts/29";
    private static final String URL_DATA2 = Config.baseUrl+"commons/blocks?district_id=";
    private static final String URL_DATA3 =  Config.baseUrl+"commons/gps?block_id=";

    private static final int TIME_OUT = 3000;
    LinearLayout layoutimagepreview;
    private String userChoosenTask;
    ImageView ivImage;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String mImageName;
    File mediaFile;
    File mediaStorageDir;

    //getextra
    String strname="",strfathername="",strmobile="",stremail="",strpass="",strgender="",strcaste="",strvillage="",
            strdistrict="Select Your District",strblock="Select Your Block",strgp="Select Your GP";

    Spinner district,block,gp;//
    Spinner last_crop_grown_in,soil_testing_done,fertiliser_used,pesticide_used,machineUsed,insuranceDone,loanTaken,any_special_crop_grown;
    Spinner fishery_activity,area_of_pond_own_lease,fingerlingsFrom,fishery_trained_for_farming,fishery_medicine_used,loanTakenFishery,insuranceDoneFishery;
    Spinner veterinary_activity,veterinary_trained_for_farming,veterinary_medicine_used,loanTakenVeterinary,insuranceDoneVeterinary;

    LinearLayout sublayoutFisheryActivity,sublayoutVeterinaryActivity;
    EditText area_of_pond_acres;
    LinearLayout layoutVillage;

    int j=0,k=0,l=0;

    String str_district_id="",str_block_id="",str_gp_id="";
    String str_last_crop_grown_in="",str_soil_testing_done="",str_fertiliser_used="",
            str_pesticide_used="",str_machineUsed="",str_insuranceDone="",str_loanTaken="",str_any_special_crop_grown;
    String str_fishery_activity="",str_area_of_pond_acres="",str_area_of_pond_own_lease="",str_fingerlingsFrom="",str_fishery_trained_for_farming="",
            str_fishery_medicine_used="",str_loanTakenFishery="",str_insuranceDoneFishery="";
    String str_veterinary_activity="",str_veterinary_trained_for_farming="",str_veterinary_medicine_used="",
            str_loanTakenVeterinary="",str_insuranceDoneVeterinary="";

    ArrayList<String> arraylist_districtid=new ArrayList<String>();
    ArrayList<String> arraylist_blockid;
    ArrayList<String> arraylist_gp_id;

    String gpNotFound="";


    EditText iluc,niluc;
    String str_iluc="",str_niluc="",str_irrisource="";
    RadioGroup radioIrrigationSource;

    //For image Upload--------------------------------------
    private static final int PICK_PHOTO = 1958;
    ImageView imageView;
    private String imagefilePath="";
    ProgressDialog progressDialog;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    RecyclerView recyclerViewAnimals;
    private List<AddAnimalModel> mList;

    String str_user_id="";

    //List<String> machines;
    ArrayList<String> machines=new ArrayList<>();
    String list_of_machines="";

    String isEditProfile="0";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_sign_up_farmer_2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        //######################################
        recyclerViewAnimals=findViewById(R.id.recyclerViewAnimals);
        register=findViewById(R.id.register);
        termcondition=findViewById(R.id.termcondition);

        readTermCondition=findViewById(R.id.readTermCondition);
        readTermCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://drive.google.com/open?id=1J1B7rCG1zME91dBTpRexHBm_Me1tPoeS");
                Context context = getApplicationContext();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
                try {
                    startActivity(intent);
                }catch (Exception e){}

            }
        });
        //#########################
        layoutimagepreview=findViewById(R.id.layoutimagepreview);
        ivImage=(ImageView)findViewById(R.id.ivImage);

        district=(Spinner) findViewById(R.id.district);
        block=findViewById(R.id.block);
        gp=findViewById(R.id.gp);

        last_crop_grown_in=findViewById(R.id.last_crop_grown_in);
        soil_testing_done=findViewById(R.id.soil_testing_done);
        fertiliser_used=findViewById(R.id.fertiliser_used);
        pesticide_used=findViewById(R.id.pesticide_used);
        machineUsed=findViewById(R.id.machineUsed);
        insuranceDone=findViewById(R.id.insuranceDone);
        loanTaken=findViewById(R.id.loanTaken);
        any_special_crop_grown=findViewById(R.id.any_special_crop_grown);
        fishery_activity=findViewById(R.id.fishery_activity);


        sublayoutFisheryActivity=findViewById(R.id.sublayoutFisheryActivity);
        area_of_pond_acres=findViewById(R.id.area_of_pond_acres);
        area_of_pond_own_lease=findViewById(R.id.area_of_pond_own_lease);
        fingerlingsFrom=findViewById(R.id.fingerlingsFrom);
        fishery_trained_for_farming=findViewById(R.id.fishery_trained_for_farming);
        fishery_medicine_used=findViewById(R.id.fishery_medicine_used);
        loanTakenFishery=findViewById(R.id.loanTakenFishery);
        insuranceDoneFishery=findViewById(R.id.insuranceDoneFishery);

        sublayoutVeterinaryActivity=findViewById(R.id.sublayoutVeterinaryActivity);
        veterinary_activity=findViewById(R.id.veterinary_activity);
        veterinary_trained_for_farming=findViewById(R.id.veterinary_trained_for_farming);
        veterinary_medicine_used=findViewById(R.id.veterinary_medicine_used);
        loanTakenVeterinary=findViewById(R.id.loanTakenVeterinary);
        insuranceDoneVeterinary=findViewById(R.id.insuranceDoneVeterinary);

        layoutVillage=findViewById(R.id.layoutVillage);
        iluc=findViewById(R.id.iluc);// irrigated land under cultivation
        niluc=findViewById(R.id.niluc);// non irrigated land under cultivation
        radioIrrigationSource=findViewById(R.id.radioIrrigationSource);
        imageView = (ImageView) findViewById(R.id.imageView);

        // Get the string array
        String[] villages = getResources().getStringArray(R.array.villagelist);

        autoTextView = (AutoCompleteTextView) findViewById(R.id.autocompleteEditTextView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, villages);
        //Used to specify minimum number of characters the user has to type in order to display the drop down hint.
        autoTextView.setThreshold(1);
        //Setting adapter
        autoTextView.setAdapter(arrayAdapter);

        //###########################################
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registerFunc();
                signupCall();
            }
        });

        termcondition.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(termcondition.isChecked())
                {
                    register.setEnabled(true);
                    register.setFocusable(true);
                    register.setClickable(true);
                    register.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }else
                    {
                    register.setEnabled(false);
                    register.setFocusable(false);
                    register.setClickable(false);
                    register.setBackgroundColor(getResources().getColor(R.color.ColorName));
                }
            }
        });

        //#######################################
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null)
        {

            str_user_id = bundle.getString("USER_ID");

        }


        Intent intentUpdate = getIntent();
        Bundle bundleUpdate = intentUpdate.getExtras();
        if( bundleUpdate != null)
        {
            isEditProfile= bundleUpdate.getString("EDIT_PROFILE");
            // name_of_farm.setText(bundle.getString("NAME_OF_FARM")); name_of_farm.setFocusable(false);

            toolbar.setTitle("Edit Profile");
        }


        if("1".equals(isEditProfile)){ // thanks Biswa for this line of code
            //then hide the terms and condition views
            termcondition.setVisibility(View.INVISIBLE);
            readTermCondition.setVisibility(View.INVISIBLE);
            layoutVillage.setVisibility(View.GONE);

            register.setEnabled(true);
            register.setFocusable(true);
            register.setClickable(true);
            register.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));



            sharedpreferences = getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            user_id=sharedpreferences.getString("FLAG", "");
            Log.d("user_id:",user_id);
            editor.commit(); // commit changes

            str_user_id=user_id;
        }

        gpNotFound="No";
        load_district_in_spinner();
        // SPINNER SELECT

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strdistrict= district.getSelectedItem().toString();
                str_district_id= String.valueOf(position+1);


                //Toast.makeText(SignUpFarmer_2.this,"CLICKED AND PASSED districtid " +str_district_id , Toast.LENGTH_SHORT).show();
                load_block_from_a_district(str_district_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strblock= block.getSelectedItem().toString();
                //str_block_id= String.valueOf(position+1);

                // TASK IS TO GET THE BLOCK_ID FROM DISTRICT_ID ..?
                str_block_id=arraylist_blockid.get(position);
                load_gps_from_blockid(str_block_id);//str_block_id
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        gp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strgp= gp.getSelectedItem().toString();

                // TASK IS TO GET THE GP_ID FROM BLOCK_ID ..?
                str_gp_id=arraylist_gp_id.get(position);

                //str_gp_id= String.valueOf(position+1);

               // Toast.makeText(SignUpFarmer_2.this, str_gp_id, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        radioIrrigationSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_irrisource=rb.getText().toString();
                    Toast.makeText(SignUpFarmer_2.this, str_irrisource, Toast.LENGTH_SHORT).show();


                }

            }
        });

        last_crop_grown_in.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_last_crop_grown_in= last_crop_grown_in.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        soil_testing_done.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_soil_testing_done= soil_testing_done.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fertiliser_used.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_fertiliser_used= fertiliser_used.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pesticide_used.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_pesticide_used= pesticide_used.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        machineUsed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_machineUsed= machineUsed.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        insuranceDone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_insuranceDone= insuranceDone.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loanTaken.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_loanTaken= loanTaken.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        any_special_crop_grown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_any_special_crop_grown= any_special_crop_grown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fishery_activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_fishery_activity= fishery_activity.getSelectedItem().toString();

                if(position==1){ // 0 means Yes , 1 means No
                    str_area_of_pond_acres="";  sublayoutFisheryActivity.setVisibility(View.GONE);
                    str_fingerlingsFrom="";
                    str_fishery_trained_for_farming="";
                    str_fishery_medicine_used="";
                    str_loanTakenFishery="";
                    str_insuranceDoneFishery="";
                }
                else{
                    sublayoutFisheryActivity.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        area_of_pond_own_lease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_area_of_pond_own_lease= area_of_pond_own_lease.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fingerlingsFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_fingerlingsFrom= fingerlingsFrom.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fishery_trained_for_farming.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_fishery_trained_for_farming= fishery_trained_for_farming.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fishery_medicine_used.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_fishery_medicine_used= fishery_medicine_used.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loanTakenFishery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_loanTakenFishery= loanTakenFishery.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        insuranceDoneFishery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_insuranceDoneFishery= insuranceDoneFishery.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        veterinary_activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_veterinary_activity= veterinary_activity.getSelectedItem().toString();

                if(position==1){ // 0 means Yes , 1 means No
                    sublayoutVeterinaryActivity.setVisibility(View.GONE);
                    str_veterinary_trained_for_farming="";
                    str_veterinary_medicine_used="";
                    str_loanTakenVeterinary="";
                    str_insuranceDoneVeterinary="";
                }
                else{
                    sublayoutVeterinaryActivity.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        veterinary_trained_for_farming.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_veterinary_trained_for_farming= veterinary_trained_for_farming.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loanTakenVeterinary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_loanTakenVeterinary= loanTakenVeterinary.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        insuranceDoneVeterinary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_insuranceDoneVeterinary= insuranceDoneVeterinary.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        veterinary_medicine_used.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_veterinary_medicine_used= veterinary_medicine_used.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*Add animal rows on click add button*/
        mList = new ArrayList<>();

        create_1_Row();

        AddAnimalAdapter rAdapter = new AddAnimalAdapter(this, mList, new AddAnimalAdapter.OnItemClickListener() {

            @Override

            public void onItemClick(int position) {

                //nothing todoo here when click on button
                Toast.makeText(SignUpFarmer_2.this, mList.toString(), Toast.LENGTH_SHORT).show();

            }

        });

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        recyclerViewAnimals.setLayoutManager(layoutManager);

        recyclerViewAnimals.setItemAnimator(new DefaultItemAnimator());

        recyclerViewAnimals.setAdapter(rAdapter);


        /*multiselect machines tools own*/

        //callMachineListAPI();

       List<String> machines = Arrays.asList(getResources().getStringArray(R.array.machinetoolsarray));

        /*ArrayList<String> machines=new ArrayList<>();
        machines.add("rotavator");
        machines.add("rotavator");
        machines.add("rotavator");
        machines.add("rotavator");*/

        MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
        multiSpinner.setItems(machines, getString(R.string.Select), this);

    }


    public void onItemsSelected(boolean[] selected , String machine_lists){

        list_of_machines=machine_lists;
        Toast.makeText(this,  " " +machine_lists, Toast.LENGTH_SHORT).show();
    }

    /**

     * When click on 'Add button' this method will be called.

     * @param view Particular View

     */

    public void addOneRow(View view) {

        addRow();

        refreshRV();

    }

    /**

     * Adding row to existing list at end.

     */

    private void addRow() {

      //  String name = String.valueOf(getSizeOfList());

        AddAnimalModel addAnimalModel = new AddAnimalModel();

      //  addAnimalModel.setAnimalName(name);

        mList.add(addAnimalModel);

    }

    /**

     * Creating 1 dynamically

     */

    private void create_1_Row() {

        for (int i = 0; i < 1; i++) {

            AddAnimalModel addAnimalModel = new AddAnimalModel();

           // addAnimalModel.setName("Row no " + i);

            mList.add(addAnimalModel);

        }

    }

    /**

     * Refreshing RecyclerView and scroll up dynamically

     */

    private void refreshRV() {

        recyclerViewAnimals.getAdapter().notifyItemInserted(getSizeOfList());

        recyclerViewAnimals.smoothScrollToPosition(getSizeOfList());

    }

    /**

     * Get total size of list

     * @return Total size of list

     */

    private int getSizeOfList() {

        return mList.size();

    }

    private void load_district_in_spinner() {

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                      /*  progressDialog.dismiss();*/
                        try {
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            Log.d("onResponse:",s);

                            //code to check what  is the language selected , if odia then load odia districts in spinner
                            String langdata = Prefs.getString("language", "");
                            if(langdata.equals("or"))
                                s=ODIA_DISTRICTS;

                            //Initialize arraylist
                            ArrayList<String> dl=new ArrayList<String>();


                            JSONObject jsonObject=new JSONObject(s);
                            JSONArray array=jsonObject.getJSONArray("districts");

                            for(int i=0;i<array.length();i++)
                            {
                                String did="",dname="",sid="";

                                JSONObject o=array.getJSONObject(i);
                                did=o.getString("id");
                                dname =o.getString("name");
                                sid =o.getString("state_id");

                                dl.add(dname);
                                arraylist_districtid.add(did);

                            }

                            final List<String> districtList = new ArrayList<>(dl);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpFarmer_2.this,R.layout.spinner_item,districtList);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            district.setAdapter(spinnerArrayAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpFarmer_2.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SignUpFarmer_2.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void load_block_from_a_district(String districtid)
    {
        //code to check what  is the language selected , if odia then load odia districts in spinner
        String langdata = Prefs.getString("language", "");
        String isOdia="";
        if(langdata.equals("or"))
            isOdia="&odia=1";

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA2+districtid+isOdia,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                      /*  progressDialog.dismiss();*/
                        try {
                          // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            Log.d("onResponse:",s);
                            JSONObject jsonObject=null;
                            JSONArray array=null;

                            //Initialize arraylist
                            ArrayList<String> al=new ArrayList<String>();
                            arraylist_blockid =new ArrayList<String>();
                            jsonObject=new JSONObject(s);
                             array=jsonObject.getJSONArray("blocks");

                           // Toast.makeText(getApplicationContext(),String.valueOf(array.length()),Toast.LENGTH_LONG).show();



                            for(int k=0;k<array.length();k++)
                            {
                                String bid="",name="",district_id="";

                                JSONObject o=array.getJSONObject(k);
                              //  bid=o.getString("id");
                                name =o.getString("name");
                                bid =o.getString("district_id");


                                al.add(name);
                                arraylist_blockid.add(bid);

                            }
                         /*   Toast.makeText(SignUpFarmer_2.this, "arraylist_blockid", Toast.LENGTH_SHORT).show();

                            for (int x=0;x<array.length();x++)
                            {
                                Toast.makeText(SignUpFarmer_2.this, arraylist_blockid.get(x), Toast.LENGTH_SHORT).show();
                            }*/

                          //  final List<String> blockList = new ArrayList<>(Arrays.asList(strarrayblocks));
                            final List<String> blockList = new ArrayList<>(al);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                                    SignUpFarmer_2.this,R.layout.spinner_item_2,blockList);

                            spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item_2);
                            block.setAdapter(spinnerArrayAdapter2);



                        } catch (JSONException e) {//JSONException
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SignUpFarmer_2.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void load_gps_from_blockid(String str_block_id)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA3+str_block_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                      /*  progressDialog.dismiss();*/
                        try {
                             //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            Log.d("onResponse:",s);
                            JSONObject jsonObject=null;
                            JSONArray array=null;

                            //Initialize arraylist
                            ArrayList<String> gpl=new ArrayList<String>();
                            arraylist_gp_id =new ArrayList<String>();

                            jsonObject=new JSONObject(s);
                            array=jsonObject.getJSONArray("gps");

                            // Toast.makeText(getApplicationContext(),String.valueOf(array.length()),Toast.LENGTH_LONG).show();



                            for(int k=0;k<array.length();k++)
                            {
                                String gid="",name="",b_id="";

                                JSONObject o=array.getJSONObject(k);
                                gid=o.getString("id");
                                name =o.getString("name");
                                b_id =o.getString("block_id");


                                gpl.add(name);
                                arraylist_gp_id.add(gid);
                            }


                            //  final List<String> blockList = new ArrayList<>(Arrays.asList(strarrayblocks));
                            final List<String> gpList = new ArrayList<>(gpl);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                                    SignUpFarmer_2.this,R.layout.spinner_item_2,gpList);

                            spinnerArrayAdapter3.setDropDownViewResource(R.layout.spinner_item_2);
                            gp.setAdapter(spinnerArrayAdapter3);
                            gp.setVisibility(View.VISIBLE);
                            gpNotFound="No";

                        } catch (JSONException e)
                        { //JSONException
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* progressDialog.dismiss();*/
               // Toast.makeText(getApplicationContext(),"No GP Found",Toast.LENGTH_LONG).show();
              /*  Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), "No GP Found", Snackbar.LENGTH_LONG);

                snackbar.show();
*/
                Alerter.create(SignUpFarmer_2.this)
                        .setTitle(getString(R.string.no_gp))
                        .show();

                str_gp_id="0"; // for passing in str_gp_id as zero
                gpNotFound="Yes";

                gp.setVisibility(View.GONE);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void addPhoto(View view) {
      //  responseTextView.setText("");
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {
            Uri imageUri = data.getData();
            imagefilePath = getPath(imageUri);
            imageView.setImageURI(imageUri);
            imageView.setVisibility(View.VISIBLE);
        }

    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };//,Video,Audio
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Video
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    public void callMachineListAPI() {

        if (Utils.hasNetwork(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.pleasewait));
            progressDialog.show();

            StringRequest stringRequest=new StringRequest(Request.Method.GET,
                    Config.getMachines,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            progressDialog.hide();

                            try {

                                Toast.makeText(SignUpFarmer_2.this, s, Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject=new JSONObject(s);
                                JSONArray jsonArray=jsonObject.getJSONArray("data");
                                for (int i=0 ;i<jsonArray.length(); i++){

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String name=jsonObject1.optString("name");
                                    machines.add(name);

                                }


                            } catch (Exception ex) {
                                ex.printStackTrace();
                                Toast.makeText(SignUpFarmer_2.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                                onBackPressed();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(SignUpFarmer_2.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }

            });

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void signupCall() {

          if(Utils.hasNetwork(this)) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(getString(R.string.pleasewait));
                progressDialog.show();


                    if("1".equals(isEditProfile)) {
                        AndroidNetworking.post(Config.farmersignup)
                                .addBodyParameter("user_id", str_user_id)
                                // .addBodyParameter("village", autoTextView.getText().toString().trim())
                                .addBodyParameter("irrigated_land_under_cultivation", iluc.getText().toString().trim())
                                .addBodyParameter("irrigation_source", str_irrisource)
                                .addBodyParameter("non_irrigated_land_under_cultivation", niluc.getText().toString().trim())
                                .addBodyParameter("any_special_crop_grown", str_any_special_crop_grown)
                                .addBodyParameter("machine_tool_own", list_of_machines)
                                .addBodyParameter("last_crop_grown_in", str_last_crop_grown_in)
                                .addBodyParameter("soil_testing_done", str_soil_testing_done)
                                .addBodyParameter("fertiliser_used", str_fertiliser_used)
                                .addBodyParameter("pesticide_used", str_pesticide_used)
                                .addBodyParameter("machines_used", str_machineUsed)
                                //   .setBodyParameter("machines",machine_lists)
                                .addBodyParameter("last_crop_insurane_done", str_insuranceDone)
                                .addBodyParameter("loan_taken", str_loanTaken)
                                .addBodyParameter("fishery_activity", str_fishery_activity)
                                .addBodyParameter("area_of_pond_acres", str_area_of_pond_acres)
                                .addBodyParameter("fingerling_from", str_fingerlingsFrom)
                                .addBodyParameter("fishery_trained_for_farming", str_fishery_trained_for_farming)
                                .addBodyParameter("fishery_medicine_used", str_fishery_medicine_used)
                                .addBodyParameter("fishery_lone_taken", str_loanTakenFishery)
                                .addBodyParameter("fishery_insurance_done", str_insuranceDoneFishery)
                                .addBodyParameter("veterinary_activity", str_veterinary_activity)
                                //  .setBodyParameter("covered_by_insurance", "xxxxxx")
                                .addBodyParameter("animals", "")
                                .addBodyParameter("veterinary_lone_taken", str_loanTakenVeterinary)
                                .addBodyParameter("veterinary_insurance_done", str_insuranceDoneVeterinary)
                                .addBodyParameter("veterinary_trained_for_farming", str_veterinary_trained_for_farming)
                                .addBodyParameter("veterinary_medicine_used", str_veterinary_medicine_used)
                                .build()
                                .getAsString(new StringRequestListener() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.hide();
                                        Toast.makeText(SignUpFarmer_2.this, response, Toast.LENGTH_SHORT).show();

                                        if (isEditProfile.equals("0")) {
                                            Intent intent = new Intent(SignUpFarmer_2.this, Login.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else {
                                            onBackPressed();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        progressDialog.hide();
                                        Toast.makeText(SignUpFarmer_2.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
                    else{
                        AndroidNetworking.post(Config.farmersignup)
                                .addBodyParameter("user_id", str_user_id)
                                .addBodyParameter("village", autoTextView.getText().toString().trim())
                                .addBodyParameter("irrigated_land_under_cultivation", iluc.getText().toString().trim())
                                .addBodyParameter("irrigation_source", str_irrisource)
                                .addBodyParameter("non_irrigated_land_under_cultivation", niluc.getText().toString().trim())
                                .addBodyParameter("any_special_crop_grown", str_any_special_crop_grown)
                                .addBodyParameter("machine_tool_own", list_of_machines)
                                .addBodyParameter("last_crop_grown_in", str_last_crop_grown_in)
                                .addBodyParameter("soil_testing_done", str_soil_testing_done)
                                .addBodyParameter("fertiliser_used", str_fertiliser_used)
                                .addBodyParameter("pesticide_used", str_pesticide_used)
                                .addBodyParameter("machines_used", str_machineUsed)
                                //   .setBodyParameter("machines",machine_lists)
                                .addBodyParameter("last_crop_insurane_done", str_insuranceDone)
                                .addBodyParameter("loan_taken", str_loanTaken)
                                .addBodyParameter("fishery_activity", str_fishery_activity)
                                .addBodyParameter("area_of_pond_acres", str_area_of_pond_acres)
                                .addBodyParameter("fingerling_from", str_fingerlingsFrom)
                                .addBodyParameter("fishery_trained_for_farming", str_fishery_trained_for_farming)
                                .addBodyParameter("fishery_medicine_used", str_fishery_medicine_used)
                                .addBodyParameter("fishery_lone_taken", str_loanTakenFishery)
                                .addBodyParameter("fishery_insurance_done", str_insuranceDoneFishery)
                                .addBodyParameter("veterinary_activity", str_veterinary_activity)
                                //  .setBodyParameter("covered_by_insurance", "xxxxxx")
                                .addBodyParameter("animals", "")
                                .addBodyParameter("veterinary_lone_taken", str_loanTakenVeterinary)
                                .addBodyParameter("veterinary_insurance_done", str_insuranceDoneVeterinary)
                                .addBodyParameter("veterinary_trained_for_farming", str_veterinary_trained_for_farming)
                                .addBodyParameter("veterinary_medicine_used", str_veterinary_medicine_used)
                                .build()
                                .getAsString(new StringRequestListener() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.hide();
                                        Toast.makeText(SignUpFarmer_2.this, response, Toast.LENGTH_SHORT).show();

                                        if (isEditProfile.equals("0")) {
                                            Intent intent = new Intent(SignUpFarmer_2.this, Login.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else {
                                            onBackPressed();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        progressDialog.hide();
                                        Toast.makeText(SignUpFarmer_2.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
            }

    }
}
