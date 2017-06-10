package banyan.com.malarresidency;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import banyan.com.malarresidency.Global.PrefManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomSheetListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static long back_pressed;
    String[] List_Rooms = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String[] List_Adults = {"1", "2", "3"};
    String[] List_Childs = {"1", "2"};
    int from_year, from_month, from_date;
    int to_year, to_month, to_date;

    DatePickerDialog dpd1;
    Date newDate2;

    // String
    String str_to_date = "DATE";
    String str_from_date = "DATE";


    EditText edt_checkin_date, edt_check_out_date;
    TextView txt_days;
    Button btn_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_fab_menu).setOnClickListener(this);
        findViewById(R.id.main_btn_booknow).setOnClickListener(this);

        edt_checkin_date = (EditText) findViewById(R.id.main_edt_checkin);
        edt_check_out_date = (EditText) findViewById(R.id.main_edt_checkout);
        txt_days = (TextView) findViewById(R.id.main_txt_nights);
        btn_book = (Button) findViewById(R.id.main_btn_booknow);

        // Spinners
        ArrayAdapter<String> arrayAdapter_rooms = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, List_Rooms);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.main_spn_no_of_room);
        materialDesignSpinner.setAdapter(arrayAdapter_rooms);

        ArrayAdapter<String> arrayAdapter_adults = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, List_Adults);
        MaterialBetterSpinner materialDesignSpinner_adults = (MaterialBetterSpinner)
                findViewById(R.id.main_spn_adults);
        materialDesignSpinner_adults.setAdapter(arrayAdapter_adults);

        ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, List_Childs);
        MaterialBetterSpinner materialDesignSpinner_child = (MaterialBetterSpinner)
                findViewById(R.id.main_spn_childs);
        materialDesignSpinner_child.setAdapter(arrayAdapter_child);

        edt_checkin_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                from_year = c.get(Calendar.YEAR);
                from_month = c.get(Calendar.MONTH);
                from_date = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox

                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;

                                if (month < 10) {

                                    formattedMonth = "0" + month;
                                }
                                if (dayOfMonth < 10) {

                                    formattedDayOfMonth = "0" + dayOfMonth;
                                }
                                edt_checkin_date.setText(year + "-"
                                        + formattedMonth + "-"
                                        + formattedDayOfMonth);

                                str_from_date = formattedDayOfMonth + " " + formattedMonth + " " + year;

                            }
                        }, from_year, from_month, from_date);
                dpd.show();


            }
        });

        edt_check_out_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c1 = Calendar.getInstance();
                to_year = c1.get(Calendar.YEAR);
                to_month = c1.get(Calendar.MONTH);
                to_date = c1.get(Calendar.DAY_OF_MONTH);
                newDate2 = c1.getTime();

                // Launch Date Picker Dialog
                dpd1 = new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year1,
                                                  int monthOfYear1, int dayOfMonth1) {

                                dpd1.getDatePicker().setMinDate(newDate2.getTime());

                                int month1 = monthOfYear1 + 1;
                                String formattedMonth1 = "" + month1;
                                String formattedDayOfMonth1 = "" + dayOfMonth1;

                                if (month1 < 10) {

                                    formattedMonth1 = "0" + month1;
                                }
                                if (dayOfMonth1 < 10) {

                                    formattedDayOfMonth1 = "0" + dayOfMonth1;
                                }
                                edt_check_out_date.setText(year1 + "-"
                                        + formattedMonth1 + "-"
                                        + formattedDayOfMonth1);

                                str_to_date = formattedDayOfMonth1 + " " + formattedMonth1 + " " + year1;

                                try {
                                    SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
                                    java.util.Date date1 = myFormat.parse(str_from_date);
                                    java.util.Date date2 = myFormat.parse(str_to_date);
                                    long diff = date2.getTime() - date1.getTime();
                                    System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                                    long str_days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) - 1;
                                    txt_days.setText("" + (str_days + 1) + " Nights");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, to_year, to_month, to_date);
                dpd1.show();


            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("PRINT :" + str_from_date);
                if (str_from_date.equals("DATE")) {
                    Toast.makeText(getApplicationContext(), "Please Select a From Date", Toast.LENGTH_LONG).show();
                } else if (str_to_date.equals("DATE")) {
                    Toast.makeText(getApplicationContext(), "Please Select a To Date", Toast.LENGTH_LONG).show();
                } else {

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //Radio button value strings
                    editor.putString("str_from_date", str_from_date);
                    editor.putString("str_to_date", str_to_date);

                    editor.commit();

                    System.out.println("str_from_date : " + str_from_date);
                    System.out.println("str_to_date : " + str_to_date);


                    Intent i = new Intent(MainActivity.this, Activity_Select_Rooms.class);
                    startActivity(i);
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.main_fab_menu:
                new BottomSheet.Builder(this, R.style.BottomSheet_Custom)
                        .setSheet(R.menu.grid_sheet)
                        .grid()
                        .setTitle("Menu")
                        .setListener(this)
                        .show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                /*Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text*//*");
                intent.putExtra(Intent.EXTRA_TEXT, "Hey, check out the BottomSheet library https://github.com/Kennyc1012/BottomSheet");
                BottomSheet bottomSheet = BottomSheet.createShareBottomSheet(this, intent, "Share");
                if (bottomSheet != null) bottomSheet.show();*/
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSheetShown(@NonNull BottomSheet bottomSheet) {
        Log.v(TAG, "onSheetShown");
    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem item) {
        Toast.makeText(getApplicationContext(), item.getTitle() + " Clicked", Toast.LENGTH_SHORT).show();

        if (item.getTitle().equals("Exit")) {

            try {

                finishAffinity();

            } catch (Exception e) {

            }

        } else if (item.getTitle().equals("Contact Us")) {

            PrefManager prefManager = new PrefManager(getApplicationContext());

            // make first time launch TRUE
            prefManager.setFirstTimeLaunch(true);

            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();

        }

    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, int which) {
        Log.v(TAG, "onSheetDismissed " + which);

        switch (which) {
            case BottomSheet.BUTTON_POSITIVE:
                Toast.makeText(getApplicationContext(), "Positive Button Clicked", Toast.LENGTH_SHORT).show();
                break;

            case BottomSheet.BUTTON_NEGATIVE:
                Toast.makeText(getApplicationContext(), "Negative Button Clicked", Toast.LENGTH_SHORT).show();
                break;
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
