package proj.mokrab.paysera_uzduotis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.concurrent.ExecutionException;

public class CurrencyConversionActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_EUR = "EUR_REPLY";
    public static final String EXTRA_REPLY_USD = "USD_REPLY";
    public static final String EXTRA_REPLY_JPY = "JPY_REPLY";
    public static final String EXTRA_REPLY_STORED_EUR_COMMISSION = "STORED_EUR_COMMISSION_REPLY";
    public static final String EXTRA_REPLY_STORED_USD_COMMISSION = "STORED_USD_COMMISSION_REPLY";
    public static final String EXTRA_REPLY_STORED_JPY_COMMISSION = "STORED_JPY_COMMISSION_REPLY";
    public static final String EXTRA_REPLY_TIMES_COVNERTED = "TIMES_CONVERTED_REPLY";


    public static TextView textViewResult;
    private static String url = "http://api.evp.lt/currency/commercial/exchange/0-EUR/EUR/latest";

    private Calculations calculations;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private EditText amountInput;
    private Double eur_balance;
    private Double usd_balance;
    private Double commission_fee;
    private Double stored_eur_commissions;
    private Double stored_usd_commissions;
    private Double stored_jpy_commissions;
    private Double jpy_balance;
    private Integer times_converted;
    private String jsonData;
    private Double amount;
    private String from;
    private String to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion);

        calculations = new Calculations();

        spinnerFrom = findViewById(R.id.spinner_from);
        spinnerTo = findViewById(R.id.spinner_to);
        amountInput = findViewById(R.id.editText_convert_amount_input);
        textViewResult = findViewById(R.id.textView_json_test);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_string_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);


        Intent intent = getIntent();
        commission_fee = intent.getDoubleExtra(MainActivity.EXTRA_COMMISSION_FEE, 0);
        stored_eur_commissions = intent.getDoubleExtra(MainActivity.EXTRA_STORED_EUR_COMMISSIONS, 0);
        stored_usd_commissions = intent.getDoubleExtra(MainActivity.EXTRA_STORED_USD_COMMISSIONS, 0);
        stored_jpy_commissions = intent.getDoubleExtra(MainActivity.EXTRA_STORED_JPY_COMMISSIONS, 0);
        eur_balance = intent.getDoubleExtra(MainActivity.EXTRA_EUR, 0);
        usd_balance = intent.getDoubleExtra(MainActivity.EXTRA_USD, 0);
        jpy_balance = intent.getDoubleExtra(MainActivity.EXTRA_JPY, 0);
        times_converted = intent.getIntExtra(MainActivity.EXTRA_TIMES_CONVERTED, 0);

    }

    public void button_convert(View view) {

        from = spinnerFrom.getSelectedItem().toString();
        to = spinnerTo.getSelectedItem().toString();

        if (TextUtils.isEmpty(amountInput.getText()) || Integer.valueOf(amountInput.getText().toString()) <= 0) {
            textViewResult.setText("Invalid amount.");
        } else {
            amount = Double.valueOf(amountInput.getText().toString());
            url = "http://api.evp.lt/currency/commercial/exchange/" + amountInput.getText() + "-" +
                    spinnerFrom.getSelectedItem().toString() + "/" +
                    spinnerTo.getSelectedItem().toString() + "/latest";

            FetchData getJsonData = new FetchData(url);
            getJsonData.execute();

            try {

                getJsonData.get();
                jsonData = String.valueOf(getJsonData.get());

                setCalculationValues();

                NumberFormat format = NumberFormat.getInstance();
                format.setCurrency(Currency.getInstance(from));

                String result = calculations.calculateBalance();

                eur_balance = calculations.getEurBalanceResult();
                usd_balance = calculations.getUsdBalanceResult();
                jpy_balance = calculations.getJpyBalanceResult();
                times_converted = calculations.getTimesConverted();


                if (from.equals("EUR")) {
                    stored_eur_commissions = calculations.getStoredCommissions();
                    textViewResult.setText(result + "\n" + "Paid Commissions: " + format.format(stored_eur_commissions) + " " + from);
                } else if (from.equals("USD")) {
                    stored_usd_commissions = calculations.getStoredCommissions();
                    textViewResult.setText(result + "\n" + "Paid Commissions: " + format.format(stored_usd_commissions) + " " + from);
                } else if (from.equals("JPY")) {
                    stored_jpy_commissions = calculations.getStoredCommissions();
                    textViewResult.setText(result + "\n" + "Paid Commissions: " + format.format(stored_jpy_commissions) + " " + from);
                } else {
                }


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public void button_return(View view) {

        returnBalanceReply();

    }

    public void returnBalanceReply() {

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY_EUR, eur_balance);
        replyIntent.putExtra(EXTRA_REPLY_USD, usd_balance);
        replyIntent.putExtra(EXTRA_REPLY_JPY, jpy_balance);
        replyIntent.putExtra(EXTRA_REPLY_STORED_EUR_COMMISSION, stored_eur_commissions);
        replyIntent.putExtra(EXTRA_REPLY_STORED_USD_COMMISSION, stored_usd_commissions);
        replyIntent.putExtra(EXTRA_REPLY_STORED_JPY_COMMISSION, stored_jpy_commissions);
        replyIntent.putExtra(EXTRA_REPLY_TIMES_COVNERTED, times_converted);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private void setCalculationValues() {

        calculations.setTimesConverted(times_converted);
        calculations.setAmount(amount);
        calculations.setFrom(from);
        calculations.setTo(to);
        calculations.setJsonData(jsonData);
        calculations.setEurBalance(eur_balance);
        calculations.setUsdBalance(usd_balance);
        calculations.setJpyBalance(jpy_balance);
        calculations.setCommissionFee(commission_fee);

        if (from.equals("EUR")) {
            calculations.setStoredCommissions(stored_eur_commissions);
        } else if (from.equals("USD")) {
            calculations.setStoredCommissions(stored_usd_commissions);

        } else if (from.equals("JPY")) {
            calculations.setStoredCommissions(stored_jpy_commissions);
        } else {
        }
    }


}


