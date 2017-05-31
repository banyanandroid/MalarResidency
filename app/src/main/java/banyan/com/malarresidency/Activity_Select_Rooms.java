package banyan.com.malarresidency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.kennyc.bottomsheet.BottomSheet;

/**
 * Created by User on 7/18/2016.
 */
public class Activity_Select_Rooms extends AppCompatActivity implements View.OnClickListener{

    private static long back_pressed;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.select_room_cardview_one).setOnClickListener(this);
        findViewById(R.id.select_room_cardview_two).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.select_room_cardview_one:

                Intent i = new Intent(Activity_Select_Rooms.this, Activity_Booking.class);
                startActivity(i);
                finish();

                break;

            case R.id.select_room_cardview_two:

                Intent j = new Intent(Activity_Select_Rooms.this, Activity_Booking.class);
                startActivity(j);
                finish();

                break;

          /*  case R.id.selectroom_img_back:

                Intent k = new Intent(Activity_Select_Rooms.this, MainActivity.class);
                startActivity(k);
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
