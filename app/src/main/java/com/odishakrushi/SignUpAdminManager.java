package com.odishakrushi;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Browser;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.pixplicity.easyprefs.library.Prefs;
import com.tapadoo.alerter.Alerter;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.ProfilePic.ProfilePic;

public class SignUpAdminManager extends AppCompatActivity  implements MultiSpinner.MultiSpinnerListener{


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    Spinner district_forstategovt,district_forcentgovt;
    CheckBox termcondition;
    Button register;

    TextView readTermCondition;

    private static final int TIME_OUT = 3000;
    Spinner spinner_adminmanager_posthold,spinner_adminmanager_posthold_stategovt_dept,district,districtCG;
    LinearLayout layout_stategovt,layout_centgovt,line,layoutdistrictjuris;
    RadioGroup radioGroupPostAgriculture,radioGroupPostHorticulture,radioGroupPostSoilConservation,radioGroupPostVeterinary,radioGroupPostFishery;
    EditText editJurisdiction,editCGJurisdiction;


    String str_name,str_mobile,str_pass,str_email,str_jurisdiction="",str_district_cg="";
    String str_post_holds="",str_department;
    String str_stategovt_dept_post="";
    String str_agriculture_post="",str_horticulture_post="",str_soilconservation_post="",
            str_veterinary_post="",str_fishery_post="",str_district="";
    EditText editSpecifyPost,editSpecifyInstitution;
    String str_specifypost,str_specifyinstitution;
    TextView posttext;

    TextView selectAll1,selectAll2;
    CheckBoxGroup<String> checkBoxGroup;
    CheckBoxGroup<String> checkBoxGroup2;

    // New fields
    AppCompatEditText name,input_mob_no,input_mail,input_pass,txtDcar, txtDpart;
    RadioGroup radioGender;
    Spinner typeOfControl,serviceStatus,depart_type,dcar,post,jurisdiction,state,districtSingle;
    ProgressDialog progressDialog;
    String str_gender="",str_typeOfControl="",str_serviceStatus="",str_depart_type="",str_jurisdictionName="",str_dcar="",str_post="";
    LinearLayout layoutGender,layoutState,layoutDistrict,layoutMultiDistrict,layoutJurisdiction,layoutJurisdictionName;

    String isOdia="false";
    String str_group_id="";

    String  department_id="",dcarci_id="",typs_control_id="1";
    ArrayList<String> arrayList_department_id=new ArrayList<String>();
    ArrayList<String> arrayList_typs_control_id=new ArrayList<String>();
    ArrayList<String> arrayList_dcarci_id=new ArrayList<String>();
    ArrayList<String> arrayList_post_id=new ArrayList<String>();
    ArrayList<String> arrayList_jurisdiction_id=new ArrayList<String>();

    String isEditProfile="0";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_sign_up_admin_manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        user_id=sharedpreferences.getString("FLAG", null);
//        Log.d("user_id:",user_id);
        editor.commit(); // commit changes

        str_group_id= Preferences.getGroupID(this);
        /*district_forcentgovt=findViewById(R.id.district_forcentgovt);
        district_forstategovt=findViewById(R.id.district_forstategovt);*/
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
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFunc();
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
      /*  selectAll1=findViewById(R.id.selectAll1);
        selectAll2=findViewById(R.id.selectAll2);
        posttext=findViewById(R.id.posttext);*/
        name=findViewById(R.id.name);
        input_mob_no=findViewById(R.id.input_mob_no);
        input_pass=findViewById(R.id.input_pass);
        input_mail=findViewById(R.id.input_mail);
        txtDpart=findViewById(R.id.txtDpart);
        txtDcar=findViewById(R.id.txtDcar);

        radioGender=findViewById(R.id.radioGender);
        typeOfControl=findViewById(R.id.typeOfControl);
        serviceStatus=findViewById(R.id.serviceStatus);
        depart_type=findViewById(R.id.depart_type);
        dcar=findViewById(R.id.dcar);
        post=findViewById(R.id.post);
        jurisdiction=findViewById(R.id.jurisdiction);
        districtSingle=findViewById(R.id.districtSingle);
        state=findViewById(R.id.state);

