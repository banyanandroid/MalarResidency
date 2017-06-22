package banyan.com.malarresidency;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import banyan.com.malarresidency.Adapter.RoomType_Adapter;
import banyan.com.malarresidency.Global.AppConfig;

public class Activity_Checkout extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {


    EditText guest_edt_fname, guest_edt_lname, guest_edt_contact_num, guest_edt_email,
            guest_edt_ad_line1, guest_edt_ad_line2, guest_edt_city, guest_edt_state, guest_edt_country, guest_edt_postcode;

    Button btn_guest_arrival_time, btn_payment;

    CheckBox chb_pickup, chb_drop;

    String str_fname, str_lname, str_contact_num, str_email, str_add_line1, str_add_line2,
            str_city, str_state, str_country, str_postcode, str_arrival_time, str_room_type,
            str_room_cost, str_room_tax, str_room_service_tax, str_total_cost = "null";

    String str_pickup, str_drop = "null";

    String str_from_date, str_to_date, str_no_of_rooms, str_no_of_adults, str_no_of_childs = "null";

    ImageView img_back;
    String TAG;
    public static RequestQueue queue;
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

        guest_edt_fname = (EditText) findViewById(R.id.guest_edt_first_name);
        guest_edt_lname = (EditText) findViewById(R.id.guest_edt_last_name);
        guest_edt_contact_num = (EditText) findViewById(R.id.guest_edt_mobile);
        guest_edt_email = (EditText) findViewById(R.id.guest_edt_email);
        guest_edt_ad_line1 = (EditText) findViewById(R.id.guest_edt_Address_line1);
        guest_edt_ad_line2 = (EditText) findViewById(R.id.guest_edt_Address_line2);
        guest_edt_city = (EditText) findViewById(R.id.guest_edt_City);
        guest_edt_state = (EditText) findViewById(R.id.guest_edt_State);
        guest_edt_country = (EditText) findViewById(R.id.guest_edt_Country);
        guest_edt_postcode = (EditText) findViewById(R.id.guest_edt_Post_Code);

        chb_pickup = (CheckBox) findViewById(R.id.guest_chb_pickup);
        chb_drop = (CheckBox) findViewById(R.id.guest_chb_drop);

        btn_guest_arrival_time = (Button) findViewById(R.id.guest_btn_arrival_time);

        btn_payment = (Button) findViewById(R.id.checkout_btn_makepayment);

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_fname = guest_edt_fname.getText().toString();
                str_lname = guest_edt_lname.getText().toString();
                str_contact_num = guest_edt_contact_num.getText().toString();
                str_email = guest_edt_email.getText().toString();
                str_add_line1 = guest_edt_ad_line1.getText().toString();
                str_add_line2 = guest_edt_ad_line2.getText().toString();
                str_city = guest_edt_city.getText().toString();
                str_state = guest_edt_state.getText().toString();
                str_country = guest_edt_country.getText().toString();
                str_postcode = guest_edt_postcode.getText().toString();

                if (chb_pickup.isChecked()) {
                    str_pickup = "YES";
                } else {
                    str_pickup = "NO";
                }
                if (chb_drop.isChecked()) {
                    str_drop = "YES";
                } else {
                    str_drop = "NO";
                }

