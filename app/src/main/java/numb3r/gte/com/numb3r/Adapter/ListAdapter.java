package numb3r.gte.com.numb3r.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import numb3r.gte.com.numb3r.MainActivity;
import numb3r.gte.com.numb3r.Model.MyNumbers;
import numb3r.gte.com.numb3r.R;
import numb3r.gte.com.numb3r.Summary;

/**
 * Created by andrewlaurienrsocia on 01/12/2017.
 */

public class ListAdapter extends ArrayAdapter {

    Context context;
    ArrayList<MyNumbers> numbers;
    int layoutID;

    public ListAdapter(@NonNull Context context, int resource, ArrayList<MyNumbers> numbers) {
        super(context, resource);
        this.context = context;
        this.numbers = numbers;
        this.layoutID = resource;
    }

    @Override
    public int getCount() {
        int size = 0;
        if (numbers != null)
            size = numbers.size();
        return size;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(layoutID, parent, false);

        if (layoutID == R.layout.list_number) {

            TextView amount = (TextView) convertView.findViewById(R.id.txtamount);
            TextView combination = (TextView) convertView.findViewById(R.id.txtnumber);
            TextView isrambolito = (TextView) convertView.findViewById(R.id.txtrambolito);

            amount.setText(numbers.get(position).getAmount());
            combination.setText(numbers.get(position).getCombination());
            isrambolito.setText(numbers.get(position).getIsRambolito());
            ImageView imgRemove = (ImageView) convertView.findViewById(R.id.imgRemove);

            imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.myNumbers.remove(position);
                    Summary.adapter.notifyDataSetInvalidated();
                    Summary.setTotal();
                }
            });

        }
        return convertView;

    }
}
