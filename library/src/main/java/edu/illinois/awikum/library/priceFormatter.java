package edu.illinois.awikum.library;

public class priceFormatter {
    public static String addOrRemoveZeros(final String price) {
        int decimalPos = price.indexOf('.');
        if (price.indexOf('E') > 0 && price.charAt(0) == '-') {
            return "-0.00";
        } else if (price.indexOf('E') > 0) {
            return "0.00";
        } else if (price.length() > decimalPos + 3) {
            return price.substring(0, decimalPos + 3);
        } else if (price.length() == decimalPos + 2) {
            return price + "0";
        } else {
            return price;
        }
    }


}
