package numb3r.gte.com.numb3r.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import numb3r.gte.com.numb3r.Adapter.UsherAdapter;
import numb3r.gte.com.numb3r.Common.Common;
import numb3r.gte.com.numb3r.LoginActivity;
import numb3r.gte.com.numb3r.MainActivity;
import numb3r.gte.com.numb3r.Model.Usher;
import numb3r.gte.com.numb3r.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsherFragment extends Fragment {


    public UsherFragment() {
        // Required empty public constructor
    }


    View view;
    ListView listView;
    Button btnSubmit;
    Context mcontext;
    TextView lblnodata;
    TextView lblread;
    public static UsherAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_usher, container, false);
        mcontext = getActivity();

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listview);
        lblnodata = view.findViewById(R.id.textView);
        lblread = view.findViewById(R.id.textView2);

        Usher[] rawlist = Common.getPreferenceObjectJson(mcontext, "UsherList", Usher[].class);

        //Log.d("List", "" + txtlist.length);


        if (rawlist != null) {
            Log.d("Count", "" + rawlist.length);
            MainActivity.ushersList = new ArrayList(Arrays.asList(rawlist));
            adapter = new UsherAdapter(mcontext, R.layout.list_child_txn, MainActivity.ushersList);
            listView.setAdapter(adapter);
            lblnodata.setVisibility(View.GONE);
            lblread.setVisibility(View.GONE);
        } else {
            lblnodata.setVisibility(View.VISIBLE);
            lblread.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.usher, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Common.showAddDialog(mcontext);
                break;
            case R.id.action_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mcontext);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("State", "Logout");
                editor.apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();


    }
}
