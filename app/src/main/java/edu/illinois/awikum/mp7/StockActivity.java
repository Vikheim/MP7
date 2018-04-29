package edu.illinois.awikum.mp7;

import android.graphics.Color;
import android.os.DropBoxManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.awikum.library.dataParser;
import edu.illinois.awikum.library.priceFormatter;

public class StockActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "MP7:Main";
    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    private JsonObject companyData = new JsonObject();
    private JsonArray chartData = new JsonArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        String selectedCompany = getIntent().getStringExtra("item");
        Log.d(TAG, selectedCompany);
        startSingleStockAPICall(apiTrimmer(selectedCompany));




    }
    private String apiTrimmer(final String company) {
        int low = company.indexOf('(');
        int high = company.indexOf(')');
        if (company.contains("(")) {
            return company.substring(low + 1, high).toLowerCase();
        } else {
            return company;
        }
    }



    void startSingleStockAPICall(final String company) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.iextrading.com/1.0/stock/market/batch?symbols=" + company + "&types=quote,chart",

                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                Log.d(TAG, response.toString(2));
                                initialProcessing(response.toString(), company);
                                TextView stockComp = findViewById(R.id.stockComp);
                                stockComp.setText(dataParser.getCompanyName(companyData));

                                TextView stockSym = findViewById(R.id.stockSym);
                                stockSym.setText(dataParser.getSymbol(companyData));

                                TextView stockPrice = findViewById(R.id.stockPrice);
                                stockPrice.setText(dataParser.getLatestPrice(companyData));

                                TextView stockChange = findViewById(R.id.stockChange);
                                String change = dataParser.getChange(companyData);
                                String changeP = dataParser.getChangePercent(companyData);
                                stockChange.setText(priceFormatter.addOrRemoveZeros(change) + " (" + priceFormatter.addOrRemoveZeros(changeP) + "%)");


                                LineChart chart = (LineChart) findViewById(R.id.chart);
                                List<Entry> entries = new ArrayList<Entry>();
                                int xValue = 1;
                                for (JsonElement i : chartData) {
                                    float yValue = Float.parseFloat(i.getAsJsonObject().get("close").getAsString());

                                    entries.add(new Entry(xValue, yValue));
                                    xValue++;
                                }
                                LineDataSet dataSet = new LineDataSet(entries, "Label");
                                LineData lineData = new LineData(dataSet);
                                chart.setData(lineData);
                                chart.invalidate(); // refresh



                            } catch (JSONException ignored) { }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.e(TAG, error.toString());
                }
            });
            Log.d(TAG, jsonObjectRequest.toString());
            Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialProcessing(final String response, final String company) {
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(response).getAsJsonObject();
        Log.d(TAG, result.toString());
        chartData = result.get(company.toUpperCase()).getAsJsonObject().get("chart").getAsJsonArray();
        companyData = result.get(company.toUpperCase()).getAsJsonObject();
    }
}
