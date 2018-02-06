package numb3r.gte.com.numb3r.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import numb3r.gte.com.numb3r.Adapter.TransactionAdapter;
import numb3r.gte.com.numb3r.Common.Common;
import numb3r.gte.com.numb3r.MainActivity;
import numb3r.gte.com.numb3r.Model.MyNumbers;
import numb3r.gte.com.numb3r.Model.Transaction;
import numb3r.gte.com.numb3r.R;


public class TxnFragment extends Fragment {


    public TxnFragment() {
        // Required empty public constructor
    }

    View view;
    ExpandableListView listView;
    //HashMap<String, List<MyNumbers>> listDataChild;
    ArrayList<ArrayList<List<MyNumbers>>> listDataChild;
    TransactionAdapter listAdapter;
    List<String> listHeader;
    List<String> listID;
    Context mcontext;
    TextView txtnodata;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mcontext = getActivity();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_txn, container, false);
        listView = view.findViewById(R.id.list);
        txtnodata = view.findViewById(R.id.txtNodata);


        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Transaction[] txtlist = Common.getPreferenceObjectJson(mcontext, "Transactions", Transaction[].class);

        //Log.d("List", "" + txtlist.length);
        if (txtlist != null) {
            // ArrayList<Transaction> listOfStrings = new ArrayList<>(txtlist.length);
            // MainActivity.transction.addAll(listOfStrings);
            //MainActivity.transction = Arrays.asList(txtlist);
            MainActivity.transction = new ArrayList(Arrays.asList(txtlist));
        }


        prepareData();

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setVisible(false);
    }

    public void prepareData() {

        listHeader = new ArrayList<String>();
        listDataChild = new ArrayList<ArrayList<List<MyNumbers>>>();//new HashMap<String, List<MyNumbers>>();
        listID = new ArrayList<>();
        //new ArrayList<ArrayList<List<MyNumbers>>>();
        if (MainActivity.transction.size() > 0) {

            for (Transaction object : MainActivity.transction) {
                Log.d("Object", object.getMobileNumber());
                listHeader.add(object.getMobileNumber());
                listID.add(object.getUniqueTxnID());
                ArrayList<List<MyNumbers>> number = new ArrayList<>();
                number.add(object.getNumbers());

                listDataChild.add(number);

                //listDataChild.put(object.getUniqueTxnID(), object.getNumbers());
            }

            //Log.d("Count",""+listDataChild.get(6).get(0).size());

            listAdapter = new TransactionAdapter(mcontext, listHeader, listDataChild, listID);
            listView.setAdapter(listAdapter);
        }else{
            txtnodata.setVisibility(View.VISIBLE);
        }
    }

}
