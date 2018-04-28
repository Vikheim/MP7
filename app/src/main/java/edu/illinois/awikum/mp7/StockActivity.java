package edu.illinois.awikum.mp7;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.illinois.awikum.library.MyClass;

public class StockActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "MP7:Main";
    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    private JsonObject companyData = new JsonObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        String selectedCompany = getIntent().getStringExtra("item");
        Log.d(TAG, "." + apiTrimmer(selectedCompany) + ".");
        startSingleStockAPICall(apiTrimmer(selectedCompany));

    }
    private String apiTrimmer(final String company) {
        int low = company.indexOf('(');
        int high = company.indexOf(')');
        return company.substring(low + 1, high).toLowerCase();
    }



    void startSingleStockAPICall(final String company) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.iextrading.com/1.0/stock/market/batch?symbols=" + company + "&types=quote",

                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                initialProcessing(response.toString(), company);
                                TextView stockComp = findViewById(R.id.stockComp);
                                stockComp.setText(MyClass.getCompanyName(companyData));

                                TextView stockSym = findViewById(R.id.stockSym);
                                stockSym.setText(MyClass.getSymbol(companyData));

                                TextView stockPrice = findViewById(R.id.stockPrice);
                                stockPrice.setText(MyClass.getChange(companyData));


                                Log.d(TAG, response.toString(2));

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
        companyData = result.get(company.toUpperCase()).getAsJsonObject();
    }
}
