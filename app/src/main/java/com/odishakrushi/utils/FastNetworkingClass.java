package com.odishakrushi.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.odishakrushi.AskQSale.SaleList;
import com.odishakrushi.Config;
import com.odishakrushi.R;

/**
 * Created by RatnaDev008 on 8/16/2018.
 */

public  class FastNetworkingClass {

    public static String message="hey";
    public static String deleteSaleItem(final Context context, final String sale_id,String del_url)
    {
        RequestQueue queue = Volley.newRequestQueue(context); // this = context
        StringRequest postRequest = new StringRequest(Request.Method.POST, del_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            message=jsonObject.getString("message");
                        } catch (JSONException e) {
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(response);
                                message=jsonObject.getString("message");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                      message="Network error";
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("sale_id", sale_id);

                return params;
            }
        };
        queue.add(postRequest);

        return message;

     /*   AndroidNetworking.post(Config.del_allsalesbyuserId)
                .addBodyParameter("sale_id", sale_id)
                .setTag("deleteitem")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("TAG", response.toString());
                        try {
                            message=response.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                message=response.getString("message");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("TAG", error.toString());
                        message="No Network Connection";

                    }
                });

        return message;*/
    }



}
