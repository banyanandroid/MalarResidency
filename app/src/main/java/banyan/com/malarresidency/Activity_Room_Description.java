package banyan.com.malarresidency;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tapadoo.alerter.Alerter;

/**
 * Created by Schan on 14-Jun-17.
 */

public class Activity_Room_Description extends AppCompatActivity {

    ImageView img_room_pic;

    TextView txt_room_type, txt_room_cost, txt_room_desc, txt_room_tax, txt_room_service_tax, txt_available_rooms, txt_room_total_cost;

    Button btn_book_now;

    String str_no_of_nights;

    long no_of_nights;

    String str_req_rooms, str_room_type, str_room_description, str_room_cost, str_room_img_path,
            str_room_img, str_room_available, str_total_cost, str_room_tax, str_room_service_tax,str_no_of_rooms;

    String str_room_new_tax, str_room_new_service_tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_desc);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_Select_Rooms.class);
                startActivity(i);
                finish();
            }
        });

        img_room_pic = (ImageView) findViewById(R.id.room_desc_roomimg);

        txt_room_type = (TextView) findViewById(R.id.room_desc_roomtype);
        txt_room_cost = (TextView) findViewById(R.id.room_desc_roomcost);
        txt_room_desc = (TextView) findViewById(R.id.room_desc_roomdesc);
        txt_room_tax = (TextView) findViewById(R.id.room_desc_room_tax);
        txt_room_service_tax = (TextView) findViewById(R.id.room_desc_room_service_tax);
        txt_available_rooms = (TextView) findViewById(R.id.room_desc_available_rooms);
        txt_room_total_cost = (TextView) findViewById(R.id.room_desc_room_total_cost);

        btn_book_now = (Button) findViewById(R.id.room_desc_book_btn);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        str_no_of_nights = sharedPreferences.getString("str_no_of_nights", "str_no_of_nights");
        str_no_of_rooms = sharedPreferences.getString("str_no_of_rooms", "str_no_of_rooms");

        no_of_nights = Long.parseLong(str_no_of_nights);


        btn_book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());

                str_req_rooms = sharedPreferences.getString("str_no_of_rooms", "str_no_of_rooms");

                str_room_img = sharedPreferences.getString("str_no_of_nights", "str_no_of_nights");

                int req_rooms = Integer.parseInt(str_req_rooms);

                int avail_rooms = Integer.parseInt(str_room_available);

                if (avail_rooms == 0) {
                    Alerter.create(Activity_Room_Description.this)
                            .setTitle("Malar Residency")
                            .setText("Sorry..! All " + str_room_type + " rooms are currently Occupied")
                            .setBackgroundColor(R.color.bg_screen3)
                            .show();
                } else if (req_rooms > avail_rooms) {

                    Toast.makeText(getApplicationContext(), "Sorry...! only " + str_room_available + " Rooms  are available in this type.", Toast.LENGTH_LONG).show();

                } else {

                    Intent i = new Intent(getApplicationContext(), Activity_Checkout.class);
                    startActivity(i);
                    finish();
                }
            }
        });


        SharedPreferences sharedPreferences_one = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        str_room_type = sharedPreferences.getString("str_room_type", "str_room_type");
        str_room_description = sharedPreferences.getString("str_room_description", "str_room_description");
        str_room_available = sharedPreferences.getString("str_room_available", "str_room_available");
        str_room_img = sharedPreferences.getString("str_room_img_path", "str_room_img_path");
        str_room_img_path = "http://epictech.in/malarresidency/admin/" + str_room_img;

        str_room_cost = sharedPreferences.getString("str_room_cost", "str_room_cost");
        str_room_tax = sharedPreferences.getString("str_room_tax", "str_room_tax");
        str_room_service_tax = sharedPreferences.getString("str_room_service_tax", "str_room_service_tax");

        try {

            // Calculating the Cost of the room by multipling with no.of.nights required and no.of.rooms required

            float total_rooms , cost, tax, service_tax;
            float room_cost, room_cost_temp, room_tax, room_service_tax;
            long room_total_cost;

            total_rooms = Float.parseFloat(str_no_of_rooms);
            cost = Float.parseFloat(str_room_cost);
            tax = Float.parseFloat(str_room_tax);
            service_tax = Float.parseFloat(str_room_service_tax);

            room_tax = (cost * tax) / 100;
            room_service_tax = (cost * service_tax) / 100;
            room_cost_temp = room_tax + room_service_tax + cost;
            room_cost = room_cost_temp * no_of_nights * total_rooms;


            room_total_cost = (int) room_cost;

            str_room_new_tax = String.valueOf(room_tax);
            str_room_new_service_tax = String.valueOf(room_service_tax);

            str_total_cost = String.valueOf(room_total_cost);

        } catch (Exception e) {

        }

        try {

            System.out.println("OUTTTTTTTTTTTTTTTTT::::::::::::::::::::" + str_total_cost);
            txt_room_type.setText(str_room_type);
            txt_room_desc.setText(str_room_description);
            txt_available_rooms.setText(str_room_available);
            txt_room_cost.setText(str_room_cost);
            txt_room_tax.setText(str_room_new_tax);
            txt_room_service_tax.setText(str_room_new_service_tax);
            txt_room_total_cost.setText(str_total_cost + "  \u20B9 ");

            SharedPreferences sharedPreferences_two = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("str_room_new_tax", str_room_new_tax);
            editor.putString("str_room_new_service_tax", str_room_new_service_tax);
            editor.putString("str_total_cost", str_total_cost);
            editor.commit();

            System.out.println("OUTPUT:::::" + "Room Type ::" + str_room_type + "Room Cost ::" + str_room_cost + "Room Description ::" + str_room_description + "tax  ::" + str_room_tax + "servicetax  ::" + str_room_service_tax + "available rooms  ::" + str_room_available);

            Glide.with(getApplicationContext()).load(str_room_img_path)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_room_pic);

        } catch (Exception e) {

        }

    }

}
