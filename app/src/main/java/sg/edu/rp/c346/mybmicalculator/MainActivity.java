package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText weight, height;
    Button cal, reset;
    TextView date, bmi, eval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = findViewById(R.id.editTextWeight);
        height = findViewById(R.id.editTextHeight);
        cal = findViewById(R.id.buttonCalculate);
        reset = findViewById(R.id.buttonReset);
        date = findViewById(R.id.textViewDate);
        bmi = findViewById(R.id.textViewBMI);
        eval = findViewById(R.id.textViewEvaluation);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double calWeight = Double.parseDouble(weight.getText().toString());
                double calheight = Double.parseDouble(height.getText().toString());
                double BmiResult = calWeight / calheight;

                if(BmiResult < 18.5){
                    eval.setText("You are underweight");
                }else if(BmiResult <24.9){
                    eval.setText("Your BMI is normal");
                }else if(BmiResult < 29.9){
                    eval.setText("You are overweight");
                }else if(BmiResult > 30){
                    eval.setText("You are obese");
                }else{
                    eval.setText("Invalid");
                }
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                date.setText(datetime);

                bmi.setText(String.format("%.3f",BmiResult));
            }

        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.putString("date", "");
                prefEdit.putFloat("bmi", 0);

                prefEdit.commit();

                weight.setText(" ");
                height.setText(" ");
                eval.setText(" ");

                onResume();
        }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        String datetrf = date.getText().toString();
        float bmitrf = Float.parseFloat(bmi.getText().toString());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString("date", datetrf);
        prefEdit.putFloat("bmi", bmitrf);

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Float Bmi = prefs.getFloat("bmi", 0);
        String Bmiset = String.valueOf(Bmi);
        String dateset = prefs.getString("date", " ");
        date.setText(dateset);
        bmi.setText(Bmiset);
    }
}
