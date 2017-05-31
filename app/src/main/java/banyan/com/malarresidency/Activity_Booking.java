package banyan.com.malarresidency;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by chandru on 7/19/2016.
 */
public class Activity_Booking extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    String[] List_room = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String[] List_Adults = {"1", "2", "3"};
    String[] List_Childs = {"1", "2"};

    ImageView img_back;

    private Toolbar mToolbar;

    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_Select_Rooms.class);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.booking_btn_proceed).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.booking_btn_proceed:

                Intent i = new Intent(Activity_Booking.this, Activity_Checkout.class);
                startActivity(i);
                finish();
                break;

            /*case R.id.bookroom_img_back:

                Intent j = new Intent(Activity_Booking.this, Activity_Select_Rooms.class);
                startActivity(j);
                finish();
                break;*/
        }

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
