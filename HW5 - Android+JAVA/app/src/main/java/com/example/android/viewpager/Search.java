package com.example.android.viewpager;

        import android.app.DownloadManager;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.AppCompatAutoCompleteTextView;
        import android.text.Editable;
        import android.text.TextUtils;
        import android.text.TextWatcher;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import android.widget.RadioButton;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Fragment that displays "Search".
 */
public class Search extends Fragment {
    private View view;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutosuggestAdapter autoSuggestAdapter;
    private String zipcode_data;


    public Search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        Spinner spinner = (Spinner)view.findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.CategoryArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final CheckBox enable_search = (CheckBox)view.findViewById(R.id.checkBox6);
        final EditText dist = (EditText)view.findViewById(R.id.distanceEditText);
        final TextView distText = (TextView)view.findViewById(R.id.distanceText);
        String distVal = dist.getText().toString();
        final RadioButton current = (RadioButton) view.findViewById(R.id.radioButton1);
        final RadioButton zipcode = (RadioButton) view.findViewById(R.id.radioButton2);
        final AppCompatAutoCompleteTextView zipBox = (AppCompatAutoCompleteTextView) view.findViewById(R.id.auto_complete_edit_text);

        final TextView from = (TextView) view.findViewById(R.id.fromText);

        enable_search.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    zipBox.setVisibility(View.VISIBLE);
                    distText.setVisibility(View.VISIBLE);
                    dist.setVisibility(View.VISIBLE);
                    current.setVisibility(View.VISIBLE);
                    zipcode.setVisibility(View.VISIBLE);
                    //zip_text.setVisibility(View.VISIBLE);
                    from.setVisibility(View.VISIBLE);
                }
                else
                {
                    zipBox.setVisibility(View.GONE);
                    distText.setVisibility(View.GONE);
                    dist.setVisibility(View.GONE);
                    current.setVisibility(View.GONE);
                    zipcode.setVisibility(View.GONE);
                    //zip_text.setVisibility(View.GONE);
                    from.setVisibility(View.GONE);
                }
            }
        });

        final AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) view.findViewById(R.id.auto_complete_edit_text);
        //final TextView selectedText = view.findViewById(R.id.selected_item);

        //Setting up the adapter for AutoSuggest
        autoSuggestAdapter = new AutosuggestAdapter(getContext (), android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        zipcode_data = autoSuggestAdapter.getObject(position).toString();
                    }
                });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler (new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeApiCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });

    Button searchButton = (Button) view.findViewById(R.id.searchButton);
    searchButton.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view){
            Intent intentToEventList = new Intent(getContext(), SearchResultTable.class);
            Bundle bundle_for_eventlist = new Bundle();

            TextView ziperror = (TextView)getView ().findViewById (R.id.zip_error);

        final EditText nameField = (EditText) getView().findViewById(R.id.keywordEditText);
        String keywordText = nameField.getText().toString();

        final Spinner catspin = (Spinner) getView().findViewById(R.id.categorySpinner);
        String catSpinVal = catspin.getSelectedItem().toString();

        final TextView KeywordError = (TextView)getView ().findViewById(R.id.keyword_error);

        final CheckBox responseCheckbox1 = (CheckBox) getView().findViewById(R.id.checkBox);
        boolean new1 = responseCheckbox1.isChecked();
        String new2 = Boolean.toString(new1);

        final CheckBox responseCheckbox2 = (CheckBox) getView().findViewById(R.id.checkBox2);
        boolean used1 = responseCheckbox2.isChecked();
        String used2 = Boolean.toString(used1);

        final CheckBox responseCheckbox3 = (CheckBox) getView().findViewById(R.id.checkBox3);
        boolean unspecified1 = responseCheckbox3.isChecked();
        String unspecified2 = Boolean.toString(unspecified1);

        final CheckBox responseCheckbox4 = (CheckBox) getView().findViewById(R.id.checkBox4);
        boolean local1 = responseCheckbox4.isChecked();

        final CheckBox responseCheckbox5 = (CheckBox) getView().findViewById(R.id.checkBox5);
        boolean free1 = responseCheckbox5.isChecked();

        AppCompatAutoCompleteTextView zip = (AppCompatAutoCompleteTextView)getView ().findViewById (R.id.auto_complete_edit_text);
        String zip_string = zip.toString();

            int anyError = 0;

            if(keywordText.equals(""))
            {
                anyError = 1;

                KeywordError.setVisibility(View.VISIBLE);
            }
            else
            {
                KeywordError.setVisibility(View.GONE);
            }

            if(zip_string.equals (""))
            {
                anyError = 1;
                ziperror.setVisibility (View.VISIBLE);
            }
            else
            {
                ziperror.setVisibility (View.GONE);
            }

            //error logic for location


            if(anyError ==1 )
            {
                Toast toast1 = Toast.makeText(getContext(), "Please fix all fields with errors" , Toast.LENGTH_SHORT);
                toast1.show();
            }
            else {
                //Enable Search on click logic
                String CatId = "";

                if (catSpinVal.equals("Art")) {
                    CatId = "550";
                } else if (catSpinVal.equals("Baby")) {
                    CatId = "2984";
                } else if (catSpinVal.equals("Books")) {
                    CatId = "267";
                }
                if (catSpinVal.equals("Clothing,Shoes and Accessories")) {
                    CatId = "11450";
                }
                if (catSpinVal.equals("Computers,Tablets and Network")) {
                    CatId = "58058";
                }

                if (catSpinVal.equals("Health and Beauty")) {
                    CatId = "26395";
                }
                if (catSpinVal.equals("Music")) {
                    CatId = "11233";
                }
                if (catSpinVal.equals("Video,Games and Console")) {
                    CatId = "1249";
                }

                if (catSpinVal.equals("All")) {
                    CatId = "all";
                }

                bundle_for_eventlist.putString("Category", CatId);
                bundle_for_eventlist.putString("miles","10");
                if(zipcode.isChecked()){
                    bundle_for_eventlist.putString("ZipCode",zip_string);
                }
                else {
                    bundle_for_eventlist.putString("ZipCode", "90007");
                }

                String slocal, sfree;

                if(local1 == true && free1 == false)
                {
                    slocal = "true";
                    sfree = "false";
                }
                else if (local1 == false && free1 == true)
                {
                    slocal = "false";
                    sfree = "true";
                }
                else
                {
                    slocal = "false";
                    sfree = "false";

                }

                bundle_for_eventlist.putString("keyword", keywordText);
                bundle_for_eventlist.putString("local",slocal);
                bundle_for_eventlist.putString("free",sfree);
                bundle_for_eventlist.putString("New",new2);
                bundle_for_eventlist.putString("Used",used2);
                bundle_for_eventlist.putString("Unspecified",unspecified2);

                //final TextView result = (TextView) getView().findViewById(R.id.resultView);
                //RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

            }
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            //result.setText("Response: " + response.toString());
//                        }
//                    }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // TODO: Handle error
//
//                        }
//                    });

            // Add the request to the RequestQueue.
            //queue.add(jsonObjectRequest);

// Access the RequestQueue through your singleton class.
            //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
            intentToEventList.putExtras(bundle_for_eventlist);
            startActivity(intentToEventList);
    }
    });

    //On click of CLEAR Button
