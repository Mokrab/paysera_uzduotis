package proj.mokrab.paysera_uzduotis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.Currency;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_EUR = "EUR_BALANCE";
    public static final String EXTRA_USD = "USD_BALANCE";
    public static final String EXTRA_JPY = "JPY_BALANCE";
    public static final String EXTRA_STORED_EUR_COMMISSIONS = "STORED_EUR_COMMISSIONS";
    public static final String EXTRA_STORED_USD_COMMISSIONS = "STORED_USD_COMMISSIONS";
    public static final String EXTRA_STORED_JPY_COMMISSIONS = "STORED_JPY_COMMISSIONS";
    public static final String EXTRA_COMMISSION_FEE = "COMMISSION_FEE";
    public static final String EXTRA_TIMES_CONVERTED = "EXTRA_TIMES_CONVERTED";
    public static final int REPLY_REQUEST = 1;

    private Double eurBalance = 1000.00;
    private Double usdBalance = 0.00;
    private Double jpyBalance = 0.00;
    private Double storedEurCommissions = 0.00;
    private Double storedUsdCommissions = 0.00;
    private Double storedJpyCommissions = 0.00;
    private Double commissionFee = 0.7;
    private TextView viewEurBalance;
    private TextView viewUsdBalance;
    private TextView viewJpyBalance;
    private TextView viewFinalMessage;
    private Integer timesConverted = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewEurBalance = findViewById(R.id.textView_balance_EUR);
        viewUsdBalance = findViewById(R.id.textView_balance_USD);
        viewJpyBalance = findViewById(R.id.textView_balance_JPY);
        viewFinalMessage = findViewById(R.id.textView_finalMessage);

        viewEurBalance.setText("EUR: " + Double.toString(eurBalance));
        viewUsdBalance.setText("USD: " + Double.toString(usdBalance));
        viewJpyBalance.setText("JPY: " + Double.toString(jpyBalance));
        viewFinalMessage.setText("Paid Commission Count: " + timesConverted + "\n" + "Paid Commission Amount: None");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REPLY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Double eur_reply = data.getDoubleExtra(CurrencyConversionActivity.EXTRA_REPLY_EUR, 0);
                Double usd_reply = data.getDoubleExtra(CurrencyConversionActivity.EXTRA_REPLY_USD, 0);
                Double jpy_reply = data.getDoubleExtra(CurrencyConversionActivity.EXTRA_REPLY_JPY, 0);
                Double stored_eur_commissions_reply = data.getDoubleExtra(CurrencyConversionActivity.EXTRA_REPLY_STORED_EUR_COMMISSION, 0);
                Double stored_usd_commissions_reply = data.getDoubleExtra(CurrencyConversionActivity.EXTRA_REPLY_STORED_USD_COMMISSION, 0);
                Double stored_jpy_commissions_reply = data.getDoubleExtra(CurrencyConversionActivity.EXTRA_REPLY_STORED_JPY_COMMISSION, 0);
                Integer times_converted_reply = data.getIntExtra(CurrencyConversionActivity.EXTRA_REPLY_TIMES_COVNERTED, 0);

                NumberFormat format_eur = NumberFormat.getInstance();
                NumberFormat format_usd = NumberFormat.getInstance();
                NumberFormat format_jpy = NumberFormat.getInstance();
                format_eur.setCurrency(Currency.getInstance("EUR"));
                format_usd.setCurrency(Currency.getInstance("USD"));
                format_jpy.setCurrency(Currency.getInstance("JPY"));


                eurBalance = eur_reply;
                usdBalance = usd_reply;
                jpyBalance = jpy_reply;
                timesConverted = times_converted_reply;
                storedEurCommissions = stored_eur_commissions_reply;
                storedUsdCommissions = stored_usd_commissions_reply;
                storedJpyCommissions = stored_jpy_commissions_reply;

                viewEurBalance.setText("EUR: " + format_eur.format(eurBalance));
                viewUsdBalance.setText("USD: " + format_usd.format(usdBalance));
                viewJpyBalance.setText("JPY: " + format_jpy.format(jpyBalance));
                viewFinalMessage.setText("Paid Commission Count: " + timesConverted + "\n" + "Paid Commission Amount: " + "\n" +
                        format_eur.format(storedEurCommissions) + " EUR" + "\n" +
                        format_usd.format(storedUsdCommissions) + " USD" + "\n" +
                        format_jpy.format(storedJpyCommissions) + " JPY");


            }
        }


    }


    public void button_openConvertCurrencyActivity(View view) {


        Intent intent = new Intent(this, CurrencyConversionActivity.class);
        intent.putExtra(EXTRA_EUR, eurBalance);
        intent.putExtra(EXTRA_USD, usdBalance);
        intent.putExtra(EXTRA_JPY, jpyBalance);
        intent.putExtra(EXTRA_TIMES_CONVERTED, timesConverted);
        intent.putExtra(EXTRA_STORED_EUR_COMMISSIONS, storedEurCommissions);
        intent.putExtra(EXTRA_STORED_USD_COMMISSIONS, storedUsdCommissions);
        intent.putExtra(EXTRA_STORED_JPY_COMMISSIONS, storedJpyCommissions);
        intent.putExtra(EXTRA_COMMISSION_FEE, commissionFee);

        startActivityForResult(intent, REPLY_REQUEST);


    }
}
