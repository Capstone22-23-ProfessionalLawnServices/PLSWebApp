package com.professionallawnservices.app.helpers;

import java.util.ArrayList;

public class DataHelper {

    public static ArrayList<Object> combineArrayLists(ArrayList<Object> list1, ArrayList<Object> list2) {

        ArrayList<Object> combinedList = new ArrayList<Object>();

        if (list1 != null) {
            combinedList.addAll(list1);
        }

        if (list2 != null) {
            combinedList.addAll(list2);
        }

        return combinedList;

    }

}
