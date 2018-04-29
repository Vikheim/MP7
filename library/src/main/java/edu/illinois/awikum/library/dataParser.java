package edu.illinois.awikum.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class dataParser {

    private static JsonArray parseToJsonArray(final String json) {
        JsonParser parser = new JsonParser();
        return parser.parse(json).getAsJsonArray();
    }


    public static String getCompanyName(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("companyName").getAsString();
    }

    public static String getSymbol(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("symbol").getAsString();
    }

    public static String getLatestTime(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("latestTime").getAsString();
    }

    public static String getLatestPrice(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("latestPrice").getAsString();
    }

    public static String getChange(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("change").getAsString();
    }

    public static String getChangePercent(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("changePercent").getAsString();
    }

}
