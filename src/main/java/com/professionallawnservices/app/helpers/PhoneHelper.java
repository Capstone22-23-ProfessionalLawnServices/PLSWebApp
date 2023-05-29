package com.professionallawnservices.app.helpers;

public class PhoneHelper {

    public static String formatPhoneNumber(String unformattedPhone) {
        String formattedPhone = unformattedPhone.replaceAll("\\D+", "");

        StringBuilder formattedNumber = new StringBuilder(formattedPhone);
        int length = formattedNumber.length();

        if (length == 10) {
            formattedNumber.insert(0, "(");
            formattedNumber.insert(length - 6, ")");
            formattedNumber.insert(length - 5, " ");
            formattedNumber.insert(length - 1, "-");
        }
        else {
            return null;
        }

        return formattedNumber.toString();
    }

}
