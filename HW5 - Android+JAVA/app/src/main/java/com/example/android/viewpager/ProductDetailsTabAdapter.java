package com.example.android.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ProductDetailsTabAdapter extends FragmentPagerAdapter {
    private String pID;
    private String kWord;
    private String ships;
    private String tabTitles[] = new String[] { "Product", "Shipping", "Photos", "Similar"};
    public ProductDetailsTabAdapter(FragmentManager fm, String productId, String keyWord, String shipInfo) {
        super(fm);
        pID = productId;
        kWord = keyWord;
        ships = shipInfo;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Bundle bundle = new Bundle();
            bundle.putString("KeyWord", kWord);
            bundle.putString("productID", pID);
            bundle.putString("ship",ships);
            // set Fragmentclass Arguments
            productFragment fragobj = new productFragment();
            fragobj.setArguments(bundle);
            return fragobj;
        } else if (position == 1){
            Bundle bundle = new Bundle();
            bundle.putString("KeyWord", kWord);
            bundle.putString("productID", pID);
            bundle.putString("ship",ships);
            // set Fragmentclass Arguments
            shippingFragment fragobj = new shippingFragment();
            fragobj.setArguments(bundle);
            return fragobj;
        } else if (position == 2) {
            Bundle bundle = new Bundle();
            bundle.putString("KeyWord", kWord);
            bundle.putString("productID", pID);
            // set Fragmentclass Arguments
            photosFragment fragobj = new photosFragment();
            fragobj.setArguments(bundle);
            return fragobj;
        } else if (position == 3) {
            Bundle bundle = new Bundle();
            bundle.putString("KeyWord", kWord);
            bundle.putString("productID", pID);
            // set Fragmentclass Arguments
            similarProductFragment fragobj = new similarProductFragment();
            fragobj.setArguments(bundle);
            return fragobj;
        } else{
            return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
