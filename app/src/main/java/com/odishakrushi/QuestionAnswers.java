package com.odishakrushi;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.NavDrawer.NavDrawer;

public class QuestionAnswers extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    Spinner spinnerdistrict;
    String spinneritem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_question_answers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Intent intent=new Intent(QuestionAnswers.this,NavDrawer.class);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setTitle(getString(R.string.Question)+" "+getString(R.string.Answer));
        spinnerdistrict=(Spinner)findViewById(R.id.spinnerdistrict);
        spinnerdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinneritem= spinnerdistrict.getSelectedItem().toString();
                if(spinneritem.equals("Select Your District"))
                {

                }
                else
                {
                    Intent intent=new Intent(QuestionAnswers.this,DistrictName.class);
                    intent.putExtra("DISTRICT", spinneritem);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