//    Button clearBtn = (Button) view.findViewById(R.id.clearButton);
//        clearBtn.setOnClickListener(new View.OnClickListener(){
//
//        @Override
//        public void onClick (View view){
//        EditText okeyword = (EditText) view.findViewById(R.id.keywordEditText);
//        okeyword.setText("");
//
//        TextView keyError = (TextView) view.findViewById(R.id.keyword_error);
//        keyError.setVisibility(View.GONE);
//
//        Spinner ospinner1 = (Spinner) view.findViewById(R.id.categorySpinner);
//        ospinner1.setSelection(0);
//
//        CheckBox check1 = (CheckBox) view.findViewById(R.id.checkBox);
//        check1.setChecked(false);
//        CheckBox check2 = (CheckBox) view.findViewById(R.id.checkBox2);
//        check2.setChecked(false);
//        CheckBox check3 = (CheckBox) view.findViewById(R.id.checkBox3);
//        check3.setChecked(false);
//
//        CheckBox check4 = (CheckBox) view.findViewById(R.id.checkBox4);
//        check4.setChecked(false);
//        CheckBox check5 = (CheckBox) view.findViewById(R.id.checkBox5);
//        check5.setChecked(false);
//
//        CheckBox check6 = (CheckBox) view.findViewById(R.id.checkBox6);
//        check6.setChecked(false);
//
//        EditText odistance = (EditText) view.findViewById(R.id.distanceEditText);
//        odistance.setText("");
//
//        RadioButton radio1 = (RadioButton) view.findViewById(R.id.radioButton1);
//        radio1.setChecked(true);
//
//        RadioButton radio2 = (RadioButton) view.findViewById(R.id.radioButton2);
//        radio2.setChecked(false);
//
//        EditText olocationtext = (EditText) view.findViewById(R.id.locationText);
//        olocationtext.setText("");
//        olocationtext.setEnabled(false);
//        olocationtext.setFocusable(false);
//        olocationtext.setFocusableInTouchMode(false);
//
//        TextView olocationError = (TextView) view.findViewById(R.id.location_error);
//        olocationError.setVisibility(View.GONE);
//    }
//    });
        return view;
    }

    private void makeApiCall(String text) {
        ApiCall.make(getContext (), text, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parsing logic, please change it as per your requirement
                List<String> stringList = new ArrayList<>();
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray array = responseObject.getJSONArray("postalCodes");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        stringList.add(row.getString("postalCode"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

}


