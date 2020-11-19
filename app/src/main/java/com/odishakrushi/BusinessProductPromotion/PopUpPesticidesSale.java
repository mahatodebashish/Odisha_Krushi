package com.odishakrushi.BusinessProductPromotion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import com.odishakrushi.Config;
import com.odishakrushi.R;
import com.odishakrushi.SignUpFarmer;

public class PopUpPesticidesSale extends Activity{ // Activity

    LinearLayout layoutBrandVariety,layoutSeedSeedling;

    EditText brand,remark;
    RadioGroup radioSubsidy,radioSeedVariety;
    TextView txtProductType;
    String str_product_type="",message="",str_sale_id="",str_variety="",str_seedseedling_variety="",str_subsidy="Yes";

    SharedPreferences sharedpreferences1;
    public static final String mypreference1 = "BUSINESS_PRODUCT_DEALS_IN";
    String deals_in_product="";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_pop_up_pesticides_sale);

        WindowManager.LayoutParams params = getWindow().getAttributes();

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.70);

        getWindow().setLayout(width, height);

        setFinishOnTouchOutside(false);// to prevent activity from dismissing on touching outside

        brand=findViewById(R.id.brand);
        remark=findViewById(R.id.remark);
        radioSubsidy=findViewById(R.id.radioSubsidy);
        radioSeedVariety=findViewById(R.id.radioSeedVariety);
        txtProductType=findViewById(R.id.txtProductType);
        layoutBrandVariety=findViewById(R.id.layoutBrandVariety);
        layoutSeedSeedling=findViewById(R.id.layoutSeedSeedling);


        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            str_product_type = bundle.getString("PROD_NAME");   txtProductType.setText(str_product_type);
            str_sale_id= bundle.getString("SALE_ID");
            str_variety= bundle.getString("VARIETY"); brand.setText(str_variety);

        }

        radioSubsidy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_subsidy=rb.getText().toString();
                    Toast.makeText(PopUpPesticidesSale.this, str_subsidy, Toast.LENGTH_SHORT).show();


                }

            }
        });


        radioSeedVariety.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_seedseedling_variety=rb.getText().toString();
                    Toast.makeText(PopUpPesticidesSale.this, str_seedseedling_variety, Toast.LENGTH_SHORT).show();

                    str_variety=str_seedseedling_variety;

                }

            }
        });
        //check for SeedSeedling for changing UI
        sharedpreferences1 =getSharedPreferences(mypreference1,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences1.edit();
        deals_in_product=sharedpreferences1.getString("DEALS_IN_PRODUCT", "");
        editor1.commit(); // commit change

        //change in UI
        if (deals_in_product.equalsIgnoreCase("Seed and Seedlings"))
        {
            layoutBrandVariety.setVisibility(View.GONE);
            layoutSeedSeedling.setVisibility(View.VISIBLE);
        }
    }
    public  void onClickOk(View view)
    {
            if(str_seedseedling_variety.equalsIgnoreCase(""))//
            str_variety=brand.getText().toString();
            else if(str_seedseedling_variety.length()>1)
                str_variety=str_seedseedling_variety;

            if(str_sale_id.equals(""))
            pestiOnSale();
            else
            pestiOnSaleUpdate(str_sale_id);
    }

    private void pestiOnSaleUpdate(String str_sale_id) {
        AndroidNetworking.post(Config.farmersales)
                .addBodyParameter("sale_id", str_sale_id)
                .addBodyParameter("user_id", str_user_id)
                .addBodyParameter("product_type", str_product_type)
                .addBodyParameter("category_name", deals_in_product)
                .addBodyParameter("variety",str_variety)
                .addBodyParameter("subsidy", str_subsidy)
               // .addBodyParameter("remark", remark.getText().toString())
                .setTag("salepestifertiseedseedling")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // do anything with response
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            message=jsonObject.getString("message");
                            Toast.makeText(PopUpPesticidesSale.this, message, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(PopUpPesticidesSale.this, getString(R.string.Network_Error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void pestiOnSale() {
        AndroidNetworking.post(Config.farmersales)
                .addBodyParameter("user_id", str_user_id)
                .addBodyParameter("product_type", str_product_type)
                .addBodyParameter("category_name",deals_in_product)
                .addBodyParameter("variety", str_variety)
                .addBodyParameter("subsidy", str_subsidy)
               // .addBodyParameter("remark", remark.getText().toString())
                .setTag("salepesticides")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // do anything with response
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            message=jsonObject.getString("message");
                            Toast.makeText(PopUpPesticidesSale.this, message, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(PopUpPesticidesSale.this, getString(R.string.Network_Error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onClickCancel(View view)
    {
        onBackPressed();
    }
}
