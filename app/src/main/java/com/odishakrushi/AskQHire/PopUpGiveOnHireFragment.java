package com.odishakrushi.AskQHire;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import com.odishakrushi.Adapter.CustomAdapter;
import com.odishakrushi.Adapter.CustomAdapterAreaOfOperation;
import com.odishakrushi.Ask_Hire;
import com.odishakrushi.Config;
import com.odishakrushi.FullscreenDialogFragment;
import com.odishakrushi.R;
import com.odishakrushi.SignUpFarmer_2;


public class PopUpGiveOnHireFragment extends DialogFragment implements
        AdapterView.OnItemSelectedListener  {

    Spinner subgroup;
    RadioGroup radioAreaOfOperation;

    EditText edittext;
    TextView txtdate,txtedate;
    String strStartDate="",strEndDate="";
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button btnSubmit,btnUpdate;

    String[] strarray_machines={"Tractor","Power tiller","Rotavator","Plough","Ridger","Leveller",
            "seed drill","transplanter","weeder","Sprayer","Thresher","Cleaner","Grader","Combine",
            "Pumps","Parboiling unit","rice mill","Decorticator","Tools for fruit and vegetable","others"};



    String strMachine="",str_area_of_operation="";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    ImageButton ticksave;

    // variable used for update
    String str_id="",str_machine_name="",str_description="",str_start_date="",str_end_date="",str_update_status="0";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_pop_up_give_on_hire, container, false);
        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        Bundle bundle = this.getArguments();
        if(bundle!=null){

          /*  str_id = bundle.getString("ID");
            str_machine_name = bundle.getString("MACHINE_NAME");
            str_description = bundle.getString("DESCRIPTION"); edittext.setText(str_description);
            str_start_date = bundle.getString("START_DATE"); txtdate.setText(str_start_date);
            str_end_date = bundle.getString("END_DATE");txtedate.setText(str_end_date);*/
            str_update_status = bundle.getString("UPDATE");


        }



        subgroup=rootView.findViewById(R.id.subgroup);
        subgroup.setOnItemSelectedListener(this);
        radioAreaOfOperation=rootView.findViewById(R.id.radioAreaOfOperation);
        edittext=rootView.findViewById(R.id.edittext);

        txtdate=rootView.findViewById(R.id.txtdate);
        txtedate=rootView.findViewById(R.id.txtedate);

        ticksave=rootView.findViewById(R.id.ticksave);
        ticksave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit();
            }
        });

        btnSubmit=rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit();
            }
        });

        btnUpdate=rootView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdate();
            }
        });

        if(str_update_status.equals("1")){
            btnSubmit.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
        }

        //GETTING USER_ID
        sharedpreferences = getContext().getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);
        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        CustomAdapter customAdapter_machines=new CustomAdapter(getContext(),strarray_machines);
        subgroup.setAdapter(customAdapter_machines);


        radioAreaOfOperation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) group.findViewById(checkedId);


                if (null != rb && checkedId > -1) {
                    str_area_of_operation=rb.getText().toString();
                  //  Toast.makeText(getContext(), str_area_of_operation, Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(getContext(), String.valueOf(checkedId), Toast.LENGTH_SHORT).show();

                }

            }
        });


        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                strStartDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                txtdate.setText(strStartDate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                strEndDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                txtedate.setText(strEndDate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        return rootView;
    }

    private void onClickUpdate() {

        //AndroidNetworking.post("http://demo.ratnatechnology.co.in/farmerapp/api/sales/updateHire") Doubt
        AndroidNetworking.post(Config.baseUrl+"sales/updateHire")
                .addBodyParameter("id",str_user_id)
                .addBodyParameter("machine_name",strMachine)
                .addBodyParameter("description",edittext.getText().toString())
                .addBodyParameter("start_date", strStartDate)
                .addBodyParameter("end_date", strEndDate)
                .setTag("updatetag")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {


                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String data=jsonObject.optString("data");
                            Toast.makeText(getContext(),data, Toast.LENGTH_SHORT).show();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                             /*    Intent intent =new Intent( getContext(), Ask_Hire.class);
                                 startActivity(intent);*/

                                 dismiss();

                                }
                            }, 3000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getContext(),"Some thing went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(getContext(),strarray_machines[position] , Toast.LENGTH_LONG).show();
        strMachine=strarray_machines[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClickSubmit(){

       // AndroidNetworking.post("http://demo.ratnatechnology.co.in/farmerapp/api/sales/give_onHire") Doubt
        AndroidNetworking.post(Config.baseUrl+"sales/give_onHire")
                .addBodyParameter("user_id",str_user_id) // "768"
                .addBodyParameter("machine_name",strMachine)  //  "Tractor"
                .addBodyParameter("description",edittext.getText().toString()) // "to hire"
                .addBodyParameter("operational_area", str_area_of_operation) //  "Within block"
                .addBodyParameter("start_date", strStartDate)   //  "1/5/2019"
                .addBodyParameter("end_date", strEndDate)  //  "1/5/2019"
                .setTag("giveonhiretag")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {


                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String data=jsonObject.optString("data");
                            Toast.makeText(getContext(),data, Toast.LENGTH_SHORT).show();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                  /*  Intent intent =new Intent( getContext(), Ask_Hire.class);
                                    startActivity(intent);*/

                                    dismiss();
                                }
                            }, 3000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getContext(),"Some thing went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
