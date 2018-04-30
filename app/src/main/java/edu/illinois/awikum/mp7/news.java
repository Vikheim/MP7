package edu.illinois.awikum.mp7;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import edu.illinois.awikum.library.dataParser;

import static com.android.volley.VolleyLog.TAG;

public class news extends Fragment{

    private JsonObject newsData = new JsonObject();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news, container, false);

        final SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainerThree);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startNewsAPICall();
                swipeContainer.setRefreshing(false);
            }
        });
        startNewsAPICall();
        return rootView;
    }

    public void startNewsAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.iextrading.com/1.0/stock/market/batch?types=news",

                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                initialProcessing(response.toString());

                                TextView headlineOne = getView().findViewById(R.id.headOne);
                                headlineOne.setText(dataParser.getNewsArray(newsData).get(0).getAsJsonObject().get("headline").getAsString());
                                TextView sourceOne = getView().findViewById(R.id.sourceOne);
                                sourceOne.setText(dataParser.getNewsArray(newsData).get(0).getAsJsonObject().get("source").getAsString()+
                                        " | " + dataParser.newsDateFormatter(dataParser.getNewsArray(newsData).get(0).getAsJsonObject().get("datetime").getAsString()));
                                TextView summaryOne = getView().findViewById(R.id.sumOne);
                                summaryOne.setText(dataParser.newsFormatter(dataParser.getNewsArray(newsData).get(0).getAsJsonObject().get("summary").getAsString()));

                                TextView headlineTwo = getView().findViewById(R.id.headTwo);
                                headlineTwo.setText(dataParser.getNewsArray(newsData).get(1).getAsJsonObject().get("headline").getAsString());
                                TextView sourceTwo = getView().findViewById(R.id.sourceTwo);
                                sourceTwo.setText(dataParser.getNewsArray(newsData).get(1).getAsJsonObject().get("source").getAsString()+
                                        " | " + dataParser.newsDateFormatter(dataParser.getNewsArray(newsData).get(1).getAsJsonObject().get("datetime").getAsString()));
                                TextView summaryTwo = getView().findViewById(R.id.sumTwo);
                                summaryTwo.setText(dataParser.newsFormatter(dataParser.getNewsArray(newsData).get(1).getAsJsonObject().get("summary").getAsString()));

                                TextView headlineThree = getView().findViewById(R.id.headThree);
                                headlineThree.setText(dataParser.getNewsArray(newsData).get(2).getAsJsonObject().get("headline").getAsString());
                                TextView sourceThree = getView().findViewById(R.id.sourceThree);
                                sourceThree.setText(dataParser.getNewsArray(newsData).get(2).getAsJsonObject().get("source").getAsString()+
                                        " | " + dataParser.newsDateFormatter(dataParser.getNewsArray(newsData).get(2).getAsJsonObject().get("datetime").getAsString()));
                                TextView summaryThree = getView().findViewById(R.id.sumThree);
                                summaryThree.setText(dataParser.newsFormatter(dataParser.getNewsArray(newsData).get(2).getAsJsonObject().get("summary").getAsString()));

                                TextView headlineFour = getView().findViewById(R.id.headFour);
                                headlineFour.setText(dataParser.getNewsArray(newsData).get(3).getAsJsonObject().get("headline").getAsString());
                                TextView sourceFour = getView().findViewById(R.id.sourceFour);
                                sourceFour.setText(dataParser.getNewsArray(newsData).get(3).getAsJsonObject().get("source").getAsString()+
                                        " | " + dataParser.newsDateFormatter(dataParser.getNewsArray(newsData).get(3).getAsJsonObject().get("datetime").getAsString()));
                                TextView summaryFour = getView().findViewById(R.id.sumFour);
                                summaryFour.setText(dataParser.newsFormatter(dataParser.getNewsArray(newsData).get(3).getAsJsonObject().get("summary").getAsString()));

                                TextView headlineFive = getView().findViewById(R.id.headFive);
                                headlineFive.setText(dataParser.getNewsArray(newsData).get(4).getAsJsonObject().get("headline").getAsString());
                                TextView sourceFive = getView().findViewById(R.id.sourceFive);
                                sourceFive.setText(dataParser.getNewsArray(newsData).get(4).getAsJsonObject().get("source").getAsString() +
                                " | " + dataParser.newsDateFormatter(dataParser.getNewsArray(newsData).get(4).getAsJsonObject().get("datetime").getAsString()));
                                TextView summaryFive = getView().findViewById(R.id.sumFive);
                                summaryFive.setText(dataParser.newsFormatter(dataParser.getNewsArray(newsData).get(4).getAsJsonObject().get("summary").getAsString()));

                                LinearLayout newsOne = getView().findViewById(R.id.newsOne);
                                newsOne.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_VIEW);
                                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                        intent.setData(Uri.parse(dataParser.getNewsArray(newsData).get(0).getAsJsonObject().get("url").getAsString()));
                                        startActivity(intent);
                                    }
                                });

                                LinearLayout newsTwo = getView().findViewById(R.id.newsTwo);
                                newsTwo.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_VIEW);
                                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                        intent.setData(Uri.parse(dataParser.getNewsArray(newsData).get(1).getAsJsonObject().get("url").getAsString()));
                                        startActivity(intent);
                                    }
                                });

                                LinearLayout newsThree = getView().findViewById(R.id.newsThree);
                                newsThree.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_VIEW);
                                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                        intent.setData(Uri.parse(dataParser.getNewsArray(newsData).get(2).getAsJsonObject().get("url").getAsString()));
                                        startActivity(intent);
                                    }
                                });

                                LinearLayout newsFour = getView().findViewById(R.id.newsFour);
                                newsFour.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_VIEW);
                                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                        intent.setData(Uri.parse(dataParser.getNewsArray(newsData).get(3).getAsJsonObject().get("url").getAsString()));
                                        startActivity(intent);
                                    }
                                });

                                LinearLayout newsFive = getView().findViewById(R.id.newsFive);
                                newsFive.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_VIEW);
                                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                        intent.setData(Uri.parse(dataParser.getNewsArray(newsData).get(4).getAsJsonObject().get("url").getAsString()));
                                        startActivity(intent);
                                    }
                                });





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
            Singleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialProcessing(final String json) {
        JsonParser parser = new JsonParser();
        newsData = parser.parse(json).getAsJsonObject();
    }

}
