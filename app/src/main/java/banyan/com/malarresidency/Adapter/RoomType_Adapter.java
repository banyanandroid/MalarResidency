package banyan.com.malarresidency.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.malarresidency.Activity_Select_Rooms;
import banyan.com.malarresidency.R;


public class RoomType_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public RoomType_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null)
            v = inflater.inflate(R.layout.list_room_type, null);

        TextView room_title = (TextView) v.findViewById(R.id.room_type_name);
        TextView room_cost = (TextView) v.findViewById(R.id.room_type_cost);
        ReadMoreTextView room_des = (ReadMoreTextView) v.findViewById(R.id.room_type_description);
        ImageView room_img = (ImageView) v.findViewById(R.id.room_type_img);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        room_title.setText(result.get(Activity_Select_Rooms.TAG_ROOM_NAME));
        room_cost.setText(result.get(Activity_Select_Rooms.TAG_ROOM_PRICE));
        room_des.setText(result.get(Activity_Select_Rooms.TAG_ROOM_DESC));
        String str_img = result.get(Activity_Select_Rooms.TAG_ROOM_PIC);
        String str_img_path = "http://epictech.in/malarresidency/admin/" + str_img;

        try{
            Glide.with(activity).load(str_img_path)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(room_img);
        }catch (Exception e){

        }

        return v;

    }

}