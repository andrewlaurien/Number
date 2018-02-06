package numb3r.gte.com.numb3r.KeyBoard;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import numb3r.gte.com.numb3r.Common.Common;
import numb3r.gte.com.numb3r.Fragment.BetFragment;
import numb3r.gte.com.numb3r.MainActivity;
import numb3r.gte.com.numb3r.Model.MyNumbers;
import numb3r.gte.com.numb3r.R;

/**
 * Created by andrewlaurienrsocia on 29/11/2017.
 */

public class MyKeyboard extends CoordinatorLayout implements View.OnClickListener {


    private Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNini, btnZero, btnAdd, btnClear;

    private SparseArray<String> keyValues = new SparseArray<>();
    InputConnection inputConnection;

    public MyKeyboard(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        btnOne = findViewById(R.id.btnOne);
        btnOne.setOnClickListener(this);
        btnTwo = findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(this);
        btnThree = findViewById(R.id.btnThree);
        btnThree.setOnClickListener(this);
        btnFour = findViewById(R.id.btnFour);
        btnFour.setOnClickListener(this);
        btnFive = findViewById(R.id.btnFive);
        btnFive.setOnClickListener(this);
        btnSix = findViewById(R.id.btnSix);
        btnSix.setOnClickListener(this);
        btnSeven = findViewById(R.id.btnSeven);
        btnSeven.setOnClickListener(this);
        btnEight = findViewById(R.id.btnEight);
        btnEight.setOnClickListener(this);
        btnNini = findViewById(R.id.btnNine);
        btnNini.setOnClickListener(this);
        btnZero = findViewById(R.id.btnZero);
        btnZero.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        keyValues.put(R.id.btnOne, "1");
        keyValues.put(R.id.btnTwo, "2");
        keyValues.put(R.id.btnThree, "3");
        keyValues.put(R.id.btnFour, "4");
        keyValues.put(R.id.btnFive, "5");
        keyValues.put(R.id.btnSix, "6");
        keyValues.put(R.id.btnSeven, "7");
        keyValues.put(R.id.btnEight, "8");
        keyValues.put(R.id.btnNine, "9");
        keyValues.put(R.id.btnZero, "0");
        keyValues.put(R.id.btnAdd, "Add");
        keyValues.put(R.id.btnClear, "Clear");

    }

    @Override
    public void onClick(View view) {
        if (inputConnection == null) {
            return;
        }
        if (view.getId() == R.id.btnClear) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                inputConnection.commitText("", 1);
            }
        } else if (view.getId() == R.id.btnAdd) {
//            BetFragment.txtFirst.setText("" + Common.getRandomNumber());
//            BetFragment.txtSecond.setText("" + Common.getRandomNumber());
//            BetFragment.txtThird.setText("" + Common.getRandomNumber());


            if(MainActivity.usher.getTASNumber().isEmpty()){
                Toast.makeText(view.getContext(), "No TAS number is active. Please inform your coordinator", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Common.validate(new EditText[]{BetFragment.txtFirst, BetFragment.txtSecond, BetFragment.txtThird})) {
                Toast.makeText(view.getContext(), "Invalid number combination", Toast.LENGTH_SHORT).show();
                return;
            }


            if (BetFragment.txtAmount.getText().toString().isEmpty()) {
                Toast.makeText(view.getContext(), "Invalid Amount", Toast.LENGTH_SHORT).show();
                return;
            }


            if (BetFragment.txtAmount.getText().toString().equals("0")) {
                Toast.makeText(view.getContext(), "Invalid Amount", Toast.LENGTH_SHORT).show();
                return;
            }

            MyNumbers numbers = new MyNumbers();
            numbers.setAmount(BetFragment.txtAmount.getText().toString());
            numbers.setCombination(BetFragment.txtFirst.getText().toString() + "" + BetFragment.txtSecond.getText().toString() + "" + BetFragment.txtThird.getText().toString());
            numbers.setIsRambolito(BetFragment.aSwitch.isChecked());
            MainActivity.myNumbers.add(numbers);

            BetFragment.txtFirst.setText("");
            BetFragment.txtSecond.setText("");
            BetFragment.txtThird.setText("");
            BetFragment.txtAmount.setText("");

        } else {
            String value = keyValues.get(view.getId());
            inputConnection.commitText(value, 1);
        }
    }


    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;

    }
}
