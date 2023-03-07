package com.professionallawnservices.app.helpers;

public class ValidationHelpers {

    public static boolean isNullOrBlank(String string){

        if (string == null) {
            return true;
        }
        else if(string.isBlank()) {
            return true;
        }

        return false;
    }

    public static boolean isNull(Object object) {

        return object == null;
    }

}
