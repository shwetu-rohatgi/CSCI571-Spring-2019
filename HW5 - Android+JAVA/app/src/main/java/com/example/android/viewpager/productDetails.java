package com.example.android.viewpager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class productDetails extends AppCompatActivity {
    private TextView mDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.product_details_viewpager);

        Intent showData = getIntent();
        Bundle bundleData = showData.getExtras();
//        String prodTitle ="";
//        String price="";
//        String itemUrl="";
        if (!bundleData.isEmpty()) {

            final String productId;
            final String keyWord;
            final String shippingInfo;
            final String prodTitle;
            final String price;
            final String itemUrl;
            productId = bundleData.getString("productDetailsID");
            keyWord = bundleData.getString("productKeyword");
            shippingInfo = bundleData.getString("shipInfo");
            prodTitle = bundleData.getString("pTitle");
            price = bundleData.getString("prodPrice");
            itemUrl = bundleData.getString("prodURL");
            //mDisplay.setText(productId);

            ProductDetailsTabAdapter adapter = new ProductDetailsTabAdapter(getSupportFragmentManager(), productId, keyWord, shippingInfo);
            viewPager.setAdapter(adapter);

            Toolbar toolbar = (Toolbar) findViewById(R.id.prod_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            getSupportActionBar().setTitle(prodTitle); // for set actionbar title
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            ImageView facebook = (ImageView) findViewById(R.id.facebook);
            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(productDetails.this,"I am clicked",Toast.LENGTH_LONG).show();
                    Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                    String appId= "602749993522223";
                    String stringToShare = "Buy"+ prodTitle+ " at " + "$" +price;
                    // String url = "https://www.google.com";

                    //  String url = "https://www.facebook.com/dialog/share?app_id=368212807366654&display=popup&href=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2F&redirect_uri=https%3A%2F%2Fdevelopers.facebook.com%2Ftools%2Fexplorer";
                    String url = "https://www.facebook.com/dialog/share?app_id=" + appId + "&display=popup"  + "&quote=" + stringToShare + "&href=" + itemUrl + "&hashtag=%23CSCI571Spring2019Ebay";
                    sharingIntent.setData(Uri.parse(url));


                    //Intent chooserIntent = Intent.createChooser(sharingIntent, "Open in...");
                    //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{sharingIntent});
                    startActivity(sharingIntent);
                }
            });
        }



        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //add app icon inside the Toolbar
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.facebook);

//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            int id = item.getItemId();
//
//            if ( id == android.R.id.home ) {
//                finish();
//                return true;
//            }
//
//            return super.onOptionsItemSelected(item);
//        }

//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            getMenuInflater().inflate(R.menu.main_activity_menu, menu);
//            return super.onCreateOptionsMenu(menu);
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.mShare:
//                    Intent  i = new Intent(android.content.Intent.ACTION_SEND);
//                    i.setType("text/plain");
//                    i.putExtra(android.content.Intent.EXTRA_TEXT, "The string you want to share, which can include URLs");
//                    startActivity(Intent.createChooser(i,"Title of your share dialog"));
//                    break;
//            }
//            return super.onOptionsItemSelected(item);
//        }

        /* Give the TabLayout the ViewPager */
        TabLayout tabLayout = (TabLayout) findViewById(R.id.product_details_sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.productPage));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.shippingPage));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.photosPage));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.similarPage));
        tabLayout.setupWithViewPager(viewPager);



//        mDisplay = (TextView) findViewById(R.id.active_text);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

            //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

}
