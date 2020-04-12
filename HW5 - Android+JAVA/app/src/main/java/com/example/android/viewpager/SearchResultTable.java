package com.example.android.viewpager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultTable extends AppCompatActivity {
    private LinearLayout progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ProductList> arreventList;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_table);

        Toolbar toolbar = (Toolbar) findViewById(R.id.prod_toolbar_results);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Search Results"); // for set actionbar title
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        progressBar = (LinearLayout) findViewById(R.id.progressLayoutId);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) findViewById(R.id.eventListRV);
        recyclerView.setVisibility(View.GONE);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        arreventList = new ArrayList<>();
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arreventList);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(this, 2);
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(adapter);

        //final TextView result = (TextView) findViewById(R.id.active_text);
        final RequestQueue queue = Volley.newRequestQueue(this);

        Intent showData = getIntent();
        Bundle bundleData = showData.getExtras();

        if (!bundleData.isEmpty()) {
            final String keywordText = bundleData.getString("keyword");
            final String category = bundleData.getString("Category");
            final String new_con = bundleData.getString("New");
            final String used_con = bundleData.getString("Used");
            final String unspec_con = bundleData.getString("Unspecified");
            final String local = bundleData.getString("local");
            final String free = bundleData.getString("free");
            final String dist = bundleData.getString("miles");
            String zip_url="";
            if(bundleData.getString("ZipCode")=="90007"){
                zip_url = "&currZip="+bundleData.getString("ZipCode");
            }
            else{
                zip_url = "&hereZip="+bundleData.getString("ZipCode");
            }
            //final String zipcode = bundleData.getString("ZipCode");
            //result.setText(keywordText);

            String url = "http://ebay-app-236704.appspot.com/form?Keyword=" + keywordText + "&Category=" + category + "&Local="+local+"&Free="+free+"&New="+new_con+"&Used="+used_con+"&Unspecified="+unspec_con+"&New="+new_con+"&Distance="+dist+zip_url;

            System.out.println(url);

            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            try {
                                JSONArray res = response.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item");

                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject jsonObj = res.getJSONObject(i);
                                    ProductList eventObj = new ProductList();
                                    eventObj.setKeyword(keywordText);
                                    eventObj.setProductID(jsonObj.getJSONArray("itemId").getString(0));
                                    eventObj.setProductTitle(jsonObj.getJSONArray("title").getString(0));
                                    eventObj.setConditionType(jsonObj.getJSONArray("condition").getJSONObject(0).getJSONArray("conditionDisplayName").getString(0));
                                    eventObj.setPrice(jsonObj.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice").getJSONObject(0).getString("__value__"));
                                    eventObj.setShippingType(jsonObj.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingType").getString(0));
                                    eventObj.setZipCode(jsonObj.getJSONArray("postalCode").getString(0));
                                    eventObj.setImgLink(jsonObj.getJSONArray("galleryURL").getString(0));
                                    eventObj.setProductUrl(jsonObj.getJSONArray("viewItemURL").getString(0));

                                    String Shipping;
                                    try {
                                        Shipping = jsonObj.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingServiceCost").getJSONObject(0).getString("_value_");
                                        if(Float.parseFloat(Shipping) == 0.0){
                                            Shipping = "Free Shipping";
                                        }
                                        else{
                                            Shipping = "$"+Shipping;
                                        }
                                    } catch (JSONException e) {
                                        Shipping = "N/A";
                                        e.printStackTrace();
                                    }
                                    eventObj.setshipInfo(Shipping);
                                    //result.setText("Response: " + jsonObj.getJSONArray("title").getString(0).toString());
                                    arreventList.add(eventObj);
                                }
                                //result.setText("Response: " + arreventList.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);
        } else {

        }
    }
}
