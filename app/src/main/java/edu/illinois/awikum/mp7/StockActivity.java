package edu.illinois.awikum.mp7;

import android.graphics.Color;
import android.os.DropBoxManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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

    private String defaultChart = "1d&chartInterval=2";



    private JsonArray chartData = new JsonArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        final String selectedCompany = getIntent().getStringExtra("item");
        Log.d(TAG, selectedCompany);
        startSingleStockAPICall(apiTrimmer(selectedCompany), defaultChart);

        TextView todayView = findViewById(R.id.todayView);
        todayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSingleStockAPICall(apiTrimmer(selectedCompany), "1d&chartInterval=2");
            }
        });

        TextView oneMonthView = findViewById(R.id.oneMonthView);
        oneMonthView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSingleStockAPICall(apiTrimmer(selectedCompany), "1m");
            }
        });
        TextView sixMonthView = findViewById(R.id.sixMonthView);
        sixMonthView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSingleStockAPICall(apiTrimmer(selectedCompany), "6m");
            }
        });
        TextView oneYearView = findViewById(R.id.oneYearView);
        oneYearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSingleStockAPICall(apiTrimmer(selectedCompany), "1y");
            }
        });
        TextView fiveYearView = findViewById(R.id.fiveYearView);
        fiveYearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSingleStockAPICall(apiTrimmer(selectedCompany), "5y");
            }
        });


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



    void startSingleStockAPICall(final String company, final String chartInterval) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.iextrading.com/1.0/stock/market/batch?symbols=" + company + "&types=quote,chart&range=" + chartInterval,

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
                                stockSym.setText("(" + dataParser.getSymbol(companyData) + ")");

                                TextView stockPrice = findViewById(R.id.stockPrice);
                                stockPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(companyData)));

                                TextView stockChange = findViewById(R.id.stockChange);
                                String change = dataParser.getChange(companyData);
                                String changeP = dataParser.getChangePercent(companyData);
                                if (stocks.isGain(changeP)) {
                                    change = "+" + change;
                                    stockChange.setTextColor(Color.GREEN);
                                }
                                stockChange.setText(priceFormatter.addOrRemoveZeros(change) + " (" + priceFormatter.addOrRemoveZeros(changeP) + "%)");


                                TextView latestPrice = findViewById(R.id.latestData);
                                latestPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(companyData)));
                                TextView latestTime = findViewById(R.id.latestTData);
                                latestTime.setText(dataParser.getLatestTime(companyData));
                                TextView open = findViewById(R.id.openData);
                                open.setText(priceFormatter.addOrRemoveZeros(dataParser.getOpen(companyData)));
                                TextView close = findViewById(R.id.closeData);
                                close.setText(priceFormatter.addOrRemoveZeros(dataParser.getClose(companyData)));
                                TextView high = findViewById(R.id.highData);
                                high.setText(priceFormatter.addOrRemoveZeros(dataParser.getHigh(companyData)));
                                TextView low = findViewById(R.id.lowData);
                                low.setText(priceFormatter.addOrRemoveZeros(dataParser.getLow(companyData)));

                                TextView previous = findViewById(R.id.previousData);
                                previous.setText(priceFormatter.addOrRemoveZeros(dataParser.getPreviousClose(companyData)));
                                TextView changePrime = findViewById(R.id.changeData);
                                changePrime.setText(priceFormatter.addOrRemoveZeros(dataParser.getChange(companyData)));
                                TextView changePPrime = findViewById(R.id.changePData);
                                changePPrime.setText(priceFormatter.addOrRemoveZeros(dataParser.getChangePercent(companyData)));
                                TextView volume = findViewById(R.id.volumeData);
                                volume.setText(dataParser.getAverageVolume(companyData));
                                TextView weekHigh = findViewById(R.id.weekHighData);
                                weekHigh.setText(priceFormatter.addOrRemoveZeros(dataParser.get52weekHigh(companyData)));
                                TextView weekLow = findViewById(R.id.weekLowData);
                                weekLow.setText(priceFormatter.addOrRemoveZeros(dataParser.get52weekLow(companyData)));

                                LineChart chart = (LineChart) findViewById(R.id.chart);
                                List<Entry> entries = new ArrayList<Entry>();
                                ArrayList<String> labels = new ArrayList<String>();
                                int xValue = 1;
                                float yValue = 0;
                                for (JsonElement i : chartData) {
                                    boolean skip = false;
                                    try {
                                        yValue = Float.parseFloat(i.getAsJsonObject().get("close").getAsString());
                                    } catch (NullPointerException e) {
                                        try {
                                            yValue = Float.parseFloat(i.getAsJsonObject().get("marketClose").getAsString());
                                        } catch (NullPointerException f) {
                                            skip = true;
                                        }
                                    }
                                    if (!skip) {
                                        labels.add(i.getAsJsonObject().get("label").getAsString());
                                        entries.add(new Entry(xValue, yValue));
                                        xValue++;
                                    }
                                }
                                LineDataSet dataSet = new LineDataSet(entries, "Stock");
                                LineData lineData = new LineData(dataSet);
                                dataSet.setFillColor(ColorTemplate.colorWithAlpha(ColorTemplate.getHoloBlue(), 85));
                                dataSet.setDrawFilled(true);
                                dataSet.setDrawValues(false);
                                dataSet.setDrawCircles(false);
                                chart.setData(lineData);
                                // the labels that should be drawn on the XAxis
                                final String[] axisLabels = labels.toArray(new String[0]);

                                IAxisValueFormatter formatter = new IAxisValueFormatter() {

                                    @Override
                                    public String getFormattedValue(float value, AxisBase axis) {
                                        if (value < axisLabels.length) {
                                            return axisLabels[(int) value - 1];
                                        } else {
                                            return "";
                                        }
                                    }

                                };

                                XAxis xAxis = chart.getXAxis();
                                chart.setPinchZoom(true);
                                xAxis.setTextSize(10f);
                                xAxis.setTextColor(ColorTemplate.colorWithAlpha(ColorTemplate.getHoloBlue(), 125));
                                xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                                xAxis.setValueFormatter(formatter);
                                chart.setBackgroundColor(Color.DKGRAY);
                                chart.setGridBackgroundColor(Color.WHITE);
                                chart.animateX(500);



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
