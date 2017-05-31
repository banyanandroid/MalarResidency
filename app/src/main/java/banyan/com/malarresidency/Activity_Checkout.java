package banyan.com.malarresidency;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;

public class Activity_Checkout extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    EditText guest_edt_email;
    Button guest_btn_register, btn_payment;
    ImageView img_back;
    private static long back_pressed;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_Booking.class);
                startActivity(i);
                finish();
            }
        });

        guest_edt_email = (EditText) findViewById(R.id.guest_edt_email);
        guest_btn_register = (Button) findViewById(R.id.guest_btn_register);
        btn_payment = (Button) findViewById(R.id.checkout_btn_makepayment);

        guest_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                int hour = 0;
                int minute = 0;
                mTimePicker = new TimePickerDialog(Activity_Checkout.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        guest_btn_register.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Thank You ! ", Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = "You picked the following time: "+hourOfDay+"h"+minute;
        guest_edt_email.setText(time);
    }

    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()) {

            this.moveTaskToBack(true);
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();

        }
        back_pressed = System.currentTimeMillis();
    }

}