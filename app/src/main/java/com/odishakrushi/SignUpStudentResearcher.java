package com.odishakrushi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Browser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import static com.odishakrushi.Config.ODIA_DISTRICTS;

public class SignUpStudentResearcher extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    CheckBox termcondition;          //###############
    Button register;                    //###################

    TextView readTermCondition;             //###########

    Dialog dialog,dialog2;
    TextView text;
    Button dialogButton,btnOK;
    EditText editOtherHighest,editOtherSpecialization;
    String str_other_qualification="",str_other_specialization="";


    RadioGroup restatus,radioGender;
    String str_edu_status="",str_gender="",str_qualification="",str_other_highest_qualification="",str_specialization="",str_institute="",str_other_detail="";
    LinearLayout layoutstudycontinuing;
    AppCompatEditText fname,/*lname,*/input_mob_no,input_mail,input_pass;
    LinearLayout layoutMainDistrict,layoutDistrict,layoutDistrictOnlyForEditProfile,layoutGender;
    Spinner spinnerspecialization, district;
    EditText editinsitutename,editotherdetails;
    TextView txtDistrict;
    String str_email="",strfname="",strlname="",strpass="",strmob="",str_district_id="";
    private static final String URL_DATA = Config.baseUrl+"commons/districts/29";
    ArrayList<String> arraylist_districtid=new ArrayList<String>();

    // new layout;
    ProgressDialog progressDialog;
    Spinner spinnerhq;
    String isEditProfile="0";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_sign_up_student_researcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });




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

        //###########################################
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




        //#######################################
        // custom dialog
        dialog = new Dialog(this);
        dialog2 = new Dialog(this);

        layoutstudycontinuing=findViewById(R.id.layoutstudycontinuing);
        fname=findViewById(R.id.fname);
       // lname=findViewById(R.id.lname);
        input_pass=findViewById(R.id.input_pass);
        input_mob_no=findViewById(R.id.input_mob_no);
        input_mail=findViewById(R.id.input_mail);
        editinsitutename=findViewById(R.id.editinsitutename);
        editotherdetails=findViewById(R.id.editotherdetails);
        spinnerhq=findViewById(R.id.spinnerhq);
        district=(Spinner) findViewById(R.id.district);
        layoutMainDistrict= findViewById(R.id.layoutMainDistrict);
        layoutDistrict= findViewById(R.id.layoutDistrict);
        layoutDistrictOnlyForEditProfile= findViewById(R.id.layoutDistrictOnlyForEditProfile);
        layoutGender= findViewById(R.id.layoutGender);
        txtDistrict= findViewById(R.id.txtDistrict);
        spinnerspecialization=findViewById(R.id.spinnerspecialization);
        radioGender=findViewById(R.id.radioGender);
        restatus=findViewById(R.id.restatus);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {

            isEditProfile= bundle.getString("EDIT_PROFILE");
            fname.setText(bundle.getString("NAME")); fname.setFocusable(false); fname.setVisibility(View.GONE);
            input_mob_no.setText(bundle.getString("MOBILE")); input_mob_no.setFocusable(false); input_mob_no.setVisibility(View.GONE);
            input_mail.setText(bundle.getString("EMAIL_ID")); input_mail.setFocusable(false); input_mail.setVisibility(View.GONE);
            txtDistrict.setText(bundle.getString("DISTRICT"));

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
            layoutDistrict.setVisibility(View.GONE);
            layoutDistrictOnlyForEditProfile.setVisibility(View.GONE);
            layoutMainDistrict.setVisibility(View.GONE);

            input_pass.setVisibility(View.GONE);
            layoutGender.setVisibility(View.GONE);
            layoutDistrict.setVisibility(View.GONE);

            register.setEnabled(true);
            register.setFocusable(true);
            register.setClickable(true);
            register.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            sharedpreferences = getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            user_id=sharedpreferences.getString("FLAG", null);
//        Log.d("user_id:",user_id);
            editor.commit(); // commit changes
        }

        load_district_in_spinner();
        // SPINNER SELECT

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // strdistrict= district.getSelectedItem().toString();
                str_district_id= String.valueOf(position+1);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerhq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_qualification= spinnerhq.getSelectedItem().toString();
                //str_qualification= ""+position;


                if(position==5)
                {

                    dialog.setContentView(R.layout.custom);
                    dialog.setTitle("Others");

                    // set the custom dialog components - text, image and button
                    editOtherHighest = dialog.findViewById(R.id.editOtherHighest);



                    dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            str_other_highest_qualification= editOtherHighest.getText().toString();

                            Toast.makeText(SignUpStudentResearcher.this, str_other_highest_qualification, Toast.LENGTH_SHORT).show();
                            if(!str_other_highest_qualification.equals(""))
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerspecialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               str_specialization= spinnerspecialization.getSelectedItem().toString();
               // str_specialization= ""+position;

                if(position==13)
                {
                    dialog2.setContentView(R.layout.custom2);
                    dialog2.setTitle("Specialization");

                    // set the custom dialog components - text, image and button
                    editOtherSpecialization = dialog2.findViewById(R.id.editOtherSpecialization);



                    btnOK = (Button) dialog2.findViewById(R.id.btnOK);
                    // if button is clicked, close the custom dialog
                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            str_other_specialization= editOtherSpecialization.getText().toString();

                            Toast.makeText(SignUpStudentResearcher.this, str_other_specialization, Toast.LENGTH_SHORT).show();
                            if(!str_other_specialization.equals(""))
                                dialog2.dismiss();
                        }
                    });

                    dialog2.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_gender=rb.getText().toString();
                    Toast.makeText(SignUpStudentResearcher.this, str_gender, Toast.LENGTH_SHORT).show();


                }

            }
        });

        restatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                  // String text=rb.getText().toString();
                     //Toast.makeText(SignUpStudentResearcher.this, str_edu_status, Toast.LENGTH_SHORT).show();
