package com.odishakrushi.Fragments;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.odishakrushi.Config;
import com.odishakrushi.Adapter.MyAdapterKTag;
import com.odishakrushi.Model.ListTags;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;
import com.odishakrushi.SearchResults;
import com.odishakrushi.utils.Utils;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class KnowledgeBank extends Fragment {

   // KnowledgeBankDropdownIndex onIndexPass;
    Button btn_search;
    TextView txt_noresults;
    String str_kdropdown="",str_kdropdown_id="",url="", search_url="",tag="";
    TextView searchtext;
   // Spinner spinnerdropdown;
    AppCompatActivity context;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListTags> listItems;



    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    TextView txt_no_questions;

    CheckBoxGroup<String> checkBoxGroup;
    CheckBox checkBoxMyStory ,checkBoxQnA ,checkBoxProduct;

    String str_selected="",str_text="";

    ImageView image_ad;
    BroadcastReceiver mSearchReceiver;
    String smallBannerUrl="";
    public KnowledgeBank() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context= (AppCompatActivity) getActivity();
        /* RECEIVING PART */
        // Register the local broadcast
        LocalBroadcastManager.getInstance(context).registerReceiver(mBannerUrlReceiver,
                new IntentFilter("small-banner-url"));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_knowledgebank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

        getActivity().setTitle(getString(R.string.Knowledge_Bank));

        checkBoxMyStory =getView().findViewById(R.id.checkBoxMyStory);
        checkBoxQnA =getView().findViewById(R.id.checkBoxQnA);
        checkBoxProduct =getView().findViewById(R.id.checkBoxProduct);
        searchtext=getView().findViewById(R.id.searchtext);
       // spinnerdropdown=getView().findViewById(R.id.spinnerdropdown);

       // txt_no_questions=getView().findViewById(R.id.txt_no_questions);

        btn_search=getView().findViewById(R.id.btn_search);
        image_ad=getView().findViewById(R.id.image_ad);
        //GETTING USER_ID
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", user_id);


        recyclerView=(RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      /*  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);*/

        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

      /*  LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);*/
//----------------------------------------------------------------------------------------------------

        //  listItems=new ArrayList<>();
//-------------------------------------------------------------------------------------------------------


  /*      spinnerdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_kdropdown= spinnerdropdown.getSelectedItem().toString();
              //  onIndexPass.onIndexPass(str_kdropdown);
                str_kdropdown_id= String.valueOf(position+1);

              //  Toast.makeText(getActivity(), str_kdropdown_id, Toast.LENGTH_SHORT).show();
                listItems=new ArrayList<>();
                loadRecyclerViewData(str_kdropdown_id);

                searchtext.setText("");// for setting text to blank when spinner is changed

                // Initialize a new Intent object
                Intent intent = new Intent();
                // Set an action for the Intent
                intent.setAction("DROPDOWN_ID");
                // Put an integer value Intent to broadcast it
                intent.putExtra("str_kdropdown_id",str_kdropdown_id);

                context.sendBroadcast(intent);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,recyclerView,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, int position) {

                ListTags list = listItems.get(position);
                 tag=list.getTags();

                searchtext.setText(tag);

                // Register the local broadcast
                LocalBroadcastManager.getInstance(context).registerReceiver(
                        mSearchReceiver,
                        new IntentFilter("search-tag")
                );
                // Initialize a new intent instance
                Intent intent = new Intent("search-tag");
                intent.putExtra("tag",tag);
                // Send the broadcast
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        // Initialize a new BroadcastReceiver instance
          mSearchReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String tag = intent.getStringExtra("tag");
                gotoSearchDetails();
                // Display a notification that the broadcast received
                Toast.makeText(context,"Searching for "+tag,Toast.LENGTH_SHORT).show();
            }
        };


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag.equals("")||tag.equals(null))
                {
                    Toast.makeText(context, "Select a Tag", Toast.LENGTH_SHORT).show();
                }
                else
                 gotoSearchDetails();

            }
        });

        // Checkboxes
        HashMap<CheckBox, String> checkBoxMap = new HashMap<>();
        checkBoxMap.put((CheckBox) getView().findViewById(R.id.checkBoxMyStory ), "My Story");
        checkBoxMap.put((CheckBox) getView().findViewById(R.id.checkBoxQnA ), "Q and A");
        checkBoxMap.put((CheckBox) getView().findViewById(R.id.checkBoxProduct), "Product");




        checkBoxGroup = new CheckBoxGroup<>(checkBoxMap, checkedChangeListener);
        checkBoxGroup.setValues(new ArrayList<String>());

        /**
         * @desc: Calling getSmallBannerAd Api for loading Small Banner Ad..
         */
        String str_zone_id= Preferences.getZone(getActivity());
        String str_group_id= Preferences.getGroupID(getActivity());
        Utils.getSmallBannerAd(getActivity(),str_zone_id,str_group_id,image_ad);
        image_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!smallBannerUrl.equals("")){
                    Toast.makeText(context, smallBannerUrl, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(smallBannerUrl));
                    startActivity(intent);
                }


            }
        });

    }


    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mBannerUrlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            smallBannerUrl = intent.getStringExtra("small_url");

        }
    };

    private CheckBoxGroup.CheckedChangeListener<String> checkedChangeListener
            = new CheckBoxGroup.CheckedChangeListener<String>() {
        @Override
        public void onCheckedChange(ArrayList<String> values) {
            // Convert the ArrayList into a String.

            StringBuilder sb = new StringBuilder();
            for (String s : values) {
                sb.append(s);
                sb.append(",");
            }
            str_text = sb.toString();
            String str_text_array[]=str_text.split(",",1);
            for (String a : str_text_array)
                Log.d("txt",a);



            checkBoxQnA.setChecked(true);// bydefault make it true

            if(str_text.contains("My Story")) {
                listItems=new ArrayList<>();

                str_kdropdown_id="1";
                loadRecyclerViewData("1");
                //checkBoxQnA.setChecked(false);
                //checkBoxProduct.setChecked(false);

                if(str_text.contains("Q and A")){
                    str_kdropdown_id="2";
                    loadRecyclerViewData("2");
                }
                if(str_text.contains("Product")){
                    str_kdropdown_id="3";
                    loadRecyclerViewData("3");
                }

            }
            else if(str_text.contains("Q and A")) {
                listItems=new ArrayList<>();

                str_kdropdown_id="2";
                loadRecyclerViewData("2");
                //checkBoxMyStory.setChecked(false);
                //checkBoxProduct.setChecked(false);


                if(str_text.contains("My Story")){
                    str_kdropdown_id="1";
                    loadRecyclerViewData("1");
                }
                if(str_text.contains("Product")){
                    str_kdropdown_id="3";
                    loadRecyclerViewData("3");
                }
            }
            else if(str_text.contains("Product")) {
                listItems=new ArrayList<>();

                str_kdropdown_id="3";
                loadRecyclerViewData("3");
                //checkBoxMyStory.setChecked(false);
                //checkBoxQnA.setChecked(false);


                if(str_text.contains("My Story")){
                    str_kdropdown_id="1";
                    loadRecyclerViewData("1");
                }
                if(str_text.contains("Q and A")){
                    str_kdropdown_id="2";
                    loadRecyclerViewData("2");
                }
            }
        }

    };
    private void gotoSearchDetails()
    {
        Intent intent =new Intent(getActivity(), SearchResults.class);
        intent.putExtra("str_kdropdown_id",str_kdropdown_id);
        intent.putExtra("tag",tag);
        startActivity(intent);

    }
    private void loadRecyclerViewData(final String str_kdropdown_id) {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        switch (str_kdropdown_id)
        {
            case "1":
                //url="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/get_mystory_dropdown"; Doubt
                url= Config.baseUrl+ "farmer/get_mystory_dropdown";
                break;
            case "2":
                //url="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/get_qna_dropdown"; Doubt
                url=Config.baseUrl+"farmer/get_qna_dropdown";
                break;
            case "3":
                //url="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/get_sales_category_name"; Doubt
                url=Config.baseUrl+"farmer/get_sales_category_name";
                break;

        }
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {

                            Log.d("onResponse:",s);
                            JSONObject jsonObject=new JSONObject(s);
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            JSONArray array=jsonObject.getJSONArray("data");

                            if(array.length()==0)
                            {
                              //  txt_no_questions.setVisibility(View.VISIBLE);
                            }
                            for(int i=0;i<array.length();i++)
                            {
                                String tag="";

                                JSONObject o=array.getJSONObject(i);
                                if(str_kdropdown_id.equals("3"))
                                    tag=o.getString("category_name");
                                else
                                    tag=o.getString("dropdown");

                                ListTags item=new ListTags(
                                        tag);


                                listItems.add(item);

                                for (int k=0; k<listItems.size(); k++)
                                    System.out.println(listItems.get(i));

                            }


                            adapter=new MyAdapterKTag(listItems,getActivity());
                            //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
               // txt_no_questions.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"Error while loading",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onPause() {
        // Unregister since the activity is paused.
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                mSearchReceiver);
        super.onPause();
    }
}
