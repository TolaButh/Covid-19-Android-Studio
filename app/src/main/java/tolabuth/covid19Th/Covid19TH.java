package tolabuth.covid19Th;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Covid19TH extends AppCompatActivity {
    private TextView tvConfirmed, tvRecovered, tvHospital, tvNewConfirmed, tvDeath,tvUpdate;

    private ReqeustQueue queue;
    String url = "https://covid19.ddc.moph.go.th/api/Cases/today-cases-all";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid19th);
        readCovid19Report();
        macthView();
    }

    private void macthView() {
        tvConfirmed = findViewById(R.id.tv_number_confirmed);
        tvRecovered = findViewById(R.id.tv_number_recovered);
        tvHospital  = findViewById(R.id.tv_number_hospital);
        tvNewConfirmed = findViewById(R.id.tv_number_newConfirmed);
        tvDeath = findViewById(R.id.tv_number_death);
        tvUpdate = findViewById(R.id.tv_update_date);
    }

    private void readCovid19Report() {
      JsonArrayRequest request = new JsonArrayRequest(
              url,
              new Response.Listener<JSONArray>() {
                  @Override
                  public void onResponse(JSONArray response) {
                        JSONObject object;
                      DecimalFormat df = new DecimalFormat("##,###");
                      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

                      try {
                         object =  response.getJSONObject(0);

                          int newConfirmed = object.getInt("new_case");
                          int confirmed = object.getInt("total_case");
                          String update = object.getString("txn_date");
                          int total_recovered = object.getInt("total_recovered");
                          int total_death = object.getInt("total_death");
                          //Log.d("NewConfirmed", newConfirmed+"");
                         // Log.d("confirmed", confirmed+"");
                          Log.d("update",update);
                          Date d = sf.parse(update);
                          sf.applyPattern("MMMM dd , yyyy");

                          tvConfirmed.setText(confirmed+"");
                          tvNewConfirmed.setText(newConfirmed+"");
                          tvUpdate.setText("Last Update  "+sf.format(d));
                          tvRecovered.setText(total_recovered+"");
                          tvDeath.setText(total_death+"");

                      } catch (JSONException | ParseException e) {
                          e.printStackTrace();
                      }

                  }
              },
              new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {

                  }
              }
      );
        queue = Volley.newRequestQueue(Covid19TH.this);
        queue.add(request);
    }
}