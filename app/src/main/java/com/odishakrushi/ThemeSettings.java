package com.odishakrushi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerDialog;
import com.skydoves.colorpickerpreference.ColorPickerView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ThemeSettings extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    private AlertDialog alertDialog;
    TextView font_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        font_type=findViewById(R.id.font_type);

        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle("ColorPicker Dialog");
        builder.setPreferenceName("MyColorPickerDialog");
        builder.setFlagView(new CustomFlag(this, R.layout.layout_flag));
        builder.setPositiveButton("Confirm", new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
               /* TextView textView = findViewById(R.id.textView);
                textView.setText("#" + colorEnvelope.getColorHtml());*/

                Toast.makeText(ThemeSettings.this, "#"+colorEnvelope.getColorHtml(), Toast.LENGTH_SHORT).show();
                LinearLayout linearLayout = findViewById(R.id.linearLayout);
                linearLayout.setBackgroundColor(colorEnvelope.getColor());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog = builder.create();

        /**
         * get ColorPicker from builder, and set views as saved data
         */
        ColorPickerView colorPickerView = builder.getColorPickerView();
/*
        TextView textView = findViewById(R.id.textView);
        textView.setText("#" + colorPickerView.getSavedColorHtml(R.color.colorAccent));*/

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setBackgroundColor(colorPickerView.getSavedColor(R.color.colorAccent));
    }

    public void showDialog(View view) {
        alertDialog.show();
    }


    public void showFont(View view)
    {
        final String[] singleChoiceItems =getResources().getStringArray(R.array.arrayFont);
        int itemSelected = 0;
        new AlertDialog.Builder(this)
                .setTitle("Select Font")
                .setSingleChoiceItems(singleChoiceItems, itemSelected, new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex)
                            {
                                font_type.setText(singleChoiceItems[selectedIndex]);

                                // Calling Application class (see application tag in AndroidManifest.xml)
                                final MyApplication globalVariable = (MyApplication) getApplicationContext();
                                //Set name and email in global/application context
                                globalVariable.setFont_name(singleChoiceItems[selectedIndex]);
                            }
                        })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