        layoutGender=findViewById(R.id.layoutGender);
        layoutState=findViewById(R.id.layoutState);
        layoutDistrict=findViewById(R.id.layoutDistrict);
        layoutMultiDistrict=findViewById(R.id.layoutMultiDistrict);
        layoutJurisdiction=findViewById(R.id.layoutJurisdiction);
        layoutJurisdictionName=findViewById(R.id.layoutJurisdictionName);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            isEditProfile= bundle.getString("EDIT_PROFILE");
            name.setText(bundle.getString("NAME")); name.setFocusable(false); name.setVisibility(View.GONE);
            input_mob_no.setText(bundle.getString("MOBILE")); input_mob_no.setFocusable(false); input_mob_no.setVisibility(View.GONE);
            input_mail.setText(bundle.getString("EMAIL_ID")); input_mail.setFocusable(false); input_mail.setVisibility(View.GONE);
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
            layoutGender.setVisibility(View.GONE);
            input_pass.setVisibility(View.GONE);

            register.setEnabled(true);
            register.setFocusable(true);
            register.setClickable(true);
            register.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        callTypeOfControlApi();

        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_gender=rb.getText().toString();
                    Toast.makeText(SignUpAdminManager.this, str_gender, Toast.LENGTH_SHORT).show();


                }

            }
        });

        typeOfControl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_typeOfControl= typeOfControl.getSelectedItem().toString();

                if(isEditProfile.equals("1")){
                    int selectionPosition= Integer.parseInt(typs_control_id)-1;
                    typeOfControl.setSelection(selectionPosition);
                    typeOfControl.setEnabled(false);
                    typeOfControl.setClickable(false);
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

                if(position==2){ // i.e. NGO

                    layoutJurisdiction.setVisibility(View.VISIBLE);
                    layoutJurisdictionName.setVisibility(View.VISIBLE);
                }
                else {

                    layoutJurisdiction.setVisibility(View.GONE);
                    layoutJurisdictionName.setVisibility(View.GONE);
                    str_jurisdictionName="";
                }
                //When type of control is selected , then it will call getDepartment API
                // callGetDepartmentApi(String.valueOf(position+1));

                Log.d("callGetDepartmentApi ", "position="+""+position + " " +arrayList_typs_control_id.get(position));
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

                if(position==1 ){ // i.e. Retired
                    layoutJurisdiction.setVisibility(View.GONE);
                    layoutJurisdictionName.setVisibility(View.GONE);
                    str_jurisdictionName="";

                }
                else{
                    layoutJurisdiction.setVisibility(View.VISIBLE);
                    layoutJurisdictionName.setVisibility(View.VISIBLE);
                }
                
                String s= str_serviceStatus;
                String s1=s.substring(1,s.length()-1);

                String s2[]=s1.split("]");

                for(String x : s2){
                    str_serviceStatus="";
                    str_serviceStatus=x;
                    System.out.println("The id to be sent to API::"+ str_serviceStatus);
                    break;
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


                if(arrayList_department_id.get(position).equals("4")){
                    txtDpart.setVisibility(View.VISIBLE);
                }
                else{

                    txtDpart.setVisibility(View.GONE);

                }
                String s= str_depart_type;
                String s1=s.substring(1,s.length()-1);

                String s2[]=s1.split("]");

                for(String x : s2){
                    str_depart_type="";
                    str_depart_type=x;
                    System.out.println("The id to be sent to API::"+ str_depart_type);
                    break;
                }


              //  Log.d("calldcarApi ", "position="+""+position + " " +arrayList_department_id.get(position));
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

                String s= str_dcar;
                String s1=s.substring(1,s.length()-1);

                String s2[]=s1.split("]");

                for(String x : s2){
                    str_dcar="";
                    str_dcar=x;
                    System.out.println("The id to be sent to API::"+ str_dcar);
                    break;
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

                String s= str_post;
                String s1=s.substring(1,s.length()-1);

                String s2[]=s1.split("]");

                for(String x : s2){
                    str_post="";
                    str_post=x;
                    System.out.println("The id to be sent to API::"+ str_post);
                    break;
                }

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

                String s= str_jurisdiction;
                String s1=s.substring(1,s.length()-1);

                String s2[]=s1.split("]");

                for(String x : s2){
                    str_jurisdiction="";
                    str_jurisdiction=x;
                    System.out.println("The id to be sent to API::"+ str_jurisdiction);
                    break;
                }

                if(arrayList_jurisdiction_id.get(position).equals("120")||
                        arrayList_jurisdiction_id.get(position).equals("122") ||
                        arrayList_jurisdiction_id.get(position).equals("123") ||
                        arrayList_jurisdiction_id.get(position).equals("125") ||
                        arrayList_jurisdiction_id.get(position).equals("126") ||
                        arrayList_jurisdiction_id.get(position).equals("127") ||
                        arrayList_jurisdiction_id.get(position).equals("128") ||
                        arrayList_jurisdiction_id.get(position).equals("129") ||
                        arrayList_jurisdiction_id.get(position).equals("131")||
                        arrayList_jurisdiction_id.get(position).equals("134")||
                        arrayList_jurisdiction_id.get(position).equals("135")||
                        arrayList_jurisdiction_id.get(position).equals("136")||
                        arrayList_jurisdiction_id.get(position).equals("137")||
                        arrayList_jurisdiction_id.get(position).equals("87")||
                        arrayList_jurisdiction_id.get(position).equals("72")||
                        arrayList_jurisdiction_id.get(position).equals("73")||
                        arrayList_jurisdiction_id.get(position).equals("51")||
                        arrayList_jurisdiction_id.get(position).equals("52")||
                        arrayList_jurisdiction_id.get(position).equals("53")||
                        arrayList_jurisdiction_id.get(position).equals("54")||
                        arrayList_jurisdiction_id.get(position).equals("59")||
                        arrayList_jurisdiction_id.get(position).equals("60")||
                        arrayList_jurisdiction_id.get(position).equals("49")||
                        arrayList_jurisdiction_id.get(position).equals("48")||
                        arrayList_jurisdiction_id.get(position).equals("45")||
                        arrayList_jurisdiction_id.get(position).equals("44")||
                        arrayList_jurisdiction_id.get(position).equals("40")||
                        arrayList_jurisdiction_id.get(position).equals("24")||
                        arrayList_jurisdiction_id.get(position).equals("25")||
                        arrayList_jurisdiction_id.get(position).equals("26")||
                        arrayList_jurisdiction_id.get(position).equals("27"))// i.e State
                {
                    layoutState.setVisibility(View.VISIBLE);
                    layoutDistrict.setVisibility(View.GONE);
                    layoutMultiDistrict.setVisibility(View.GONE);
                }

                if(arrayList_jurisdiction_id.get(position).equals("84")||
                        arrayList_jurisdiction_id.get(position).equals("83")||
                        arrayList_jurisdiction_id.get(position).equals("82")||
                        arrayList_jurisdiction_id.get(position).equals("81")||
                        arrayList_jurisdiction_id.get(position).equals("80")||
                        arrayList_jurisdiction_id.get(position).equals("60")||
                        arrayList_jurisdiction_id.get(position).equals("61")||
                        arrayList_jurisdiction_id.get(position).equals("62")||
                        arrayList_jurisdiction_id.get(position).equals("63")||
                        arrayList_jurisdiction_id.get(position).equals("64")||
                        arrayList_jurisdiction_id.get(position).equals("65")||
                        arrayList_jurisdiction_id.get(position).equals("69")||
                        arrayList_jurisdiction_id.get(position).equals("70")||
                        arrayList_jurisdiction_id.get(position).equals("74")||
                        arrayList_jurisdiction_id.get(position).equals("75")||
                        arrayList_jurisdiction_id.get(position).equals("76")||
                        arrayList_jurisdiction_id.get(position).equals("77")||
                        arrayList_jurisdiction_id.get(position).equals("78")||
                        arrayList_jurisdiction_id.get(position).equals("79"))// i.e District
                {
                    layoutState.setVisibility(View.GONE);
                    layoutDistrict.setVisibility(View.VISIBLE);
                    layoutMultiDistrict.setVisibility(View.GONE);
                }

                if(arrayList_jurisdiction_id.get(position).equals("85")||
                        arrayList_jurisdiction_id.get(position).equals("30")||
                        arrayList_jurisdiction_id.get(position).equals("31")||
                        arrayList_jurisdiction_id.get(position).equals("32")||
                        arrayList_jurisdiction_id.get(position).equals("33")||
                        arrayList_jurisdiction_id.get(position).equals("34")||
                        arrayList_jurisdiction_id.get(position).equals("35")||
                        arrayList_jurisdiction_id.get(position).equals("36")||
                        arrayList_jurisdiction_id.get(position).equals("37")||
                        arrayList_jurisdiction_id.get(position).equals("38")||
                        arrayList_jurisdiction_id.get(position).equals("87")||
                        arrayList_jurisdiction_id.get(position).equals("138")||
                        arrayList_jurisdiction_id.get(position).equals("139")||
                        arrayList_jurisdiction_id.get(position).equals("140")
                ) // i.e. Districts
                {
                    layoutState.setVisibility(View.GONE);
                    layoutDistrict.setVisibility(View.GONE);
                    layoutMultiDistrict.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_jurisdictionName= ""+position; //districtSingle.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        districtSingle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_jurisdictionName= ""+position; //districtSingle.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> machines = Arrays.asList(getResources().getStringArray(R.array.array_district));
        MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
        multiSpinner.setItems(machines, getString(R.string.Select), this);

        if(isEditProfile.equals("0")) //donot load pop when it is edit profile
            loadPopUp();



        // checkbox2 for district-------------------------------------------------


       /* HashMap<CheckBox, String> checkBoxMap2 = new HashMap<>();
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox1D), "Angul/Anugul");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox2D), "Balangir");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox3D), "Balasore-Baleshwar");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox4D), "Bargarh");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox5D), "Baudh");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox6D), "Bhadrak");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox7D), "Cuttack");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox8D), "Deogarh-Debagarh");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox9D), "Dhenkanal");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox10D), "Gajapati");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox11D), "Ganjam");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox12D), "Jagatsinghapur");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox13D), "Jajpur");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox14D), "Jharsuguda");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox15D), "Kalahandi");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox16D), "Kandhamal");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox17D), "Kendrapara");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox18D), "Kendujhar");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox19D), "Khordha");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox20D), "Koraput");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox21D), "Malkangiri");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox22D), "Mayurbhanj");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox23D), "Nabarangpur");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox24D), "Nayagarh");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox25D), "Nuapada");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox26D), "Puri");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox27D), "Rayagada");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox28D), "Sambalpur");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox29D), "Subarnapur");
        checkBoxMap2.put((CheckBox) findViewById(R.id.checkBox30D), "Sundargarh");

*/

/*
        checkBoxGroup2 = new CheckBoxGroup<>(checkBoxMap2, checkedChangeListener);
        checkBoxGroup2.setValues(new ArrayList<String>());

        // Select All checkbox
        selectAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkBoxGroup2.setValues(Arrays.asList( "Angul/Anugul","Balangir","Balasore-Baleshwar","Bargarh","Baudh","Bhadrak","Cuttack","Deogarh-Debagarh","Dhenkanal","Gajapati",
                        "Ganjam","Jagatsinghapur","Jajpur","Jharsuguda","Kalahandi","Kandhamal","Kendrapara","Kendujhar","Khordha","Koraput",
                        "Malkangiri","Mayurbhanj","Nabarangpur","Nayagarh","Nuapada","Puri","Rayagada","Sambalpur","Subarnapur","Sundargarh"));
            }
        });*/
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
                                    arrayList_department.add("["+department_id+"]"+department_odia_name);
                                else
                                    arrayList_department.add("["+department_id+"]"+department_name);

                                arrayList_department_id.add(department_id);
                            }


                            final List<String> department_list = new ArrayList<>(arrayList_department);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpAdminManager.this,R.layout.spinner_item,department_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            depart_type.setAdapter(spinnerArrayAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpAdminManager.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
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
                                String jurisdiction_id=jsonObject1.optString("jurisdiction_id");
                                String jurisdiction_name=jsonObject1.optString("jurisdiction_name");
                                String jurisdiction_odia_name=jsonObject1.optString("jurisdiction_odia_name");
                                String post5_id=jsonObject1.optString("post5_id");


                                if(isOdia.equals("true"))
                                    arrayList_jurisdiction.add("["+jurisdiction_id+"]"+jurisdiction_odia_name);
                                else
                                    arrayList_jurisdiction.add("["+jurisdiction_id+"]"+jurisdiction_name);

                                arrayList_jurisdiction_id.add(jurisdiction_id);
                            }


                            final List<String> jurisdiction_list = new ArrayList<>(arrayList_jurisdiction);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpAdminManager.this,R.layout.spinner_item,jurisdiction_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            jurisdiction.setAdapter(spinnerArrayAdapter);


                            layoutJurisdiction.setVisibility(View.VISIBLE);
                            layoutJurisdictionName.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            layoutJurisdiction.setVisibility(View.GONE);
                            layoutJurisdictionName.setVisibility(View.GONE);

                            str_jurisdiction="";
                            str_jurisdictionName="";

                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpAdminManager.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
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
                                    arrayList_dcar.add("["+dcarci_id+"]"+dcarci_odia_name);
                                else
                                    arrayList_dcar.add("["+dcarci_id+"]"+dcarci_name);

                                arrayList_dcarci_id.add(dcarci_id);
                            }


                            final List<String> dcar_list = new ArrayList<>(arrayList_dcar);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpAdminManager.this,R.layout.spinner_item,dcar_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            dcar.setAdapter(spinnerArrayAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpAdminManager.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpAdminManager.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
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
        String url = Config.getPost+"post4_id="+dcarci_id+"&group_id="+ "7" /*str_group_id*/;


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
                                    arrayList_post.add("["+post_id+"]"+post_odia_name);
                                else
                                    arrayList_post.add("["+post_id+"]"+post_name);

                                arrayList_post_id.add(post_id);
                            }


                            final List<String> post_list = new ArrayList<>(arrayList_post);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpAdminManager.this,R.layout.spinner_item,post_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            post.setAdapter(spinnerArrayAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpAdminManager.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpAdminManager.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();


            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

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
                                    SignUpAdminManager.this,R.layout.spinner_item,type_of_control_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            typeOfControl.setAdapter(spinnerArrayAdapter);


                            callServiceStatusAPI();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            onBackPressed();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpAdminManager.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
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
                                    arrayList_service_status_list.add("["+service_status_id+"]"+service_status_name);
                                else
                                    arrayList_service_status_list.add("["+service_status_id+"]"+service_status_name);
                            }


                            final List<String> service_status_list = new ArrayList<>(arrayList_service_status_list);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    SignUpAdminManager.this,R.layout.spinner_item,service_status_list);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            serviceStatus.setAdapter(spinnerArrayAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpAdminManager.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpAdminManager.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();


            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
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
        View dialogLayout = inflater.inflate(R.layout.admin_prompt_layout, null);

        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*dialog.setTitle("After registration and approval, Please watch the Tutorial video available in 'Setting' to know about different features and use of the app");
        dialog.setMessage("Officers of Agriculture and allied sector (both In-service and Retired) having jurisdiction (working area) of District and above can register as Admin/Manager. \n" +
                " They may be from State or Central Govt, Private sector, Educational Instute, Financial institutions, NGO etc.");
      */  dialog.show();

    }
    /*
    private CheckBoxGroup.CheckedChangeListener<String> checkedChangeListener
            = new CheckBoxGroup.CheckedChangeListener<String>() {
        @Override
        public void onCheckedChange(ArrayList<String> values) {
            // Convert the ArrayList into a String.

            //Commenting out temporariliy
            if(str_post_holds.equals("State Govt"))
            {
                StringBuilder sb = new StringBuilder();
                for (String s : values) {
                    sb.append(s);
                    sb.append(",");
                }
                str_district = sb.toString();
              //  Toast.makeText(SignUpAdminManager.this,"state govt", Toast.LENGTH_SHORT).show();
                Toast.makeText(SignUpAdminManager.this, str_district, Toast.LENGTH_SHORT).show();
                System.out.println(sb.toString());
            }
            else if(str_post_holds.equals("Central Govt"))
            {
                StringBuilder sb2 = new StringBuilder();
                for (String s2 : values) {
                    sb2.append(s2);
                    sb2.append(",");
                }
                str_district_cg = sb2.toString();
               // Toast.makeText(SignUpAdminManager.this,"Central govt", Toast.LENGTH_SHORT).show();
                Toast.makeText(SignUpAdminManager.this, str_district_cg, Toast.LENGTH_SHORT).show();
                System.out.println(sb2.toString());
            }
        }

    };*/

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

    public void registerFunc()
    {
        //validation
        str_name=name.getText().toString().trim();
        str_mobile=input_mob_no.getText().toString().trim();
        str_pass=input_pass.getText().toString().trim();
        str_email=input_mail.getText().toString().trim();

        if(str_name.equals("")||str_mobile.equals("")||(isEditProfile.equals("0")&&str_pass.equals(""))||(isEditProfile.equals("0")&&str_gender.equals("")))
        {
            /*Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "One or More Field Blank", Snackbar.LENGTH_LONG);

            snackbar.show();*/
            Toast.makeText(this, "One or More Field Blank", Toast.LENGTH_SHORT).show();


        }

        else if (!Utils.isValidEmail(str_email,SignUpAdminManager.this)){
           /* Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Invalid Email", Snackbar.LENGTH_LONG);

            snackbar.show();*/
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();

        }

        else if(!(isValidPhone(str_mobile)))
        {
            /*Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Phone number is Invalid", Snackbar.LENGTH_LONG);

            snackbar.show();*/
            Toast.makeText(this, "Phone number is Invalid", Toast.LENGTH_SHORT).show();

        }
        else if(str_pass.length()<6&&str_pass.length()>8)
        {
            Toast.makeText(this, "Length error(min 6 and max 8 characters)", Toast.LENGTH_SHORT).show();
           /* Alerter.create(SignUpAdminManager.this)
                    .setTitle(getString(R.string.pass_length))
                    .show();*/
        }
        else
        {
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

            AndroidNetworking.post(Config.adminmanager_signup)
                    .addBodyParameter("user_id", user_id)
                    /*.addBodyParameter("name", name.getText().toString())
                    .addBodyParameter("email", input_mail.getText().toString())
                    .addBodyParameter("password", input_pass.getText().toString())*/
                    /* .addBodyParameter("phone", input_mob_no.getText().toString())
                     .addBodyParameter("gender", str_gender)*/
                    .addBodyParameter("typs_of_control", str_typeOfControl)
                    .addBodyParameter("service_status", str_serviceStatus)
                    .addBodyParameter("department",str_depart_type)
                    .addBodyParameter("D_C_A_R_C", str_dcar)
                    .addBodyParameter("post", str_post)
                    /* .setBodyParameter("state", properiter_name.getText().toString())
                     .setBodyParameter("block", properiter_name.getText().toString())
                     .setBodyParameter("gp", properiter_name.getText().toString())
                     */.addBodyParameter("jurisdiction", str_jurisdiction)
                    .addBodyParameter("Jurisdiction_name", str_jurisdictionName)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.hide();
                            Toast.makeText(SignUpAdminManager.this, "Update Successful", Toast.LENGTH_SHORT).show();

                            onBackPressed();
                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.hide();
                            Toast.makeText(SignUpAdminManager.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                            //onBackPressed();
                        }
                    });


        }

    }


    @Override
    public void onItemsSelected(boolean[] selected, String districts) {
        str_jurisdictionName=districts;
        // Toast.makeText(this,  " " +districts, Toast.LENGTH_SHORT).show();
    }
    private void signupCall() {

        if(Utils.hasNetwork(this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.pleasewait));
            progressDialog.show();

            usingFastNetworkingLibrary();

        }


    }

    public void usingFastNetworkingLibrary()
    {

        if(txtDpart.getVisibility()==View.VISIBLE)
            str_depart_type=txtDpart.getText().toString();

        if(txtDcar.getVisibility()==View.VISIBLE)
            str_dcar=txtDcar.getText().toString();

        AndroidNetworking.post(Config.adminmanager_signup)
                .addBodyParameter("name", name.getText().toString())
                .addBodyParameter("email", input_mail.getText().toString())
                .addBodyParameter("password", input_pass.getText().toString())
                .addBodyParameter("phone", input_mob_no.getText().toString())
                .addBodyParameter("gender", str_gender)
                .addBodyParameter("typs_of_control", str_typeOfControl)
                .addBodyParameter("service_status", str_serviceStatus)
                .addBodyParameter("department",str_depart_type)
                .addBodyParameter("D_C_A_R_C", str_dcar)
                .addBodyParameter("post", str_post)
                /* .setBodyParameter("state", properiter_name.getText().toString())
                 .setBodyParameter("block", properiter_name.getText().toString())
                 .setBodyParameter("gp", properiter_name.getText().toString())
                 */.addBodyParameter("jurisdiction", str_jurisdiction)
                .addBodyParameter("Jurisdiction_name", str_jurisdictionName)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                          progressDialog.hide();
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if(status)
                            {
                                Toast.makeText(SignUpAdminManager.this, message, Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent (SignUpAdminManager.this, Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SignUpAdminManager.this, message, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                         progressDialog.hide();
                        Toast.makeText(SignUpAdminManager.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });

    }
}
