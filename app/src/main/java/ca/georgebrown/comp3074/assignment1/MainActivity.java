package ca.georgebrown.comp3074.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        calculateRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoursWorkedText = hoursWorked.getText().toString();
                String hourlyRateText = hourlyRate.getText().toString();

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
                    calculatedPay.setText(String.format("%.2f", pay));
                    overtimePay.setText("N/A");
                }else{
                    calculatedPay.setText("N/A");
                    overtimePay.setText(String.format("%.2f", pay));
                }
                tax.setText(String.format("%.2f", taxAmount));
                totalPay.setText(String.format("%.2f", totalPayout));
            }
        });
    }
}
