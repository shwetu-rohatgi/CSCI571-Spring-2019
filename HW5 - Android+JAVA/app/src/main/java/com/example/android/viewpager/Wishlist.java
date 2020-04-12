package com.example.android.viewpager;

        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.LinearLayout;

        import java.util.List;
        import java.util.Map;

/**
 * Fragment that displays "Wishlist".
 */
public class Wishlist extends Fragment {

    private RecyclerView wishL;
    private LinearLayoutManager linearLayoutManager;
    //private DividerItemDecoration dividerItemDecoration;
    private List<ProductList> arreventList;
    private wishListAdapter fadapter;
    private RecyclerView recyclerView;
    private LinearLayout progressBar;
    View view;
    public Wishlist() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        wishL = (RecyclerView) view.findViewById(R.id.wishListRV);

        SharedPreferences varPref;
        varPref = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor spEditor = varPref.edit();
        //spEditor.clear();
        //spEditor.commit();

        Map<String, ?> allEntries = varPref.getAll();
        System.out.println(allEntries.toString());
        if(allEntries.size()!=0) {
            System.out.println("All entry in favfragment" + allEntries);
            fadapter = new wishListAdapter(view, getContext(), allEntries);

            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            //dividerItemDecoration = new DividerItemDecoration(favList.getContext(), linearLayoutManager.getOrientation());
            wishL.setHasFixedSize(true);
            wishL.setLayoutManager(linearLayoutManager);
            //favList.addItemDecoration(dividerItemDecoration);
            wishL.setAdapter(fadapter);

            fadapter.notifyDataSetChanged();
        }

        return view;
    }
}