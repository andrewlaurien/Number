package numb3r.gte.com.numb3r.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import numb3r.gte.com.numb3r.KeyBoard.MyKeyboard;
import numb3r.gte.com.numb3r.MainActivity;
import numb3r.gte.com.numb3r.R;
import numb3r.gte.com.numb3r.Summary;

/**
 * A simple {@link Fragment} subclass.
 */
public class BetFragment extends Fragment implements View.OnFocusChangeListener {


    public BetFragment() {
        // Required empty public constructor
    }

    View view;
    Context mcontext;
    public static EditText txtFirst;
    public static EditText txtSecond;
    public static EditText txtThird;
    public static EditText txtAmount;
    public static Switch aSwitch;
    MyKeyboard keyboard;
    InputConnection ic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bet, container, false);
        mcontext = getActivity();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtFirst = view.findViewById(R.id.txtFirst);
        txtSecond = view.findViewById(R.id.txtSecond);
        txtThird = view.findViewById(R.id.txtThird);
        txtAmount = view.findViewById(R.id.txtAmount);
        keyboard = view.findViewById(R.id.keyboard);
        aSwitch = view.findViewById(R.id.switch1);


        txtFirst.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        txtFirst.setTextIsSelectable(true);
        txtSecond.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        txtSecond.setTextIsSelectable(true);
        txtThird.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        txtThird.setTextIsSelectable(true);
        txtAmount.setRawInputType(R.id.txtAmount);
        txtAmount.setTextIsSelectable(true);

        txtFirst.setOnFocusChangeListener(this);
        txtSecond.setOnFocusChangeListener(this);
        txtThird.setOnFocusChangeListener(this);
        txtAmount.setOnFocusChangeListener(this);

        txtAmount.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtAmount.getText().toString().matches("^0")) {
                    txtAmount.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        InputConnection ic = txtFirst.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);
    }

    @Override
    public void onFocusChange(View view, boolean b) {


        switch (view.getId()) {

            case R.id.txtFirst:
                ic = txtFirst.onCreateInputConnection(new EditorInfo());
                keyboard.setInputConnection(ic);
                break;

            case R.id.txtSecond:
                ic = txtSecond.onCreateInputConnection(new EditorInfo());
                keyboard.setInputConnection(ic);
                break;

            case R.id.txtThird:
                ic = txtThird.onCreateInputConnection(new EditorInfo());
                keyboard.setInputConnection(ic);
                break;

            case R.id.txtAmount:
                ic = txtAmount.onCreateInputConnection(new EditorInfo());
                keyboard.setInputConnection(ic);

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_next) {
            if (MainActivity.myNumbers.size() == 0) {
                Toast.makeText(mcontext, "Add at least 1 combination to continue", Toast.LENGTH_SHORT).show();
                return false;
            }

            Intent intent = new Intent(mcontext, Summary.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.bet, menu);
    }

}
