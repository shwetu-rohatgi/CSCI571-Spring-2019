package com.example.android.viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class wishListAdapter extends RecyclerView.Adapter<wishListAdapter.ViewHolder> {
    private Context mContext;
    private View view1;
    private Map<String, ?> mData;
    //private ProductList obj;
    private List<String> keys = new ArrayList<>();

    public wishListAdapter(View view, Context context, Map<String, ?> allEntries) {
        mContext = context;
        this.view1 = view;
        mData = allEntries;
        keys.addAll(mData.keySet());
    }

    @Override
    public wishListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_card_view, viewGroup, false);
        return new wishListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final wishListAdapter.ViewHolder holder, int i) {

        final String value = (String)mData.get(keys.get(i));
        String[] eventData = value.split("!!");
        System.out.println(value);

        //final String itemid = mData.get(i).
        //holder.productID.setText(eventData[0]);
        holder.productTitle.setText(eventData[1]);
        holder.conditionType.setText(eventData[3]);
        holder.price.setText(eventData[2]);
        holder.shippingType.setText(eventData[5]);
        holder.zipCode.setText(eventData[4]);
        holder.cart_btn.setImageResource(R.drawable.cart_remove);
        //holder.keyW.setText(eventData[7]);
        //holder.img_url.setImageURI(mData.get(position).getImgLink());
        Picasso.with(mContext).load(eventData[6]).into(holder.img_url);


//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, productDetails.class);
//                intent.putExtra("productDetailsID",mData.get(position).getProductID());
//                intent.putExtra("productKeyword",mData.get(position).getKeyword());
//                intent.putExtra("shipInfo", mData.get(position).getshipInfo());
//
//                mContext.startActivity(intent);
//            }
//        });

        SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedpref.edit();
        if( sharedpref.getString(String.valueOf(holder.productID), null) == null){
            holder.cart_btn.setImageResource(R.drawable.cart_plus);
            holder.cart_btn.setTag(R.drawable.cart_plus);
        }else{
            holder.cart_btn.setImageResource(R.drawable.cart_remove);
            holder.cart_btn.setTag(R.drawable.cart_remove);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , productDetails.class);
                intent.putExtra("productDetailsID", String.valueOf(holder.productID));
                intent.putExtra("productKeyword", String.valueOf(holder.keyW));
                intent.putExtra("shipInfo", String.valueOf(holder.shippingType));
                intent.putExtra("pTitle", String.valueOf(holder.productTitle));
                intent.putExtra("prodPrice", String.valueOf(holder.price));
                intent.putExtra("prodURL", String.valueOf(holder.img_url));

                //intent.putExtra(EXTRA_ITEMJSON, content);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        final String finalTitle = String.valueOf(holder.productTitle);
        holder.cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SharedPreferences sharedPref = mContext.getPreferences(Context.MODE_PRIVATE);

                Integer resource = (Integer)holder.cart_btn.getTag();
                if(resource == R.drawable.cart_plus){
                    holder.cart_btn.setImageResource(R.drawable.cart_remove);
                    holder.cart_btn.setTag(R.drawable.cart_remove);
                    Toast.makeText(view.getContext(), finalTitle +" was added to wishlist",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString(String.valueOf(holder.productID), value);
                    editor.commit();


//                    FragmentTransaction ft = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
//
//                    ft.detach(this).attach(this).commit();
                    // do something
                }else {
                    holder.cart_btn.setImageResource(R.drawable.cart_plus);
                    holder.cart_btn.setTag(R.drawable.cart_plus);

                    Toast.makeText(view.getContext(), finalTitle +" was removed from wishlist",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    System.out.println("before removal");
                    System.out.println(sharedpref.getAll().toString());

                    editor.remove(String.valueOf(holder.productID));
                    //editor.putString(itemid, content);
                    editor.commit();
                    System.out.println("after removal");
                    System.out.println(sharedpref.getAll().toString());
                    ProductList cur1 = null;
                    int i =0;

                        if(mData.containsKey(holder.productID)){
                            mData.remove(holder.productID);
                        }



                    Double Total =0.0;
                    for(String wish: mData.keySet()){
                        String strval = (String)mData.get(wish);
                        String [] strarr = strval.split("!!");
                        Total += Double.parseDouble(strarr[2]);
                    }
                    TextView total = (TextView) view1.findViewById(R.id.Total);
                    total.setText("Wishlist total("+mData.size()+" items):                   $"+Double.toString(Total));

                    if(Total == 0.0){
                        TextView nowish = (TextView) view1.findViewById(R.id.content);
                        nowish.setText("No Wishes");
                    }

                    notifyDataSetChanged();

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView productTitle;
        public TextView zipCode;
        public TextView shippingType;
        public TextView conditionType;
        public TextView price;
        public ImageView img_url;
        public ImageView cart_btn;
        public CardView cardView;
        public TextView productID;
        public TextView keyW;

        public ViewHolder(View view) {
            super(view);
            productTitle = (TextView) view.findViewById(R.id.text_view_card);
            zipCode = (TextView) view.findViewById(R.id.textView5);
            shippingType = (TextView) view.findViewById(R.id.textView6);
            conditionType = (TextView) view.findViewById(R.id.textView7);
            price = (TextView) view.findViewById(R.id.textView8);
            img_url = (ImageView) view.findViewById(R.id.img_view_card);
            cart_btn = (ImageView) view.findViewById(R.id.cartbtn);
            cardView = (CardView) view.findViewById(R.id.productCardView);

            //cart_btn = view.findViewById(R.id.cartbtn);
            cart_btn.setOnClickListener(this);

            //cardView = view.findViewById((R.id.productCardView));
            cardView.setOnClickListener(this);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String elementId = keys.get(getAdapterPosition());
            String value = (String)mData.get(keys.get(getAdapterPosition()));
            //IF FAVORITE IS CLICKED
            if(view.getId() == cart_btn.getId()){
                SharedPreferences varPref;
                varPref = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor spEditor = varPref.edit();
                //REMOVE FROM FAV
                spEditor.remove(elementId);
                spEditor.commit();

            }else if(view.getId() == cardView.getId()){
                System.out.println("Layout is clicked");
                Intent i = new Intent(mContext, productDetails.class);
                i.putExtra("productDetailsID",elementId);
                //i.putExtra("productKeyword",mData.get(position).getKeyword());
                //i.putExtra("shipInfo", mData.get(position).getshipInfo());
                (mContext).startActivity(i);
            }
            notifyDataSetChanged();
        }
    }
}
