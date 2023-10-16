package ca.georgebrown.comp3074.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView title;
    EditText hoursWorked;
    EditText hourlyRate;
    Button calculateRate;
    TextView calculatedPay;
    TextView overtimePay;
    TextView tax;
    TextView totalPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        hoursWorked = findViewById(R.id.HoursWorked);
        hourlyRate = findViewById(R.id.HourlyRate);
        calculateRate = findViewById(R.id.CalculateButton);
        calculatedPay = findViewById(R.id.CalculatedPay);
        overtimePay = findViewById(R.id.OvertimePay);
        tax = findViewById(R.id.Tax);
        totalPay = findViewById(R.id.TotalPay);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        calculateRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoursWorkedText = hoursWorked.getText().toString();
                String hourlyRateText = hourlyRate.getText().toString();
                //if field empty, app crashes. added this to handle that edge case
                if(hoursWorkedText.isEmpty() || hourlyRateText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Both fields must be filled out", Toast.LENGTH_SHORT).show();
                    return;
                }

                //I noticed the keyboard takes up too much space, and you need to minimize to see
                //googled how to lower the keyboard after pressing on the button
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }


                //convert for the calculations
                double pay;
                double taxAmount;
                boolean hasOvertime = false;
                double totalPayout;
                double hoursWorkedValue = Double.parseDouble(hoursWorkedText);
                double hourlyRateValue = Double.parseDouble(hourlyRateText);


                //quick maths
                if(hoursWorkedValue <= 40){
                   pay = hoursWorkedValue * hourlyRateValue;
                }else{
                    hasOvertime = true;
                    pay = (hoursWorkedValue - 40) * hourlyRateValue * 1.5 + 40 * hourlyRateValue;
                }
                taxAmount = pay * 0.18;
                totalPayout = pay - taxAmount;



                if(!hasOvertime){
                    calculatedPay.setText("Normal pay: " + String.format("%.2f", pay));
                    overtimePay.setText("Overtime pay: N/A");
                }else{
                    calculatedPay.setText("Normal pay: N/A");
                    overtimePay.setText("Overtime Pay: " + String.format("%.2f", pay));
                }
                tax.setText("Tax: " + String.format("%.2f", taxAmount));
                totalPay.setText("Total pay: " + String.format("%.2f", totalPayout));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about_activity) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
