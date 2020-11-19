package com.odishakrushi.Message;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import com.odishakrushi.NavDrawer.NavDrawerBusiness;
import com.odishakrushi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupMessaging extends Fragment {

    EditText edit_phone;
    String flag="";
    Spinner spinnerValues;
    EditText message;
    Button btnOK;
    String str_message="",str_dropdown_id="",url="";
    String data="";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";
    public GroupMessaging() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_messaging, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

       // getActivity().setTitle(getString(R.string.Group_Messaging));
        getActivity().setTitle(getString(R.string.Message));

        //GETTING USER_ID
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);

        user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", user_id);

        edit_phone=getView().findViewById(R.id.edit_phone);
        spinnerValues=getView().findViewById(R.id.spinnerValues);
        message=getView().findViewById(R.id.message);
        btnOK=getView().findViewById(R.id.btnOK);

        spinnerValues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             //   str_spinnerValues=spinnerValues.getSelectedItem().toString();
                str_dropdown_id= String.valueOf(position+1);
                // Toast.makeText(SignUpResourcePerson_2.this, str_agriculture_expertisein_subgroup, Toast.LENGTH_SHORT).show();
                switch (str_dropdown_id)
                {
                    case "1":
                 //       url="http://demo.ratnatechnology.co.in/farmerapp/api/message/OfficersOfDepartmentInMyJurisdiction"; Doubt
                        url= Config.baseUrl+ "message/OfficersOfDepartmentInMyJurisdiction";
                        flag="";
                        edit_phone.setVisibility(View.INVISIBLE);
                        break;

                    case "2":
                        //url="http://demo.ratnatechnology.co.in/farmerapp/api/message/setfarmersOfMyJurisdiction"; Doubt
                        url=Config.baseUrl+"message/setfarmersOfMyJurisdiction";
                        flag="";
                        edit_phone.setVisibility(View.INVISIBLE);
                        break;

                    case "3":
                        //url="http://demo.ratnatechnology.co.in/farmerapp/api/message/AllExpertOfMyDomain"; Doubt
                        url=Config.baseUrl+"message/AllExpertOfMyDomain";
                        flag="";
                        edit_phone.setVisibility(View.INVISIBLE);
                        break;

                    case "4":
                        //url="http://demo.ratnatechnology.co.in/farmerapp/api/message/allbusinessmen"; Doubt
                        url=Config.baseUrl+"message/allbusinessmen";
                        flag="";
                        edit_phone.setVisibility(View.INVISIBLE);
                        break;

                    case "5":
                        //url="http://demo.ratnatechnology.co.in/farmerapp/api/message/custome_msg"; Doubt
                        url=Config.baseUrl+"message/custome_msg";
                        flag="custom";
                        edit_phone.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data="";


              /*switch (str_dropdown_id)
              {
                  case "1":
                  url="http://demo.ratnatechnology.co.in/farmerapp/api/message/OfficersOfDepartmentInMyJurisdiction";

                  break;

                  case "2":
                      url="http://demo.ratnatechnology.co.in/farmerapp/api/message/setfarmersOfMyJurisdiction";

                      break;

                  case "3":
                      url="http://demo.ratnatechnology.co.in/farmerapp/api/message/AllExpertOfMyDomain";

                      break;

                  case "4":
                      url="http://demo.ratnatechnology.co.in/farmerapp/api/message/allbusinessmen";

                      break;

                  case "5":
                      url="http://demo.ratnatechnology.co.in/farmerapp/api/message/custome_msg";
                      break;
              }*/

                str_message=message.getText().toString();

              if(!str_message.equals(""))
              {

                  if(flag.equals("custom")&& (edit_phone.getText().toString().length()==10))
                  {
                      AndroidNetworking.post(url)
                              .addBodyParameter("phone", edit_phone.getText().toString())
                              .addBodyParameter("questext", str_message)
                              .addBodyParameter("user_id", user_id)
                              .setTag("custom_messages")
                              .setPriority(Priority.HIGH)
                              .build()
                              .getAsString(new StringRequestListener() {

                                  @Override
                                  public void onResponse(String response) {

                                      try {
                                          JSONObject jsonObject = new JSONObject(response);
                                          data = jsonObject.getString("data");

                                      } catch (JSONException e) {
                                          e.printStackTrace();
                                      }
                                      if(data.equals("No user available"))
                                      {
                                          Alerter.create(getActivity())
                                                  .setTitle("Message Sent")
                                                  .show();
                                      }
                                      else
                                      {
                                           Alerter.create(getActivity())
                                              .setTitle(data)
                                              .show();
                                      }


                                  }

                                  @Override
                                  public void onError(ANError error) {
                                      // handle error
                                      Alerter.create(getActivity())
                                              .setTitle(getString(R.string.Network_Error))
                                              .show();
                                  }
                              });
                  }
                  else
                      {
                              AndroidNetworking.post(url)
                                      .addBodyParameter("extension_user_id", user_id)
                                      .addBodyParameter("questext", str_message)
                                      .setTag("extoff_messages")
                                      .setPriority(Priority.HIGH)
                                      .build()
                                      .getAsString(new StringRequestListener() {

                                          @Override
                                          public void onResponse(String response) {

                                              try {
                                                  JSONObject jsonObject = new JSONObject(response);
                                                  data = jsonObject.getString("data");
                                              } catch (JSONException e) {
                                                  e.printStackTrace();
                                              }

                                              Alerter.create(getActivity())
                                                      .setTitle(data)
                                                      .show();
                                          }

                                          @Override
                                          public void onError(ANError error) {
                                              // handle error
                                              Alerter.create(getActivity())
                                                      .setTitle(getString(R.string.Network_Error))
                                                      .show();
                                          }
                                      });
                  }
              }
            }
        });


    }
}
