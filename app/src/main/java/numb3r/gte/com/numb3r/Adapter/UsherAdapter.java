package numb3r.gte.com.numb3r.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import numb3r.gte.com.numb3r.Model.Usher;
import numb3r.gte.com.numb3r.R;

/**
 * Created by andrewlaurienrsocia on 12/12/2017.
 */

public class UsherAdapter extends ArrayAdapter {

    private ArrayList<Usher> _mList;
    Context context;
    int layoutID;

    public UsherAdapter(@NonNull Context context, int resource, ArrayList<Usher> list) {
        super(context, resource);
        Log.d("CountAdapter", ""+list.size());
        this.context = context;
        this._mList = list;
        this.layoutID = resource;
    }

    @Override
    public int getCount() {
        int size = 0;
        if (_mList != null)
            size = _mList.size();
        return size;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        convertView = LayoutInflater.from(context).inflate(R.layout.list_child_txn, parent, false);

        //if (layoutID == R.layout.list_child_txn) {

        TextView tv1 = (TextView) convertView.findViewById(R.id.txtcombination);
        TextView tv2 = (TextView) convertView.findViewById(R.id.txtbetamount);
        Log.d("Count", _mList.get(position).getRepresentative());
        tv1.setText(_mList.get(position).getRepresentative());
        tv2.setText(_mList.get(position).getMobileNumber());
        //}

        return convertView;

    }
}
