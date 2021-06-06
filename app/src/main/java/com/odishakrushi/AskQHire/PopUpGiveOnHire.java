package com.odishakrushi.AskQHire;

import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.odishakrushi.Adapter.CustomAdapter;
import com.odishakrushi.Adapter.CustomAdapterAreaOfOperation;
import com.odishakrushi.R;




public class PopUpGiveOnHire extends AppCompatActivity  {

    ImageView close;
    Spinner subgroup,area_of_operation;
    EditText edittext;
    TextView txtdate,txtedate;
    Button btnSubmit;

    String[] strarray_machines={"Tractor","Power tiller","Rotavator","Plough","Ridger","Leveller",
            "seed drill","transplanter","weeder","Sprayer","Thresher","Cleaner","Grader","Combine",
    "Pumps","Parboiling unit","rice mill","Decorticator","Tools for fruit and vegetable","others"};

    String [] strarray_area_of_operation={"Within Block","Within District","Within State"};

    String strsubgroup="",str_area_of_operation="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_pop_up_give_on_hire);


        resize_window();
      //  initialize_views();
        close=findViewById(R.id.close);
        subgroup=findViewById(R.id.subgroup);
        edittext=findViewById(R.id.edittext);
        area_of_operation=findViewById(R.id.area_of_operation);
        txtdate=findViewById(R.id.txtdate);
        txtedate=findViewById(R.id.txtedate);
        btnSubmit=findViewById(R.id.btnSubmit);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CustomAdapter customAdapter_machines=new CustomAdapter(this,strarray_machines);
        subgroup.setAdapter(customAdapter_machines);

        CustomAdapterAreaOfOperation customAdapter_area_of_operation=new CustomAdapterAreaOfOperation(this,strarray_area_of_operation);
        area_of_operation.setAdapter(customAdapter_area_of_operation);

      subgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strsubgroup= subgroup.getSelectedItem().toString();

                Toast.makeText(PopUpGiveOnHire.this, strsubgroup, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        area_of_operation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_area_of_operation= area_of_operation.getSelectedItem().toString();

                Toast.makeText(PopUpGiveOnHire.this, str_area_of_operation, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



             if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
                 Toast.makeText(this, "Kitkat", Toast.LENGTH_SHORT).show();
        }
    }

    private void resize_window() {
        WindowManager.LayoutParams params = getWindow().getAttributes();

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.70);

        getWindow().setLayout(width, height);

        setFinishOnTouchOutside(false);// to prevent activity from dismissing on touching outside
    }

    private void initialize_views() {
        close=findViewById(R.id.close);
        subgroup=findViewById(R.id.subgroup);
        edittext=findViewById(R.id.edittext);
        area_of_operation=findViewById(R.id.area_of_operation);
        txtdate=findViewById(R.id.txtdate);
        txtedate=findViewById(R.id.txtedate);
        btnSubmit=findViewById(R.id.btnSubmit);


    }


}