                if (str_fname.equals("null")) {
                    Toast.makeText(getApplicationContext(), "FirstName Cannot be Empty", Toast.LENGTH_LONG).show();
                    guest_edt_fname.setError("Enter FirstName");
                } else if (str_contact_num.equals("null")) {
                    Toast.makeText(getApplicationContext(), "Contact Number Cannot be Empty", Toast.LENGTH_LONG).show();
                    guest_edt_contact_num.setError("Enter Contact Number");
                } else if (str_email.equals("null")) {
                    Toast.makeText(getApplicationContext(), "Email Cannot be Empty", Toast.LENGTH_LONG).show();
                    guest_edt_email.setError("Enter Email");
                } else if (str_add_line1.equals("null")) {
                    Toast.makeText(getApplicationContext(), "AddressLine 1 Cannot be Empty", Toast.LENGTH_LONG).show();
                    guest_edt_ad_line1.setError("Enter AddressLine 1");
                } else if (str_city.equals("null")) {
                    Toast.makeText(getApplicationContext(), "City Cannot be Empty", Toast.LENGTH_LONG).show();
                    guest_edt_city.setError("Enter City");
                } else if (str_state.equals("null")) {
                    Toast.makeText(getApplicationContext(), "State Cannot be Empty", Toast.LENGTH_LONG).show();
                    guest_edt_state.setError("Enter State");
                } else if (str_country.equals("null")) {
                    Toast.makeText(getApplicationContext(), "Country Cannot be Empty", Toast.LENGTH_LONG).show();
                    guest_edt_country.setError("Enter Country");
                } else if (str_postcode.equals("null")) {
                    Toast.makeText(getApplicationContext(), "Postcode Cannot be Empty", Toast.LENGTH_LONG).show();
                    guest_edt_postcode.setError("Enter Postcode");
                } else {

                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(getApplicationContext());

                    //From Main Activity
                    str_from_date = sharedPreferences.getString("str_from_date", "str_from_date");
                    str_to_date = sharedPreferences.getString("str_to_date", "str_to_date");
                    str_no_of_rooms = sharedPreferences.getString("str_no_of_rooms", "str_no_of_rooms");
                    str_no_of_adults = sharedPreferences.getString("str_no_of_adults", "str_no_of_adults");
                    str_no_of_childs = sharedPreferences.getString("str_no_of_childs", "str_no_of_childs");
                    //From Select Rooms Activity
                    str_room_type = sharedPreferences.getString("str_room_id", "str_room_id");
                    str_room_cost = sharedPreferences.getString("str_room_cost", "str_room_cost");
                    //From Room Description Activity
                    str_room_tax = sharedPreferences.getString("str_room_new_tax", "str_room_new_tax");
                    str_room_service_tax = sharedPreferences.getString("str_room_new_service_tax", "str_room_new_service_tax");
                    str_total_cost = sharedPreferences.getString("str_total_cost", "str_total_cost");

                    try {
                        queue = Volley.newRequestQueue(Activity_Checkout.this);
                        GetGuest_Details();
                    } catch (Exception e) {

                    }
                }

            }
        });

        btn_guest_arrival_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                int hour = 0;
                int minute = 0;
                mTimePicker = new TimePickerDialog(Activity_Checkout.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        btn_guest_arrival_time.setText(selectedHour + ":" + selectedMinute);
                        str_arrival_time = "" + selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = "You picked the following time: " + hourOfDay + "h" + minute;
        guest_edt_email.setText(time);
    }

    /***************************
     * GET Guest Details
     ***************************/
    public void GetGuest_Details() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.room_check, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());


                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    System.out.println("JSON :::: Response");

                    if (success == 1) {

                        Alerter.create(Activity_Checkout.this)
                                .setTitle("MALAR RESIDENCY")
                                .setText("Details Added Sucessfully :)")
                                .setBackgroundColor(R.color.bg_screen3)
                                .show();

                    } else {

                        Alerter.create(Activity_Checkout.this)
                                .setTitle("MALAR RESIDENCY")
                                .setText("Oops..! Something went Wrong :(")
                                .setBackgroundColor(R.color.bg_screen1)
                                .show();
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Alerter.create(Activity_Checkout.this)
                        .setTitle("Malar Residency")
                        .setText("Internal Error")
                        .setBackgroundColor(R.color.bg_screen1)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                //From Main Activity
                params.put("guest_check_in", str_from_date);
                params.put("guest_check_out", str_to_date);
                params.put("guest_no_of_rooms", str_no_of_rooms);
                params.put("guest_no_of_adults", str_no_of_adults);
                params.put("guest_no_of_children", str_no_of_childs);
                //From Select Rooms Activity
                params.put("guest_room_type", str_room_type);
                params.put("guest_room_cost", str_room_cost);
                //From Room Description Activity
                params.put("guest_tax", str_room_tax);
                params.put("guest_service_tax", str_room_service_tax);
                params.put("guest_total_cost", str_total_cost);
                //From this Activity
                params.put("guest_fname", str_fname);
                params.put("guest_lname", str_lname);
                params.put("guest_mobile", str_contact_num);
                params.put("guest_email", str_email);
                params.put("guest_add_line1", str_add_line1);
                params.put("guest_add_line2", str_add_line2);
                params.put("guest_city", str_city);
                params.put("guest_state", str_state);
                params.put("guest_country", str_country);
                params.put("guest_postcode", str_postcode);
                params.put("guest_pickup", str_pickup);
                params.put("guest_drop", str_drop);
                params.put("guest_arrival_time", str_arrival_time);

                System.out.println("OUTPUT::::" + "Room Type :" + str_room_type + "Room Cost :" +
                        str_room_cost + "checkin :" + str_from_date + "Checkout :" + str_to_date +
                        "No.of.Rooms :" + str_no_of_rooms + "Adults :" + str_no_of_adults +
                        "Childs :" + str_no_of_childs + "FirstName " + str_fname + "LastName :" +
                        str_lname + "Contact Number :" + str_contact_num + "Email :" + str_email +
                        "Address Line 1 :" + str_add_line1 + "Address Line 2 :" + str_add_line2 +
                        "City :" + str_city + "State :" + str_state + "Country :" + str_country +
                        "Post Code :" + str_postcode + "PickUP :" + str_pickup + "Drop :" + str_drop +
                        "Arrival Time :" + str_arrival_time + "TAX :" + str_room_tax + "Service Tax :" +
                        str_room_service_tax + "Total Cost :" + str_total_cost);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
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