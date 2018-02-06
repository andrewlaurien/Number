package numb3r.gte.com.numb3r.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import numb3r.gte.com.numb3r.MainActivity;
import numb3r.gte.com.numb3r.Model.MyNumbers;
import numb3r.gte.com.numb3r.R;

/**
 * Created by andrewlaurienrsocia on 04/12/2017.
 */

public class TransactionAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> _listHeader;
    // private HashMap<String, List<MyNumbers>> _listDataChild;
    private List<String> _listUnique;
    private ArrayList<ArrayList<List<MyNumbers>>> _listDataChild;

    public TransactionAdapter(Context context, List<String> _listHeader, ArrayList<ArrayList<List<MyNumbers>>> _listDataChild, List<String> uniqueid) {// HashMap<String, List<MyNumbers>> _listDataChild
        this.context = context;
        this._listHeader = _listHeader;
        this._listDataChild = _listDataChild;
        this._listUnique = uniqueid;


        Log.d("Header", "" + _listHeader.size());
        Log.d("HeaderChile", "" + _listDataChild.size());
    }

    @Override
    public int getGroupCount() {
        return this._listHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return _listDataChild.get(i).get(0).size();
        //return this._listDataChild.get(this._listUnique.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this._listHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this._listDataChild.get(i).get(0).get(i1);


        //return this._listDataChild.get(this._listUnique.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_parent_txn, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.txtmobile);
        TextView txtTotalAmount = (TextView) convertView
                .findViewById(R.id.txttotalamount);
        TextView txtdate = (TextView) convertView.findViewById(R.id.txtdate);
        TextView txtdrwhour = (TextView) convertView.findViewById(R.id.txtdrwhour);

        txtdate.setText(MainActivity.transction.get(i).getDrawDate());
        txtdrwhour.setText(MainActivity.transction.get(i).getDrawHour());

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        txtTotalAmount.setText(MainActivity.transction.get(i).getTotalAmount());//

        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {


        final MyNumbers numbers = (MyNumbers) getChild(i, i1);
        //final MyNumbers numbers = (MyNumbers) _listDataChild.get(i).get(i1); //getChild(i, i1);

        Log.d("Data", numbers.getAmount());

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_child_txn, null);
        }
        Log.d("Position", "Parent:" + i + "==========Child" + i1);
        TextView txtcombination = (TextView) view.findViewById(R.id.txtcombination);
        TextView txtbet = view.findViewById(R.id.txtbetamount);
        txtcombination.setText(numbers.getCombination());
        txtbet.setText(numbers.getAmount());

        return view;


//        int count = _listDataChild.get(i).get(i1).size();
//        final MyNumbers numbers;
//
//        Log.d("Count",""+ count);
//        for (int x = 0; x < count; x++) {
//            numbers = (MyNumbers) getChild(i, x);
//            //final MyNumbers numbers = (MyNumbers) _listDataChild.get(i).get(i1); //getChild(i, i1);
//
//            Log.d("Data", numbers.getAmount());
//
//            if (view == null) {
//                LayoutInflater infalInflater = (LayoutInflater) this.context
//                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = infalInflater.inflate(R.layout.list_child_txn, null);
//            }
//            Log.d("Position", "Parent:" + i + "==========Child" + i1);
//            TextView txtcombination = (TextView) view.findViewById(R.id.txtcombination);
//            TextView txtbet = view.findViewById(R.id.txtbetamount);
//            txtcombination.setText(numbers.getCombination());
//            txtbet.setText(numbers.getAmount());
//
//            return view;
//
//        }
//
//        return view;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
