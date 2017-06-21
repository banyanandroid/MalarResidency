package banyan.com.malarresidency;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kennyc.bottomsheet.BottomSheet;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.malarresidency.Adapter.RoomType_Adapter;
import banyan.com.malarresidency.Global.AppConfig;
import dmax.dialog.SpotsDialog;

import static banyan.com.malarresidency.R.id.parent;

/**
 * Created by Jo on 7/18/2016.
 */

public class Activity_Select_Rooms extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static long back_pressed;
    private Toolbar mToolbar;

    SpotsDialog dialog;
    public static RequestQueue queue;

    public static final String TAG_ROOM_NAME = "room_type_name";
    public static final String TAG_ROOM_ID = "room_type_id";
    public static final String TAG_ROOM_PRICE = "room_type_cost";
    public static final String TAG_ROOM_DESC = "room_type_description";
    public static final String TAG_ROOM_PIC = "room_type_img_path";

    public static final String TAG_ROOM_TAX = "room_type_tax";
    public static final String TAG_ROOM_SERVICE_TAX = "room_type_service_tax";
    public static final String TAG_ROOM_AVAILABLE_ROOMS = "available_rooms";

    String TAG = "Room_Type";

    private ListView List;
    private SwipeRefreshLayout swipeRefreshLayout;

    static ArrayList<HashMap<String, String>> room_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public RoomType_Adapter adapter;

    String str_from_date, str_to_date = "";

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

        List = (ListView) findViewById(R.id.followup_listView);
        List.setClickable(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.followup_swipe_refresh_layout);

        // Hashmap for ListView
        room_list = new ArrayList<HashMap<String, String>>();

        try {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());

            str_from_date = sharedPreferences.getString("str_from_date", "str_from_date");
            str_to_date = sharedPreferences.getString("str_to_date", "str_to_date");

        } catch (Exception e) {

        }

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                            room_list.clear();
                                            queue = Volley.newRequestQueue(Activity_Select_Rooms.this);
                                            GetProductGroup();

                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }
                                    }
                                }
        );

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String str_room_type = room_list.get(position).get(TAG_ROOM_NAME);
                String str_room_id = room_list.get(position).get(TAG_ROOM_ID);
                String str_room_description = room_list.get(position).get(TAG_ROOM_DESC);
                String str_room_cost = room_list.get(position).get(TAG_ROOM_PRICE);
                String str_room_img_path = room_list.get(position).get(TAG_ROOM_PIC);

                String str_room_tax = room_list.get(position).get(TAG_ROOM_TAX);
                String str_room_service_tax = room_list.get(position).get(TAG_ROOM_SERVICE_TAX);
                String str_room_available = room_list.get(position).get(TAG_ROOM_AVAILABLE_ROOMS);

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("str_room_type", str_room_type);
                editor.putString("str_room_id", str_room_id);
                editor.putString("str_room_description", str_room_description);
                editor.putString("str_room_img_path", str_room_img_path);
                editor.putString("str_room_available", str_room_available);
                editor.putString("str_room_cost", str_room_cost);

                editor.putString("str_room_tax", str_room_tax);
                editor.putString("str_room_service_tax", str_room_service_tax);

                System.out.println("OUTPUT:::::" + str_room_type + str_room_cost + str_room_description +  "tax" + str_room_tax + "servicetax" + str_room_service_tax + "available rooms" + str_room_available);

                editor.commit();

                Intent i = new Intent(getApplicationContext(), Activity_Room_Description.class);
                startActivity(i);


            }

        });

    }

    @Override
    public void onRefresh() {
        try {
            room_list.clear();
            queue = Volley.newRequestQueue(Activity_Select_Rooms.this);
            GetProductGroup();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    /***************************
     * GET Product Group
     ***************************/

    public void GetProductGroup() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.room_type, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);

                    JSONArray arr;

                    arr = obj.getJSONArray("room_type");

                    for (int i = 0; arr.length() > i; i++) {
                        JSONObject obj1 = arr.getJSONObject(i);

                        String room_type_name = obj1.getString(TAG_ROOM_NAME);
                        String room_type_id = obj1.getString(TAG_ROOM_ID);
                        String room_type_description = obj1.getString(TAG_ROOM_DESC);
                        String room_type_cost = obj1.getString(TAG_ROOM_PRICE);
                        String room_type_img_path = obj1.getString(TAG_ROOM_PIC);
                        String room_type_tax = obj1.getString(TAG_ROOM_TAX);
                        String room_type_service_tax = obj1.getString(TAG_ROOM_SERVICE_TAX);
                        String available_rooms = obj1.getString(TAG_ROOM_AVAILABLE_ROOMS);


                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ROOM_NAME, room_type_name);
                        map.put(TAG_ROOM_ID, room_type_id);
                        map.put(TAG_ROOM_DESC, room_type_description);
                        map.put(TAG_ROOM_PRICE, room_type_cost);
                        map.put(TAG_ROOM_PIC, room_type_img_path);
                        map.put(TAG_ROOM_TAX, room_type_tax);
                        map.put(TAG_ROOM_SERVICE_TAX, room_type_service_tax);
                        map.put(TAG_ROOM_AVAILABLE_ROOMS, available_rooms);

                        room_list.add(map);

                        System.out.println("HASHMAP ARRAY" + room_list);


                        adapter = new RoomType_Adapter(Activity_Select_Rooms.this,
                                room_list);
                        List.setAdapter(adapter);


                    }

                    swipeRefreshLayout.setRefreshing(false);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);
                Alerter.create(Activity_Select_Rooms.this)
                        .setTitle("Malar Residency")
                        .setText(error.getMessage())
                        .setBackgroundColor(R.color.bg_screen1)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("check_indate", str_from_date);
                params.put("check_outdate", str_to_date);

                System.out.println("FROM : " + str_from_date);
                System.out.println("TO : " + str_to_date);

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
