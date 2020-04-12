package com.example.android.viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<ProductList> mData;

    public RecyclerViewAdapter(Context mContext, List<ProductList> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.search_card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductList prodList = mData.get(position);
        String tempname = mData.get(position).getproductTitle();
        if(tempname.length()>35)
            tempname = tempname.substring(0,35)+"...";
        holder.productTitle.setText(tempname);
        holder.conditionType.setText(mData.get(position).getConditionType());
        holder.price.setText(mData.get(position).getPrice());
        holder.shippingType.setText(mData.get(position).getShippingType());
        holder.zipCode.setText(mData.get(position).getZipCode());
        holder.cart_btn.setImageResource(R.drawable.cart_plus);
        //holder.img_url.setImageURI(mData.get(position).getImgLink());
        Picasso.with(mContext).load(mData.get(position).getImgLink()).into(holder.img_url);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, productDetails.class);
                intent.putExtra("productDetailsID",mData.get(position).getProductID());
                intent.putExtra("productKeyword",mData.get(position).getKeyword());
                intent.putExtra("shipInfo", mData.get(position).getshipInfo());
                intent.putExtra("pTitle", mData.get(position).getproductTitle());
                intent.putExtra("prodPrice", mData.get(position).getPrice());
                intent.putExtra("prodURL", mData.get(position).getProductUrl());

                mContext.startActivity(intent);
            }
        });

        SharedPreferences varPref;
        varPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor spEditor = varPref.edit();

        if( varPref.getString(prodList.getProductID(), null) == null){
            holder.cart_btn.setImageResource(R.drawable.cart_plus);
            holder.cart_btn.setTag(R.drawable.cart_plus);
        }else{
            holder.cart_btn.setImageResource(R.drawable.cart_remove);
            holder.cart_btn.setTag(R.drawable.cart_remove);
        }

        holder.cart_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //SharedPreferences sharedPref = mContext.getPreferences(Context.MODE_PRIVATE);

                Integer resource = (Integer)holder.cart_btn.getTag();
                if(resource == R.drawable.cart_plus){
                    holder.cart_btn.setImageResource(R.drawable.cart_remove);
                    holder.cart_btn.setTag(R.drawable.cart_remove);
                    Toast.makeText(view.getContext(), prodList.getproductTitle() +" was added to wishlist",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor spEditor = sharedpref.edit();
                    String s = prodList.getProductID() +"!!"+ prodList.getproductTitle() +"!!"+ prodList.getPrice() +"!!"+ prodList.getConditionType()+"!!"+ prodList.getZipCode()+"!!"+ prodList.getShippingType() +"!!"+ prodList.getImgLink()+"!!"+ prodList.getKeyword();
                    System.out.println("////////////////"+s);
                    spEditor.putString(prodList.getProductID(), s);
                    spEditor.commit();
                    // do something
                }else {
                    holder.cart_btn.setImageResource(R.drawable.cart_plus);
                    holder.cart_btn.setTag(R.drawable.cart_plus);
                    Toast.makeText(view.getContext(), prodList.getproductTitle() +" was removed from wishlist",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor spEditor = sharedpref.edit();
                    spEditor.remove(prodList.getProductID());
                    //editor.putString(itemid, content);
                    spEditor.commit();

                }

            }
        });

//        if( varPref.getString(prodList.getProductID(), null) == null){
//            holder.cart_btn.setImageResource(R.drawable.cart_plus);
//        }else{
//            holder.cart_btn.setImageResource(R.drawable.cart_remove);
//        }

//        holder.cart_btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                /// button click event
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView productTitle;
        TextView zipCode;
        TextView shippingType;
        TextView conditionType;
        TextView price;
        ImageView img_url;
        ImageView cart_btn;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            productTitle = (TextView) itemView.findViewById(R.id.text_view_card);
            zipCode = (TextView) itemView.findViewById(R.id.textView5);
            shippingType = (TextView) itemView.findViewById(R.id.textView6);
            conditionType = (TextView) itemView.findViewById(R.id.textView7);
            price = (TextView) itemView.findViewById(R.id.textView8);
            img_url = (ImageView) itemView.findViewById(R.id.img_view_card);
            cart_btn = (ImageView) itemView.findViewById(R.id.cartbtn);
            cardView = (CardView) itemView.findViewById(R.id.productCardView);

            //cart_btn = (ImageView) itemView.findViewById(R.id.cartbtn);
            //cart_btn.setOnClickListener((View.OnClickListener) this);

            //cardView = (CardView) itemView.findViewById(R.id.productCardView);
            //cardView.setOnClickListener((View.OnClickListener) this);

            //itemView.setOnClickListener((View.OnClickListener) this);


        }

//        @Override
//        public void onClick(View view) {
//            ProductList prodList = mData.get(getAdapterPosition());
//            if(view.getId()==cart_btn.getId()){
//                Drawable.ConstantState setone = cart_btn.getDrawable().getConstantState();
//                Drawable.ConstantState border = mContext.getResources().getDrawable(R.drawable.cart_plus).getConstantState();
//                Drawable.ConstantState red = mContext.getResources().getDrawable(R.drawable.cart_remove).getConstantState();
//
//                SharedPreferences varPref;
//                varPref = PreferenceManager.getDefaultSharedPreferences(mContext);
//                SharedPreferences.Editor spEditor = varPref.edit();
//
//                Map<String, ?> allEntries = varPref.getAll();
//                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
//                    Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
//                }
//                //ADD TO FAV
//                if(setone.equals(border)){
//                    System.out.println("Black Heart");
//                    cart_btn.setImageResource(R.drawable.cart_plus);
//
//                    String s = prodList.getProductID() +"!!"+ prodList.getproductTitle() +"!!"+ prodList.getPrice() +"!!"+ prodList.getConditionType()+"!!"+ prodList.getZipCode()+"!!"+ prodList.getShippingType() +"!!"+ prodList.getImgLink()+"!!"+ prodList.getKeyword();
//                    spEditor.putString(prodList.getProductID(), s);
//                    Toast.makeText(view.getContext(),prodList.getproductTitle()+" was added to favorites",Toast.LENGTH_SHORT).show();
//                }
//                //REMOVE FROM FAV
//                else{
//                    spEditor.remove(prodList.getProductID());
//                    System.out.println("Red Heart");
//                    cart_btn.setImageResource(R.drawable.cart_remove);
//                    Toast.makeText(view.getContext(),prodList.getproductTitle()+" was removed from favorites",Toast.LENGTH_SHORT).show();
//                }
//                spEditor.commit();
//            }
//           //else if(view.getId() == cardView.getId()){
////                System.out.println("Layout is clicked");
////                Intent i = new Intent(mContext, eventDetails4.class);
////                i.putExtra("eventId",prodList.getProductID());
////                ((Activity)mContext).startActivity(i);
////            }
//        }
    }
}
