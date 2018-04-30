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

    public static String getOpen(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("open").getAsString();
    }

    public static String getClose(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("close").getAsString();
    }

    public static String getHigh(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        if (quote.get("high").isJsonNull()) {
            return " --";
        } else {
            return quote.get("high").getAsString();
        }
    }

    public static String getLow(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        if (quote.get("low").isJsonNull()) {
            return " --";
        } else {
            return quote.get("low").getAsString();
        }
    }

    public static String getPreviousClose(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("previousClose").getAsString();
    }

    public static String getAverageVolume(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("avgTotalVolume").getAsString();
    }

    public static String get52weekHigh(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("week52High").getAsString();
    }

    public static String get52weekLow(final JsonObject companyData) {
        JsonObject quote = companyData.get("quote").getAsJsonObject();
        return quote.get("week52Low").getAsString();
    }

    public static JsonArray getNewsArray(final JsonObject newsData) {
        return newsData.get("news").getAsJsonArray();
    }

    public static String newsFormatter(final String news) {
        int hyphenIndex = news.indexOf("--");
        if (hyphenIndex > 0) {
            return newsFormatter(news.substring(hyphenIndex + 2));
        } else if (news.charAt(0) == ' ') {
            return newsFormatter(news.substring(1));
        }
        return news;
    }

    public static String newsDateFormatter(final String news) {
        int colonIndex = news.indexOf(":");
        return news.substring(0,colonIndex - 3);
    }


}
