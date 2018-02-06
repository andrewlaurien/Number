package numb3r.gte.com.numb3r;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.gsm.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import numb3r.gte.com.numb3r.Common.Common;
import numb3r.gte.com.numb3r.Model.Transaction;

public class Summary extends AppCompatActivity {

    EditText txtmobile;
    ListView list;
    public static numb3r.gte.com.numb3r.Adapter.ListAdapter adapter;
    public static TextView txtotalAmount;
    public static int totalAmount = 0;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtmobile = findViewById(R.id.txtmobile);
        txtotalAmount = findViewById(R.id.txttotalamount);
        list = findViewById(R.id.list);
        adapter = new numb3r.gte.com.numb3r.Adapter.ListAdapter(getBaseContext(), R.layout.list_number, MainActivity.myNumbers);
        list.setAdapter(adapter);
        setTotal();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.summary, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send) {
            if (txtmobile.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "Add Mobile number to continue.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!Common.isNumberMatch(txtmobile.getText().toString())) {
                Toast.makeText(getBaseContext(), "Invalid mobile number.", Toast.LENGTH_SHORT).show();
                return false;
            }
            sendSMS(txtmobile.getText().toString(), composeMessage());
        }
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setTotal() {
        totalAmount = 0;
        for (int i = 0; i < MainActivity.myNumbers.size(); i++) {
            totalAmount = totalAmount + Integer.parseInt(MainActivity.myNumbers.get(i).getAmount());
        }
        txtotalAmount.setText("" + totalAmount);
    }

//    private void sendSMS(String phoneNumber, String message) {
//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumber, null, message, null, null);
//        Transaction trxn = new Transaction(txtmobile.getText().toString(), txtotalAmount.getText().toString(), MainActivity.myNumbers);
//        MainActivity.transction.add(trxn);
//        Common.setPreferenceObject(getBaseContext(), MainActivity.transction, "Transactions");
//        MainActivity.myNumbers.clear();
//        adapter.notifyDataSetChanged();
//        setTotal();
//        this.finish();
//    }

    // Method to send SMS.
    private void sendSMS(String mobNo, String message) {


        String numbers[] = {MainActivity.usher.getTASNumber(), mobNo};

        String smsSent = "SMS_SENT";
        String smsDelivered = "SMS_DELIVERED";
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(smsSent), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(smsDelivered), 0);

        // Receiver for Sent SMS.
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                unregisterReceiver(this);
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(smsSent));

        // Receiver for Delivered SMS.
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(smsDelivered));

        SmsManager sms = SmsManager.getDefault();
        for (String number : numbers) {
            sms.sendTextMessage(number, null, message, sentPI, deliveredPI);
        }

        Transaction trxn = new Transaction(txtmobile.getText().toString(), txtotalAmount.getText().toString(), MainActivity.myNumbers, Common.getLongDate());
        trxn.setDrawDate(Common.getDate());
        trxn.setDrawHour(Common.getHour());
        MainActivity.transction.add(trxn);
        Common.setPreferenceObject(getBaseContext(), MainActivity.transction, "Transactions");
        MainActivity.myNumbers.clear();
        adapter.notifyDataSetChanged();
        setTotal();
        this.finish();

    }

    public String composeMessage() {
        String result = "";
        for (int i = 0; i < MainActivity.myNumbers.size(); i++) {
            result = result + "Number:" +
                    MainActivity.myNumbers.get(i).getCombination() + "   IsRambolito:" + MainActivity.myNumbers.get(i).getIsRambolito() + "  Amount:" + MainActivity.myNumbers.get(i).getAmount() + "\n";
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
