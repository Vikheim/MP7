package edu.illinois.awikum.mp7;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

public class stocks extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stocks, container, false);

        String COUNTRIES[]={"INDIA","ITALY","JAPAN","USA","ICELAND","INDONESIA","UK","IRAN","IRAQ"};
        final AutoCompleteTextView autoCompleteTextViewCountry = (AutoCompleteTextView)rootView.findViewById(R.id.countries_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        autoCompleteTextViewCountry.setAdapter(adapter);
        autoCompleteTextViewCountry.setThreshold(1);
        //textView tv = (textView)findViewbyid(R.id.)
/*
        btnSelectedCountry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String country=autoCompleteTextViewCountry.getText().toString();
                textViewSelectedCountry.setText("Selected Country: "+country);
            }
        });
        */
        return rootView;
    }
}
