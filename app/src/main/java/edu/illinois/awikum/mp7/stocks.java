package edu.illinois.awikum.mp7;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import edu.illinois.awikum.library.priceFormatter;

public class stocks extends Fragment{
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "MP7:Main";
    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    //dia,ivv,oneq,goog,aapl,msft,eem,efa,spy
    private JsonObject dia = new JsonObject();
    private JsonObject ivv = new JsonObject();
    private JsonObject oneq = new JsonObject();
    private JsonObject goog = new JsonObject();
    private JsonObject aapl = new JsonObject();
    private JsonObject msft = new JsonObject();
    private JsonObject eem = new JsonObject();
    private JsonObject efa = new JsonObject();
    private JsonObject spy = new JsonObject();

    JsonObject[] createCompanies = {dia, ivv, oneq, goog, aapl, msft, eem, efa, spy};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stocks, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());
        startCreateStockAPICall();

        final String[] company_list = getResources().getStringArray(R.array.companies_array);
        final AutoCompleteTextView autoCompleteTextViewCompany = (AutoCompleteTextView)rootView.findViewById(R.id.companies_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, company_list );

        autoCompleteTextViewCompany.setAdapter(adapter);
        autoCompleteTextViewCompany.setThreshold(1);


        autoCompleteTextViewCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteTextViewCompany.showDropDown();
            }
        });

        final TextView ticker = rootView.findViewById(R.id.textView40);
        ticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startCreateStockAPICall();
            }
        });

        autoCompleteTextViewCompany.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View view,
                                       int index, long id)
            {
                String selection = (String)parent.getItemAtPosition(index);
                Intent i = new Intent(getActivity(),StockActivity.class);
                i.putExtra("item",selection);
                startActivity(i);
            }
        });

         final SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startCreateStockAPICall();
                swipeContainer.setRefreshing(false);
            }
        });


        LinearLayout diaLay = (LinearLayout)rootView.findViewById(R.id.diaLay);
        diaLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diaIn = new Intent(getActivity(),StockActivity.class);
                diaIn.putExtra("item","dia");
                startActivity(diaIn);
            }
        });

        LinearLayout ivvLay = (LinearLayout)rootView.findViewById(R.id.ivvLay);
        ivvLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ivvIn = new Intent(getActivity(),StockActivity.class);
                ivvIn.putExtra("item","ivv");
                startActivity(ivvIn);
            }
        });
        LinearLayout googLay = (LinearLayout)rootView.findViewById(R.id.googLay);
        googLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googIn = new Intent(getActivity(),StockActivity.class);
                googIn.putExtra("item","goog");
                startActivity(googIn);
            }
        });
        LinearLayout oneqLay = (LinearLayout)rootView.findViewById(R.id.oneqLay);
        oneqLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oneqIn = new Intent(getActivity(),StockActivity.class);
                oneqIn.putExtra("item","oneq");
                startActivity(oneqIn);
            }
        });
        LinearLayout aaplLay = (LinearLayout)rootView.findViewById(R.id.aaplLay);
        aaplLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aaplIn = new Intent(getActivity(),StockActivity.class);
                aaplIn.putExtra("item","aapl");
                startActivity(aaplIn);
            }
        });
        LinearLayout msftLay = (LinearLayout)rootView.findViewById(R.id.msftLay);
        msftLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msftIn = new Intent(getActivity(),StockActivity.class);
                msftIn.putExtra("item","msft");
                startActivity(msftIn);
            }
        });
        LinearLayout eemLay = (LinearLayout)rootView.findViewById(R.id.eemLay);
        eemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eemIn = new Intent(getActivity(),StockActivity.class);
                eemIn.putExtra("item","eem");
                startActivity(eemIn);
            }
        });
        LinearLayout efaLay = (LinearLayout)rootView.findViewById(R.id.efaLay);
        efaLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent efaIn = new Intent(getActivity(),StockActivity.class);
                efaIn.putExtra("item","efa");
                startActivity(efaIn);
            }
        });
        LinearLayout spyLay = (LinearLayout)rootView.findViewById(R.id.spyLay);
        spyLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent spyIn = new Intent(getActivity(),StockActivity.class);
                spyIn.putExtra("item","spy");
                startActivity(spyIn);
            }
        });




        return rootView;
    }
    void startCreateStockAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.iextrading.com/1.0/stock/market/batch?symbols=dia,ivv,oneq,goog,aapl,msft,eem,efa,spy&types=quote",

                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                initialProcessing(response.toString());

                                //Dow Jones Industrial Average
                                TextView diaSym = (TextView) getView().findViewById(R.id.diaSym);
                                diaSym.setText(dataParser.getSymbol(dia));
                                TextView diaComp = (TextView) getView().findViewById(R.id.diaComp);
                                diaComp.setText(dataParser.getCompanyName(dia));
                                TextView diaPrice = (TextView) getView().findViewById(R.id.diaPrice);
                                diaPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(dia)));
                                TextView diaTime = (TextView) getView().findViewById(R.id.diaTime);
                                diaTime.setText(dataParser.getLatestTime(dia));
                                TextView diaChange = (TextView) getView().findViewById(R.id.diaChange);
                                String change = dataParser.getChange(dia);
                                change = priceFormatter.addOrRemoveZeros(change);
                                if (isGain(change)) {
                                    change = "+ " + change;
                                    diaChange.setTextColor(Color.GREEN);
                                } else {
                                    change = change.substring(0,1) + " " + change.substring(1);
                                }
                                diaChange.setText(change);

                                TextView diaChangeP = (TextView) getView().findViewById(R.id.diaChangeP);
                                String changeP = dataParser.getChangePercent(dia);
                                changeP = priceFormatter.addOrRemoveZeros(changeP);
                                if (isGain(changeP)) {
                                    changeP = "+ " + changeP;
                                    diaChangeP.setTextColor(Color.GREEN);
                                } else {
                                    changeP = changeP.substring(0,1) + " " + changeP.substring(1);
                                }
                                diaChangeP.setText("(" + changeP + ")");

                                //S&P 500
                                TextView ivvSym = (TextView) getView().findViewById(R.id.ivvSym);
                                ivvSym.setText(dataParser.getSymbol(ivv));
                                TextView ivvComp = (TextView) getView().findViewById(R.id.ivvComp);
                                ivvComp.setText(dataParser.getCompanyName(ivv));
                                TextView ivvPrice = (TextView) getView().findViewById(R.id.ivvPrice);
                                ivvPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(ivv)));
                                TextView ivvTime = (TextView) getView().findViewById(R.id.ivvTime);
                                ivvTime.setText(dataParser.getLatestTime(ivv));
                                TextView ivvChange = (TextView) getView().findViewById(R.id.ivvChange);
                                change = dataParser.getChange(ivv);
                                change = priceFormatter.addOrRemoveZeros(change);
                                if (isGain(change)) {
                                    change =  "+ " + change;
                                    ivvChange.setTextColor(Color.GREEN);
                                } else {
                                    change = change.substring(0,1) + " " + change.substring(1);
                                }

                                ivvChange.setText(change);

                                TextView ivvChangeP = (TextView) getView().findViewById(R.id.ivvChangeP);
                                changeP = dataParser.getChangePercent(ivv);
                                changeP = priceFormatter.addOrRemoveZeros(changeP);
                                if (isGain(changeP)) {
                                    changeP = "+ " + changeP;
                                    ivvChangeP.setTextColor(Color.GREEN);
                                } else {
                                    changeP = changeP.substring(0,1) + " " + changeP.substring(1);
                                }
                                ivvChangeP.setText("(" + changeP + ")");

                                //Alphabet Inc.
                                TextView googSym = (TextView) getView().findViewById(R.id.googSym);
                                googSym.setText(dataParser.getSymbol(goog));
                                TextView googComp = (TextView) getView().findViewById(R.id.googComp);
                                googComp.setText(dataParser.getCompanyName(goog));
                                TextView googPrice = (TextView) getView().findViewById(R.id.googPrice);
                                googPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(goog)));
                                TextView googTime = (TextView) getView().findViewById(R.id.googTime);
                                googTime.setText(dataParser.getLatestTime(goog));
                                TextView googChange = (TextView) getView().findViewById(R.id.googChange);
                                change = dataParser.getChange(goog);
                                if (isGain(change)) {
                                    change = "+ " + change;
                                    googChange.setTextColor(Color.GREEN);
                                } else {
                                    change = change.substring(0,1) + " " + change.substring(1);
                                }
                                change = priceFormatter.addOrRemoveZeros(change);
                                googChange.setText(change);

                                TextView googChangeP = (TextView) getView().findViewById(R.id.googChangeP);
                                changeP = dataParser.getChangePercent(goog);
                                changeP = priceFormatter.addOrRemoveZeros(changeP);
                                if (isGain(changeP)) {
                                    changeP = "+ " + changeP;
                                    googChangeP.setTextColor(Color.GREEN);
                                } else {
                                    changeP = changeP.substring(0,1) + " " + changeP.substring(1);
                                }
                                googChangeP.setText("(" + changeP + ")");

                                //NASDAQ
                                TextView oneqSym = (TextView) getView().findViewById(R.id.oneqSym);
                                oneqSym.setText(dataParser.getSymbol(oneq));
                                TextView oneqComp = (TextView) getView().findViewById(R.id.oneqComp);
                                oneqComp.setText(dataParser.getCompanyName(oneq));
                                TextView oneqPrice = (TextView) getView().findViewById(R.id.oneqPrice);
                                oneqPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(oneq)));
                                TextView oneqTime = (TextView) getView().findViewById(R.id.oneqTime);
                                oneqTime.setText(dataParser.getLatestTime(oneq));
                                TextView oneqChange = (TextView) getView().findViewById(R.id.oneqChange);
                                change = dataParser.getChange(oneq);
                                change = priceFormatter.addOrRemoveZeros(change);
                                if (isGain(change)) {
                                    change = "+ " + change;
                                    oneqChange.setTextColor(Color.GREEN);
                                } else {
                                    change = change.substring(0,1) + " " + change.substring(1);
                                }
                                oneqChange.setText(change);

                                TextView oneqChangeP = (TextView) getView().findViewById(R.id.oneqChangeP);
                                changeP = dataParser.getChangePercent(oneq);
                                changeP = priceFormatter.addOrRemoveZeros(changeP);
                                if (isGain(changeP)) {
                                    changeP = "+ " + changeP;
                                    oneqChangeP.setTextColor(Color.GREEN);
                                } else {
                                    changeP = changeP.substring(0,1) + " " + changeP.substring(1);
                                }
                                oneqChangeP.setText("(" + changeP + ")");

                                //Apple Inc.
                                TextView aaplSym = (TextView) getView().findViewById(R.id.aaplSym);
                                aaplSym.setText(dataParser.getSymbol(aapl));
                                TextView aaplComp = (TextView) getView().findViewById(R.id.aaplComp);
                                aaplComp.setText(dataParser.getCompanyName(aapl));
                                TextView aaplPrice = (TextView) getView().findViewById(R.id.aaplPrice);
                                aaplPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(aapl)));
                                TextView aaplTime = (TextView) getView().findViewById(R.id.aaplTime);
                                aaplTime.setText(dataParser.getLatestTime(aapl));
                                TextView aaplChange = (TextView) getView().findViewById(R.id.aaplChange);
                                change = dataParser.getChange(aapl);
                                if (isGain(change)) {
                                    change = "+ " + change;
                                    aaplChange.setTextColor(Color.GREEN);
                                } else {
                                    change = change.substring(0,1) + " " + change.substring(1);
                                }
                                change = priceFormatter.addOrRemoveZeros(change);
                                aaplChange.setText(change);

                                TextView aaplChangeP = (TextView) getView().findViewById(R.id.aaplChangeP);
                                changeP = dataParser.getChangePercent(aapl);
                                changeP = priceFormatter.addOrRemoveZeros(changeP);
                                if (isGain(changeP)) {
                                    changeP = "+ " + changeP;
                                    aaplChangeP.setTextColor(Color.GREEN);
                                } else {
                                    changeP = changeP.substring(0,1) + " " + changeP.substring(1);
                                }
                                aaplChangeP.setText("(" + changeP + ")");

                                //Microsoft
                                TextView msftSym = (TextView) getView().findViewById(R.id.msftSym);
                                msftSym.setText(dataParser.getSymbol(msft));
                                TextView msftComp = (TextView) getView().findViewById(R.id.msftComp);
                                msftComp.setText(dataParser.getCompanyName(msft));
                                TextView msftPrice = (TextView) getView().findViewById(R.id.msftPrice);
                                msftPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(msft)));
                                TextView msftTime = (TextView) getView().findViewById(R.id.msftTime);
                                msftTime.setText(dataParser.getLatestTime(msft));
                                TextView msftChange = (TextView) getView().findViewById(R.id.msftChange);
                                change = dataParser.getChange(msft);
                                if (isGain(change)) {
                                    change = "+ " + change;
                                    msftChange.setTextColor(Color.GREEN);
                                } else {
                                    change = change.substring(0,1) + " " + change.substring(1);
                                }
                                change = priceFormatter.addOrRemoveZeros(change);
                                msftChange.setText(change);

                                TextView msftChangeP = (TextView) getView().findViewById(R.id.msftChangeP);
                                changeP = dataParser.getChangePercent(msft);
                                changeP = priceFormatter.addOrRemoveZeros(changeP);
                                if (isGain(changeP)) {
                                    changeP = "+ " + changeP;
                                    msftChangeP.setTextColor(Color.GREEN);
                                } else {
                                    changeP = changeP.substring(0,1) + " " + changeP.substring(1);
                                }
                                msftChangeP.setText("(" + changeP + ")");


                                //Emerging Markets
                                TextView eemSym = (TextView) getView().findViewById(R.id.eemSym);
                                eemSym.setText(dataParser.getSymbol(eem));
                                TextView eemComp = (TextView) getView().findViewById(R.id.eemComp);
                                eemComp.setText(dataParser.getCompanyName(eem));
                                TextView eemPrice = (TextView) getView().findViewById(R.id.eemPrice);
                                eemPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(eem)));
                                TextView eemTime = (TextView) getView().findViewById(R.id.eemTime);
                                eemTime.setText(dataParser.getLatestTime(eem));
                                TextView eemChange = (TextView) getView().findViewById(R.id.eemChange);
                                change = dataParser.getChange(eem);
                                change = priceFormatter.addOrRemoveZeros(change);
                                if (isGain(change)) {
                                    change = "+ " + change;
                                    eemChange.setTextColor(Color.GREEN);
                                } else {
                                    change = change.substring(0,1) + " " + change.substring(1);
                                }
                                eemChange.setText(change);

                                TextView eemChangeP = (TextView) getView().findViewById(R.id.eemChangeP);
                                changeP = dataParser.getChangePercent(eem);
                                changeP = priceFormatter.addOrRemoveZeros(changeP);
                                if (isGain(changeP)) {
                                    changeP = "+ " + changeP;
                                    eemChangeP.setTextColor(Color.GREEN);
                                } else {
                                    changeP = changeP.substring(0,1) + " " + changeP.substring(1);
                                }

                                eemChangeP.setText("(" + changeP + ")");

                                //
                                TextView efaSym = (TextView) getView().findViewById(R.id.efaSym);
                                efaSym.setText(dataParser.getSymbol(efa));
                                TextView efaComp = (TextView) getView().findViewById(R.id.efaComp);
                                efaComp.setText(dataParser.getCompanyName(efa));
                                TextView efaPrice = (TextView) getView().findViewById(R.id.efaPrice);
                                efaPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(efa)));
                                TextView efaTime = (TextView) getView().findViewById(R.id.efaTime);
                                efaTime.setText(dataParser.getLatestTime(efa));
                                TextView efaChange = (TextView) getView().findViewById(R.id.efaChange);
                                change = dataParser.getChange(efa);
                                change = priceFormatter.addOrRemoveZeros(change);
                                if (isGain(change)) {
                                    change = "+ " + change;
                                    efaChange.setTextColor(Color.GREEN);
                                } else {
                                    change = change.substring(0,1) + " " + change.substring(1);
                                }
                                efaChange.setText(change);
                                TextView efaChangeP = (TextView) getView().findViewById(R.id.efaChangeP);
                                changeP = dataParser.getChangePercent(efa);
                                changeP = priceFormatter.addOrRemoveZeros(changeP);
                                if (isGain(changeP)) {
                                    changeP = "+ " + changeP;
                                    efaChangeP.setTextColor(Color.GREEN);
                                } else {
                                    changeP = changeP.substring(0,1) + " " + changeP.substring(1);
                                }

                                efaChangeP.setText("(" + changeP + ")");

                                //
                                TextView spySym = (TextView) getView().findViewById(R.id.spySym);
                                spySym.setText(dataParser.getSymbol(spy));
                                TextView spyComp = (TextView) getView().findViewById(R.id.spyComp);
                                spyComp.setText(dataParser.getCompanyName(spy));
                                TextView spyPrice = (TextView) getView().findViewById(R.id.spyPrice);
                                spyPrice.setText(priceFormatter.addOrRemoveZeros(dataParser.getLatestPrice(spy)));
                                TextView spyTime = (TextView) getView().findViewById(R.id.spyTime);
                                spyTime.setText(dataParser.getLatestTime(spy));
                                TextView spyChange = (TextView) getView().findViewById(R.id.spyChange);
                                change = dataParser.getChange(spy);
                                if (isGain(change)) {
                                    change = "+ " + change;
                                    spyChange.setTextColor(Color.GREEN);
                                } else {
                                    change = change.substring(0,1) + " " + change.substring(1);
                                }
                                change = priceFormatter.addOrRemoveZeros(change);
                                spyChange.setText(change);
                                TextView spyChangeP = (TextView) getView().findViewById(R.id.spyChangeP);
                                changeP = dataParser.getChangePercent(spy);
                                changeP = priceFormatter.addOrRemoveZeros(changeP);
                                if (isGain(changeP)) {
                                    changeP = "+" + changeP;
                                    spyChangeP.setTextColor(Color.GREEN);
                                } else {
                                    changeP = changeP.substring(0,1) + " " + changeP.substring(1);
                                }
                                spyChangeP.setText("(" + changeP + ")");



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
        JsonObject response = parser.parse(json).getAsJsonObject();
        dia = response.get("DIA").getAsJsonObject();
        ivv = response.get("IVV").getAsJsonObject();
        oneq = response.get("ONEQ").getAsJsonObject();
        goog = response.get("GOOG").getAsJsonObject();
        aapl = response.get("AAPL").getAsJsonObject();
        msft = response.get("MSFT").getAsJsonObject();
        eem = response.get("EEM").getAsJsonObject();
        efa = response.get("EFA").getAsJsonObject();
        spy = response.get("SPY").getAsJsonObject();
    }




    //Color formatting methods
    public static boolean isGain(final String change) {
        return !(change.charAt(0) == '-');
    }

}
