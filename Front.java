package pk.sudoware.timezone;

import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Front extends AppCompatActivity {
    Spinner location;
    TextView textView, tv2;
    final String API2 = "https://www.sudoware.pk/timezone/gettime.php";

    String[] ids = { "25.0700510,67.2847795",
                      "21.4362767,39.7064562",
                      "24.4714392,39.3373483",
                      "28.7022344,77.1018001",
                      "40.6976701,-74.2598808",
                      "43.6570321,-79.6010476",

                                 };

    String[] name = { "Karachi, Pakistan",
                      "Mecca, Saudi Arabia",
                      "Medina, Saudi Arabia",
                      "Delhi, India",
                      "New York, USA",
                      "Toronto Canada",
                                      };
    String[] loc;
    String latitude,longitude;
    String timez, CountryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        location = findViewById(R.id.location);
        textView = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        final ArrayAdapter<String> timezones = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,name);
        timezones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(timezones);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CountryName = name[i];
                timez = ids[i];
                loc = timez.split(",");
                 latitude = loc[0];
                 longitude = loc[1];
                callWebService(latitude,longitude);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView.setText("Select the Location ");
            }
        });

    }


    void callWebService(final String latitude, final String longitude) {

        StringRequest getReq = new StringRequest(Request.Method.POST, API2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                 String timezone = response.toString();
                 textView.setText("Time & Date of "+CountryName+" is: \n"+timezone);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error",error);
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("latitude", latitude);
                params.put("longitude", longitude);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(getReq);


   }
}