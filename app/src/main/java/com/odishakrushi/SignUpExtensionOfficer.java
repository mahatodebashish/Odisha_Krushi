package com.odishakrushi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Browser;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.utils.Utils;
import com.odishakrushi.utils.multiselectspinner.MultiSpinner;
import com.odishakrushi.utils.multiselectspinner.MultiSpinnerExpertiseIn;
import com.pixplicity.easyprefs.library.Prefs;
import com.tapadoo.alerter.Alerter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.EditProfileActivity.EditProfileStudentResearcher;

import static com.odishakrushi.Config.ODIA_DISTRICTS;


public class SignUpExtensionOfficer extends AppCompatActivity implements
        MultiSpinnerExpertiseIn.MultiSpinnerExpertiseInListener,
        MultiSpinner.MultiSpinnerListener{


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    CheckBox termcondition;          //###############

    TextView readTermCondition;         // ###################
    int flag=0;

    TextView txtinservice;
    AppCompatEditText name,input_mob_no,input_pass,input_mail,
            txtDcar, txtDpart;
    Spinner typeOfControl,serviceStatus,depart_type,dcar,post,jurisdiction,expertise_in;

    RadioGroup radioGender;
    Button btnRegister;

    String str_gender="",str_input_mail;
    String str_name="",str_input_mob_no="",str_input_pass="",str_typeOfControl="",str_serviceStatus="",
            str_depart_type="",str_dcar="",str_post="",str_jurisdiction="",str_jurisdictionName="",str_expertise_in="";
    String str_check_value="";


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    String email,salt,first_name,phone,gender;
    String str_password_field="0";
    LinearLayout layoutMobileNumber;
    ProgressDialog progressDialog;

    private static final String URL_DATA = Config.baseUrl+"commons/districts/29";
    private static final String URL_DATA2 = Config.baseUrl+"commons/blocks?district_id=";
    private static final String URL_DATA3 =  Config.baseUrl+"commons/gps?block_id=";

    Spinner district,block,gp;
    String str_district_id="",str_block_id="",str_gp_id="";
    String typs_control_id="1";
    LinearLayout layoutBlock,layoutGPs,layoutBlocks,layoutJurisdiction,layoutJurisdictionName;

    ArrayList<String> arraylist_districtid=new ArrayList<String>();
    ArrayList<String> arraylist_blockid;
    ArrayList<String> arraylist_gp_id;

    String gpNotFound="";

    LinearLayout rootContainer,rootContainerGP;
    CheckBox checkBox,checkBoxGPs;

    String str_multiple_blocks="",str_multiple_gp="";
    TextView txtViewSelectBlocks,txtViewSelectGps;

    String isOdia="false";
    String str_group_id="";
    String  department_id="";
    String jurisdiction_id="";
    ArrayList<String> arrayList_typs_control_id=new ArrayList<String>();
    ArrayList<String> arrayList_department_id=new ArrayList<String>();
    ArrayList<String> arrayList_dcarci_id=new ArrayList<String>();
    ArrayList<String> arrayList_post_id=new ArrayList<String>();
    ArrayList<String> arrayList_jurisdiction_id=new ArrayList<String>();

    LinearLayout layoutDistrict;
    LinearLayout layoutMultiDistrict;
    LinearLayout layoutState;
    Spinner spinnerState;

    String isEditProfile="0";
    LinearLayout layoutPassword,layoutGender,layoutTypeOfControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_sign_up_extension_officer);


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

        str_group_id= "3";//Preferences.getGroupID(this);
        termcondition=findViewById(R.id.termcondition);
        btnRegister=findViewById(R.id.btnRegister);
        layoutPassword=findViewById(R.id.layoutPassword);
        layoutGender=findViewById(R.id.layoutGender);
        layoutTypeOfControl=findViewById(R.id.layoutTypeOfControl);

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



        termcondition.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(termcondition.isChecked())
                {
                    flag=1;
                    btnRegister.setEnabled(true);
                    btnRegister.setFocusable(true);
                    btnRegister.setClickable(true);
                    btnRegister.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }else
                {
                    flag=0;
                    btnRegister.setEnabled(false);
                    btnRegister.setFocusable(false);
                    btnRegister.setClickable(false);
                    btnRegister.setBackgroundColor(getResources().getColor(R.color.ColorName));
                }
            }
        });
        //#######################################

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        user_id=sharedpreferences.getString("FLAG", "");
        Log.d("user_id:",user_id);
        editor.commit(); // commit changes


        name=findViewById(R.id.name);
        input_mob_no=findViewById(R.id.input_mob_no);
        input_pass=findViewById(R.id.input_pass);
        input_mail=findViewById(R.id.input_mail);
        radioGender=findViewById(R.id.radioGender);
        txtDcar=findViewById(R.id.txtDcar);
        txtDpart=findViewById(R.id.txtDpart);

        layoutMobileNumber=findViewById(R.id.layoutMobileNumber);

        typeOfControl=findViewById(R.id.typeOfControl);
        serviceStatus=findViewById(R.id.serviceStatus);
        depart_type=findViewById(R.id.depart_type);
        dcar=findViewById(R.id.dcar);
        post=findViewById(R.id.post);
        jurisdiction=findViewById(R.id.jurisdiction);
        expertise_in=findViewById(R.id.expertise_in);

        district=(Spinner) findViewById(R.id.district);
        block=findViewById(R.id.block);
        gp=findViewById(R.id.gp);

        layoutDistrict=findViewById(R.id.layoutDistrict);
        layoutBlock=findViewById(R.id.layoutBlock);
        layoutGPs=findViewById(R.id.layoutGPs);
        layoutBlocks=findViewById(R.id.layoutBlocks);
        layoutJurisdiction=findViewById(R.id.layoutJurisdiction);
        layoutJurisdictionName=findViewById(R.id.layoutJurisdictionName);
        txtViewSelectBlocks=findViewById(R.id.txtViewSelectBlocks);
        rootContainer=findViewById(R.id.rootContainer);
        txtViewSelectGps=findViewById(R.id.txtViewSelectGps);
        rootContainerGP=findViewById(R.id.rootContainerGP);
        layoutMultiDistrict=findViewById(R.id.layoutMultiDistrict);
        spinnerState=findViewById(R.id.spinnerState);
        layoutState=findViewById(R.id.layoutState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            isEditProfile= bundle.getString("EDIT_PROFILE");
            name.setText(bundle.getString("NAME")); name.setFocusable(false); name.setVisibility(View.GONE);
            input_mob_no.setText(bundle.getString("MOBILE")); input_mob_no.setFocusable(false); input_mob_no.setVisibility(View.GONE);
            input_mail.setText(bundle.getString("EMAIL_ID")); input_mail.setFocusable(false);  input_mail.setVisibility(View.GONE);
            typs_control_id= bundle.getString("TYPE_OF_CONTROL_ID");

            typeOfControl.setFocusable(false);

            if(bundle.getString("GENDER").equalsIgnoreCase(getString(R.string.Male)))
            {
                radioGender.check(R.id.radioButtonMale);
                for(int i = 0; i < radioGender.getChildCount(); i++){
                    ((RadioButton)radioGender.getChildAt(1)).setEnabled(false);
                }
            }
            else if (bundle.getString("GENDER").equalsIgnoreCase(getString(R.string.Female))){
                radioGender.check(R.id.radioButtonFemale);
                for(int i = 0; i < radioGender.getChildCount(); i++){
                    ((RadioButton)radioGender.getChildAt(0)).setEnabled(false);
                }
            }

            toolbar.setTitle("Edit Profile");
        }

        if(isEditProfile.equals("1")){
            //then hide the terms and condition views
            termcondition.setVisibility(View.INVISIBLE);
            readTermCondition.setVisibility(View.INVISIBLE);
            layoutPassword.setVisibility(View.GONE);
            layoutGender.setVisibility(View.GONE);
            layoutTypeOfControl.setVisibility(View.INVISIBLE);

            btnRegister.setEnabled(true);
            btnRegister.setFocusable(true);
            btnRegister.setClickable(true);
            btnRegister.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        callTypeOfControlApi();

        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_gender=rb.getText().toString();
                   // Toast.makeText(SignUpExtensionOfficer.this, str_gender, Toast.LENGTH_SHORT).show();

                }

            }
        });

        typeOfControl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_typeOfControl= typeOfControl.getSelectedItem().toString();


                if("1".equals(isEditProfile)){
                    int selectionPosition= Integer.parseInt(typs_control_id)-1;
                    typeOfControl.setSelection(selectionPosition);
                    typeOfControl.setEnabled(false);
                    typeOfControl.setClickable(false);

                    callGetDepartmentApi(typs_control_id);
                    return;
                }
                String s= str_typeOfControl;
                String s1=s.substring(1,s.length()-1);

                String s2[]=s1.split("]");

                for(String x : s2){
                    str_typeOfControl="";
                    str_typeOfControl=x;
                    System.out.println("The id to be sent to API::"+ str_typeOfControl);
                    break;
                }

                //When type of control is selected , then it will call getDepartment API
                // callGetDepartmentApi(String.valueOf(position+1));
                callGetDepartmentApi(arrayList_typs_control_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        serviceStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_serviceStatus= serviceStatus.getSelectedItem().toString();

                if(position==1){ // i.e. Retired
                    layoutJurisdiction.setVisibility(View.GONE);
                    layoutJurisdictionName.setVisibility(View.GONE);
                    str_jurisdictionName="";

                }
                else{
                    layoutJurisdiction.setVisibility(View.VISIBLE);
                    layoutJurisdictionName.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        depart_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_depart_type= depart_type.getSelectedItem().toString();

                // calldcarApi(arrayList_department_id.get(position));

                if(arrayList_department_id.get(position).equals("4")){
                    txtDpart.setVisibility(View.VISIBLE);

                }
                else{

                    txtDpart.setVisibility(View.GONE);


                }
                calldcarApi(arrayList_department_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dcar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_dcar= dcar.getSelectedItem().toString();

                if(arrayList_dcarci_id.get(position).equals("14") || arrayList_dcarci_id.get(position).equals("15")||
                        arrayList_dcarci_id.get(position).equals("16")){
                    txtDcar.setVisibility(View.VISIBLE);

                }
                else {
                    txtDcar.setVisibility(View.GONE);

                }
                callPostApi(arrayList_dcarci_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_post= post.getSelectedItem().toString();

                callJurisdictionApi(arrayList_post_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        jurisdiction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_jurisdiction=jurisdiction.getSelectedItem().toString();

                if(getString(R.string.Block).equals(str_jurisdiction)){
                    layoutGPs.setVisibility(View.GONE);
                    if(rootContainer!=null)
                        rootContainer.removeAllViews();

                    if(rootContainerGP!=null)
                        rootContainerGP.removeAllViews();

                    str_jurisdiction="Block";
                }

                if(arrayList_jurisdiction_id.get(position).equals("3")||arrayList_jurisdiction_id.get(position).equals("88")||
                        arrayList_jurisdiction_id.get(position).equals("89")||arrayList_jurisdiction_id.get(position).equals("90")||
                        arrayList_jurisdiction_id.get(position).equals("91")||arrayList_jurisdiction_id.get(position).equals("92")||
                        arrayList_jurisdiction_id.get(position).equals("93")||arrayList_jurisdiction_id.get(position).equals("94")
                )// i.e GP
                {
                    layoutState.setVisibility(View.GONE);
                    layoutMultiDistrict.setVisibility(View.GONE);
                    layoutBlock.setVisibility(View.VISIBLE);
                    txtViewSelectBlocks.setVisibility(View.GONE);
                    layoutGPs.setVisibility(View.VISIBLE);
                    if(rootContainer!=null)
                        rootContainer.removeAllViews();

                    if(rootContainerGP!=null)
                        rootContainerGP.removeAllViews();

                    loadGpsInCheckbox(str_block_id);
                    str_jurisdiction="GP";
                }

                if(arrayList_jurisdiction_id.get(position).equals("4")||arrayList_jurisdiction_id.get(position).equals("95")||
                        arrayList_jurisdiction_id.get(position).equals("96")||arrayList_jurisdiction_id.get(position).equals("97")||
                        arrayList_jurisdiction_id.get(position).equals("98")||arrayList_jurisdiction_id.get(position).equals("99")||
                        arrayList_jurisdiction_id.get(position).equals("100")||arrayList_jurisdiction_id.get(position).equals("101")||
                        arrayList_jurisdiction_id.get(position).equals("102")||arrayList_jurisdiction_id.get(position).equals("103")||
                        arrayList_jurisdiction_id.get(position).equals("104")||arrayList_jurisdiction_id.get(position).equals("105")||
                        arrayList_jurisdiction_id.get(position).equals("106")||arrayList_jurisdiction_id.get(position).equals("107")||
                        arrayList_jurisdiction_id.get(position).equals("108")||arrayList_jurisdiction_id.get(position).equals("109")
                ) // i.e Block
                {
                    layoutMultiDistrict.setVisibility(View.GONE);
                    layoutBlock.setVisibility(View.VISIBLE);
                    txtViewSelectBlocks.setVisibility(View.GONE);
                    layoutGPs.setVisibility(View.GONE);
                    layoutBlocks.setVisibility(View.GONE);
                    str_jurisdictionName=str_block_id;
                    layoutState.setVisibility(View.GONE);

                    str_jurisdiction="Block";
                }

                if(arrayList_jurisdiction_id.get(position).equals("5")||arrayList_jurisdiction_id.get(position).equals("15")||
                        arrayList_jurisdiction_id.get(position).equals("16")||arrayList_jurisdiction_id.get(position).equals("17")||
                        arrayList_jurisdiction_id.get(position).equals("110")||arrayList_jurisdiction_id.get(position).equals("111")||
                        arrayList_jurisdiction_id.get(position).equals("112")||arrayList_jurisdiction_id.get(position).equals("113")||arrayList_jurisdiction_id.get(position).equals("114")||
                        arrayList_jurisdiction_id.get(position).equals("115")||arrayList_jurisdiction_id.get(position).equals("116")||
                        arrayList_jurisdiction_id.get(position).equals("117")||arrayList_jurisdiction_id.get(position).equals("118")||
                   arrayList_jurisdiction_id.get(position).equals("119")) // i.e. Blocks
                {
                    layoutMultiDistrict.setVisibility(View.GONE);
                    txtViewSelectBlocks.setVisibility(View.VISIBLE);
                    layoutBlock.setVisibility(View.GONE);
                    layoutGPs.setVisibility(View.GONE);
                   // spinnerState.setVisibility(View.GONE);
                    loadBlocksInCheckbox(str_district_id);
                    layoutBlocks.setVisibility(View.VISIBLE);
                    layoutState.setVisibility(View.GONE);

                    str_jurisdiction="Blocks";
                }

                if(arrayList_jurisdiction_id.get(position).equals("1")) // i.e District
                {
                    layoutMultiDistrict.setVisibility(View.GONE);
                    layoutDistrict.setVisibility(View.VISIBLE);
                    //spinnerState.setVisibility(View.GONE);
                    layoutState.setVisibility(View.GONE);

                    str_jurisdiction="District";
                }

                if(arrayList_jurisdiction_id.get(position).equals("2")||
                        arrayList_jurisdiction_id.get(position).equals("141")||
                        arrayList_jurisdiction_id.get(position).equals("142")) // i.e Districts
                {
                    layoutMultiDistrict.setVisibility(View.VISIBLE);
                    layoutDistrict.setVisibility(View.GONE);
                    //spinnerState.setVisibility(View.GONE);
                    layoutState.setVisibility(View.GONE);

                    str_jurisdiction="Districts";
                }

                if(arrayList_jurisdiction_id.get(position).equals("6") ||
                        arrayList_jurisdiction_id.get(position).equals("59") ||
                        arrayList_jurisdiction_id.get(position).equals("120")
                        ) // i.e State
                {
                    //spinnerState.setVisibility(View.VISIBLE);
                    layoutState.setVisibility(View.VISIBLE);
                    layoutMultiDistrict.setVisibility(View.GONE);
                    layoutDistrict.setVisibility(View.GONE);

                    str_jurisdiction="State";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gpNotFound="No";
        load_district_in_spinner();
        // SPINNER SELECT

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // strdistrict= district.getSelectedItem().toString();
                str_district_id= String.valueOf(position+1);
                str_jurisdictionName=district.getSelectedItem().toString();

                //Toast.makeText(SignUpFarmer_2.this,"CLICKED AND PASSED districtid " +str_district_id , Toast.LENGTH_SHORT).show();
                if(str_jurisdiction.equals(String.valueOf(2))||str_jurisdiction.equals("Blocks"))
                {
                    loadBlocksInCheckbox(str_district_id);
                }

                    else{
                        str_jurisdiction="";
                    load_block_from_a_district(str_district_id);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // strdistrict= district.getSelectedItem().toString();
                // strdistrict= district.getSelectedItem().toString();
              //  str_district_id= String.valueOf(position+1);
                str_jurisdictionName=spinnerState.getSelectedItem().toString();

                //Toast.makeText(SignUpFarmer_2.this,"CLICKED AND PASSED districtid " +str_district_id , Toast.LENGTH_SHORT).show();
              /*  if(str_jurisdiction.equals(String.valueOf(2)))
                    loadBlocksInCheckbox(str_district_id);
                else
                    load_block_from_a_district(str_district_id);*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // strblock= block.getSelectedItem().toString();
                //str_block_id= String.valueOf(position+1);

                // TASK IS TO GET THE BLOCK_ID FROM DISTRICT_ID ..?
                str_block_id=arraylist_blockid.get(position);
               // load_gps_from_blockid(str_block_id);//str_block_id

                loadGpsInCheckbox(str_block_id);

                //-------------------- new code-----------------


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


     /*   gp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  strgp= gp.getSelectedItem().toString();

                // TASK IS TO GET THE GP_ID FROM BLOCK_ID ..?
                str_gp_id=arraylist_gp_id.get(position);

                //str_gp_id= String.valueOf(position+1);

                // Toast.makeText(SignUpFarmer_2.this, str_gp_id, Toast.LENGTH_SHORT).show();

                str_jurisdictionName=str_gp_id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/





        List<String> expertise_in = Arrays.asList(getResources().getStringArray(R.array.array_expertise_in));
        MultiSpinnerExpertiseIn multiSpinner_expertise_in = (MultiSpinnerExpertiseIn) findViewById(R.id.expertise_in);
        multiSpinner_expertise_in.setItems(expertise_in, getString(R.string.Select), this);


        List<String> machines = Arrays.asList(getResources().getStringArray(R.array.array_district));
        MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
        multiSpinner.setItems(machines, getString(R.string.Select), this);

     /*   expertise_in.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_expertise_in= expertise_in.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        if(isEditProfile.equals("0")) //donot load pop when it is edit profile
        loadPopUp();
    }

    private void loadGpsInCheckbox(String str_block_id) {
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


                            final List<String> gpList = new ArrayList<>(gpl);

                            if(rootContainerGP!=null)
                                rootContainerGP.removeAllViews();

                            //Dynamic add of checkbox
                            int checkbox_position;
                            str_multiple_gp="";
                            for ( int i=0;i<gpList.size();i++){
                                checkBoxGPs=new CheckBox(SignUpExtensionOfficer.this);
                                checkBoxGPs.setId(i);
                                checkBoxGPs.setText(gpList.get(i));

                                rootContainerGP.addView(checkBoxGPs);

                                checkbox_position=i;
                                final int finalCheckbox_position = checkbox_position;
                                checkBoxGPs.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        str_jurisdictionName="";
                                        str_multiple_gp=str_multiple_gp+","+gpList.get(finalCheckbox_position);
                                        str_jurisdictionName=str_multiple_gp;
                                        Toast.makeText(SignUpExtensionOfficer.this,str_jurisdictionName , Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }




                        } catch (JSONException e)
                        { //JSONException
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(rootContainerGP!=null)
                    rootContainerGP.removeAllViews();

                Alerter.create(SignUpExtensionOfficer.this)
                        .setTitle(getString(R.string.no_gp))
                        .show();


                // gp.setVisibility(View.GONE);
                //  layoutGP.setVisibility(View.GONE);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadBlocksInCheckbox(String districtid) {



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
                                //bid =o.getString("district_id");
                                bid =o.getString("block_id");


                                al.add(name);
                                arraylist_blockid.add(bid);

                            }


                            //  final List<String> blockList = new ArrayList<>(Arrays.asList(strarrayblocks));
                            final List<String> blockList = new ArrayList<>(al);

                            if(rootContainer!=null)
                                rootContainer.removeAllViews();

                            //Dynamic add of checkbox
                             int checkbox_position;
                             str_multiple_blocks="";
                            for ( int i=0;i<blockList.size();i++){
                                checkBox=new CheckBox(SignUpExtensionOfficer.this);
                                checkBox.setId(i);
                                checkBox.setText(blockList.get(i));

                                rootContainer.addView(checkBox);

                                checkbox_position=i;
                                final int finalCheckbox_position = checkbox_position;
                                checkBox.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        str_jurisdictionName="";
                                        str_multiple_blocks=str_multiple_blocks+","+blockList.get(finalCheckbox_position);
                                        str_jurisdictionName=str_multiple_blocks;
                                        Toast.makeText(SignUpExtensionOfficer.this,str_jurisdictionName , Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }



                        } catch (JSONException e) {//JSONException
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /* progressDialog.dismiss();*/
                //  Toast.makeText(getApplicationContext(),"Error while loading",Toast.LENGTH_LONG).show();
                Alerter.create(SignUpExtensionOfficer.this)
                        .setTitle(getString(R.string.Network_Error))
                        .show();

                onBackPressed();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onItemsSelectedExpertiseIn(boolean[] selected, String expertise_in) {
        str_expertise_in=expertise_in;
    }

    @Override
    public void onItemsSelected(boolean[] selected, String district) {
        str_jurisdictionName=district;
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
                                    SignUpExtensionOfficer.this,R.layout.spinner_item,districtList);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            district.setAdapter(spinnerArrayAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /* progressDialog.dismiss();*/
                // Toast.makeText(getApplicationContext(),"Error while loading",Toast.LENGTH_LONG).show();
               /* Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), "Error while loading", Snackbar.LENGTH_LONG);

                snackbar.show();*/

                Alerter.create(SignUpExtensionOfficer.this)
                        .setTitle(getString(R.string.Network_Error))
                        .show();

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
                                //bid =o.getString("district_id");
                                bid =o.getString("block_id");


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
                                    SignUpExtensionOfficer.this,R.layout.spinner_item_2,blockList);

                            spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item_2);
                            block.setAdapter(spinnerArrayAdapter2);



                        } catch (JSONException e) {//JSONException
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /* progressDialog.dismiss();*/
                //  Toast.makeText(getApplicationContext(),"Error while loading",Toast.LENGTH_LONG).show();
                Alerter.create(SignUpExtensionOfficer.this)
                        .setTitle(getString(R.string.Network_Error))
                        .show();

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
                                    SignUpExtensionOfficer.this,R.layout.spinner_item_2,gpList);

                            spinnerArrayAdapter3.setDropDownViewResource(R.layout.spinner_item_2);
                            gp.setAdapter(spinnerArrayAdapter3);
                           // layoutGP.setVisibility(View.VISIBLE);
                            gpNotFound="No";

                        } catch (JSONException e)
                        { //JSONException
                            e.printStackTrace();
                            str_gp_id="0"; // for passing in str_gp_id as zero
                            gpNotFound="Yes";
                         //   layoutGP.setVisibility(View.GONE);
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
                Alerter.create(SignUpExtensionOfficer.this)
                        .setTitle(getString(R.string.no_gp))
                        .show();

                str_gp_id="0"; // for passing in str_gp_id as zero
                gpNotFound="Yes";

                // gp.setVisibility(View.GONE);
              //  layoutGP.setVisibility(View.GONE);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void loadPopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });


        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.extoff_prompt_layout, null);

        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       /* dialog.setTitle("After registration and approval, Please watch the Tutorial video available in 'Setting' to know about different features and use of the app");
        dialog.setMessage("Officers from Agriculture and allied sector (both In-service and Retired) having jurisdiction (working area) of Sub-division or blocks or block or Gram panchayat(s) can register as Extension Officer. \n" +
                " They may be from State or Central Govt, Private sector, Educational Instute, Financial institutions, NGO etc.");
       */ dialog.show();

    }

    public void getData()
    {
     //   String url ="http://demo.ratnatechnology.co.in/farmerapp/api/user/getExtensionOfficer?user_id="+user_id; Doubt
        String url =Config.baseUrl+"user/getExtensionOfficer?user_id="+user_id;

        //creating a string request to send request to the url


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                      //  Toast.makeText(SignUpExtensionOfficer.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject=new JSONObject(response);

                            JSONArray array=jsonObject.getJSONArray("data");

                            for(int i=0;i<array.length();i++) {
                                JSONObject o = array.getJSONObject(i);
                                email = o.getString("email");
                                if(email.equalsIgnoreCase("NULL"))
                                input_mail.setText("--");
                                else
                                input_mail.setText(email);
                              //input_mail.setEnabled(false);

                                salt = o.getString("salt");
                                input_pass.setText(salt);
                            //  input_pass.setEnabled(false);

                                first_name = o.getString("first_name");
                                name.setText(first_name);

                               // name.setEnabled(false);

                                phone = o.getString("phone");
                                input_mob_no.setText(phone);
                                layoutMobileNumber.setVisibility(View.GONE);
                             // input_mob_no.setEnabled(false);

                                gender = o.getString("gender");
                                if (gender.equals("male"))
                                    radioGender.check(R.id.radioButtonMale);
                                else if(gender.equals("female"))
                                    radioGender.check(R.id.radioButtonFemale);


                                //diaabling radiogroup button
                                /*for (int k = 0; k < radioGender.getChildCount(); k++) {
                                    radioGender.getChildAt(k).setEnabled(false);
                                }*/

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(SignUpExtensionOfficer.this,"Error while loading", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }
    public void onClickRegisterBtn(View view)
    {
        str_name=name.getText().toString().trim();
        str_input_mob_no=input_mob_no.getText().toString().trim();
        str_input_pass=input_pass.getText().toString().trim();
        str_input_mail=input_mail.getText().toString().trim();
        if(str_name.equals("")||str_input_mob_no.equals("")||
        (isEditProfile.equals("0")&&str_input_pass.equals(""))||(isEditProfile.equals("0")&&str_gender.equals("")))
        {
           // Toast.makeText(this, "Some field(s) are blank", Toast.LENGTH_SHORT).show();
          /*  Alerter.create(SignUpExtensionOfficer.this)
                    .setTitle(getString(R.string.blanks))
                    .show();*/

            Toast.makeText(this, getString(R.string.blanks), Toast.LENGTH_SHORT).show();
        }
        else if (!Utils.isValidEmail(str_input_mail,SignUpExtensionOfficer.this)){
           /* Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Invalid Email", Snackbar.LENGTH_LONG);

            snackbar.show();*/
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();

        }

        else if(!(isValidPhone(str_input_mob_no)))
        {
           /* Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Phone number is Invalid", Snackbar.LENGTH_LONG);

            snackbar.show();*/

           /* Alerter.create(SignUpExtensionOfficer.this)
                    .setTitle(getString(R.string.invalidphone))
                    .show();*/
            Toast.makeText(this, getString(R.string.invalidphone), Toast.LENGTH_SHORT).show();
        }
        else if(str_input_pass.length()<6&&str_input_pass.length()>8)
        {
           Toast.makeText(this, getString(R.string.pass_length), Toast.LENGTH_SHORT).show();

           /* Alerter.create(SignUpExtensionOfficer.this)
                    .setTitle(getString(R.string.pass_length))
                    .show();*/
        }
        else {

            if(isEditProfile.equals("1"))
                updateProfileCall();
            else
                signupCall();

        }

    }

    private void updateProfileCall() {

        if(Utils.hasNetwork(this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.pleasewait));
            progressDialog.show();

            if(txtDpart.getVisibility()==View.VISIBLE)
                str_depart_type=txtDpart.getText().toString();

            if(txtDcar.getVisibility()==View.VISIBLE)
                str_dcar=txtDcar.getText().toString();

            AndroidNetworking.post(Config.extoff_signup)
                    .addBodyParameter("user_id", user_id)
                    .addBodyParameter("name", name.getText().toString().trim())
                    .addBodyParameter("email", input_mail.getText().toString().trim())
                   /*.addBodyParameter("password",input_pass.getText().toString().trim())*/
                    .addBodyParameter("phone", input_mob_no.getText().toString())
                    .addBodyParameter("gender", str_gender)
                    .addBodyParameter("D_C_A_R_C", str_dcar)
                    .addBodyParameter("typs_of_control", str_typeOfControl)
                    .addBodyParameter("department",str_depart_type)
                    .addBodyParameter("service_status", str_serviceStatus)
                    .addBodyParameter("post", str_post)
                    .addBodyParameter("jurisdiction", str_jurisdiction)
                    .addBodyParameter("Jurisdiction_name", str_jurisdictionName)
                    .addBodyParameter("expertise_in", str_expertise_in)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.hide();
                            Toast.makeText(SignUpExtensionOfficer.this, "Update Successful", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.hide();
                            Toast.makeText(SignUpExtensionOfficer.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                            //onBackPressed();
                        }
                    });

        }

    }

    private void signupCall() {

        if(Utils.hasNetwork(this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.pleasewait));
            progressDialog.show();

            if(txtDpart.getVisibility()==View.VISIBLE)
                str_depart_type=txtDpart.getText().toString();

            if(txtDcar.getVisibility()==View.VISIBLE)
                str_dcar=txtDcar.getText().toString();


            AndroidNetworking.post(Config.extoff_signup)
                    .addBodyParameter("name", name.getText().toString().trim())
                    .addBodyParameter("email", input_mail.getText().toString().trim())
                    .addBodyParameter("password",input_pass.getText().toString().trim())
                    .addBodyParameter("phone", input_mob_no.getText().toString())
                    .addBodyParameter("gender", str_gender)
                    .addBodyParameter("D_C_A_R_C", str_dcar)
                    .addBodyParameter("typs_of_control", str_typeOfControl)
                    .addBodyParameter("department",str_depart_type)
                    .addBodyParameter("service_status", str_serviceStatus)
                    .addBodyParameter("post", str_post)
                    .addBodyParameter("jurisdiction", str_jurisdiction)
                    .addBodyParameter("Jurisdiction_name", str_jurisdictionName)
                    .addBodyParameter("expertise_in", str_expertise_in)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.hide();
                           /* Toast.makeText(SignUpExtensionOfficer.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent (SignUpExtensionOfficer.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);*/

                            String message="";
                            boolean status;
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                //str_user_id = String.valueOf(jsonObject.optInt("user_id"));
                                message = jsonObject.optString("message");
                                status = jsonObject.optBoolean("status");
                                Toast.makeText(SignUpExtensionOfficer.this, message, Toast.LENGTH_SHORT).show();

                                if (status) {
                                    Toast.makeText(SignUpExtensionOfficer.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpExtensionOfficer.this, Login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }

                            } catch (JSONException ex) {
                                ex.printStackTrace();
                                progressDialog.hide();
                                try {
                                    JSONObject jsonObject1 = new JSONObject(response);
                                    message = jsonObject1.optString("message");
                                    Toast.makeText(SignUpExtensionOfficer.this, message, Toast.LENGTH_SHORT).show();
                                } catch (JSONException ex1) {
                                    ex.printStackTrace();

                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.hide();
                            Toast.makeText(SignUpExtensionOfficer.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                            //onBackPressed();
                        }
                    });

        }


    }

    public static boolean isValidPhone(String phone)
    {
        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);

        char ch=phone.charAt(0);
        int flag=0;
        if (ch=='9'||ch=='8'||ch=='7')
        {
            if (matcher.matches())
            {
                flag=1;
                //return true;
            }
        }

        else{
            flag=0;
            // return false;
        }
        if (flag==1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void callTypeOfControlApi() {

        if(arrayList_typs_control_id.size()>0){
            arrayList_typs_control_id.clear();
        }
        final ArrayList<String> arrayList_type_of_control= new ArrayList<String>();

        //code to check what  is the language selected , if odia then load odia districts in spinner
        String langdata = Prefs.getString("language", "");

        if(langdata.equals("or"))
            isOdia="true";

        StringRequest stringRequest;
        RequestQueue queue;

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = Config.getTypeOfControl;


        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String typs_control_id=jsonObject1.optString("typs_control_id");
                                String typs_control_name=jsonObject1.optString("typs_control_name");
                                String odia_typs_control=jsonObject1.optString("odia_typs_control");

                                if(isOdia.equals("true"))
                                    arrayList_type_of_control.add("["+typs_control_id+"]"+odia_typs_control);
                                else
                                    arrayList_type_of_control.add("["+typs_control_id+"]"+typs_control_name);

                                arrayList_typs_control_id.add(typs_control_id);
                            }


                            final List<String> type_of_control_list = new ArrayList<>(arrayList_type_of_control);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpExtensionOfficer.this,R.layout.spinner_item,type_of_control_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            typeOfControl.setAdapter(spinnerArrayAdapter);


                            callServiceStatusAPI();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();


            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void callServiceStatusAPI() {

        final ArrayList<String> arrayList_service_status_list= new ArrayList<String>();

        //code to check what  is the language selected , if odia then load odia districts in spinner
        String langdata = Prefs.getString("language", "");

        if(langdata.equals("or"))
            isOdia="true";

        StringRequest stringRequest;
        RequestQueue queue;

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = Config.getServiceStatus;


        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String service_status_id=jsonObject1.optString("service_status_id");
                                String service_status_name=jsonObject1.optString("service_status_name");
                                String group_id=jsonObject1.optString("group_id");

                                if(isOdia.equals("true"))
                                    arrayList_service_status_list.add(service_status_name);
                                else
                                    arrayList_service_status_list.add(service_status_name);
                            }


                            final List<String> service_status_list = new ArrayList<>(arrayList_service_status_list);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpExtensionOfficer.this,R.layout.spinner_item,service_status_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            serviceStatus.setAdapter(spinnerArrayAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();


            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void callGetDepartmentApi(String typs_control_id) {

        if(arrayList_department_id.size()>0)
            arrayList_department_id.clear();

        final ArrayList<String> arrayList_department= new ArrayList<String>();

        //code to check what  is the language selected , if odia then load odia districts in spinner
        String langdata = Prefs.getString("language", "");

        if(langdata.equals("or"))
            isOdia="true";

        StringRequest stringRequest;
        RequestQueue queue;

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = Config.getDepartment+typs_control_id;


        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                department_id=jsonObject1.optString("department_id");
                                String department_name=jsonObject1.optString("department_name");
                                String department_odia_name=jsonObject1.optString("department_odia_name");
                                String post1_id=jsonObject1.optString("post1_id");

                                if(isOdia.equals("true"))
                                    arrayList_department.add(department_odia_name);
                                else
                                    arrayList_department.add(department_name);

                                arrayList_department_id.add(department_id);
                            }


                            final List<String> department_list = new ArrayList<>(arrayList_department);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpExtensionOfficer.this,R.layout.spinner_item,department_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            depart_type.setAdapter(spinnerArrayAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();


            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void calldcarApi(String department_id) {

        if(arrayList_dcarci_id.size()>0)
            arrayList_dcarci_id.clear();

        final ArrayList<String> arrayList_dcar= new ArrayList<String>();

        //code to check what  is the language selected , if odia then load odia districts in spinner
        String langdata = Prefs.getString("language", "");

        if(langdata.equals("or"))
            isOdia="true";

        StringRequest stringRequest;
        RequestQueue queue;

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = Config.getDcar+department_id+"&group_id="+str_group_id;


        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String dcarci_id=jsonObject1.optString("dcarci_id");
                                String dcarci_name=jsonObject1.optString("dcarci_name");
                                String dcarci_odia_name=jsonObject1.optString("dcarci_odia_name");
                                String post3_id=jsonObject1.optString("post3_id");


                                if(isOdia.equals("true"))
                                    arrayList_dcar.add(dcarci_odia_name);
                                else
                                    arrayList_dcar.add(dcarci_name);

                                arrayList_dcarci_id.add(dcarci_id);
                            }


                            final List<String> dcar_list = new ArrayList<>(arrayList_dcar);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpExtensionOfficer.this,R.layout.spinner_item,dcar_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            dcar.setAdapter(spinnerArrayAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();


            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void callPostApi(String dcarci_id) {


        if(arrayList_post_id.size()>0)
            arrayList_post_id.clear();

        final ArrayList<String> arrayList_post= new ArrayList<String>();

        //code to check what  is the language selected , if odia then load odia districts in spinner
        String langdata = Prefs.getString("language", "");

        if(langdata.equals("or"))
            isOdia="true";

        StringRequest stringRequest;
        RequestQueue queue;

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = Config.getPost+"post4_id="+dcarci_id+"&group_id="+ str_group_id;


        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String post_id=jsonObject1.optString("post_id");
                                String post_name=jsonObject1.optString("post_name");
                                String post_odia_name=jsonObject1.optString("post_odia_name");
                                String post4_id=jsonObject1.optString("post4_id");
                                String group_id=jsonObject1.optString("group_id");

                                if(isOdia.equals("true"))
                                    arrayList_post.add(post_odia_name);
                                else
                                    arrayList_post.add(post_name);

                                arrayList_post_id.add(post_id);
                            }


                            final List<String> post_list = new ArrayList<>(arrayList_post);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpExtensionOfficer.this,R.layout.spinner_item,post_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            post.setAdapter(spinnerArrayAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();


            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void callJurisdictionApi(String post_id) {

        if(arrayList_jurisdiction_id.size()>0)
            arrayList_jurisdiction_id.clear();

        final ArrayList<String> arrayList_jurisdiction= new ArrayList<String>();

        //code to check what  is the language selected , if odia then load odia districts in spinner
        String langdata = Prefs.getString("language", "");

        if(langdata.equals("or"))
            isOdia="true";

        StringRequest stringRequest;
        RequestQueue queue;

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = Config.getJurisdiction+post_id;


        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                jurisdiction_id=jsonObject1.optString("jurisdiction_id");
                                String jurisdiction_name=jsonObject1.optString("jurisdiction_name");
                                String jurisdiction_odia_name=jsonObject1.optString("jurisdiction_odia_name");
                                String post5_id=jsonObject1.optString("post5_id");


                                if(isOdia.equals("true"))
                                    arrayList_jurisdiction.add(jurisdiction_odia_name);
                                else
                                    arrayList_jurisdiction.add(jurisdiction_name);

                                 arrayList_jurisdiction_id.add(jurisdiction_id);
                            }


                            final List<String> jurisdiction_list = new ArrayList<>(arrayList_jurisdiction);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpExtensionOfficer.this,R.layout.spinner_item,jurisdiction_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            jurisdiction.setAdapter(spinnerArrayAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpExtensionOfficer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();


            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