/*
                    if(text.equals("Study Continuing")||text.equals("Passed Out"))
                    {
                        layoutstudycontinuing.setVisibility(View.VISIBLE);
                    }
                    if (text.equals("Study Continuing"))
                    {
                        str_edu_status="1";
                    }
                    else if(text.equals("Passed Out"))
                    {
                        str_edu_status="2";
                    }*/

                     str_edu_status=rb.getText().toString();
                    if(str_edu_status.equals("Study Continuing")||str_edu_status.equals("Passed Out"))
                    {
                        layoutstudycontinuing.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

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
                                    SignUpStudentResearcher.this,R.layout.spinner_item,districtList);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            district.setAdapter(spinnerArrayAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpStudentResearcher.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /* progressDialog.dismiss();*/

                Toast.makeText(SignUpStudentResearcher.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                onBackPressed();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void registerFunc()
    {
        strfname=fname.getText().toString().trim();
       // strlname=lname.getText().toString().trim();
        str_institute=editinsitutename.getText().toString().trim();
        str_other_detail=editotherdetails.getText().toString().trim();
        str_email=input_mail.getText().toString().trim();
        strpass=input_pass.getText().toString().trim();
        strmob=input_mob_no.getText().toString().trim();
        if(/*strfname.equals("")||strlname.equals("")||*/str_institute.equals("")||
                (isEditProfile.equals("0")&&strpass.equals(""))||strmob.equals(""))
        {
            /*Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "One or More Field(s) Blank", Snackbar.LENGTH_LONG);

            snackbar.show();*/

            Toast.makeText(this, "One or More Field(s) Blank", Toast.LENGTH_SHORT).show();

        }

        else if (!Utils.isValidEmail(str_email,SignUpStudentResearcher.this)){
           /* Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Invalid Email", Snackbar.LENGTH_LONG);

            snackbar.show();*/
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();

        }
        else if(!(isValidPhone(strmob)))
        {
           /* Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Phone number is Invalid", Snackbar.LENGTH_LONG);

            snackbar.show();*/

            Toast.makeText(this, "Phone number is Invalid", Toast.LENGTH_SHORT).show();

        }
        else if(strpass.length()<6&&strpass.length()>8)
        {
            Toast.makeText(this, "Length error(min 6 and max 8 characters)", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(isEditProfile.equals("1"))
                updateProfileCall();
            else
                signupCall();
        }
    }
    private void signupCall() {

        if(Utils.hasNetwork(this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.pleasewait));
            progressDialog.show();


            AndroidNetworking.post(Config.sturesignup)
                    .addBodyParameter("first_name", fname.getText().toString())
                    .addBodyParameter("email", input_mail.getText().toString())
                    .addBodyParameter("password",input_pass.getText().toString() )
                    .addBodyParameter("phone", input_mob_no.getText().toString())
                    .addBodyParameter("gender", str_gender)
                    .addBodyParameter("education_status",str_edu_status )
                    .addBodyParameter("highest_qualification", str_qualification)
                    .addBodyParameter("other_highest_qualification", str_other_highest_qualification)
                    .addBodyParameter("other_detail", editotherdetails.getText().toString())
                    .addBodyParameter("specialization", str_specialization)
                    .addBodyParameter("other_specialization", str_other_specialization)
                    .addBodyParameter("university_college",  editinsitutename.getText().toString())
                    .addBodyParameter("district", str_district_id )
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
                                    Toast.makeText(SignUpStudentResearcher.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent (SignUpStudentResearcher.this, Login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(SignUpStudentResearcher.this, message, Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.hide();

                            //Toast.makeText(SignUpStudentResearcher.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                            //onBackPressed();
                            Log.d( "Error",anError.getErrorBody());
                            Log.d( "Error",anError.getErrorDetail());
                            Log.d( "Error", String.valueOf(anError.getResponse()));
                        }
                    });

        }

    }

    private void updateProfileCall() {

        if(Utils.hasNetwork(this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.pleasewait));
            progressDialog.show();


            AndroidNetworking.post(Config.sturesignup)
                    .addBodyParameter("user_id", user_id)
                  /*  .addBodyParameter("name", fname.getText().toString())
                    .addBodyParameter("email", input_mail.getText().toString())*/
                    //.addBodyParameter("password",input_pass.getText().toString() )
                  /*  .addBodyParameter("phone", input_mob_no.getText().toString())
                    .addBodyParameter("gender", str_gender)*/
                    .addBodyParameter("education_status",str_edu_status )
                    .addBodyParameter("highest_qualification", str_qualification)
                    .addBodyParameter("other_VIhighest_qualification", str_other_highest_qualification)
                    .addBodyParameter("other_detail", editotherdetails.getText().toString())
                    .addBodyParameter("specialization", str_specialization)
                    .addBodyParameter("other_specialization", str_other_specialization)
                    .addBodyParameter("university_college",  editinsitutename.getText().toString())
                   /* .addBodyParameter("district", str_district_id )*/
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.hide();


                            try {
                                JSONObject jsonObject =new JSONObject(response);
                                boolean status = jsonObject.optBoolean("status");
                                String message = jsonObject.optString("message");

                                Toast.makeText(SignUpStudentResearcher.this, message, Toast.LENGTH_SHORT).show();
                                onBackPressed();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.hide();
                            Toast.makeText(SignUpStudentResearcher.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                            onBackPressed();
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


}
